<template>
    <div class="upload-container">
        <div class="image-preview" v-if="value">
            <div class="image-preview-wrapper">
                <img :src="imageUrl+'?imageView2/1/w/200/h/200'">
                <div class="image-preview-action">
                    <i @click="rmImage" class="el-icon-delete"></i>
                </div>
            </div>
        </div>
        <v-upload class-name="image-uploader" drag :show-file-list="false" accept="image/png, image/jpeg"
                  :on-success="handleImageScucess" :on-error="handleImageError" v-else>
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        </v-upload>
    </div>
</template>

<script>
    import VUpload from "@/components/Upload/v-upload.vue";
    import {QINIU_WEBSITE} from '@/utils/constants'

    export default {
        name: 'singleImageUpload',
        props: {
            value: String
        },
        components: {
            'v-upload': VUpload,
        },
        computed: {
            imageUrl() {
                return QINIU_WEBSITE + '/' + this.value
            }
        },
        data() {
            return {
            }
        },
        methods: {
            rmImage() {
                this._emitInput('')
            },
            _emitInput(val) {
                this.$emit('input', val)
            },
            handleImageScucess(response) {
                this._emitInput(response.key)
            },
            handleImageError() {
                this.$message.error('上传图片失败，请刷新后重试');
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .upload-container {
        width: 100%;
        position: relative;
        &:after {
            content: "";
            display: table;
            clear: both;
        }
        .image-uploader {
            width: 100%;
            float: left;
        }
        &.my-center {
            .image-preview {
                width: 100px;
                height: 100px;
                border-radius: 50%;
                float: none;
                border: none;
                margin: 0 auto;
                .image-preview-wrapper {
                    img {
                        border-radius: 50%;
                    }
                }
                .image-preview-action {
                    line-height: 100px;
                    .el-icon-delete {
                        font-size: 22px;
                    }
                }
            }
        }
        .image-preview {
            width: 200px;
            height: 200px;
            position: relative;
            border: 1px dashed #d9d9d9;
            float: left;
            .image-preview-wrapper {
                position: relative;
                width: 100%;
                height: 100%;
                img {
                    width: 100%;
                    height: 100%;
                }
            }
            .image-preview-action {
                position: absolute;
                width: 100%;
                height: 100%;
                left: 0;
                top: 0;
                cursor: default;
                text-align: center;
                color: #fff;
                opacity: 0;
                font-size: 20px;
                background-color: rgba(0, 0, 0, .5);
                transition: opacity .3s;
                cursor: pointer;
                text-align: center;
                line-height: 200px;
                .el-icon-delete {
                    font-size: 36px;
                }
            }
            &:hover {
                .image-preview-action {
                    opacity: 1;
                }
            }
        }
    }

</style>
