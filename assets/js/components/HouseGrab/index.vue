<template>
    <el-dialog
        :title="title"
        :visible.sync="visible"
        :before-close="cancelDialog"
        :width="width">
        <el-form label-width="100px" :model="grabForm" ref="grabForm" :rules="rules" label-position="right">
            <el-row>
                <el-form-item label="选择城市:" prop="cityCode">
                    <el-select v-model="grabForm.cityCode" placeholder="全部" filterable clearable>
                        <el-option v-for="city in cities" :key="city.code" :label="city.name" :value="city.code"></el-option>
                    </el-select>
                </el-form-item>
            </el-row>
             <el-row>
                <el-form-item label="选择网站:" class="grab-website" prop="webSiteName">
                    <el-button v-for="grabWebsite in grabWebsites" :key="grabWebsite.value"  type="primary" 
                            :class="websiteBtn(grabWebsite.value)" 
                            @click="selectWebsite(grabWebsite.value)" >
                        {{ grabWebsite.name }}
                    </el-button>
                    <el-input type="hidden" v-model="grabForm.webSiteName" class="hidden-input"></el-input>
                </el-form-item>
            </el-row>
            <el-row>
                <el-col :xs="24" :sm="12" :md="12">
                    <el-form-item label="起始页数:" prop="startPageNum">
                        <el-input v-model.number="grabForm.startPageNum"><template slot="suffix">页</template> </el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="12" :md="12">
                    <el-form-item label="结束页数:" prop="endPageNum">
                        <el-input v-model.number="grabForm.endPageNum"><template slot="suffix">页</template> </el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="cancelDialog">取 消</el-button>
            <el-button type="primary" @click="handGrabHouseData">确 定</el-button>
        </span>
    </el-dialog>
</template>

<script>
    import { grabWebsites } from '@/utils/constants';
    import locationService from '@/api/location';
    import spiderService from '@/api/spider';

    export default {
        name: 'house-grab-dialog',
        props: {
            title: {
                type: String,
                default: '房屋数据采集'
            },
            visible: {
                type: Boolean,
                default: false
            },
            width: {
                type: String,
                default: '580px'
            },
            isNewHouse: {
                type: Boolean,
                default: false
            }
        },
        data: function () {
            return {
                cities: [],
                grabWebsites: grabWebsites,
                grabForm: {},
                webSiteName: '',
                rules: {
                    cityCode: [
                        { required: true, message: '请选择采集城市', trigger: 'change' }
                    ],
                    webSiteName: [
                        { required: true, message: '请选择采集网站', trigger: 'change' }
                    ],
                    startPageNum: [
                        { required: true, message: '请输入采集起始页数', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ],
                    endPageNum: [
                        { required: true, message: '请输入采集结束页数', trigger: 'change' },
                        { type: 'number', message: '请输入数字', trigger: 'blur' }
                    ]
                }
            }
        },
        computed: {
            websiteBtn: function() {
                return function(value) {
                    const btnClass = {};
                    btnClass['btn-' + value] = true;
                    btnClass.active = value === this.webSiteName;
                    return btnClass;
                };
            }
        },
        created() {
            locationService.getCities((response) => {
                this.cities = response.data
            })
        },
        methods: {
            handGrabHouseData() {
                let onSuccess = () => {
                    this.$emit('grabSuccess');
                }
                let onError = (response) => {
                    this.$emit('grabfailed');
                }
                this.$refs['grabForm'].validate((valid) => {
                    if (valid) {
                        this.$emit('grabStarting');
                        this.grabForm.ifNewHouse = this.isNewHouse;
                        if (this.isNewHouse) {
                            spiderService.grabHouseData(this.grabForm, onSuccess, onError);
                        } else {
                            spiderService.grabResoldHouseData(this.grabForm, onSuccess, onError);
                        }
                    } else {
                        return false;
                    }
                });
                
            },
            selectWebsite(name) {
                this.webSiteName = name;
                this.grabForm.webSiteName = name;
            },
            cancelDialog() {
                this.$emit('setVisible', false);
            }
        },
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .grab-website {
        .btn-LJ {
            background-color: #fff;
            border-color: #3baa6a;
            color: #3baa6a;
            &.active, &:hover {
                background-color: #3baa6a;
                border-color: #3baa6a;
                color: #fff;
            }
        }
        .btn-BK {
            background-color: #fff;
            border-color: #3072f6;
            color: #3072f6;
            &.active, &:hover {
                background-color: #3072f6;
                border-color: #3072f6;
                color: #fff;
            }
        }
        .btn-AJK {
            background-color: #fff;
            border-color: #62AB00;
            color: #62AB00;
            &.active, &:hover {
                background-color: #62AB00;
                border-color: #62AB00;
                color: #fff;
            }
        }
        .btn-YYHF {
            background-color: #fff;
            border-color: #f24f00;
            color: #f24f00;
            &.active, &:hover {
                background-color: #f24f00;
                border-color: #f24f00;
                color: #fff;
            }
        }
        .hidden-input {
            display: none;
        }
    }
</style>