<template>
    <div>
        <el-form :model="newsForm" :rules="rules" ref="newsForm" label-width="100px" class="news-form" label-position="top">
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="封面图片" prop="coverImage">
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
                        <v-upload v-else accept="image/png, image/jpeg" :drag=true :on-success="handleImageScucess" class="upload-drag">
                            <i class="el-icon-upload"></i>
                            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                            <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
                        </v-upload>
                        <el-input type="hidden" v-model="coverImageKey" class="cover-image-input"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="标题" prop="title">
                        <el-input v-model="newsForm.title"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="作者" prop="author">
                        <el-input v-model="newsForm.author"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="发布日期" prop="publishDate">
                        <el-date-picker v-model="newsForm.publishDate" type="datetime" value-format="yyyy-MM-dd hh:mm:ss" placeholder="请选择发布日期"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="浏览次数" prop="viewTimes">
                        <el-input v-model.number="newsForm.viewTimes"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="8" :md="6">
                    <el-form-item label="点赞次数" prop="likeTimes">
                        <el-input v-model.number="newsForm.likeTimes"></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
            <el-row :gutter="16">
                <el-col :xs="24" :sm="24" :md="18">
                    <el-form-item label="内容" prop="content">
                        <vue-ueditor-wrap v-model="newsForm.content" :config="myConfig"></vue-ueditor-wrap>
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
    import newsService from '@/api/news'
    import SingleImage from '@/components/Upload/singleImage2.vue'
    import VUpload from "@/components/Upload/v-upload.vue"
    import VueUeditorWrap from 'vue-ueditor-wrap'

    export default {
        mixins: [errorMixin],
        components: {
            'v-upload': VUpload,
            'single-image': SingleImage,
            VueUeditorWrap
        },
        data() {
            return {
                username: this.$currentUser.username,
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
            const newsId = this.$route.params.newsId;
            if (newsId) {
                newsService.get(newsId, (response) => {
                    this.newsForm = response.data;
                    if (this.username != this.newsForm.creator) {
                        this.newsForm.id = null;
                    }
                    this.coverImageKey = this.newsForm.coverImage;
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
