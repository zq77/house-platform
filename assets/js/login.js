import Vue from "vue";
import Resource from "vue-resource";
import VueProgressBar from 'vue-progressbar';
import Element from 'element-ui';
import Login from "./views/login/index.vue";
import userService from './api/user.js';
import './icons';

Vue.use(Resource);
Vue.use(Element);
Vue.use(VueProgressBar);
userService.init(Vue);
Vue.http.options.emulateJSON = true;

new Vue({
    el: '#app',
    components: {
        'login': Login
    }
});
