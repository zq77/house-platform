<template>
    <div class="app-wrapper" :class="classObj">
        <div v-if="device==='mobile'&& sidebar" class="drawer-bg" @click="handleClickOutside"></div>
        <sidebar class="sidebar-container"></sidebar>
        <div class="main-container">
            <navbar></navbar>
            <section class="app-main">
                <transition name="fade" mode="out-in">
                    <router-view></router-view>
                </transition>
            </section>
        </div>
    </div>
</template>

<script>
    import { Navbar, Sidebar } from '@/views/layout/components'
    import ResizeMixin from '@/views/layout/mixin/ResizeHandler'

    export default {
        data: function () {
            return {
                device: window.localStorage.getItem("device"),
                sidebar: window.localStorage.getItem("sideBar") === 'true'
            }
        },
        components: {
            Navbar,
            Sidebar
        },
        mixins: [ResizeMixin],
        created() {
            this.$eventHub.$on("toggleDevice", (device) => {
                window.localStorage.setItem("device", device);
                this.device = device;
            });
            this.$eventHub.$on('toggleSideBar', (value) => {
                this.sidebar = value;
            });
            this.$eventHub.$on('closeSideBar', () => {
                this.sidebar = false;
            });
        },
        destroyed() {
            this.$eventHub.$off(["toggleDevice", "toggleSideBar", "closeSideBar"]);
        },
        computed: {
            classObj() {
                return {
                    hideSidebar: !this.sidebar,
                    // withoutAnimation: this.sidebar.withoutAnimation,
                    mobile: this.device === 'mobile'
                }
            }
        },
        methods: {
            handleClickOutside() {
                this.$eventHub.$emit("closeSideBar");
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    @import "../scss/mixin.scss";
    .app-wrapper {
        @include clearfix;
        position: relative;
        height: 100%;
        width: 100%;
    }
    .drawer-bg {
        background: #000;
        opacity: 0.3;
        width: 100%;
        top: 0;
        height: 100%;
        min-height: 900px;
        position: absolute;
        z-index: 999;
    }
    .app-main {
    	height: calc(100% - 50px);
    }
</style>
