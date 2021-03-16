let _ = require('lodash');
let path = require('path');
let webpack = require('webpack');
let ExtractTextPlugin = require('extract-text-webpack-plugin');
let UglifyJSPlugin = require('uglifyjs-webpack-plugin');

let extractCss = new ExtractTextPlugin({
    filename: 'css/vendor.css'
});

let getEntry = function () {
    return {
        'main': './js/main.js',
        'login': './js/login.js',
        'news': './js/newsWebview.js',
        'vendor': './js/vendor.js'
    }
};

let getLoaders = function (isProduction) {
    let javascriptLoader = {
        test: /\.js$/,
        exclude: /(node_modules|vue\/src)/,
        use: {
            loader: 'babel-loader',
            options: {
                presets: ['env']
            }
        }
    };

    let vueLoader = {
        test: /\.vue$/,
        loader: 'vue-loader',
        options: {
            loaders: {
                scss: 'vue-style-loader!css-loader!sass-loader', // <style lang="scss">
            }
        }

    };

    let fontLoader = {
        test: /\.(ttf|eot|woff|svg|woff2)$/,
        loader: 'file-loader?name=[name].[ext]&publicPath=/static/&outputPath=fonts/',
        exclude: [
            path.join(__dirname, "assets/js/icons"),
        ]
    };

    return [
        javascriptLoader,
        vueLoader,
        {
            test: /\.scss$/,
            use: extractCss.extract({
                use: [
                    {
                        loader: "css-loader",
                        options: {
                            sourceMap: !isProduction,
                            minimize: isProduction
                        }
                    },
                    {
                        loader: "sass-loader",
                        options: {
                            sourceMap: !isProduction,
                            minimize: isProduction
                        }
                    }
                ]
            })
        },
        {
            test: /\.svg$/,
            loader: 'svg-sprite-loader',
            include: [path.join(__dirname, "assets/js/icons")],
            options: {
                symbolId: 'icon-[name]'
            }
        },
        fontLoader
    ]
};

let getPlugins = function (isProduction) {
    let defaultPlugins = [
        new webpack.ProvidePlugin({
            jQuery: 'jquery',
            $: 'jquery',
            jquery: 'jquery',
            _: 'lodash'
        }),
        new webpack.optimize.CommonsChunkPlugin({name: 'vendor'}),
        extractCss,
        new webpack.NoEmitOnErrorsPlugin()
    ];
    if (isProduction) {
        plugins = _.union(defaultPlugins, [
            new UglifyJSPlugin({
                sourceMap: false
            }),
            new webpack.DefinePlugin({
                'process.env': {
                    NODE_ENV: '"production"'
                }
            })
        ]);
    } else {
        plugins = _.union(defaultPlugins, [
            new webpack.DefinePlugin({
                'process.env': {
                    NODE_ENV: '"development"'
                }
            })
        ]);
    }
    return plugins;
};


module.exports = function (isProduction) {
    return {
        context: path.join(__dirname, 'assets'),
        entry: getEntry(),
        output: {
            path: path.join(__dirname, 'src/main/webapp/static'),
            filename: 'js/[name].js'
        },
        devtool: isProduction ? '' : 'inline-source-map',
        watch: !isProduction,
        profile: true,
        cache: true,
        module: {
            rules: getLoaders(isProduction)
        },
        plugins: getPlugins(isProduction),
        resolve: {
            alias: {
                'jquery': path.join(__dirname, 'node_modules/jquery/src/jquery'),
                'vue': 'vue/dist/vue.common.js',
                '@': path.join(__dirname, 'assets/js')
            }
        }
    }
};