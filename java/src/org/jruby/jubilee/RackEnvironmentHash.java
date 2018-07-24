package org.jruby.jubilee;

import io.netty.handler.codec.http.HttpHeaders;
import org.vertx.java.core.MultiMap;
import org.jruby.*;
import org.jruby.jubilee.utils.RubyHelper;
import org.jruby.runtime.Block;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.builtin.IRubyObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RackEnvironmentHash extends RubyHash {

    public RackEnvironmentHash(final Ruby runtime, final MultiMap headers,
                               final Map<RubyString, RackEnvironment.RACK_KEY> rackKeyMap) {
        super(runtime);
        this.headers = headers;
        this.rackKeyMap = rackKeyMap;
        this.headerKeyMap = new HashMap<>();
    }

    public void lazyPut(RackEnvironment.RACK_KEY rackKey, final Object value, boolean usAscii) {
        rackValues[rackKey.ordinal()] = value;
        usAsciiValues[rackKey.ordinal()] = usAscii;
    }

    // synchronized probably isn't needed here since we create a new RackEnvironment
    // per request, but we can't guarantee users aren't spawning a new thread and
    // passing the env to that new thread
    synchronized void fillKey(final IRubyObject rubyKey) {
        if (!filledEntireHash) {
            if (rubyKey instanceof RubyString && !containsKey(rubyKey)) {
                if (!filledHeaderKeyMap) populateHeaderKeyMap();
                byte[] keyBytes = ((RubyString) rubyKey).getBytes();
                if (keyBytes.length > 5 && keyBytes[0] == 'H'
                        && keyBytes[1] == 'T' && keyBytes[2] == 'T'
                        && keyBytes[3] == 'P' && keyBytes[4] == '_') {

                    fillHeaderKey((RubyString) rubyKey);
                } else {
                    fillRackKey((RubyString) rubyKey);
                }
            }
        }
    }

    private synchronized void fillEntireHash() {
        if (!filledEntireHash) {
            for (RubyString key : rackKeyMap.keySet()) {
                fillRackKey(key);
            }

            for (String headerKey : headers.names()) {
                RubyString rubyKey = RubyHelper.toUsAsciiRubyString(getRuntime(), rackHeaderNameToBytes(headerKey));
                putHeaderKey(rubyKey, headerKey);
                fillHeaderKey(rubyKey, headerKey);
            }
            filledEntireHash = true;
        }
    }

    private synchronized void fillRackKey(final RubyString key) {
        RackEnvironment.RACK_KEY rackKey = rackKeyMap.get(key);
        if (rackKey != null) {
            Object value = rackValues[rackKey.ordinal()];
            if (value != null) {
                if (value instanceof String) {
                    boolean usAscii = usAsciiValues[rackKey.ordinal()];
                    RubyString rubyValue = usAscii ? RubyHelper.toUsAsciiRubyString(getRuntime(), (String) value) :
                            RubyHelper.toUnicodeRubyString(getRuntime(), (String) value);
                    put(key, rubyValue);
                } else {
                    put(key, value);
                }
                rackValues[rackKey.ordinal()] = null;
            }
        }
    }

    private synchronized void populateHeaderKeyMap() {
        for (String key : headers.names()) {
            byte[] rubyKeyBytes = rackHeaderNameToBytes(key);
            RubyString rubyKey = RubyHelper.toUsAsciiRubyString(getRuntime(), rubyKeyBytes);
            putHeaderKey(rubyKey, key);
        }
        this.filledHeaderKeyMap = true;
    }

    private void putHeaderKey(RubyString rubyKey, String key) {
        this.headerKeyMap.put(rubyKey, key);
    }

    private synchronized void fillHeaderKey(final RubyString rubyKey) {
        String headerKey = this.headerKeyMap.get(rubyKey);
        if (headerKey != null) fillHeaderKey(rubyKey, headerKey);
    }

    private synchronized void fillHeaderKey(final RubyString rubyKey, String key) {
        // RACK spec says not to create HTTP_CONTENT_TYPE or HTTP_CONTENT_LENGTH headers
        if (!key.equals(HttpHeaders.Names.CONTENT_TYPE) && !key.equals(HttpHeaders.Names.CONTENT_LENGTH)) {
            List<String> headerValues = headers.getAll(key);
            if (!headerValues.isEmpty()) {
                String headerValue = headerValues.get(0);
                int valueIndex = 1;
                while (valueIndex < headerValues.size()) {
                    headerValue += "\n" + headerValues.get(valueIndex++);
                }
                put(rubyKey, RubyHelper.toUnicodeRubyString(getRuntime(), headerValue));
            }
        }
    }

    private static byte[] rackHeaderNameToBytes(final String headerName) {
        // This is a more performant implemention of:
        // "HTTP_" + headerName.toUpperCase().replace('-', '_');
        byte[] envNameBytes = new byte[headerName.length() + 5];
        envNameBytes[0] = 'H';
        envNameBytes[1] = 'T';
        envNameBytes[2] = 'T';
        envNameBytes[3] = 'P';
        envNameBytes[4] = '_';
        for (int i = 5; i < envNameBytes.length; i++) {
            envNameBytes[i] = (byte) rackHeaderize(headerName.charAt(i - 5));
        }
        return envNameBytes;
    }

    private static char rackHeaderize(char c) {
        if (c == '-') {
            c = '_';
        }
        return toUpperCase(c);
    }

    private static char toUpperCase(char c) {
        if (c >= 'a' && c <= 'z') {
            c -= 32;
        }
        return c;
    }

    private final Object[] rackValues = new Object[RackEnvironment.NUM_RACK_KEYS];
    private final boolean[] usAsciiValues = new boolean[RackEnvironment.NUM_RACK_KEYS];
    private final MultiMap headers;
    private final Map<RubyString, RackEnvironment.RACK_KEY> rackKeyMap;
    private final Map<RubyString, String> headerKeyMap;
    private boolean filledEntireHash = false;
    private boolean filledHeaderKeyMap = false;


    //
    // Overridden RubyHash methods that operate on individual keys
    //
    @Override
    public IRubyObject op_aref(ThreadContext context, IRubyObject key) {
        fillKey(key);
        return super.op_aref(context, key);
    }

    @Override
    public IRubyObject fetch(ThreadContext context, IRubyObject key, Block block) {
        fillKey(key);
        return super.fetch(context, key, block);
    }

    @Override
    public IRubyObject fetch(ThreadContext context, IRubyObject key, IRubyObject _default, Block block) {
        fillKey(key);
        return super.fetch(context, key, _default, block);
    }

    @Override
    public RubyBoolean has_key_p(IRubyObject key) {
        fillKey(key);
        return super.has_key_p(key);
    }

    @Override
    public IRubyObject op_aset(ThreadContext context, IRubyObject key, IRubyObject value) {
        fillKey(key);
        return super.op_aset(context, key, value);
    }

    @Override
    public IRubyObject delete(ThreadContext context, IRubyObject key, Block block) {
        fillKey(key);
        return super.delete(context, key, block);
    }

    //
    // Overridden RubyHash methods that don't operate on individual keys so we
    // fill the entire hash
    //
    @Override
    public IRubyObject inspect(ThreadContext context) {
        fillEntireHash();
        return super.inspect(context);
    }

    @Override
    public IRubyObject inspect19(ThreadContext context) {
        fillEntireHash();
        return super.inspect19(context);
    }

    @Override
    public RubyFixnum rb_size() {
        fillEntireHash();
        return super.rb_size();
    }

    @Override
    public RubyBoolean empty_p() {
        fillEntireHash();
        return super.empty_p();
    }

    @Override
    public RubyArray to_a() {
        fillEntireHash();
        return super.to_a();
    }

    @Override
    public IRubyObject to_s(ThreadContext context) {
        fillEntireHash();
        return super.to_s(context);
    }

    @Override
    public RubyHash rehash() {
        fillEntireHash();
        return super.rehash();
    }

    @Override
    public IRubyObject op_equal(final ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.op_equal(context, other);
    }

    @Override
    public IRubyObject op_eql19(final ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.op_eql19(context, other);
    }

    @Override
    public RubyFixnum hash() {
        fillEntireHash();
        return super.hash();
    }

    @Override
    public RubyFixnum hash19() {
        fillEntireHash();
        return super.hash19();
    }

    @Override
    public IRubyObject fetch(ThreadContext context, IRubyObject[] args, Block block) {
        fillEntireHash();
        return super.fetch(context, args, block);
    }

    @Override
    public RubyBoolean has_value_p(ThreadContext context, IRubyObject expected) {
        fillEntireHash();
        return super.has_value_p(context, expected);
    }

    @Override
    public IRubyObject each(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.each(context, block);
    }

    @Override
    public IRubyObject each19(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.each19(context, block);
    }

    @Override
    public IRubyObject each_value(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.each_value(context, block);
    }

    @Override
    public IRubyObject each_key(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.each_key(context, block);
    }

    @Override
    public IRubyObject select_bang(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.select_bang(context, block);
    }

    @Override
    public IRubyObject keep_if(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.keep_if(context, block);
    }

    @Override
    public IRubyObject sort(ThreadContext context, Block block) {
        fillEntireHash();
        return super.sort(context, block);
    }

    @Override
    public IRubyObject index(ThreadContext context, IRubyObject expected) {
        fillEntireHash();
        return super.index(context, expected);
    }

    @Override
    public IRubyObject index19(ThreadContext context, IRubyObject expected) {
        fillEntireHash();
        return super.index19(context, expected);
    }

    @Override
    public IRubyObject key(ThreadContext context, IRubyObject expected) {
        fillEntireHash();
        return super.key(context, expected);
    }

    @Override
    public RubyArray keys() {
        fillEntireHash();
        return super.keys();
    }

    @Override
    public RubyArray rb_values() {
        fillEntireHash();
        return super.rb_values();
    }

    @Override
    public IRubyObject shift(ThreadContext context) {
        fillEntireHash();
        return super.shift(context);
    }

    @Override
    public IRubyObject select(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.select(context, block);
    }

    @Override
    public IRubyObject select19(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.select19(context, block);
    }

    @Override
    public IRubyObject delete_if(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.delete_if(context, block);
    }

    @Override
    public IRubyObject reject(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.reject(context, block);
    }

    @Override
    public IRubyObject reject_bang(final ThreadContext context, final Block block) {
        fillEntireHash();
        return super.reject_bang(context, block);
    }

    @Override
    public RubyHash rb_clear() {
        fillEntireHash();
        return super.rb_clear();
    }

    @Override
    public RubyHash invert(final ThreadContext context) {
        fillEntireHash();
        return super.invert(context);
    }

    @Override
    public RubyHash merge_bang(final ThreadContext context, final IRubyObject other, final Block block) {
        fillEntireHash();
        return super.merge_bang(context, other, block);
    }

    @Override
    public RubyHash merge_bang19(final ThreadContext context, final IRubyObject other, final Block block) {
        fillEntireHash();
        return super.merge_bang19(context, other, block);
    }

    @Override
    public RubyHash merge(ThreadContext context, IRubyObject other, Block block) {
        fillEntireHash();
        return super.merge(context, other, block);
    }

    @Override
    public RubyHash initialize_copy(ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.initialize_copy(context, other);
    }

    @Override
    public RubyHash initialize_copy19(ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.initialize_copy19(context, other);
    }

    @Override
    public RubyHash replace(final ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.replace(context, other);
    }

    @Override
    public RubyHash replace19(final ThreadContext context, IRubyObject other) {
        fillEntireHash();
        return super.replace19(context, other);
    }

    @Override
    public RubyArray values_at(ThreadContext context, IRubyObject[] args) {
        fillEntireHash();
        return super.values_at(context, args);
    }

    @Override
    public IRubyObject assoc(final ThreadContext context, final IRubyObject obj) {
        fillEntireHash();
        return super.assoc(context, obj);
    }

    @Override
    public IRubyObject rassoc(final ThreadContext context, final IRubyObject obj) {
        fillEntireHash();
        return super.rassoc(context, obj);
    }

    @Override
    public IRubyObject flatten(ThreadContext context) {
        fillEntireHash();
        return super.flatten(context);
    }

    @Override
    public IRubyObject flatten(ThreadContext context, IRubyObject level) {
        fillEntireHash();
        return super.flatten(context, level);
    }
/*
    @Override
    public IRubyObject getCompareByIdentity(ThreadContext context) {
        fillEntireHash();
        return super.getCompareByIdentity(context);
    }

    @Override
    public IRubyObject getCompareByIdentity_p(ThreadContext context) {
        fillEntireHash();
        return super.getCompareByIdentity_p(context);
    }
*/
    @Override
    public IRubyObject dup(ThreadContext context) {
        fillEntireHash();
        return super.dup(context);
    }

    @Override
    public IRubyObject rbClone(ThreadContext context) {
        fillEntireHash();
        return super.rbClone(context);
    }
}
