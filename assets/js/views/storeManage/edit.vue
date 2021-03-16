<template>
    <div>
        <el-form :model="storeForm" :rules="rules" ref="storeForm" label-width="100px" class="news-form" label-position="top">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="门店名称" prop="name">
                        <el-input v-model="storeForm.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="联系人" prop="contactName">
                        <el-input v-model="storeForm.contactName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="联系电话" prop="contactPhone">
                        <el-input v-model="storeForm.contactPhone"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="开店时间" prop="openDate">
                        <el-date-picker v-model="storeForm.openDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择开店时间"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="18">
                    <el-form-item label="地址" prop="address">
                        <el-input v-model="storeForm.address"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="18">
                    <el-form-item label="备注" prop="content">
                        <el-input type="textarea" v-model="storeForm.content" :autosize="{ minRows: 3 }"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
            </el-form-item>
        </el-form>
        
    </div>
</template>

<script>
    import errorMixin from '@/mixins/error'
    import storeService from '@/api/store'

    export default {
        mixins: [errorMixin],
        data() {
            return {
                storeForm: {},
                rules: {
                    name: [
                        { required: true, message: '请输入门店名称', trigger: 'blur' }
                    ],
                    contactName: [
                        { required: true, message: '请输入联系人', trigger: 'blur' }
                    ],
                    contactPhone: [
                        { required: true, message: '请输入联系电话', trigger: 'blur' }
                    ]
                }
            };
        },
        created() {
            const storeId = this.$route.params.storeId;
            if (storeId) {
                storeService.get(storeId, (response) => {
                    this.storeForm = response.data;
                })
            }
        },
        methods: {
            submitForm() {
                let onSuccess = () => {
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }
                let onError = (response) => {
                    this.$message.error('保存失败')
                    this.showErrorField(response)
                }
                this.$refs['storeForm'].validate((valid) => {
                    if (valid) {
                        if (this.storeForm.id) {
                            storeService.update(this.storeForm.id, this.storeForm, onSuccess, onError)
                        } else {
                            storeService.create(this.storeForm, onSuccess, onError)
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-date-editor.el-input {
        width: 100%;
    }
    .el-upload-list--picture-card .el-upload-list__item-actions:hover {
        cursor: pointer;
    }
    .news-form {
        .cover-image-input {
            display: none;
        }
        .select-store {
            .el-select {
                width: calc(100% - 45px);
                margin-right: 5px;
            }
        }
        .el-upload-list__item {
            width: 200px;
            height: 150px;
            margin: 0;
        }
    }
</style>
<style rel="stylesheet/scss" lang="scss">
    .news-form .el-upload-dragger {
        min-width: 200px;
        height: 150px;
        .el-icon-upload {
            margin-top: 25px;
        }
    }
</style>
