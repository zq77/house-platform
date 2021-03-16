<template>
    <div>
        <el-form :model="customerForm" :rules="rules" ref="customerForm" label-width="100px" class="news-form" label-position="top">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="姓名" prop="name">
                        <el-input v-model="customerForm.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="联系电话" prop="mobile">
                        <el-input v-model="customerForm.mobile"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="性别" prop="gender">
                        <el-select v-model="customerForm.gender" filterable placeholder="请选择性别">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in genderList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="客户等级" prop="level">
                        <el-select v-model="customerForm.level" filterable placeholder="请选择客户等级">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in levelList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="需求类型" prop="houseNeed">
                        <el-select v-model="customerForm.houseNeed" filterable placeholder="请选择需求类型" @change="clearSelectedHouse">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in houseNeedList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="区域">
                        <el-cascader placeholder="请选择地区信息" v-model="customerForm.region" :options="regions" filterable change-on-select></el-cascader>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="意向楼盘">
                        <el-input placeholder="请选择房源楼盘" v-model="customerForm.houseName" :readOnly="true" @focus="showHouseDialog"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-row class="row-two-col">
                        <el-form-item label="预算" prop="minPrice" class="two-col-item">
                            <el-input v-model.number="customerForm.minPrice" placeholder="最小"><template slot="suffix">万元</template></el-input>
                        </el-form-item>
                        <el-form-item prop="maxPrice" class="two-col-item">
                            <el-input v-model.number="customerForm.maxPrice" placeholder="最大"><template slot="suffix">万元</template></el-input>
                        </el-form-item>
                    </el-row>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-row class="row-two-col">
                        <el-form-item label="意向面积" prop="minArea" class="two-col-item">
                            <el-input v-model.number="customerForm.minArea" placeholder="最小"><template slot="suffix">平米</template></el-input>
                        </el-form-item>
                        <el-form-item prop="maxArea" class="two-col-item">
                            <el-input v-model.number="customerForm.maxArea" placeholder="最大"><template slot="suffix">平米</template></el-input>
                        </el-form-item>
                    </el-row>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="户型" prop="roomCount">
                        <el-select v-model="customerForm.roomCount" filterable placeholder="请选择户型">
                            <el-option v-if="item.value != ''" :label="item.key" :key="item.value" :value="item.value" v-for="item in roomTypeList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="房屋类型" prop="category">
                        <el-select v-model="customerForm.category" filterable placeholder="请选择房屋类型">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in houseKeyTypes"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="客户来源" prop="source">
                        <el-select v-model="customerForm.source" filterable placeholder="请选择客户来源">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in customerSourceList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="购房目的" prop="purpose">
                        <el-select v-model="customerForm.purpose" filterable placeholder="请选择购房目的">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in buyPurposeList"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="18">
                    <el-form-item label="备注" prop="content">
                        <el-input type="textarea" v-model="customerForm.content" :autosize="{ minRows: 3 }"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
            </el-form-item>
        </el-form>
        <el-dialog title="新房选择" :visible.sync="dialogVisible" width="60%">
            <house-select v-on:setSelectedHouse="setSelectedHouse"></house-select>
        </el-dialog>
        <el-dialog title="二手房选择" :visible.sync="dialogVisibleForResold" width="60%">
            <resold-house-select v-on:setSelectedResoldHouse="setSelectedResoldHouse"></resold-house-select>
        </el-dialog>
        
    </div>
    
</template>

<script>
    import errorMixin from '@/mixins/error'
    import regionsMixin from '@/mixins/regions'
    import customerService from '@/api/customer'
    import HouseSelect from "@/components/HouseSelect/index.vue"
    import ResoldHouseSelect from "@/components/ResoldHouseSelect/index.vue"
    import { genderList, houseNeedList, levelList, roomTypeList, houseKeyTypes, customerSourceList, buyPurposeList } from '@/utils/constants'

    export default {
        mixins: [errorMixin, regionsMixin],
        components:{
            'house-select': HouseSelect,
            'resold-house-select': ResoldHouseSelect
        },
        data() {
            const validateMobile = (rule, value, callback) => {
                if(!value) {
                    callback(new Error('请输入联系电话'));
                } else if (!/^1(3|4|5|7|8)\d{9}$/.test(value)) {
                    callback(new Error('请输入正确的手机号码'));
                } else {
                    callback();
                }
            };
            return {
                genderList,
                levelList,
                houseNeedList,
                roomTypeList,
                houseKeyTypes,
                customerSourceList,
                buyPurposeList,
                customerForm: {},
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    mobile: [
                        { validator: validateMobile,  trigger: 'blur' }
                    ],
                    gender: [
                        { required: true, message: '请选择性别', trigger: 'change' }
                    ],
                    level: [
                        { required: true, message: '请选择客户等级', trigger: 'change' }
                    ],
                    houseNeed: [
                        { required: true, message: '请选择需求类型', trigger: 'change' }
                    ],
                    minPrice: [
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ],
                    maxPrice: [
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ],
                    minArea: [
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ],
                    maxArea: [
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ],
                },
                dialogVisible: false,
                dialogVisibleForResold: false,
            };
        },
        created() {
            const customerId = this.$route.params.customerId;
            if (customerId) {
                customerService.get(customerId, (response) => {
                    this.customerForm = response.data;
                    const city = this.customerForm.city;
                    if (city) {
                        this.customerForm.province = city.substring(0, 2);
                        this.generateRegion(this.customerForm);
                    }
                })
            }
        },
        methods: {
            submitForm() {
                this.parseRegion(this.customerForm);
                let onSuccess = () => {
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }
                let onError = (response) => {
                    this.$message.error('保存失败')
                    this.showErrorField(response)
                }
                this.$refs['customerForm'].validate((valid) => {
                    if (valid) {
                        if(this.customerForm.minPrice > this.customerForm.maxPrice) {
                            this.$message.error('预算最小值应小于最大值');
                        } else if(this.customerForm.minArea> this.customerForm.maxArea) {
                            this.$message.error('意向面积最小值应小于最大值')
                        } else {
                            if (this.customerForm.id) {
                                customerService.update(this.customerForm.id, this.customerForm, onSuccess, onError)
                            } else {
                                customerService.create(this.customerForm, onSuccess, onError)
                            }
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            },
            clearSelectedHouse() {
                this.customerForm.houseId = '';
                this.customerForm.houseName = '';
                this.customerForm.resoldHouseId = '';
            },
            showHouseDialog() {
                if(this.customerForm.houseNeed === 'ESF') {
                    this.dialogVisibleForResold = true;
                } else {
                    this.dialogVisible = true;
                }
            },
            setSelectedHouse(selectedHouse) {
                this.dialogVisible = false;
                this.customerForm.houseId = selectedHouse.id;
                this.customerForm.houseName = selectedHouse.name;
                this.customerForm.resoldHouseId = '';
            },
            setSelectedResoldHouse(selectedHouse) {
                this.dialogVisibleForResold = false;
                this.customerForm.houseId = '';
                this.customerForm.houseName = selectedHouse.name;
                this.customerForm.resoldHouseId = selectedHouse.id;
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .row-two-col .two-col-item {
        width: 49%;
        display: inline-block;
    }
</style>
