'use strict';
var path = require("path");
var del = require('del');
var webpack = require('webpack');
var gulp = require('gulp');
var gutil = require('gulp-util');
var plumber = require('gulp-plumber');
var newer = require('gulp-newer');
var logger = require('gulp-logger');

var exec = require('child_process').exec;

var compileLogger = require('./assets/lib/compileLogger');
var handleErrors = require('./assets/lib/handleErrors');
var isProductionMode = process.env.ASSETS_ENV === 'PRODUCTION';


var config = {
    clean: ['src/main/webapp/static/*', '!src/main/webapp/static/UEditor'],
    images: {src: 'assets/images/**/*', dest: 'src/main/webapp/static/images/'}
};

gulp.task('clean:coverage', function () {
    return del('coverage', {force: true});
});

gulp.task('clean', function () {
    return del(config.clean, {force: true});
});

gulp.task('webpack', function (done) {
    webpack(require('./webpack.config.js')(isProductionMode), function (err, stats) {
        compileLogger(err, stats);
        done();
    });
});

var compileHash = null;
gulp.task('watch:webpack', function (done) {
    webpack(require('./webpack.config.js')(isProductionMode))
        .watch(200, function (err, stats) {
            if (compileHash !== stats.hash) {
                compileHash = stats.hash;
                compileLogger(err, stats);
                done();
            }
        });
});

gulp.task('images', function () {
    return gulp.src(config.images.src)
        .pipe(plumber(handleErrors))
        .pipe(newer(config.images.dest))
        .pipe(gulp.dest(config.images.dest));
});

gulp.task('watch:images', function (done) {
    gulp.watch(config.images.src, gulp.series('images'));
    done();
});


gulp.task(
    'watch',
    gulp.series(
        gulp.parallel('watch:images', 'watch:webpack')
    )
);


gulp.task('default',
    gulp.series(
        'clean',
        gulp.parallel('images'),
        'watch'
    )
);

gulp.task('prod',
    gulp.series(
        'clean',
        gulp.parallel('images'),
        'webpack'
    )
);