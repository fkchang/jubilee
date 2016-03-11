# Generated by jeweler
# DO NOT EDIT THIS FILE DIRECTLY
# Instead, edit Jeweler::Tasks in Rakefile, and run 'rake gemspec'
# -*- encoding: utf-8 -*-
# stub: jubilee 3.0.0.beta1 java lib

Gem::Specification.new do |s|
  s.name = "jubilee"
  s.version = "3.0.0.beta1"
  s.platform = "java"

  s.required_rubygems_version = Gem::Requirement.new("> 1.3.1") if s.respond_to? :required_rubygems_version=
  s.require_paths = ["lib"]
  s.authors = ["Isaiah Peng"]
  s.date = "2016-03-11"
  s.description = "Jubilee is a rack server for JRuby built upon the high performance Vertx platform. It provides the best features of Vertx such as EventBus, SharedData, and clustering."
  s.email = "issaria@gmail.com"
  s.executables = ["jubilee", "jubilee_d"]
  s.extra_rdoc_files = [
    "LICENSE.txt",
    "README.md"
  ]
  s.files = [
    ".ruby-version",
    ".travis.yml",
    "CHANGELOG",
    "Gemfile",
    "Gemfile.lock",
    "Guardfile",
    "KNOWN_ISSUES",
    "LICENSE.txt",
    "README.md",
    "ROADMAP",
    "Rakefile",
    "bin/jubilee",
    "bin/jubilee_d",
    "examples/chatapp/Gemfile",
    "examples/chatapp/Gemfile.lock",
    "examples/chatapp/README.md",
    "examples/chatapp/app.rb",
    "examples/chatapp/config.ru",
    "examples/chatapp/public/assets/javascripts/application.js",
    "examples/chatapp/public/assets/javascripts/jquery.js",
    "examples/chatapp/public/assets/javascripts/sockjs-0.3.4.min.js",
    "examples/chatapp/public/assets/javascripts/vertxbus.js",
    "examples/chatapp/public/assets/stylesheets/application.css",
    "examples/client/sockjs-0.3.4.min.js",
    "examples/client/vertxbus.js",
    "examples/jubilee.conf.rb",
    "examples/keystore.jks",
    "examples/shims_example/log/development.log",
    "jubilee.gemspec",
    "lib/jubilee.rb",
    "lib/jubilee/application.rb",
    "lib/jubilee/cli.rb",
    "lib/jubilee/configuration.rb",
    "lib/jubilee/const.rb",
    "lib/jubilee/jubilee.jar",
    "lib/jubilee/response.rb",
    "lib/jubilee/server.rb",
    "lib/jubilee/version.rb",
    "lib/rack/handler/jubilee.rb",
    "pom.xml",
    "spec/apps/rack/basic/config.ru",
    "spec/apps/rails4/basic/.gitignore",
    "spec/apps/rails4/basic/Gemfile",
    "spec/apps/rails4/basic/Gemfile.lock",
    "spec/apps/rails4/basic/README.rdoc",
    "spec/apps/rails4/basic/Rakefile",
    "spec/apps/rails4/basic/app/assets/images/.keep",
    "spec/apps/rails4/basic/app/assets/images/rails.png",
    "spec/apps/rails4/basic/app/assets/javascripts/application.js",
    "spec/apps/rails4/basic/app/assets/stylesheets/application.css",
    "spec/apps/rails4/basic/app/controllers/application_controller.rb",
    "spec/apps/rails4/basic/app/controllers/concerns/.keep",
    "spec/apps/rails4/basic/app/controllers/reloader_controller.rb",
    "spec/apps/rails4/basic/app/controllers/reloader_controller.rb.erb",
    "spec/apps/rails4/basic/app/controllers/root_controller.rb",
    "spec/apps/rails4/basic/app/helpers/application_helper.rb",
    "spec/apps/rails4/basic/app/mailers/.keep",
    "spec/apps/rails4/basic/app/models/.keep",
    "spec/apps/rails4/basic/app/models/concerns/.keep",
    "spec/apps/rails4/basic/app/views/layouts/application.html.erb",
    "spec/apps/rails4/basic/app/views/reloader/index.html.erb",
    "spec/apps/rails4/basic/app/views/root/index.html.erb",
    "spec/apps/rails4/basic/app/views/root/streaming.html.erb",
    "spec/apps/rails4/basic/bin/bundle",
    "spec/apps/rails4/basic/bin/rails",
    "spec/apps/rails4/basic/bin/rake",
    "spec/apps/rails4/basic/config.ru",
    "spec/apps/rails4/basic/config/application.rb",
    "spec/apps/rails4/basic/config/boot.rb",
    "spec/apps/rails4/basic/config/database.yml",
    "spec/apps/rails4/basic/config/environment.rb",
    "spec/apps/rails4/basic/config/environments/development.rb",
    "spec/apps/rails4/basic/config/environments/production.rb",
    "spec/apps/rails4/basic/config/environments/test.rb",
    "spec/apps/rails4/basic/config/initializers/backtrace_silencers.rb",
    "spec/apps/rails4/basic/config/initializers/filter_parameter_logging.rb",
    "spec/apps/rails4/basic/config/initializers/inflections.rb",
    "spec/apps/rails4/basic/config/initializers/mime_types.rb",
    "spec/apps/rails4/basic/config/initializers/secret_token.rb",
    "spec/apps/rails4/basic/config/initializers/session_store.rb",
    "spec/apps/rails4/basic/config/initializers/wrap_parameters.rb",
    "spec/apps/rails4/basic/config/locales/en.yml",
    "spec/apps/rails4/basic/config/routes.rb",
    "spec/apps/rails4/basic/db/seeds.rb",
    "spec/apps/rails4/basic/lib/assets/.keep",
    "spec/apps/rails4/basic/lib/tasks/.keep",
    "spec/apps/rails4/basic/public/404.html",
    "spec/apps/rails4/basic/public/422.html",
    "spec/apps/rails4/basic/public/500.html",
    "spec/apps/rails4/basic/public/favicon.ico",
    "spec/apps/rails4/basic/public/robots.txt",
    "spec/apps/rails4/basic/public/some_page.html",
    "spec/apps/rails4/basic/test/controllers/.keep",
    "spec/apps/rails4/basic/test/fixtures/.keep",
    "spec/apps/rails4/basic/test/helpers/.keep",
    "spec/apps/rails4/basic/test/integration/.keep",
    "spec/apps/rails4/basic/test/mailers/.keep",
    "spec/apps/rails4/basic/test/models/.keep",
    "spec/apps/rails4/basic/test/test_helper.rb",
    "spec/apps/rails4/basic/vendor/assets/javascripts/.keep",
    "spec/apps/rails4/basic/vendor/assets/stylesheets/.keep",
    "spec/apps/sinatra/basic/Gemfile",
    "spec/apps/sinatra/basic/Gemfile.lock",
    "spec/apps/sinatra/basic/basic.rb",
    "spec/apps/sinatra/basic/config.ru",
    "spec/apps/sinatra/basic/public/some_page.html",
    "spec/apps/sinatra/basic/views/index.erb",
    "spec/apps/sinatra/basic/views/posted.haml",
    "spec/apps/sinatra/basic/views/poster.haml",
    "spec/apps/sinatra/basic/views/request_mapping.haml",
    "spec/integration/basic_rack_spec.rb",
    "spec/integration/basic_rails4_spec.rb",
    "spec/integration/basic_sinatra_spec.rb",
    "spec/spec_helper.rb",
    "src/main/java/jubilee/JubileeService.java",
    "src/main/java/org/jruby/jubilee/Const.java",
    "src/main/java/org/jruby/jubilee/JubileeVerticle.java",
    "src/main/java/org/jruby/jubilee/RackApplication.java",
    "src/main/java/org/jruby/jubilee/RackEnvironment.java",
    "src/main/java/org/jruby/jubilee/RackEnvironmentHash.java",
    "src/main/java/org/jruby/jubilee/RackInput.java",
    "src/main/java/org/jruby/jubilee/RackResponse.java",
    "src/main/java/org/jruby/jubilee/RubyHttpServerResponse.java",
    "src/main/java/org/jruby/jubilee/RubyNetSocket.java",
    "src/main/java/org/jruby/jubilee/RubyPlatformManager.java",
    "src/main/java/org/jruby/jubilee/impl/RubyIORackInput.java",
    "src/main/java/org/jruby/jubilee/impl/RubyNullIO.java",
    "src/main/java/org/jruby/jubilee/utils/RubyHelper.java",
    "src/main/java/org/jruby/jubilee/vertx/JubileeVertx.java",
    "src/main/resources/META-INF/services/org.vertx.java.core.spi.cluster.ClusterManagerFactory",
    "src/main/resources/default-cluster.xml",
    "test/.ruby-version",
    "test/apps/app.rb",
    "test/apps/checker.ru",
    "test/apps/chunked.ru",
    "test/apps/config.ru",
    "test/apps/content_length.ru",
    "test/apps/hex.ru",
    "test/apps/hijack.ru",
    "test/apps/hijack2.ru",
    "test/apps/huge.ru",
    "test/apps/method_override.ru",
    "test/apps/overwrite_check.ru",
    "test/apps/persistent.rb",
    "test/apps/persistent.ru",
    "test/apps/rack_crasher.ru",
    "test/apps/rack_input.ru",
    "test/apps/self_chunked.ru",
    "test/apps/sha1.ru",
    "test/apps/simple.ru",
    "test/apps/url_scheme.ru",
    "test/jubilee/test_cli.rb",
    "test/jubilee/test_configuration.rb",
    "test/jubilee/test_hijack.rb",
    "test/jubilee/test_persistent.rb",
    "test/jubilee/test_rack_server.rb",
    "test/jubilee/test_server.rb",
    "test/jubilee/test_upload.rb",
    "test/test_helper.rb"
  ]
  s.homepage = "http://isaiah.github.io/jubilee"
  s.licenses = ["MIT"]
  s.rubygems_version = "2.4.8"
  s.summary = "More than a server for rack applications."

  if s.respond_to? :specification_version then
    s.specification_version = 4

    if Gem::Version.new(Gem::VERSION) >= Gem::Version.new('1.2.0') then
      s.add_runtime_dependency(%q<rack>, [">= 1.4.1"])
      s.add_runtime_dependency(%q<spoon>, ["~> 0.0.4"])
      s.add_development_dependency(%q<jeweler>, ["~> 1.8.7"])
    else
      s.add_dependency(%q<rack>, [">= 1.4.1"])
      s.add_dependency(%q<spoon>, ["~> 0.0.4"])
      s.add_dependency(%q<jeweler>, ["~> 1.8.7"])
    end
  else
    s.add_dependency(%q<rack>, [">= 1.4.1"])
    s.add_dependency(%q<spoon>, ["~> 0.0.4"])
    s.add_dependency(%q<jeweler>, ["~> 1.8.7"])
  end
end

