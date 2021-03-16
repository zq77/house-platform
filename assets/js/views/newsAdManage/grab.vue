<template>
    <el-row :gutter="24" v-loading.body="loading">
        <el-col :span="12" :offset="6">
            <div class="news-ad-grab">
                <h2 class="publish-step-title">热文一键植入广告</h2>
                <div class="publish-step">
                    <div class="step">
                        <div class="step-num">1</div>
                        <div>复制文章链接</div>
                        <div class="text-samll">(支持链接：公众号、今日头条)</div>
                    </div>
                    <div class="step">
                        <div class="step-num">2</div>
                        <div>粘贴文章链接</div>
                        <div class="grab-opt">
                            <input class="link-input" placeholder="将复制的文章链接粘贴到这里" :autofocus="true" v-model="grabUrl" />
                            <el-button class="publish-btn" type="primary" @click="handleInputChange">发布</el-button>
                        </div>
                    </div>
                    <div class="step">
                        <div class="step-num">3</div>
                        <div>发布广告</div>
                    </div>
                </div>
            </div>
        </el-col>
    </el-row>
</template>

<script>
    import errorMixin from '@/mixins/error'
    import newsService from '@/api/news'

    export default {
        mixins: [errorMixin],
        data() {
            return {
                grabUrl: '',
                loading: false,
            };
        },
        created() {
            
        },
        methods: {
            handleInputChange() {
                const inputUrl = this.grabUrl;
                const gzhDomain = "mp.weixin.qq.com";
                const jrttDomain = "toutiao.com";
                const siteUrlReg = new RegExp('^(http|https):\/\/.+');
                if (siteUrlReg.test(inputUrl) && (inputUrl.indexOf(gzhDomain) >= 1 || inputUrl.indexOf(jrttDomain) >= 1)) {
                    this.grabUrl = "";
                    this.loading = true;
                    this.grabNews(inputUrl);
                } else {
                    this.$message.warning('请输入符合要求的链接');
                }
            },
            grabNews(grabUrl) {
                let onSuccess = (res) => {
                    const news = res.data;
                    this.$message.success('操作成功');
                    this.loading = false;
                    const userId = this.$currentUser.id;
                    window.open(`/share/${userId}/news/${news.id}`, '_blank');
                }
                let onError = (response) => {
                    this.$message.error('操作失败');
                    this.loading = false;
                }
                newsService.getGrabNews(grabUrl, onSuccess, onError)
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .publish-step-title {
        text-align: center;
    }
    .publish-step {
        margin-top: 50px;
    }

    .publish-step .step {
        padding-bottom: 20px;
    }

    .step .text-samll {
        font-size: 14px;
        color: #666;
        margin-top: 10px;
    }

    .publish-step .step {
        position: relative;
        display: flex;
        flex-direction: column;
        font-size: 16px;
        color: #666;
        padding-bottom: 25px;
        padding-left: 25px;
        border-left: 1px dashed rgba(242,163,39, 1);
    }
    .publish-step .step:last-child {
        border-left: none;
    }
    .publish-step .step .grab-opt {
        width: 100%;
    }
    .publish-step .step .link-input {
        box-sizing: border-box;
        margin-top: 10px;
        width: 80%;
        display: inline-block;
        height: 40px;
        border: 1px solid rgba(242,163,39, 1);
        padding: 3px 10px;
        outline: none;
    }
    .publish-step .step .publish-btn {
        height: 40px;
        background: rgba(242,163,39, 1);
        border-color: rgba(242,163,39, 1);
    }
    .publish-step .step .grab-btn {
        width: 80px;
        display: inline-block;
    }
    .publish-step .step .link-input:focus {
        border: 1px solid rgba(242,163,39, 1);
    }
    .link-input::-webkit-input-placeholder {
        font-size: 14px;
        text-align: center;
        color: rgba(242,163,39, 1);
    }
    .link-input:-moz-placeholder {
        font-size: 14px;
        text-align: center;
        color: rgba(242,163,39, 1);
    }
    .link-input:-ms-input-placeholder {
        font-size: 14px;
        text-align: center;
        color: rgba(242,163,39, 1);
    }
    .publish-step .step .step-num {
        position: absolute;
        top: 0;
        left: -10px;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        line-height: 20px;
        text-align: center;
        color: #fff;
        background: rgba(242,163,39, 1);
    }
</style>

