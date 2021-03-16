<template>
    <div class="app-container">
        <transition name="fade" mode="out-in">
            <router-view></router-view>
        </transition>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {

            }
        },
        computed: {
        },
        methods: {
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
