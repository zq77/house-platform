<template>
    <el-row :gutter="24">
        <form id="newsAdForm" methods="post">
            <el-col class="news-ad-template" :span="24">
                <el-tabs v-model="selectedTabName" type="border-card">
                    <el-tab-pane v-for="(tabItem, index) in newsAdTypes"
                        :key="tabItem.name"
                        :label="tabItem.title"
                        :name="tabItem.name"
                        :class="'tab-content-news-ad ' + tabItem.name"
                    >
                        <el-carousel height="300px" arrow="always" indicator-position="none" :autoplay="false" @change="carouselChange" :initial-index="initIndex[tabItem.name]">
                            <el-carousel-item class="ad-edit-item" v-for="(item, index) in newsTypes[tabItem.name]" :key="item.index">
                                <div class="ad-edit img-TW">
                                    <div v-if="newsTypes[tabItem.name].length > 1" class="ad-del-btn" @click="delNewsAdItem(item.type, index)">X</div>
                                    <input type="hidden" :name="'id-'+item.type+'-' + index" :value="item.id"/>
                                    <input type="hidden" :name="'type-'+item.type+'-' + index" :value="item.type"/>
                                    <input type="hidden" :name="'templateType-'+item.type+'-' + index" :value="item.templateType"/>
                                    <input type="hidden" :name="'imageUrl-'+item.type+'-' + index" :value="item.imageUrl"/>
                                    <div class="form-row with-image">
                                        <div class="form-label">添加图片</div>
                                        <div v-if="item.templateType=='TWLJ'||item.templateType=='TWMS'" class="form-content">
                                            
                                            <div class="form-img" :data-key="'imageUrl-'+item.type+'-'+index" bindtap='addImage'>
                                                <v-upload accept="image/png, image/jpeg" :drag=true :on-success="handleImageScucess('imageUrl-'+item.type+'-'+index)" class="upload-drag">
                                                    <div class="bg-img" v-if="item.imageUrl" :style="'background-image: url('+qiNiuImgUrl+item.imageUrl+')'"></div>
                                                    <div v-else class="no-img-text">120*120</div>
                                                </v-upload>
                                            </div>
                                            
                                            
                                            <div class="form-input-list">
                                                <div v-if="item.templateType == 'TWLJ'">
                                                    <input class="form-input"
                                                    :name="'title-'+item.type+'-' + index" :value="item.title" placeholder='请输入描述' @change="handleInpuBlur"/>
                                                    <input class="form-input"
                                                    :name="'intro-'+item.type+'-' + index" :value="item.intro" placeholder='请输入产品或服务名称' @change="handleInpuBlur"/>
                                                </div>
                                                <div v-if="item.templateType == 'TWMS'">
                                                    <input class="form-input"
                                                    :name="'title-'+item.type+'-' + index" :value="item.title" placeholder='请输入您的姓名' @change="handleInpuBlur"/>
                                                    <input class="form-input"
                                                    :name="'intro-'+item.type+'-' + index" :value="item.intro" placeholder='请输入您的联系方式' @change="handleInpuBlur"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div v-else class="form-content">
                                            <div class="form-img img-only" bindtap='addImage'>
                                                <v-upload accept="image/png, image/jpeg" :drag=true :on-success="handleImageScucess('imageUrl-'+item.type+'-'+index)" class="upload-drag">
                                                    <div class="bg-img" v-if="item.imageUrl" :style="'background-image: url('+qiNiuImgUrl+item.imageUrl+')'"></div>
                                                    <div v-else class="no-img-text">建议图片尺寸720*120像素</div>
                                                </v-upload>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="form-row">
                                        <div v-if="item.templateType == 'TPLJ' || item.templateType == 'TWLJ'" class="form-label">添加链接</div>
                                        <div v-if="item.templateType == 'TPMS'" class="form-label">添加电话</div>
                                        <div v-if="item.templateType == 'TWMS'" class="form-label">添加地址</div>
                                        <div class="form-content">
                                            <input v-if="item.templateType=='TPLJ'||item.templateType=='TWLJ'" class="form-input"
                                            :name="'link-'+item.type+'-' + index" :value="item.link" placeholder='请输入您的链接地址' @change="handleInpuBlur"/>
                                            <input v-if="item.templateType == 'TPMS'" class="form-input"
                                            :name="'link-'+item.type+'-' + index" :value="item.link" placeholder='请输入您的联系电话' @change="handleInpuBlur"/>
                                            <input v-if="item.templateType == 'TWMS'" class="form-input"
                                            :name="'text-'+item.type+'-' + index" :value="item.text" placeholder='请输入您的地址' @change="handleInpuBlur"/>
                                        </div>
                                    </div>
                                </div> 
                            </el-carousel-item>
                        </el-carousel>
                        <ul class="dot-list">
                            <li v-for="(item, dotIndex) in newsTypes[tabItem.name]" :key="item.index">
                                <div :class="dotIndex == initIndex[selectedTabName] ? 'dot-item active': 'dot-item'" ></div>
                            </li>
                        </ul>
                        <div class="bottom-info">
                            <div v-if="selectedTabName == 'BOTTOM'" class="check-box">
                                <el-checkbox class="css-fixed-check" v-model="cssFixedCheck">悬浮广告</el-checkbox>
                            </div>
                            <div class="page-text">
                                第{{initIndex[selectedTabName] + 1}}页
                            </div>
                        </div>
                    </el-tab-pane>
                </el-tabs>
                <div class="news-ad-type">
                    <div class="ad-type-label">添加广告类型</div>
                    <div class="ad-type-btn-list">
                        <el-button class="ad-type-btn btn-TPLJ" data-type="TPLJ" @click="addNewsAdTmpl('TPLJ')">通用广告</el-button>
                        <el-button class="ad-type-btn btn-TPMS" data-type="TPMS" @click="addNewsAdTmpl('TPMS')">电话广告</el-button>
                        <el-button class="ad-type-btn btn-TWLJ" data-type="TWLJ" @click="addNewsAdTmpl('TWLJ')">图文广告</el-button>
                        <el-button class="ad-type-btn btn-TWMS" data-type="TWMS" @click="addNewsAdTmpl('TWMS')">名片广告</el-button>
                    </div>
                </div>
                <div class="save-btn">
                    
                    <el-button type="primary" @click="onSubmit">保存</el-button>
                </div>
            </el-col>
        </form>
    </el-row>
