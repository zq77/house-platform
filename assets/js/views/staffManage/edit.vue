<template>
    <div>
        <el-form :model="staffForm" :rules="rules" ref="staffForm" label-width="100px" class="staff-form" label-position="top">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="上传头像" prop="avater">
                        <single-image v-model="staffForm.avatar"></single-image>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="姓名" prop="name" :error="errors.name">
                        <el-input v-model="staffForm.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="性别" prop="gender" :error="errors.gender">
                        <el-select v-model="staffForm.gender" filterable placeholder="请选择性别">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in genderTypes"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="手机号" prop="phone" :error="errors.phone">
                        <el-input v-model="staffForm.phone" placeholder="请输入手机号"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="身份证" prop="idCard">
                        <el-input v-model="staffForm.idCard"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="jobNumber" label="工号">
                        <el-input v-model="staffForm.jobNumber"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="岗位" prop="role" :error="errors.role">
                        <el-select v-model="staffForm.role" filterable placeholder="请选择岗位">
                            <el-option label="门店管理员" :value="roleType.STORE_ADMIN"></el-option>
                            <el-option label="经纪人" :value="roleType.AGENT"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <!--
                    TODO only admin can add store,
                    this field will not display in view of store admin, store admin only can add staff in his store
                    A store need name, address, open time(date picker), description, creator, create time update time
                -->
                <el-col v-if="isAdmin" :xs="20" :sm="8" :md="6">
                    <el-form-item label="门店" prop="storeId" :error="errors.storeId" class="isAdmin ? 'select-store' : ''">
                        <el-input placeholder="请选择门店" v-model="staffForm.storeName" :readOnly="true" @focus="dialogVisible=true"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <h1>基本信息</h1>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="入职日期">
                        <el-date-picker v-model="staffForm.entryDate" type="date" value-format="yyyy-MM-dd" placeholder="请选择入职日期"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="出生日期">
                        <el-date-picker v-model="staffForm.birthday" type="date" value-format="yyyy-MM-dd" placeholder="请选择出生日期"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="招聘渠道">
                        <el-input v-model="staffForm.joinWay"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="推荐人">
                        <el-input v-model="staffForm.referrer"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="籍贯">
                        <el-input v-model="staffForm.birthplace"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="政治面貌">
                        <el-select v-model="staffForm.political" filterable placeholder="请选择政治面貌">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in politicalStatuses"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="婚姻情况">
                        <el-select v-model="staffForm.maritalStatus" filterable placeholder="请选择婚姻情况">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in maritalStatuses"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="最高学历">
                        <el-select v-model="staffForm.education" filterable placeholder="请选择最高学历">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in educations"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="毕业年份">
                        <el-date-picker v-model="staffForm.graduatedYear" type="year" value-format="yyyy-MM-dd" placeholder="请选择毕业年份"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="毕业学校">
                        <el-input v-model="staffForm.graduatedSchool"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="专业">
                        <el-input v-model="staffForm.specialty"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-row :gutter="20">
                <h1>联系方式</h1>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="紧急联系人">
                        <el-input v-model="staffForm.emergencyContact"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="紧急联系电话">
                        <el-input v-model="staffForm.emergencyPhone"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

            <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
            </el-form-item>
        </el-form>
        <el-dialog title="门店选择" :visible.sync="dialogVisible" width="60%">
            <store-select v-on:setSelectedStore="setSelectedStore"></store-select>
        </el-dialog>
    </div>
</template>

<script>
    import errorMixin from '@/mixins/error'
    import staffService from '@/api/staff'
    import storeService from '@/api/store'
    import SingleImage from '@/components/Upload/singleImage.vue'
    import VUpload from "@/components/Upload/v-upload.vue";
    import StoreSelect from '@/components/StoreSelect/index.vue'
    import { genderTypes, maritalStatuses, educations, politicalStatuses, roleType } from '@/utils/constants';

    export default {
        mixins: [errorMixin],
        components: {
            'single-image': SingleImage,
            'store-select': StoreSelect
        },
        data() {
            return {
                isAdmin: this.$currentUser.isAdmin,
                staffForm: {},
                genderTypes: genderTypes,
                maritalStatuses: maritalStatuses,
                educations: educations,
                politicalStatuses: politicalStatuses,
                roleType: roleType,
                stores: [],
                rules: {
                    name: [
                        { required: true, message: '请输入人员名称', trigger: 'blur' }
                    ],
                    role: [
                        { required: true, message: '请选择岗位', trigger: 'change' },
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    phone: [
                        { required: true, message: '请输入手机号', trigger: 'blur' }
                    ],
                    storeId: [
                        { required: true, message: '请选择门店', trigger: 'change' }
                    ]
                },
                dialogVisible: false,
            };
        },
        computed: {
        },
        watch: {
        },
        created() {
            const staffId = this.$route.params.staffId;
            if (staffId) {
                staffService.get(staffId, (response) => {
                    this.staffForm = response.data
                })
            }
            // this.$eventHub.$on('create:store:success', (store) => {
            //     this.stores.push(store)
            // })
        },
        beforeDestroy() {
            // this.$eventHub.$off('create:store:success')
        },
        methods: {
            openCreateStoreDialog() {
                this.$eventHub.$emit('create:store')
            },
            submitForm() {
                let onSuccess = () => {
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }
                let onError = (response) => {
                    this.$message.error('保存失败')
                    this.showErrorField(response)
                }
                this.$refs['staffForm'].validate((valid) => {
                    if (valid) {
                        if (this.staffForm.id) {
                            staffService.update(this.staffForm.id, this.staffForm, onSuccess, onError)
                        } else {
                            staffService.create(this.staffForm, onSuccess, onError)
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            },
            handleImageScucess(result) {
                staffService.addHouseImage(this.staffForm.id, {'imageUrl': result.key}).then((response) => {
                    this.staffForm.images.push(response.data)
                })
            },
            deleteImage(imageId, index) {
                staffService.deleteHouseImage(this.staffForm.id, imageId).then(() => {
                    this.staffForm.images.splice(index, 1)
                })
            },
            imageMoved(event) {
                if (event.newIndex < 0 || event.newIndex === event.oldIndex) {
                    return false;
                }
                staffService.sortHouseImage(this.staffForm.id, this.staffForm.images[event.newIndex].id, event.newIndex)
            },
            setSelectedStore(selectedStore) {
                this.dialogVisible = false;
                this.staffForm.storeId = selectedStore.id;
                this.staffForm.storeName = selectedStore.name;
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .el-date-editor.el-input {
        width: 100%;
    }
    .el-upload-list--picture-card .el-upload-list__item-actions:hover {
        cursor: move;
    }
    .staff-form {
        .select-store {
            .el-select {
                width: calc(100% - 45px);
                margin-right: 5px;
            }
        }
        .create-btn {
            position: absolute;
            bottom: 0;
            right: -45px;
        }
    }
</style>
