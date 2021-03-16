import Vue from "vue";
import Resource from "vue-resource";
import Element from 'element-ui';
import NewsWebview from "./views/webview/newsWebview.vue";
import newsService from './api/newsWebview.js';
import uploadService from './api/upload.js';
import * as filters from './filters';
import './icons';

Vue.use(Resource);
Vue.use(Element);
newsService.init(Vue);
uploadService.init(Vue);
Vue.http.options.emulateJSON = true;

let initFilters = function () {
    _.forOwn(filters, (value, key) => {
        Vue.filter(key, value)
    })
};

new Vue({
    el: '#app',
    components: {
        'news': NewsWebview
    }
});

initFilters();
