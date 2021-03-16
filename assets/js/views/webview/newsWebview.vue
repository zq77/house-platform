<template>
    <div class="news-webview">
        <el-form :model="newsForm" :rules="rules" ref="newsForm" label-width="100px" class="news-form" label-position="top">
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="" prop="coverImage" class="news-cover">
                        <ul v-if="coverImageKey" class="el-upload-list el-upload-list--picture-card">
                            <li tabindex="0" class="el-upload-list__item is-success">
                                <img :src="coverImageKey + '?imageView2/1/w/200/h/150' | imagePrefix" alt="loading..." class="el-upload-list__item-thumbnail"/>
                                <i class="el-icon-close"></i>
                                <span class="el-upload-list__item-actions">
                                    <span class="el-upload-list__item-delete" @click="deleteImage">
                                        <i class="el-icon-delete"></i>
                                    </span>
                                </span>
                            </li>
                        </ul>
                        <v-upload v-else accept="image/png, image/jpeg, image/jpg" :drag=true :on-success="handleImageScucess" class="upload-drag">
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">封面图片<em>点击上传</em></div>
                            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
                        </v-upload>
                        <el-input type="hidden" v-model="coverImageKey" class="cover-image-input"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="" prop="title">
                        <el-input v-model="newsForm.title" placeholder="标题"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="" prop="author">
                        <el-input v-model="newsForm.author" placeholder="作者"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="" prop="publishDate">
                        <el-date-picker v-model="newsForm.publishDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="请选择发布日期"></el-date-picker>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="24" :md="18">
                    <el-form-item label="" prop="content">
                        <vue-ueditor-wrap v-model="newsForm.content" :config="myConfig"></vue-ueditor-wrap>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-form-item>
                <el-button type="primary" @click="submitForm()">保存</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>
    import errorMixin from '@/mixins/error'
    import newsService from '@/api/newsWebview'
    import VUpload from "@/components/Upload/v-upload.vue"
    import VueUeditorWrap from 'vue-ueditor-wrap'

    export default {
        mixins: [errorMixin],
        components: {
            'v-upload': VUpload,
            VueUeditorWrap
        },
        props: {
            id: String,
            userid: String,
            username: String
        },
        data() {
            return {
                myConfig: {
                    // 编辑器不自动被内容撑高
                    autoHeightEnabled: false,
                    // 初始容器高度
                    initialFrameHeight: 800,
                    // 初始容器宽度
                    initialFrameWidth: '100%',
                },
                newsForm: {},
                coverImageKey: '',
                rules: {
                    coverImage: [
                        { required: true, message: '请上传封面图片', trigger: 'blur' }
                    ],
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' }
                    ],
                    author: [
                        { required: true, message: '请输入作者', trigger: 'blur' },
                    ],
                    publishDate: [
                        { required: true, message: '请输入发布时间', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' }
                    ],
                    viewTimes: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ],
                    likeTimes: [
                        { type: 'number', message: '请输入数字', trigger: 'change' }
                    ]
                },
            };
        },
        created() {
            const newsId = this.id;
            if (newsId) {
                newsService.get(newsId, (response) => {
                    this.newsForm = response.data;
                    if (this.username != this.newsForm.creator) {
                        this.newsForm.id = null;
                    }
                    this.newsForm.userId = this.userid;
                    this.coverImageKey = this.newsForm.coverImage;
                })
            }
        },
        mounted() {
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
                this.$refs['newsForm'].validate((valid) => {
                    if (valid) {
                        if (this.newsForm.id) {
                            newsService.update(this.newsForm.id, this.newsForm, onSuccess, onError)
                        } else {
                            newsService.create(this.newsForm, onSuccess, onError)
                        }
                    } else {
                        this.$message.error('保存失败')
                        return false;
                    }
                });
            },
            handleImageScucess(result) {
                this.coverImageKey = result.key;
                this.newsForm.coverImage = result.key;
            }, 
            deleteImage() {
                this.coverImageKey = '';
                this.newsForm.coverImage = '';
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
    .news-webview {
        .cover-image-input {
            display: none;
        }
        .el-form-item__content {
            line-height: 1;
        }
        .el-upload-list__item {
            width: 100%;
            height: 150px;
            margin: 0;
            margin-bottom: -15px;
        }
    }
    
</style>

<style rel="stylesheet/scss" lang="scss">
    .news-webview {
        padding: 10px;
    }
    .news-form .el-upload-dragger {
        min-width: 100%;
        height: 150px;
        .el-icon-upload {
            margin-top: 25px;
        }
    }
    .body-news-webview {
        .el-message {
            min-width: 90%;
        }
    }
</style>
