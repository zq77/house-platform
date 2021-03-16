<template>
    <div>
        <el-form :model="houseForm" :rules="rules" ref="houseForm" label-width="100px" class="demo-ruleForm" label-position="top">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="12" :md="12">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="houseForm.title"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12">
                    <el-form-item label="次标题" prop="subTitle">
                        <el-input v-model="houseForm.subTitle"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="小区名称" prop="name">
                        <el-input v-model="houseForm.name"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="栋座" prop="block">
                        <el-input v-model="houseForm.block"><template slot="suffix">栋/座</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="单元" prop="unit">
                        <el-input v-model="houseForm.unit"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="房号" prop="roomNum">
                        <el-input v-model="houseForm.roomNum"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="业主姓名" prop="ownerName">
                        <el-input v-model="houseForm.ownerName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="业主电话" prop="ownerPhone">
                        <el-input v-model="houseForm.ownerPhone"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="region" label="区域">
                        <el-cascader placeholder="请选择地区信息" v-model="houseForm.region" :options="regions" filterable change-on-select></el-cascader>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="address" label="地址">
                        <el-input v-model="houseForm.address" placeholder="请输入详细地址"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="总价" prop="totalPrice">
                        <el-input v-model.number="houseForm.totalPrice"><template slot="suffix">万元</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="底价" prop="lowestPrice">
                        <el-input v-model.number="houseForm.lowestPrice"><template slot="suffix">万元</template></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="建筑面积" prop="floorage">
                        <el-input v-model.number="houseForm.floorage"><template slot="suffix">m²</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="使用面积" prop="useageArea">
                        <el-input v-model.number="houseForm.useageArea"><template slot="suffix">m²</template></el-input>
                    </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="室" prop="roomCount">
                        <el-input v-model.number="houseForm.roomCount"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="厅" prop="hallCount">
                        <el-input v-model.number="houseForm.hallCount"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="卫" prop="bathroomCount">
                        <el-input v-model.number="houseForm.bathroomCount"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="厨" prop="kitchenCount">
                        <el-input v-model.number="houseForm.kitchenCount"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="sellingStatus" label="售卖状态">
                        <el-select v-model="houseForm.sellingStatus" filterable placeholder="请选择类型">
                            <el-option :label="item" :key="item" :value="item" v-for="item in houseSellingStatus"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="category" label="类型">
                        <el-select v-model="houseForm.category" filterable placeholder="请选择类型">
                            <el-option :label="item.name" :key="item.value" :value="item.value" v-for="item in types"></el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="楼层" prop="floor">
                        <el-input v-model="houseForm.floor"><template slot="suffix">层</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="总楼层" prop="totalFloor">
                        <el-input v-model="houseForm.totalFloor"><template slot="suffix">层</template></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="orientation" label="房屋朝向">
                        <el-input v-model="houseForm.orientation"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="decoration" label="装修情况">
                        <!--<el-select v-model="houseForm.decoration" filterable placeholder="请选择装修">
                            <el-option :label="item" :key="item" :value="item" v-for="item in houseDecoration"></el-option>
                        </el-select>-->
                        <el-input v-model="houseForm.decoration"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="useYear" label="房屋年限">
                        <!--<el-select v-model="houseForm.useYear" filterable placeholder="请选择房屋年限">
                            <el-option :label="item" :key="item" :value="item" v-for="item in houseUseYear"></el-option>
                        </el-select>-->
                        <el-input v-model="houseForm.useYear"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="挂牌时间" prop="buildingYear">
                        <el-input v-model="houseForm.buildingYear"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="propertyNature" label="产权性质">
                        <!--<el-select v-model="houseForm.propertyNature" filterable placeholder="请选择产权性质">
                            <el-option :label="item" :key="item" :value="item" v-for="item in housePropertyNature"></el-option>
                        </el-select>-->
                        <el-input v-model="houseForm.propertyNature"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="propertyYear" label="产权年限">
                        <el-input v-model="houseForm.propertyYear"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item prop="mortgageStatus" label="抵押信息">
                        <!--<el-select v-model="houseForm.mortgageStatus" filterable placeholder="请选择有无抵押">
                            <el-option :label="item" :key="item" :value="item" v-for="item in houseMortgageStatus"></el-option>
                        </el-select>-->
                        <el-input v-model="houseForm.mortgageStatus"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>

             <el-form-item label="标签">
                <el-tag :key="index" v-for="(tag, index) in dynamicTags" closable :disable-transitions="false" @close="handleClose(tag)">
                    {{tag}}
                </el-tag>
                <template v-if="isShowAddBtn">
                    <el-input class="input-new-tag" v-if="inputVisible" v-model="inputValue" ref="saveTagInput" size="small" @keyup.enter.native="handleInputConfirm" 
                    @blur="handleInputConfirm"></el-input>
                    <el-button v-else class="button-new-tag" size="small" @click="showInput">+ 新标签</el-button>
                </template>
            </el-form-item>
            <el-form-item label="小区介绍">
                <el-input type="textarea" v-model="houseForm.introduction" :autosize="{ minRows: 4 }"></el-input>
            </el-form-item>

            <el-form-item label="小区图集" v-if="houseForm.images">
                <ul class="el-upload-list el-upload-list--picture-card" v-if="houseForm.images && houseForm.images.length > 0">
                    <draggable v-model="houseForm.images" @end="imageMoved">
                        <transition-group>
                            <li tabindex="0" class="el-upload-list__item is-success" v-for="(image, index) in houseForm.images" :key="image.id">
                                <img :src="image.url | imagePrefix" alt="loading..." class="el-upload-list__item-thumbnail">
                                <i class="el-icon-close"></i>
                                <span class="el-upload-list__item-actions"><span class="el-upload-list__item-delete" @click="deleteImage(image.id, index)"><i class="el-icon-delete"></i></span></span>
                            </li>
                        </transition-group>
                    </draggable>
                </ul>
                <v-upload accept="image/png, image/jpeg" :drag=true :multiple=true :on-success="handleImageScucess" class="upload-drag">
                    <i class="el-icon-upload"></i>
                    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                    <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
                </v-upload>
            </el-form-item>
            <el-form-item>
                <el-button type="primary" @click="submitForm('houseForm')">保存</el-button>
                <el-button @click="$router.go(-1)">取消</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import regionsMixin from '@/mixins/regions'
    import resoldHouseService from '@/api/resoldHouse'
    import SingleImage from '@/components/Upload/singleImage.vue'
    import VUpload from "@/components/Upload/v-upload.vue";
    import draggable from "vuedraggable";
    import { houseTypes, houseOrientation, houseDecoration, houseUseYear, housePropertyNature, housePropertyYear, houseMortgageStatus, houseSellingStatus, LABEL_SPLIT } from '@/utils/constants';

    export default {
        mixins: [regionsMixin],
        components: {
            'v-upload': VUpload,
            'single-image': SingleImage,
            'draggable': draggable
        },
        data() {
            return {
                houseForm: {},
                types: houseTypes,
                houseOrientation, houseDecoration, houseUseYear, housePropertyNature, housePropertyYear, houseMortgageStatus, houseSellingStatus,
                dynamicTags: [],
                isShowAddBtn: true,
                inputVisible: false,
                inputValue: '',
                rules: {
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' }
                    ],
                    name: [
                        { required: true, message: '请输入小区名称', trigger: 'blur' }
                    ],
                    ownerName: [
                        { required: true, message: '请输入业主姓名', trigger: 'blur' }
                    ],
                    ownerPhone: [
                        { required: true, message: '请输业主电话', trigger: 'blur' }
                    ],
                    block: [
                        { required: true, message: '请输入栋/座', trigger: 'blur' }
                    ],
                    unit: [
                        { required: true, message: '请输入单元', trigger: 'blur' }
                    ],
                    roomNum: [
                        { required: true, message: '请输入房号', trigger: 'blur' }
                    ],
                    region: [
                        { required: true, message: '请选择地区信息', trigger: 'change' },
                        { type: 'array', min: 3, message: '请选择正确的地区信息', trigger: 'blur' },
                    ],
                    address: [
                        { required: true, message: '请输入详细地址', trigger: 'blur' }
                    ],
                    totalPrice: [
                        { required: true, message: '请输入总价', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    lowestPrice: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    floorage: [
                        { required: true, message: '请输入建筑面积', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    useageArea: [ { type: 'number',  message: '请输入数字', trigger: 'change'} ],
                    roomCount: [ 
                        { required: true, message: '不能为空', trigger: 'change' }, 
                        { type: 'integer', message: '请输入整数', trigger: 'change' }
                    ],
                    hallCount: [
                        { required: true, message: '不能为空', trigger: 'change' },
                        { type: 'integer', message: '请输入整数', trigger: 'change' }
                    ],
                    bathroomCount: [
                        { required: true, message: '不能为空', trigger: 'change' },
                        { type: 'integer', message: '请输入整数', trigger: 'change' }
                    ],
                    kitchenCount: [
                        { required: true, message: '不能为空', trigger: 'change' },
                        { type: 'integer', message: '请输入整数', trigger: 'change' }
                    ],
                    category: [
                        { required: true, message: '请选择类型', trigger: 'change' }
                    ],
                    sellingStatus: [
                        { required: true, message: '请选择售卖状态', trigger: 'change' }
                    ],
                    desc: [
                        { required: true, message: '请填写活动形式', trigger: 'blur' }
                    ]
                },
            };
        },
        computed: {
        },
        created() {
            const houseId = this.$route.params.houseId;
            if (houseId) {
                resoldHouseService.get(houseId, (response) => {
                    this.houseForm = response.data;
                    this.generateRegion(this.houseForm);
                    this.dynamicTags = this.houseForm.labels ? this.houseForm.labels.split(LABEL_SPLIT) : [];
                    if (this.dynamicTags.length >= 4) {
                        this.isShowAddBtn = false;
                    }
                })
            }
        },
        watch: {
        },
        methods: {
            submitForm() {
                // 拼接标签
                this.houseForm.labels = this.dynamicTags.join(LABEL_SPLIT);
                this.parseRegion(this.houseForm)
                let onSuccess = () => {
                    this.$message.success('保存成功')
                    this.$router.go(-1)
                }
                let onError = (response) => {
                    this.$message.error('保存失败')
                    this.$set(this.errors, response.data.field, response.data.tip)
                }
                this.$refs['houseForm'].validate((valid) => {
                    if (valid) {
                        if (this.houseForm.id) {
                            resoldHouseService.update(this.houseForm.id, this.houseForm, onSuccess, onError)
                        } else {
                            resoldHouseService.create(this.houseForm, onSuccess, onError)
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            },
            handleImageScucess(result) {
                resoldHouseService.addHouseImage(this.houseForm.id, {'imageUrl': result.key}).then((response) => {
                    this.houseForm.images.push(response.data)
                })
            },
            deleteImage(imageId, index) {
                resoldHouseService.deleteHouseImage(this.houseForm.id, imageId).then(() => {
                    this.houseForm.images.splice(index, 1)
                })
            },
            imageMoved(event) {
                if (event.newIndex < 0 || event.newIndex === event.oldIndex) {
                    return false;
                }
                resoldHouseService.sortHouseImage(this.houseForm.id, this.houseForm.images[event.newIndex].id, event.newIndex)
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
                    this.$message.error('每个标签最多输入6个字！');
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
    .el-upload-list--picture-card .el-upload-list__item-actions:hover {
        cursor: move;
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
    }
    .input-new-tag {
        width: 120px;
        margin-right: 10px;
        vertical-align: bottom;
    }
</style>
