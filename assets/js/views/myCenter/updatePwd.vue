<template>
    <div>
        <el-form :model="userForm" :rules="rules" ref="userForm" label-width="100px" class="user-form" label-position="top">
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="旧密码" prop="password">
                        <el-input type="password" v-model="userForm.password" minlength="6"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="新密码" prop="newPassword">
                        <el-input type="password" v-model="userForm.newPassword" minlength="6"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="确认密码" prop="confirmPassword">
                        <el-input type="password" v-model="userForm.confirmPassword" minlength="6"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button type="primary" @click="submitForm()">修改</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import userService from '@/api/user';

    export default {
        data() {
            const validateRePwd = (rule, value, callback) => {
                if (this.userForm.newPassword && value != this.userForm.newPassword) {
                    callback(new Error('两次密码输入不一致'));
                } else {
                    callback();
                }
            };
            const validateNewPwd = (rule, value, callback) => {
                if (this.userForm.confirmPassword && value != this.userForm.confirmPassword) {
                    callback(new Error('两次密码输入不一致'));
                } else {
                    callback();
                }
            };
            return {
                userForm: {},
                rules: {
                    password: [
                        { required: true, message: '请输入旧密码', trigger: 'blur' },
                        { min: 6, message: '密码长度至少为6个字符', trigger: 'blur' },
                    ],
                    newPassword: [
                        { required: true, message: '请输入新密码', trigger: 'blur' },
                        { min: 6, message: '密码长度至少为6个字符', trigger: 'blur' },
                        { validator: validateNewPwd, trigger: 'blur' }
                    ],
                    confirmPassword: [
                        { required: true, message: '请输入确认密码', trigger: 'blur' },
                        { min: 6, message: '密码长度至少为6个字符', trigger: 'blur' },
                        { validator: validateRePwd, trigger: 'blur' }
                    ],
                },
            };
        },
        computed: {},
        watch: {},
        created() {
        },
        methods: {
            submitForm() {
                let onSuccess = () => {
                    this.$message.success('修改成功')
                    this.userForm = {};
                }
                let onError = res => {
                    this.$message.error(res.bodyText);
                }
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        userService.updatePwd(this.userForm, onSuccess, onError)
                    } else {
                        this.$message.error('修改失败')
                        return false;
                    }
                });
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
</style>
