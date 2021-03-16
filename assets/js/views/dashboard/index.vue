<template>
    <div class="app-container">
    	<h1 class="welcome-text">{{welcomeMsg}}</h1>
    </div>
</template>

<script>
    export default {
        data: function () {
            return {
				welcomeMsg: '欢迎使用今日好房后台管理系统！'
            }
        },
        computed: {
        },
        methods: {
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.app-container {
		display: table;
		width: 100%;
		height: 100%;
		background: linear-gradient(to bottom right,#FFF, #409EFF);
		.welcome-text {
			display: table-cell;
			vertical-align: middle;
			font-size: 42px;
			font-weight: 400;
			color: #fff;
			text-align: center;
		}
	}
</style>