</template>

<script>
    import errorMixin from '@/mixins/error'
    import newsService from '@/api/news'
    import {QINIU_WEBSITE} from '@/utils/constants'
    import VUpload from "@/components/Upload/v-upload.vue"
    const TYPE_TOP = "TOP";
    const TYPE_BOTTOM = "BOTTOM";
    export default {
        mixins: [errorMixin],
        components: {
            'v-upload': VUpload
        },
        data() {
            return {
                selectedTabName: 'TOP',
                qiNiuImgUrl: QINIU_WEBSITE + '/',
                newsAdTypes: [{
                    title: '顶部广告',
                    name: TYPE_TOP
                }, {
                    title: '底部广告',
                    name: TYPE_BOTTOM
                }],
                newsTypes: {
                    [TYPE_TOP]: [],
                    [TYPE_BOTTOM]: []
                },
                resNewsAdArray: [],
                activeItemIndex: 0,
                initIndex: {
                    [TYPE_TOP]: 0,
                    [TYPE_BOTTOM]: 0
                },
                cssFixedCheck: false,
            };
        },
        computed: {
        },
        created() {
            this.getNewsAdTmpl();
        },
        methods: {
            carouselChange(currentIndex, preIndex) {
                this.initIndex[this.selectedTabName] = currentIndex;
            },
            initNewsAdTemplates(resNewsAdArray) {
                let topAdArray = [{templateType: 'TWLJ',type: TYPE_TOP}];
                let bottomAdArray = [{templateType: 'TWLJ',type: TYPE_BOTTOM}];
                if (resNewsAdArray && resNewsAdArray.length > 0) {
                    const searchedTopAdArray = resNewsAdArray.filter(item => item.type == TYPE_TOP);
                    if (searchedTopAdArray && searchedTopAdArray.length > 0) {
                        topAdArray = searchedTopAdArray;
                    }
                    
                    const searchedBottomAdArray  = resNewsAdArray.filter(item => item.type == TYPE_BOTTOM);
                    if (searchedBottomAdArray && searchedBottomAdArray.length > 0) {
                        bottomAdArray = searchedBottomAdArray;
                    }
                    this.setBottomFixed(bottomAdArray);
                }
                this.newsTypes = {
                    [TYPE_TOP]: topAdArray,
                    [TYPE_BOTTOM]: bottomAdArray
                }
            },
            setBottomFixed(bottomAdArray) {
                if (bottomAdArray && bottomAdArray.length > 0) {
                    const cssFixed = bottomAdArray[0].cssFixed;
                    this.cssFixedCheck = cssFixed ? true: false
                }
            },
            getNewsAdTmpl() {
                let onSuccess = (res) => {
                    this.initNewsAdTemplates(res.data);
                }
                let onError = (response) => {
                    this.$message.error('获取广告失败');
                }
                newsService.getNewsAdTmpl(onSuccess, onError)
            },
            handleInpuBlur(e) {
                console.log($(e.target).attr('name'));
                const key = $(e.target).attr('name');
                const value = $(e.target).val();
                this.handleNewsAdField(key, value);
            },
            handleNewsAdField(key, value) {
                const keyArray = key.split('-');
                const field = keyArray[0];
                const type = keyArray[1];
                const index = keyArray[2];
                this.newsTypes[type][index][field] = value;
                this.newsTypes = this.newsTypes;
                console.log(this.newsTypes);
            },
            onSubmit() {
                let onSuccess = (res) => {
                    this.$message.success('保存成功');
                }
                let onError = (response) => {
                    this.$message.error('保存失败');
                }
                const newsAdFormArray = $('#newsAdForm').serializeArray();
                console.log(newsAdFormArray);
                const topNewsArray = [];
                const bottomNewsArray = [];
                for(let i= 0; i<newsAdFormArray.length; i++) {
                    const newsAdField = newsAdFormArray[i];
                    const keyArray = newsAdField.name.split('-');
                    const field = keyArray[0];
                    const type = keyArray[1];
                    const index = keyArray[2];
                    if (type == TYPE_TOP) {
                        topNewsArray[index] =  topNewsArray[index] || {};
                        topNewsArray[index][field] = newsAdField.value;
                    } else {
                        bottomNewsArray[index] =  bottomNewsArray[index] || {};
                        bottomNewsArray[index][field] = newsAdField.value;
                    }
                    
                }
                bottomNewsArray.map(item => item.cssFixed = this.cssFixedCheck ? 'true' : null);
                let saveNewsAdFormArray = topNewsArray.concat(bottomNewsArray);
                console.log(saveNewsAdFormArray);
                saveNewsAdFormArray = saveNewsAdFormArray.filter(item => item.imageUrl && item.imageUrl != '');
                newsService.createNewsAdTmpl(JSON.stringify(saveNewsAdFormArray), onSuccess, onError);
            },
            handleImageScucess: function(fieldKey) {
                return result => {
                    // this.handleNewsAdField(fieldKey, result.key);
                    const keyArray = fieldKey.split('-');
                    const field = keyArray[0];
                    const type = keyArray[1];
                    const index = keyArray[2];
                    const topNewsAdArray = this.newsTypes[TYPE_TOP];
                    const bottomNewsAdArray = this.newsTypes[TYPE_BOTTOM];
                    if (type == TYPE_TOP) {
                        topNewsAdArray[index][field] = result.key;
                    } else {
                        bottomNewsAdArray[index][field] = result.key;
                    }
                    this.newsTypes = {
                        [TYPE_TOP]: topNewsAdArray,
                        [TYPE_BOTTOM]: bottomNewsAdArray
                    };
                }
            },
            addNewsAdTmpl(tmplType) {
                const type = this.selectedTabName;
                const newsTypes = this.newsTypes;
                const newsAdArray = newsTypes[type];
                if (newsAdArray.length === 6) {
                    this.$message.warning('最多只能添加6个');
                    return;
                }
                newsAdArray.push({templateType: tmplType, type: type});
                this.initIndex[this.selectedTabName] = newsAdArray.length -1;
                this.newsTypes = newsTypes;
                console.log(this.newsTypes);
            },
            delNewsAdItem(type, delIndex) {
               const newsTypes = this.newsTypes;
               newsTypes[type] = newsTypes[type].filter((item, index) => index != delIndex);
               if (delIndex >= 1) {
                   this.initIndex[this.selectedTabName] = delIndex -1;
               } else {
                   this.initIndex[this.selectedTabName] = 0;
               }
               
               this.newsTypes = newsTypes;
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss">
    .css-fixed-check .el-checkbox__label,
    .css-fixed-check .el-checkbox__input.is-checked+.el-checkbox__label {
        color: #333;
    }
    .el-tabs--border-card>.el-tabs__content {
        background: rgba(64,158,255, 0.5);
    }
    .tab-content-news-ad .el-tabs--border-card>.el-tabs__content {
        background: rgba(64,158,255, 0.5);
    }
    .el-carousel__arrow {
        background: rgba(3, 3, 3, 0.8);
    }
    .el-carousel__arrow:hover {
        background: rgba(3, 3, 3, 0.9);
    }
    .upload-drag .el-upload {
        display: inline-block;
        width: 120px;
        height: 120px;
        text-align: center;
        .el-upload-dragger {
            height: 120px;
            border: none;
            border-radius: 0;
        }
    }
    .img-only .upload-drag .el-upload {
        width: 100%;
    }
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
    .bottom-info .page-text {
        float: right;
    }
    .dot-list {
        text-align: center;
        margin: 0;
        padding: 0;
    }
    .dot-list li {
        display: inline-block;
        list-style: none;
        padding: 0 5px;
    }
    .dot-list li .dot-item {
        width: 5px;
        height: 5px;
        border-radius: 50%;
        background: rgba(3, 3, 3, 0.5);
    }
    .dot-list li .dot-item.active {
        background: rgba(3, 3, 3, 0.9);
    }
    .news-ad-template {
        width: 100%;
        max-width: 786px;
        margin: 0 auto;
        float: none;
    }
    .el-carousel__item {
        background: #fff;
        padding: 15px;
    }
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 150px;
        margin: 0;
    }
    .ad-edit .ad-del-btn {
        position: absolute;
        top: 0;
        right: 0;
        background: rgba(216,69,75,0.8);
        color: #fff;
        width: 25px;
        height: 25px;
        line-height: 25px;
        text-align: center;
        cursor: pointer;
    }
    .form-row .form-label {
        margin-bottom: 10px;
    }
    .form-row .form-content {
        width: 100%;
    }
    .form-row.with-image .form-content {
        height: 120px;
        margin-bottom: 10px;
    }
    .form-row .form-content .form-img {
        float: left;
        width: 120px;
        height: 120px;
        text-align: center;
        line-height: 120px;
        box-sizing: border-box;
        padding: 0;
        border: 1px solid #ececec;
        background: #f4f4f4;
        color: #999;
        cursor: pointer;
        overflow: hidden;
    }
   
    .form-row .form-content .form-img.img-only {
        width: 100%;
    }
    .form-row .form-content .form-input-list {
        float: left;
        box-sizing: border-box;
        width: calc(100% - 120px);
        max-width: 600px;
        padding: 10px;
    }
    .form-content input {
        display: block;
        width: 100%;
        height: 35px;
        padding: 5px 10px;
        margin-bottom: 15px;
    }
    .form-row .form-content .form-img .bg-img {
        width: 100%;
        height: 100%;
        background-repeat: no-repeat;
        background-size: cover;
        background-position: center;
    }
    .save-btn {
        margin-top: 25px;
        text-align: right;
    }
    .btn-TPLJ {
        background: rgba(154,197,96, 1);
    }
    .btn-TPMS {
        background: rgba(154,197,198, 1);
    }
    .btn-TWLJ {
        background: rgba(246,200,145, 1);
    }
    .btn-TWMS {
        background: rgba(247,200,198, 1);
    }
    .news-ad-type {
        margin-top: 15px;
        color: #666;
    }
    .news-ad-type .ad-type-btn-list {
        margin-top: 15px;
    }
    .ad-type-btn-list .ad-type-btn {
        color: #fff;
        border: none;
    }
</style>

