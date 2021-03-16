import Vue from "vue";
import Resource from "vue-resource";
import Element from 'element-ui';
import VueProgressBar from 'vue-progressbar';
import App from './App.vue';
import router from './router';

import * as filters from './filters'; // global filters

import './icons'; // icon

import userService from './api/user.js';
import houseService from './api/house.js';
import spiderService from './api/spider.js';
import staffService from './api/staff.js';
import storeService from './api/store.js';
import resoldHouseService from './api/resoldHouse.js';
import newsService from './api/news.js';
import customerService from './api/customer.js';
import locationService from './api/location.js';
import uploadService from './api/upload.js';
import {roleType} from './utils/constants.js';

Vue.prototype.$eventHub = new Vue();

let initFilters = function () {
    _.forOwn(filters, (value, key) => {
        Vue.filter(key, value)
    })
};

let initPlugins = function () {
    Vue.use(Resource);
    Vue.use(Element, {
        size: 'medium', // set element-ui default size
    });
    Vue.use(VueProgressBar, {
        color: 'rgb(143, 255, 199)',
        failedColor: 'red',
        height: '2px'
    });
};

let initHttpInterceptors = function () {
    Vue.http.options.emulateJSON = true
    Vue.http.interceptors.push((request, next) => {
        next((response) => {
            if (response.status === 401) {
                window.location.href = "/login"
            }
        })
    })
};

let initApi = function () {
    userService.init(Vue);
    houseService.init(Vue);
    resoldHouseService.init(Vue);
    locationService.init(Vue);
    uploadService.init(Vue);
    storeService.init(Vue);
    staffService.init(Vue);
    newsService.init(Vue);
    spiderService.init(Vue);
    customerService.init(Vue);
};

let initCurrentUser = function() {
    return userService.getCurrentUser((response) => {
        Vue.prototype.$currentUser = response.body;
    })
};

let initVue = function () {
    new Vue({
        el: '#app',
        router,
        components: { App },
        created() {
            this.initRole()
            if (window.localStorage.getItem("sideBar") == null) {
                window.localStorage.setItem("sideBar", true)
            }
        },
        methods: {
            initRole() {
                _.each(this.$currentUser.roles, (role) => {
                    switch (role.toUpperCase()) {
                        case roleType.ADMIN: this.$currentUser.isAdmin = true; break;
                        case roleType.AGENT: this.$currentUser.isAgent = true; break;
                        case roleType.NORMAL_USER: this.$currentUser.isNormalUser = true; break;
                        case roleType.STORE_ADMIN: this.$currentUser.isStoreAdmin = true; break;
                        case roleType.WECHAT_USER: this.$currentUser.isWechatUser = true; break;
                    }
                })
            }
        }
    })
};

initPlugins();
initFilters();
initHttpInterceptors();
initApi();
initCurrentUser().then(initVue);