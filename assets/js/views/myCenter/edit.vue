<template>
    <div>
        <el-form :model="userForm" :rules="rules" ref="userForm" label-width="100px" class="user-form" label-position="top">
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="上传头像" prop="avater">
                        <div class="upload-container">
                            <div class="image-preview" v-if="userForm.avatar">
                                <div class="image-preview-wrapper">
                                    <img :src="userForm.avatar+'?imageView2/1/w/100/h/100' | imagePrefix">
                                    <div class="image-preview-action">
                                        <i @click="deleteImage" class="el-icon-delete"></i>
                                    </div>
                                </div>
                            </div>
                            <v-upload class-name="image-uploader" drag :show-file-list="false" accept="image/png, image/jpeg"
                                    :on-success="handleImageScucess" v-else>
                                <i class="el-icon-upload"></i>
                            </v-upload>
                        </div>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="姓名" prop="realName">
                        <el-input v-model="userForm.realName"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="性别" prop="gender">
                        <el-select v-model="userForm.gender" filterable placeholder="请选择性别">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in genderTypes"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="手机" prop="phone">
                        <el-input v-model="userForm.phone"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="邮箱" prop="email">
                        <el-input v-model="userForm.email"></el-input>
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
    import VUpload from "@/components/Upload/v-upload.vue";
    import { genderTypes } from '@/utils/constants';
    import { validatePhone, validateEmail } from '@/utils/validate';

    export default {
        components: {
            'v-upload': VUpload,
        },
        data() {
            const validateUserPhone = (rule, value, callback) => {
                if (!validatePhone(value)) {
                    callback(new Error('请输入正确的手机号'));
                } else {
                    callback();
                }
            };
            const validateUserEmail = (rule, value, callback) => {
                if (!validateEmail(value)) {
                    callback(new Error('请输入正确的邮箱'));
                } else {
                    callback();
                }
            };
            return {
                genderTypes,
                userForm: {},
                rules: {
                    realName: [
                        { required: true, message: '请输入人员名称', trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { validator: validateUserEmail, trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入手机号', trigger: 'blur' },
                        { validator: validateUserPhone, trigger: 'blur' }
                    ],
                },
            };
        },
        computed: {},
        watch: {},
        created() {
            this.getCurrentUser();
        },
        methods: {
            getCurrentUser() {
                userService.getCurrentUser(res => {
                    this.userForm = res.data;
                    this.userForm.realName = res.data.realname;
                });
            },
            submitForm() {
                let onSuccess = () => {
                    this.$message.success('修改成功');
                    this.getCurrentUser();
                }
                let onError = (response) => {
                    this.$message.error('修改失败');
                }
                this.$refs['userForm'].validate((valid) => {
                    if (valid) {
                        userService.updateCurrentUser(this.userForm, onSuccess, onError);
                    } else {
                        this.$message.error('修改失败')
                        return false;
                    }
                });
            },
            handleImageScucess(result) {
                this.coverImageKey = result.key;
                this.userForm.avatar = result.key;
            }, 
            deleteImage() {
                this.coverImageKey = '';
                this.userForm.avatar = '';
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss">
    .user-form {
        .upload-container .el-upload .el-upload-dragger {
            height: 100px;
            border-radius: 50%;
            .el-icon-upload {
                font-size: 30px;
                margin: 20px 0 16px;
            }
        }
    }
    
</style>
<style rel="stylesheet/scss" lang="scss" scoped> 
    .upload-container {
        width: 100px;
        height: 100px;
        position: relative;
        &:after {
            content: "";
            display: table;
            clear: both;
        }
        .el-upload,
        .image-uploader {
            width: 100%;
            height: 100%;
        }
        
        .image-preview {
            width: 100%;
            height: 100%;
            margin: 0 auto;
            .image-preview-wrapper {
                position: relative;
                width: 100%;
                height: 100%;
                img {
                    width: 100%;
                    height: 100%;
                    border-radius: 50%;
                }
            }
            .image-preview-action {
                position: absolute;
                width: 100%;
                height: 100%;
                left: 0;
                top: 0;
                cursor: default;
                text-align: center;
                color: #fff;
                opacity: 0;
                font-size: 20px;
                background-color: rgba(0, 0, 0, .5);
                transition: opacity .3s;
                cursor: pointer;
                text-align: center;
                line-height: 100px;
                .el-icon-delete {
                    font-size: 25px;
                }
            }
            &:hover {
                .image-preview-action {
                    opacity: 1;
                }
            }
        }
    }
</style>
