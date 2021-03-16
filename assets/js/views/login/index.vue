<template>
    <div class="login-container">
    	<div class="login-overlay"></div>
    	<div class="form-wrapper">
	        <el-form class="login-form" autoComplete="on" :model="loginForm" :rules="loginRules" ref="loginForm" label-position="left">
	            <div class="title-container">
	                <h3 class="title">登&nbsp;&nbsp;录</h3>
	            </div>
	            <el-form-item prop="username">
	                <span class="svg-container svg-container_login">
	                    <svg-icon icon-class="user" />
	                </span>
	                <el-input name="username" type="text" v-model.trim="loginForm.username" autoComplete="on" placeholder="用户名" />
	            </el-form-item>
	
	            <el-form-item prop="password">
	                <span class="svg-container">
	                    <svg-icon icon-class="password" />
	                </span>
	                    <el-input name="password" :type="passwordType" @keyup.enter.native="handleLogin" v-model.trim="loginForm.password" autoComplete="on" placeholder="密码" />
	                    <span class="show-pwd" @click="showPwd">
	                    <svg-icon icon-class="eye" />
	                </span>
	            </el-form-item>
	
	            <el-button type="primary" style="width:100%;margin-bottom:30px;" :loading="loading" @click.native.prevent="handleLogin">登录</el-button>
	
	            <!--<social-sign />-->
	        </el-form>
		</div>
    </div>
</template>

<script>
    import { isvalidUsername } from '@/utils/validate'
    import SocialSign from './socialsignin.vue'
    import userService from '@/api/user'

    export default {
        components: { SocialSign },
        name: 'login',
        data() {
            const validateUsername = (rule, value, callback) => {
                if (_.isEmpty(value)) {
                    callback(new Error('请输入用户名'))
                } else {
                    callback()
                }
            }
            const validatePassword = (rule, value, callback) => {
                if (_.isEmpty(value)) {
                    callback(new Error('请输入密码'))
                } else if (value.length < 6) {
                    callback(new Error('密码不能少于6位'))
                } else {
                    callback()
                }
            }
            return {
                loginForm: {
                    username: '',
                    password: ''
                },
                loginRules: {
                    username: [{ required: true, trigger: 'blur', validator: validateUsername }],
                    password: [{ required: true, trigger: 'blur', validator: validatePassword }]
                },
                passwordType: 'password',
                loading: false
            }
        },
        methods: {
            showPwd() {
                if (this.passwordType === 'password') {
                    this.passwordType = ''
                } else {
                    this.passwordType = 'password'
                }
            },
            handleLogin() {
                this.$refs.loginForm.validate(valid => {
                    if (valid) {
                        this.loading = true
                        userService.login(this.loginForm, () => {
                            this.loading = false
                            window.location.href = '/'
                        }, (response) => {
                            this.loading = false
                            if (response.data.message) {
                                this.$message.error(response.data.message);
                            } else {
                                this.$message.error('登陆失败');
                            }
                        })
                    } else {
                        return false
                    }
                })
            },
            afterQRScan() {
                // const hash = window.location.hash.slice(1)
                // const hashObj = getQueryObject(hash)
                // const originUrl = window.location.origin
                // history.replaceState({}, '', originUrl)
                // const codeMap = {
                //   wechat: 'code',
                //   tencent: 'code'
                // }
                // const codeName = hashObj[codeMap[this.auth_type]]
                // if (!codeName) {
                //   alert('第三方登录失败')
                // } else {
                //   this.$store.dispatch('LoginByThirdparty', codeName).then(() => {
                //     this.$router.push({ path: '/' })
                //   })
                // }
            }
        },
        created() {
            // window.addEventListener('hashchange', this.afterQRScan)
        },
        destroyed() {
            // window.removeEventListener('hashchange', this.afterQRScan)
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    $bg:#2d3a4b;
    $light_gray: #454545;

    /* reset element-ui css */
    .login-container {
        .el-input {
            display: inline-block;
            height: 47px;
            width: 85%;
            input {
                background: transparent;
                border: 0px;
                -webkit-appearance: none;
                border-radius: 0px;
                padding: 12px 5px 12px 15px;
                color: #454545;
                height: 47px;
                &:-webkit-autofill {
                    -webkit-box-shadow: 0 0 0px 1000px #fff inset !important;
                    -webkit-text-fill-color: $light_gray !important;
                }
            }
        }
        .el-form-item {
            border: 1px solid #ededed;
            background: #fff;
            color: $light_gray;
        }
    }
</style>

<style rel="stylesheet/scss" lang="scss" scoped>
    $bg:#2d3a4b;
    $dark_gray:#889aa4;
    $light_gray:#454545;

    .login-container {
        position: fixed;
        height: 100%;
        width: 100%;
        background-image: url('/static/images/login_bg.jpg');
        background-size: cover;
        .login-overlay {
        	position: absolute;
        	width: 100%;
        	height: 100%;
        	top: 0;
        	bottom: 0;
        	left: 0;
        	right: 0;
        	background-color: rgba(0, 0, 0, 0.2);
        }
        .form-wrapper {
        	position: absolute;
            top: calc(50% - 200px);
            left: 0;
            right: 0;
            padding: 0 15px;
        }
        .login-form {
            max-width: 500px;
            min-height: 350px;
            margin: 0 auto;
            padding: 35px;
            background-color: rgba(255, 255, 255, 0.6);
            .el-button {
            	border-radius: 0;
            }
        }
        .tips {
            font-size: 14px;
            color: #fff;
            margin-bottom: 10px;
            span {
                &:first-of-type {
                    margin-right: 16px;
                }
            }
        }
        .svg-container {
            padding: 0 5px 0 15px;
            color: $dark_gray;
            vertical-align: middle;
            width: 30px;
            display: inline-block;
            &_login {
                font-size: 20px;
            }
        }
        .title-container {
            position: relative;
            .title {
                font-size: 26px;
                font-weight: 400;
                color: $light_gray;
                margin: 0px auto 40px auto;
                text-align: center;
            }
            .set-language {
                color: #fff;
                position: absolute;
                top: 5px;
                right: 0px;
            }
        }
        .show-pwd {
            position: absolute;
            right: 10px;
            top: 7px;
            font-size: 16px;
            color: $dark_gray;
            cursor: pointer;
            user-select: none;
        }
        .thirdparty-button {
            position: absolute;
            right: 35px;
            bottom: 28px;
        }
    }
</style>
