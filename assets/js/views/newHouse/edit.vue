<template>
    <div>
        <el-form :model="newHouseForm" :rules="rules" ref="newHouseForm" label-width="100px" class="demo-newHouseForm" label-position="top">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="小区名称" prop="name" :error="errors.name">
                        <el-input v-model="newHouseForm.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="region" label="区域">
                        <el-cascader placeholder="请选择地区信息" v-model="newHouseForm.region" :options="regions" filterable change-on-select></el-cascader>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="sellingStatus" label="售卖状态">
                        <el-select v-model="newHouseForm.sellingStatus" filterable placeholder="请选择类型">
                            <el-option :label="item.key" :key="item.value" :value="item.value" v-for="item in houseStatus"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="address" label="楼盘地址">
                        <el-input v-model="newHouseForm.address" placeholder="请输入详细地址"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="salesDepartmentAddress" label="售楼地址">
                        <el-input v-model="newHouseForm.salesDepartmentAddress" placeholder="请输入详细地址"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="小区均价" prop="price">
                        <el-input v-model.number="newHouseForm.price"><template slot="suffix">元/m²</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="总户数" prop="households">
                        <el-input v-model="newHouseForm.households"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="占地面积" prop="totalArea">
                        <el-input v-model.number="newHouseForm.totalArea"><template slot="suffix">m²</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="建筑面积" prop="floorage">
                        <el-input v-model.number="newHouseForm.floorage"><template slot="suffix">m²</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="category" label="房屋类型">
                        <el-select v-model="newHouseForm.category" filterable placeholder="请选择类型">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in types"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="developers" label="开发商">
                        <el-input v-model="newHouseForm.developers"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="sellingDate" label="开盘时间">
                        <el-input v-model="newHouseForm.sellingDate"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="completionDate" label="交房时间">
                        <el-input v-model="newHouseForm.completionDate"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="buildYear" label="建筑年代">
                        <el-input v-model="newHouseForm.buildYear"></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="propertyRightsYears" label="产权年限">
                        <el-input v-model="newHouseForm.propertyRightsYears"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="物业公司" prop="propertyCompany">
                        <el-input v-model="newHouseForm.propertyCompany"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="物业费用" prop="propertyPrice">
                        <el-input v-model="newHouseForm.propertyPrice"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="物业类别">
                        <el-input v-model="newHouseForm.propertyType"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="绿化率" prop="greeningRate">
                        <el-input v-model="newHouseForm.greeningRate"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="容积率" prop="plotRatio">
                        <el-input v-model="newHouseForm.plotRatio"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="车位数量" prop="parkCount">
                        <el-input v-model="newHouseForm.parkCount"></el-input>
                    </el-form-item>
                </el-col>
                
            </el-row>
            <el-form-item label="标签">
                <el-tag :key="index" v-for="(tag, index) in dynamicTags" v-if="tag" closable :disable-transitions="false" @close="handleClose(tag)">
                    {{tag}}
                </el-tag>
                <template v-if="isShowAddBtn">
                    <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small" @keyup.enter.native="handleInputConfirm" 
                    @blur="handleInputConfirm"></el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新标签</el-button>
                </template>
            </el-form-item>
            <el-form-item label="小区介绍">
                <el-input type="textarea" v-model="newHouseForm.introduction" :autosize="{ minRows: 4 }"></el-input>
            </el-form-item>

            <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import regionsMixin from '@/mixins/regions'
    import { houseTypes, houseStatus, LABEL_SPLIT } from '@/utils/constants'
    import houseService from '@/api/house'

    export default {
        mixins: [regionsMixin],
        data: function () {
            return {
                newHouseForm: {},
                errors: {},
                types: houseTypes,
                houseStatus: houseStatus,
                dynamicTags: [],
                isShowAddBtn: true,
                inputVisible: false,
                inputValue: '',
                rules: {
                    name: [
                        { required: true, message: '请输入小区名称', trigger: 'blur' }
                    ],
                    developers: [
                        { required: true, message: '请输入开发商', trigger: 'blur' }
                    ],
                    region: [
                        { required: true, message: '请选择地区信息', trigger: 'change' },
                        { type: 'array', min: 3, message: '最少选择3个', trigger: 'blur' },
                    ],
                    address: [
                        { required: true, message: '请输入详细地址', trigger: 'blur' }
                    ],
                    total_price: [
                        { required: true, message: '请输入总价', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    price: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    floorage:[
                        { required: true, message: '请输入建筑面积', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    totalArea: [
                        { required: true, message: '请输入占地面积', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    category: [
                        { required: true, message: '请选择类型', trigger: 'change' }
                    ]
                },
            }
        },
        computed: {
        },
        created() {
            const houseId = this.$route.params.houseId
            if (houseId) {
                houseService.get(houseId, (response) => {
                    this.newHouseForm = response.data;
                    this.generateRegion(this.newHouseForm);
                    this.dynamicTags = this.newHouseForm.labels ? this.newHouseForm.labels.split(LABEL_SPLIT) : [];
                    if (this.dynamicTags.length >= 4) {
                        this.isShowAddBtn = false;
                    }
                })
            }
        },
        methods: {
            submitForm() {
                // 拼接标签
                this.newHouseForm.labels = this.dynamicTags.join(LABEL_SPLIT);
                this.parseRegion(this.newHouseForm)
                let onSuccess = () => {
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }
                let onError = (response) => {
                    this.$message.error('保存失败')
                    this.$set(this.errors, response.data.field, response.data.tip)
                }
                this.$refs['newHouseForm'].validate((valid) => {
                    if (valid) {
                        if (this.newHouseForm.id) {
                            houseService.update(this.newHouseForm.id, this.newHouseForm, onSuccess, onError)
                        } else {
                            houseService.create(this.newHouseForm, onSuccess, onError)
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            },
            handleClose(tag) {
                this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
                if (this.dynamicTags.length < 4) {
                    this.isShowAddBtn = true;
                }
            },
            showInput() {
                this.inputVisible = true;
                this.$nextTick(_ => {
                    this.$refs.saveTagInput.$refs.input.focus();
                });
            },
            handleInputConfirm() {
                let inputValue = this.inputValue;
                if (inputValue.length > 5) {
                    this.$message.error('每个标签最多输入5个字！');
                    this.inputValue = '';
                    return;
                }
                if (inputValue) {
                    this.dynamicTags.push(inputValue);
                    if (this.dynamicTags.length == 4) {
                        this.isShowAddBtn = false;
                    }
                }
                this.inputValue = '';
                this.inputVisible = false;
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
	.el-date-editor.el-input {
		width: 100%;
	}
    .el-tag {
        margin-right: 10px;
        height: 32px;
        line-height: 30px;
    }
    .button-new-tag {
        margin-right: 10px;
        height: 32px;
        line-height: 30px;
        padding-top: 0;
        padding-bottom: 0;
        &:hover {
            background-color: rgba(103,194,58,.1);
            border-color: rgba(103,194,58,.2);
            color: #67c23a;
        }
    }
    .input-new-tag {
        width: 120px;
        margin-right: 10px;
        vertical-align: bottom;
    }
</style>
