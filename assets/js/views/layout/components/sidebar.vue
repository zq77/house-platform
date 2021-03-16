<template>
    <el-scrollbar wrapClass="scrollbar-wrapper">
        <el-menu mode="vertical"
                :show-timeout="200"
                :default-active="$route.meta.label"
                :collapse="isCollapse"
                background-color="#304156"
                text-color="#bfcbd9"
                active-text-color="#409EFF">
            <div class="menu-wrapper">
            	<router-link to="/" >
                    <el-menu-item index="homeRoute" class="submenu-title-noDropdown">
                        <i class="iconfont icon-home"></i>
                        <span slot="title">今日好房</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/store" v-if="showStore">
                    <el-menu-item index="storeManage" class="submenu-title-noDropdown">
                        <i class="iconfont icon-store"></i>
                        <span slot="title">门店管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/staffs" v-if="showStaffManage">
                    <el-menu-item index="staffManage" class="submenu-title-noDropdown">
                        <i class="iconfont icon-user"></i>
                        <span slot="title">人员管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/new-house-grab" v-if="showHouseGrab">
                    <el-menu-item index="newHouseGrab" class="submenu-title-noDropdown">
                        <i class="iconfont icon-new-house-grab"></i>
                        <span slot="title">新房采集</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/resold-house-grab" v-if="showHouseGrab">
                    <el-menu-item index="resoldHouseGrab" class="submenu-title-noDropdown">
                        <i class="iconfont icon-resold-house-grab"></i>
                        <span slot="title">二手房采集</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/new-house" v-if="showNewHouse">
                    <el-menu-item index="newHouse" class="submenu-title-noDropdown">
                        <i class="iconfont icon-new-house"></i>
                        <span slot="title">新房管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/resold-house" v-if="showResoldHouse">
                    <el-menu-item index="resoldHouse" class="submenu-title-noDropdown">
                        <i class="iconfont icon-resold-house"></i>
                        <span slot="title">二手房管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/customer">
                    <el-menu-item index="customerManage" class="submenu-title-noDropdown">
                        <i class="iconfont icon-customer"></i>
                        <span slot="title">客源管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/news" v-if="showNews">
                    <el-menu-item index="newsManage" class="submenu-title-noDropdown">
                        <i class="iconfont icon-news"></i>
                        <span slot="title">资讯管理</span>
                    </el-menu-item>
                </router-link>
                <router-link to="/newsAd">
                    <el-menu-item index="newsAdManage" class="submenu-title-noDropdown">
                        <i class="iconfont icon-propagation"></i>
                        <span slot="title">软文营销</span>
                    </el-menu-item>
                </router-link>
            </div>
        </el-menu>
    </el-scrollbar>
</template>

<script>
    export default {
        data: function () {
            return {
                isCollapse: window.localStorage.getItem("sideBar") !== 'true',
            }
        },
        computed: {
            showStore() {
                return this.$currentUser.isAdmin;
            },
            showHouseGrab() {
                return this.$currentUser.isAdmin;
            },
            showNewHouse() {
                return this.$currentUser.isAdmin || this.$currentUser.isStoreAdmin
            },
            showResoldHouse() {
                return this.$currentUser.isAdmin || this.$currentUser.isStoreAdmin
            },
            showStaffManage() {
                return this.$currentUser.isAdmin || this.$currentUser.isStoreAdmin
            },
            showNews() {
                return this.$currentUser.isAdmin;
            },
        },
        created() {
            
            this.$eventHub.$on('closeSideBar', () => {
                this.isCollapse = true;
                window.localStorage.setItem('sideBar', false);
            })
            this.$eventHub.$on('toggleSideBar', (value) => {
                this.isCollapse = !value;
                window.localStorage.setItem('sideBar', value);
            })
        },
        destroyed() {
            this.$eventHub.$off(['closeSideBar', 'toggleSideBar']);
        },
    }
</script>


<!--
<template>
    <div class="menu-wrapper">
        <template v-for="item in routes" v-if="!item.hidden&&item.children">

            <router-link v-if="hasOneShowingChildren(item.children) && !item.children[0].children&&!item.alwaysShow" :to="item.path+'/'+item.children[0].path"
                         :key="item.children[0].name">
                <el-menu-item :index="item.path+'/'+item.children[0].path" :class="{'submenu-title-noDropdown':!isNest}">
                    <svg-icon v-if="item.children[0].meta&&item.children[0].meta.icon" :icon-class="item.children[0].meta.icon"></svg-icon>
                    <span v-if="item.children[0].meta&&item.children[0].meta.title" slot="title">{{generateTitle(item.children[0].meta.title)}}</span>
                </el-menu-item>
            </router-link>

            <el-submenu v-else :index="item.name||item.path" :key="item.name">
                <template slot="title">
                    <svg-icon v-if="item.meta&&item.meta.icon" :icon-class="item.meta.icon"></svg-icon>
                    <span v-if="item.meta&&item.meta.title" slot="title">{{generateTitle(item.meta.title)}}</span>
                </template>

                <template v-for="child in item.children" v-if="!child.hidden">
                    <sidebar-item :is-nest="true" class="nest-menu" v-if="child.children&&child.children.length>0" :routes="[child]" :key="child.path"></sidebar-item>

                    <router-link v-else :to="item.path+'/'+child.path" :key="child.name">
                        <el-menu-item :index="item.path+'/'+child.path">
                            <svg-icon v-if="child.meta&&child.meta.icon" :icon-class="child.meta.icon"></svg-icon>
                            <span v-if="child.meta&&child.meta.title" slot="title">{{generateTitle(child.meta.title)}}</span>
                        </el-menu-item>
                    </router-link>
                </template>
            </el-submenu>

        </template>
    </div>
</template>

<script>
    import { generateTitle } from '@/utils/i18n'

    export default {
        name: 'SidebarItem',
        props: {
            routes: {
                type: Array
            },
            isNest: {
                type: Boolean,
                default: false
            }
        },
        methods: {
            hasOneShowingChildren(children) {
                const showingChildren = children.filter(item => {
                    return !item.hidden
                })
                if (showingChildren.length === 1) {
                    return true
                }
                return false
            },
            generateTitle
        }
    }
</script>

-->
<style scoped lang="scss">
	.el-menu .router-link-exact-active {
        li, li.is-active {
            background-color: rgb(38, 52, 69)!important;
        }
		li, i {
			color: rgb(64, 158, 255)!important;
		}
	}
</style>
