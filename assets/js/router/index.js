import Vue from "vue"
import Router from "vue-router"
import dashboard from "@/views/dashboard/index.vue"
import newHouse from "@/views/newHouse/index.vue"
import newHouseList from "@/views/newHouse/list.vue"
import newHouseEdit from "@/views/newHouse/edit.vue"
import newHouseDetail from "@/views/newHouse/detail.vue"
import resoldHouse from "@/views/resoldHouse/index.vue"
import resoldHouseList from "@/views/resoldHouse/list.vue"
import resoldHouseEdit from "@/views/resoldHouse/edit.vue"

import storeManage from "@/views/storeManage/index.vue"
import storeManageList from "@/views/storeManage/list.vue"
import storeManageEdit from "@/views/storeManage/edit.vue"

import staffManage from "@/views/staffManage/index.vue"
import staffManageList from "@/views/staffManage/list.vue"
import staffManageEdit from "@/views/staffManage/edit.vue"

import customerManage from "@/views/customerManage/index.vue"
import customerManageList from "@/views/customerManage/list.vue"
import customerManageEdit from "@/views/customerManage/edit.vue"

import newsManage from "@/views/newsManage/index.vue"
import newsManageList from "@/views/newsManage/list.vue"
import newsManageEdit from "@/views/newsManage/edit.vue"

import newsAdManage from "@/views/newsAdManage/index.vue"
import newsAdManageList from "@/views/newsAdManage/list.vue"
import newsAdManageGrab from "@/views/newsAdManage/grab.vue"
import newsAdEdit from '@/views/newsAdManage/newsAdEdit.vue'

import newHouseGrab from "@/views/newHouseGrab/index.vue"
import newHouseGrabList from "@/views/newHouseGrab/list.vue"
import resoldHouseGrab from "@/views/resoldHouseGrab/index.vue"
import resoldHouseGrabList from "@/views/resoldHouseGrab/list.vue"

import myCenterIndex from "@/views/myCenter/index.vue"
import myCenterEdit from "@/views/myCenter/edit.vue"
import updatePwdView from "@/views/myCenter/updatePwd.vue"

Vue.use(Router);
const homeRoute = {
    name: 'homeRoute',
    path: '/',
    component: dashboard,
    meta: {title: "今日好房", label: 'homeRoute'}
};

const myCenter = {
    path: '/my-center',
    component: myCenterIndex,
    meta: {title: "个人中心", label: 'myCenter'},
    children:[
        {path: '/', component: myCenterEdit}
    ]
};

const updatePwd = {
    path: '/update-pwd',
    component: myCenterIndex,
    meta: {title: "密码修改", label: 'updatePwd'},
    children:[
        {path: '/', component: updatePwdView}
    ]
};

const newHouseRoute = {
    path: '/new-house',
    component: newHouse,
    meta: {title: "新房管理", label: 'newHouse'},
    children:[
        {path: '/', component: newHouseList},
        {path: 'add', component: newHouseEdit, meta: {title: "新增", label: 'newHouse'}},
        {path: ':houseId', component: newHouseDetail, meta: {title: "详情", label: 'newHouse'}},
        {path: ':houseId/edit', component: newHouseEdit, meta: {title: "编辑", label: 'newHouse'}},
    ]
};

const newHouseGrabRoute = {
    path: '/new-house-grab',
    component: newHouseGrab,
    meta: {title: "新房采集", label: 'newHouseGrab'},
    children:[
        {path: '/', component: newHouseGrabList},
        {path: 'add', component: newHouseEdit, meta: {title: "新增", label: 'newHouseGrab'}},
        {path: ':houseId', component: newHouseDetail, meta: {title: "详情", label: 'newHouseGrab'}},
        {path: ':houseId/edit', component: newHouseEdit, meta: {title: "编辑", label: 'newHouseGrab'}},
    ]
};

const resoldHouseRoute = {
    path: '/resold-house',
    component: resoldHouse,
    meta: {title: "二手房管理", label: 'resoldHouse'},
    children:[
        {path: '/', component: resoldHouseList},
        {path: 'add', component: resoldHouseEdit, meta: {title: "新增", label: 'resoldHouse'}},
        {path: ':houseId', component: resoldHouseEdit, meta: {title: "编辑", label: 'resoldHouse'}},
    ]
};

const resoldHouseGrabRoute = {
    path: '/resold-house-grab',
    component: resoldHouseGrab,
    meta: {title: "二手房采集", label: 'resoldHouseGrab'},
    children:[
        {path: '/', component: resoldHouseGrabList},
        {path: 'add', component: resoldHouseEdit, meta: {title: "新增", label: 'resoldHouseGrab'}},
        {path: ':houseId', component: resoldHouseEdit, meta: {title: "编辑", label: 'resoldHouseGrab'}},
    ]
};

const storeManageRoute = {
    path: '/store',
    component: storeManage,
    meta: {title: "门店管理", label: 'storeManage'},
    children:[
        {path: '/', component: storeManageList},
        {path: 'add', component: storeManageEdit, meta: {title: "新增", label: 'storeManage'}},
        {path: ':storeId', component: storeManageEdit, meta: {title: "编辑", label: 'storeManage'}},
    ]
};

const userManageRoute = {
    path: '/staffs',
    component: staffManage,
    meta: {title: "人员管理", label: 'staffManage'},
    children:[
        {path: '/', component: staffManageList},
        {path: 'add', component: staffManageEdit, meta: {title: "新增", label: 'staffManage'}},
        {path: ':staffId', component: staffManageEdit, meta: {title: "编辑", label: 'staffManage'}},
    ]
};

const customerManageRoute = {
    path: '/customer',
    component: customerManage,
    meta: {title: "客源管理", label: 'customerManage'},
    children:[
        {path: '/', component: customerManageList},
        {path: 'add', component: customerManageEdit, meta: {title: "新增", label: 'customerManage'}},
        {path: ':customerId', component: customerManageEdit, meta: {title: "编辑", label: 'customerManage'}},
    ]
};

const newsManageRoute = {
    path: '/news',
    component: newsManage,
    meta: {title: "资讯管理", label: 'newsManage'},
    children:[
        {path: '/', component: newsManageList},
        {path: 'add', component: newsManageEdit, meta: {title: "新增", label: 'newsManage'}},
        {path: ':newsId', component: newsManageEdit, meta: {title: "编辑", label: 'newsManage'}},
    ]
};

const newsAdManageRoute = {
    path: '/newsAd',
    component: newsAdManage,
    meta: {title: "易推广", label: 'newsAdManage'},
    children:[
        {path: '/', component: newsAdManageList},
        {path: 'add', component: newsAdManageGrab, meta: {title: "热文植入", label: 'newsAdManage'}},
        {path: 'template', component: newsAdEdit, meta: {title: "广告制作", label: 'newsAdManage'}},
        {path: ':newsId', component: newsManageEdit, meta: {title: "编辑", label: 'newsAdManage'}},
    ]
};

export default new Router({
    scrollBehavior: () => ({ y: 0 }),
    routes: [
        homeRoute,
        myCenter,
        updatePwd,
        newHouseRoute,
        resoldHouseRoute,
        storeManageRoute,
        userManageRoute,
        customerManageRoute,
        newsManageRoute,
        newsAdManageRoute,
        newHouseGrabRoute,
        resoldHouseGrabRoute
    ]
})