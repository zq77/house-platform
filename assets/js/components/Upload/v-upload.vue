<template>
    <el-upload action="//upload.qiniup.com" :class="className" :file-key="fileKey" :data="dataObj"
            :list-type="listType" :accept="accept" :multiple="multiple" :show-file-list="showFileList"
            :drag="drag" :thumbnail-mode="thumbnailMode" :auto-upload="autoUpload" :disabled="disabled"
            :on-preview="onPreview" :limit="limit"
            :on-remove="onRemove" :on-change="onChange" :on-exceed="onExceed"
            :before-upload="handleBeforeUpload" :on-progress="onProgress"
            :on-success="onSuccess" :on-error="handleOnError">
        <slot></slot>
    </el-upload>
</template>

<script>
    import uploadService from '@/api/upload'

    export default {
        name: "v-upload",
        props: {
            fileKey: String,
            className: String,
            listType: {
                type: String,
                default: 'text'
            },
            limit: Number,
            accept: String,
            multiple: {
                type: Boolean,
                default: false
            },
            showFileList: {
                type: Boolean,
                default: false
            },
            drag: {
                type: Boolean,
                default: false
            },
            thumbnailMode: {
                type: Boolean,
                default: false
            },
            autoUpload: {
                type: Boolean,
                default: true
            },
            disabled: {
                type: Boolean,
                default: false
            },
            beforeUpload: Function,
            onPreview: Function,
            onChange: Function,
            onRemove: Function,
            onProgress: Function,
            onSuccess: Function,
            onExceed: Function,
            onError: Function
        },
        data() {
            return {
                dataObj: { token: '', key: '' }
            }
        },
        methods: {
            handleBeforeUpload() {
                const _self = this
                
                let onSuccess =  (response, resolve) => {
                    _self._data.dataObj.token = response.body.token
                    _self._data.dataObj.key = response.body.key
                    _self.beforeUpload && _self.beforeUpload()
                    resolve(true)
                }

                return new Promise((resolve, reject) => {
                    if (_self.fileKey) {
                        uploadService.getTokenByFilekey(_self.fileKey, (response) => {
                            onSuccess(response, resolve);
                        }, err => {
                            reject(false)
                        })
                    } else {
                        uploadService.getToken((response) => {
                            onSuccess(response, resolve);
                        }, err => {
                            reject(false)
                        })
                    }
                })
            },
            handleOnError() {
                if (this.onError) {
                    return this.onError()
                } else {
                    this.$message.error('上传失败，请刷新后重试');
                }
            }
        }
    }
</script>

<style scoped>

</style>