<template>
    <div v-if="house">
        <div class="base-info">
            <h1>{{ house.name }}</h1>
            <div class="address">
                {{ house.location }}
            </div>
            <div class="price">
                {{ house.price }} 元/m²
            </div>
            <div class="info">
                <el-row>
                    <el-col :span="6">
                        <div class="title"> 总面积 </div>
                        <div class="value"> {{ house.totalArea ? house.totalArea + 'm²' : '暂无' }} </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="title"> 总户数 </div>
                        <div class="value"> {{ house.households ? house.households + '户' : '暂无' }} </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="title"> 开发商 </div>
                        <div class="value"> {{ house.developers || '暂无' }} </div>
                    </el-col>
                    <el-col :span="6">
                        <div class="title"> 建筑年代 </div>
                        <div class="value"> {{ house.buildYear ? house.buildYear  + '年' : '暂无' }} </div>
                    </el-col>
                </el-row>
            </div>
        </div>
        <div class="extend-info">
            <el-row>
                <el-col :span="8">
                    <div class="title"> 物业公司 </div>
                    <div class="value"> {{ house.propertyCompany || '暂无' }} </div>
                </el-col>
                <el-col :span="8">
                    <div class="title"> 物业费用 </div>
                    <div class="value"> {{ house.propertyPrice ? house.propertyPrice + '元/m²' : '暂无' }} </div>
                </el-col>
                <el-col :span="8">
                    <div class="title"> 物业类别 </div>
                    <div class="value"> {{ house.propertyType || '暂无' }} </div>
                </el-col>
            </el-row>
            <el-row>
                <el-col :span="8">
                    <div class="title"> 绿化率 </div>
                    <div class="value"> {{ house.greeningRate? house.greeningRate + '%' : '暂无' }} </div>
                </el-col>
                <el-col :span="8">
                    <div class="title"> 容积率 </div>
                    <div class="value"> {{ house.plotRatio ? house.plotRatio + '%' : '暂无' }} </div>
                </el-col>
                <el-col :span="8">
                    <div class="title"> 车位数量 </div>
                    <div class="value"> {{ house.parkCount ? house.parkCount + '个' : '暂无' }} </div>
                </el-col>
            </el-row>
        </div>
        <div class="images">
            <div class="title">
                小区图片
            </div>
            <ul class="el-upload-list el-upload-list--picture-card" v-if="house.normalImages && house.normalImages.length > 0">
                <draggable v-model="house.normalImages" @end="imageMoved">
                    <transition-group>
                        <li tabindex="0" class="el-upload-list__item is-success" v-for="(image, index) in house.normalImages" :key="image.id">
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
        </div>
        <div class="description">
            {{ house.introduction || '暂无小区描述' }}
        </div>
        <div class="position">
            <div class="title">
                户型
            </div>
            <el-radio-group v-model="tabPosition" style="margin-bottom: 30px;">
                <el-radio-button label="all">不限</el-radio-button>
                <el-radio-button label="one">一室</el-radio-button>
                <el-radio-button label="two">二室</el-radio-button>
                <el-radio-button label="three">三室</el-radio-button>
                <el-radio-button label="four">四室</el-radio-button>
                <el-radio-button label="more">五室及以上</el-radio-button>
            </el-radio-group>
            <el-button type="text" class="right" @click="openHouseTypeDialog">新增</el-button>
            <div v-if="filteredHouseTypes && filteredHouseTypes.length > 0">
                <div class="item" v-for="(type, index) in filteredHouseTypes" :key="type.id">
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                        <img :src="type.imageUrl +'?imageView2/1/w/200/h/200' | imagePrefix" class="image">
                        <div style="padding: 14px;">
                            <span>{{type.roomCount}}室</span>
                            <span>{{type.hallCount}}厅</span>
                            <span>{{type.bathroomCount}}卫</span>
                            <span>{{type.floorage}}m²</span>
                            <div class="bottom clearfix">
                                <el-button type="text" class="button" @click="openHouseTypeDialog(type)">编辑</el-button>
                                <el-button type="text" class="button" @click="deleteHouseType(type.id, index)">删除</el-button>
                            </div>
                        </div>
                    </el-card>
                </div>
            </div>
            <div class="no-data" v-else>
                暂无户型
            </div>
        </div>

        <el-dialog title="编辑户型图" :visible.sync="dialogHouseTypeVisible">
            <el-form :model="editHouseTypeForm" :rules="rules" ref="editHouseTypeForm" label-width="100px" class="demo-ruleForm" label-position="top">
                <el-row :gutter="20">
                    <el-col :span="6">
                        <el-form-item label="室" prop="roomCount">
                            <el-input v-model.number="editHouseTypeForm.roomCount"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="厅" prop="hallCount">
                            <el-input v-model.number="editHouseTypeForm.hallCount"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="卫" prop="bathroomCount">
                            <el-input v-model.number="editHouseTypeForm.bathroomCount"></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="6">
                        <el-form-item label="厨" prop="kitchenCount">
                            <el-input v-model.number="editHouseTypeForm.kitchenCount"></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row :gutter="20">
                    <el-col :span="8">
                        <el-form-item label="面积" prop="floorage">
                            <el-input v-model.number="editHouseTypeForm.floorage">
                                <template slot="suffix">m²</template>
                            </el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="总价" prop="totalPrice">
                            <el-input v-model.number="editHouseTypeForm.totalPrice"><template slot="suffix">元</template></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="单价" prop="price">
                            <el-input v-model.number="editHouseTypeForm.price"><template slot="suffix">元/m²</template></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-form-item prop="imageUrl">
                    <single-image v-model="editHouseTypeForm.imageUrl"></single-image>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogHouseTypeVisible = false">取消</el-button>
                <el-button type="primary" @click="saveHouseType">保存</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import houseService from '@/api/house'
    import SingleImage from '@/components/Upload/singleImage.vue'
    import VUpload from "@/components/Upload/v-upload.vue";
    import draggable from "vuedraggable";

    export default {
        components: {
            'v-upload': VUpload,
            'single-image': SingleImage,
            'draggable': draggable
        },
        data: function () {
            return {
                house: {},
                tabPosition: 'all',
                errors: {},
                dialogHouseTypeVisible: false,
                editHouseTypeForm: {},
                rules: {
                    floorage: [
                        { required: true, message: '请输入建筑面积', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
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
                    totalPrice: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    price: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    imageUrl: [
                        { required: true, message: '请上传图片', trigger: 'change' }
                    ]
                }
            }
        },
        created() {
            const houseId = this.$route.params.houseId
            houseService.get(houseId, (response) => {
                this.house = response.data
            })
        },
        computed: {
            filteredHouseTypes() {
                if (this.tabPosition === 'one') {
                    return _.filter(this.house.houseTypes, (item) => item.roomCount === 1)
                }
                if (this.tabPosition === 'two') {
                    return _.filter(this.house.houseTypes, (item) => item.roomCount === 2)
                }
                if (this.tabPosition === 'three') {
                    return _.filter(this.house.houseTypes, (item) => item.roomCount === 3)
                }
                if (this.tabPosition === 'four') {
                    return _.filter(this.house.houseTypes, (item) => item.roomCount === 4)
                }
                if (this.tabPosition === 'more') {
                    return _.filter(this.house.houseTypes, (item) => item.roomCount > 4)
                }
                return this.house.houseTypes
            }
        },
        methods: {
            saveHouseType() {
                this.$refs['editHouseTypeForm'].validate((valid) => {
                    if (valid) {
                        this.dialogHouseTypeVisible = false
                        if (this.editHouseTypeForm.id) {
                            houseService.updateHouseType(this.house.id, this.editHouseTypeForm.id, this.editHouseTypeForm).then((response) => {
                                let index = _.findLastIndex(this.house.houseTypes, type => type.id === response.data.id)
                                this.house.houseTypes.splice(index, 1, response.data)
                            })
                        } else {
                            houseService.addHouseType(this.house.id, this.editHouseTypeForm).then((response) => {
                                this.house.houseTypes.push(response.data)
                            })
                        }
                    } else {
                        return false;
                    }
                });
            },
            openHouseTypeDialog(type) {
                this.dialogHouseTypeVisible = true
                this.$refs['editHouseTypeForm'] && this.$refs['editHouseTypeForm'].clearValidate()
                this.editHouseTypeForm = _.cloneDeep(type) || {}
            },
            deleteHouseType(typeId, index) {
                houseService.deleteHouseType(this.house.id, typeId).then(() => {
                    this.house.houseTypes.splice(index, 1)
                })
            },
            handleImageScucess(result) {
                houseService.addHouseImage(this.house.id, {'imageUrl': result.key}).then((response) => {
                    this.house.normalImages.push(response.data)
                })
            },
            deleteImage(imageId, index) {
                houseService.deleteHouseImage(this.house.id, imageId).then(() => {
                    this.house.normalImages.splice(index, 1)
                })
            },
            imageMoved(event) {
                if (event.newIndex < 0 || event.newIndex === event.oldIndex) {
                    return false;
                }
                houseService.sortHouseImage(this.house.id, this.house.normalImages[event.newIndex].id, event.newIndex)
            }
        }
    }
</script>

<style lang="scss" scoped>
    @mixin title {
        .title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
        }
    }
    .base-info {
        .address {
            color: grey;
        }
        .price {
            color: red;
            font-size: 18px;
        }
        .info {
            padding: 20px 0;
            border-bottom: solid 1px #e6e6e6;
            .title {
                color: grey;
            }
        }
    }
    .extend-info {
        padding: 20px 0;
        border-bottom: solid 1px #e6e6e6;
        .el-row {
            margin-bottom: 15px;
            .title {
                color: grey;
            }
        }
    }
    .images {
        padding: 20px 0;
        @include title;
        .el-upload-list--picture-card .el-upload-list__item-actions:hover {
            cursor: move;
        }
    }
    .description {
        padding: 10px 0;
        border-top: solid 1px #e6e6e6;
        border-bottom: solid 1px #e6e6e6;
    }
    .position {
        margin-top: 20px;
        @include title;
        .no-data {
            color: grey;
        }
        .item {
            display: inline-block;
            margin-right: 10px;
        }
    }
</style>
