module.exports = function(grunt) {
	var oPackage = grunt.file.readJSON('package.json'),
		sTarget = grunt.option('target') || '',
		sJar = grunt.option('jar');

	grunt.initConfig({
		"bower-install-simple": {
			install: {
				interactive: false
			}
		},
		"clean": ['dist'],
		"compress": {
			dist: {
				options: {
					archive: 'sbwo.zip'
				},
				files: [{
					expand: true,
					dot: true,
					cwd: 'dist/',
					src: '**/*',
					dest: '.'
				}]
			}
		},
		"openui5_preload": {
			json: {
				options: {
					resources: {
						cwd: 'web/private/',
						prefix: 'spet/sbwo/web'
					},
					dest: 'dist/web/private',
					compatVersion: '1.38'
				},
				components: 'spet/sbwo/web'
			},
			js: {
				options: {
					resources: {
						cwd: 'web/private/',
						prefix: 'spet/sbwo/web'
					},
					dest: 'dist/web/private',
				},
				components: 'spet/sbwo/web'
			}
		},
		"uglify": {
			dist: {
				files: [{
					expand: true,
					dot: true,
					src: ['web/public/**/*.js', 'web/private/**/*.js', 'web/public/*.js', 'web/private/*.js'],
					dest: 'dist/'
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
					cwd: 'web',
					src: ['public/**', 'private/**'],
					dest: 'dist/web/',
					rename: function(dest, src) {
						if (src.endsWith(".js")) {
							return dest + src.replace('.js','-dbg.js');
						} else {
							return dest + src;
						}
					}
				}, {
					src: 'target/' + sJar,
					dest: 'dist/sbwo.jar'
				}, {
					expand: true,
					cwd: 'target',
					src: 'lib/*.jar',
					dest: 'dist'
				}, {
					src: 'data/sbwo.mv.db',
					dest: 'dist/data/sbwo.mv.db'
				}]
			},
			install: {
				files: [{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-sap.ui.core/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-sap.ui.layout/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-sap.ui.unified/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-themelib_sap_belize/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-themelib_sap_bluecrystal/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-sap.m/resources",
					src: "**",
					dest: sTarget + "web/lib/openui5"
				},{
					expand: true,
					dot: true,
					cwd: "bower_components/openui5-googlemaps/openui5/googlemaps",
					src: "**",
					dest: sTarget + "web/lib/googlemaps"
				}]
			}
		}
	});

	grunt.loadNpmTasks("grunt-bower-install-simple");
	grunt.loadNpmTasks("grunt-contrib-copy");
	grunt.loadNpmTasks('grunt-contrib-uglify');
	grunt.loadNpmTasks('grunt-openui5');
	grunt.loadNpmTasks('grunt-contrib-compress');
	grunt.loadNpmTasks('grunt-mkdir');
	grunt.loadNpmTasks('grunt-contrib-clean');
	
	grunt.registerTask('install', ['bower-install-simple:install', 'copy:install']);
	grunt.registerTask('default', ['clean', 'install', 'openui5_preload', 'uglify', 'copy:dist', 'mkdir', 'compress']);
};