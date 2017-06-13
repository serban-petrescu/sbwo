module.exports = function(grunt) {
	var oPackage = grunt.file.readJSON("package.json"),
		sTarget = grunt.option("target") || "",
		sJar = grunt.option("jar");

	var ES8_FILES_SRC = ['web/**/*.es6.js', "!web/lib/**"];
	var UI5_SAP_LIBS = ["sap.ui.core", "sap.ui.layout", "sap.ui.unified", "sap.m", "themelib_sap_belize", "themelib_sap_bluecrystal"];
	
	function renameWithoutEs6(sSrc) {
		return sSrc.substring(0, sSrc.length - ".es6.js".length) + ".js";
	}
	
	function makeUI5CopyConfig(sLibrary) {
		return {
			expand: true,
			dot: true,
			cwd: "bower_components/openui5-" + sLibrary + "/resources",
			src: "**",
			dest: sTarget + "web/lib/openui5"
		};
	}
	
	grunt.config.init({
		"babel": {
			options: {
				sourceMap: true
			},
			all: {
				files: [{
					expand: true,
					src: ES8_FILES_SRC,
					rename: function (sDest, sSrc) {
						return renameWithoutEs6((sDest || "") + sSrc);
					}
				}]
			},
			single: {
				files: [{
					src: "",
					dest: ""
				}]
			}
		},
		"watch": {
            options: {
                nospawn: true
            },
			babel: {
				files: ES8_FILES_SRC,
				tasks: ['babel:single'],
				options: {
				  event: ['added', 'changed'],
				},
			}
		},
		"bower-install-simple": {
			install: {
				interactive: false
			}
		},
		"clean": ["dist"],
		"compress": {
			dist: {
				options: {
					archive: "sbwo.zip"
				},
				files: [{
					expand: true,
					dot: true,
					cwd: "dist/",
					src: "**/*",
					dest: "."
				}]
			}
		},
		"openui5_preload": {
			json: {
				options: {
					resources: {
						cwd: "web/private/",
						prefix: "spet/sbwo/web"
					},
					dest: "dist/web/private",
					compatVersion: "1.38"
				},
				components: "spet/sbwo/web"
			},
			js: {
				options: {
					resources: {
						cwd: "web/private/",
						prefix: "spet/sbwo/web"
					},
					dest: "dist/web/private",
				},
				components: "spet/sbwo/web"
			}
		},
		"uglify": {
			dist: {
				files: [{
					expand: true,
					dot: true,
					src: ["web/public/**/*.js", "web/private/**/*.js", "web/public/*.js", "web/private/*.js"],
					dest: "dist/"
				}]
			}
		},
		"mkdir": {
			dist: {
				options: {
					create: ["dist/log", "dist/backup"]
				}
			}
		},
		"copy": {
			dist: {
				files: [{
					expand: true,
					dot: true,
					cwd: "web",
					src: ["public/**", "private/**"],
					dest: "dist/web/",
					rename: function(dest, src) {
						if (src.endsWith(".js")) {
							return dest + src.replace(".js","-dbg.js");
						} else {
							return dest + src;
						}
					}
				}, {
					src: "target/" + sJar,
					dest: "dist/sbwo.jar"
				}, {
					expand: true,
					cwd: "target",
					src: "lib/*.jar",
					dest: "dist"
				}, {
					src: "data/sbwo.mv.db",
					dest: "dist/data/sbwo.mv.db"
				}]
			},
			install: {
				files: UI5_SAP_LIBS.map(makeUI5CopyConfig).concat([{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-googlemaps/openui5/googlemaps",
					src: "**",
					dest: sTarget + "web/lib/googlemaps"
				}, {
					expand: true,
					dot: true,
					cwd: "node_modules/babel-polyfill/dist",
					src: "*.js",
					dest: sTarget + "web/lib/babel"
				}])
			}
		}
	});

	grunt.event.on('watch', function(action, filepath) {
		grunt.config.merge({
			babel: {
				single: {
					files: [{
						src: filepath,
						dest: renameWithoutEs6(filepath)
					}]
				}
			}
		});
	});
	
	grunt.loadNpmTasks("grunt-bower-install-simple");
	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks("grunt-contrib-uglify");
	grunt.loadNpmTasks("grunt-openui5");
	grunt.loadNpmTasks("grunt-contrib-compress");
	grunt.loadNpmTasks("grunt-mkdir");
	grunt.loadNpmTasks("grunt-contrib-clean");
	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-babel');
	
	grunt.registerTask("install", ["bower-install-simple:install", "copy:install"]);
	grunt.registerTask("default", ["clean", "install", "babel:all", "openui5_preload", "uglify", "copy:dist", "mkdir", "compress"]);
};