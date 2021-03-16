<template>
    <div>
        <el-dialog title="创建门店" :visible.sync="createStoreVisible">
            <el-form :model="storeForm" :rules="rules" ref="storeForm" label-width="100px" class="demo-ruleForm" label-position="top">
                <el-form-item label="名称" prop="name" :error="errors.name">
                    <el-input v-model="storeForm.name"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                    <el-button @click="createStoreVisible = false">取消</el-button>
                    <el-button type="primary" @click="saveStore">保存</el-button>
                </span>
        </el-dialog>
    </div>
</template>

<script>
    import storeService from '@/api/store'
    import errorMixin from '@/mixins/error'

    export default {
        name: "create-store",
        mixins: [errorMixin],
        data() {
            return {
                createStoreVisible: false,
                storeForm: {},
                rules: {
                    name: [
                        { required: true, message: '请输入门店名称', trigger: 'blur' }
                    ],
                }
            }
        },
        created() {
            this.$eventHub.$on('create:store', () => {
                this.createStoreVisible = true
                this.storeForm = {}
                this.errors = []
            })
        },
        beforeDestroy() {
            this.$eventHub.$off('create:store')
        },
        methods: {
            saveStore() {
                let onSuccess = (response) => {
                    this.$message.success('保存成功')
                    this.createStoreVisible = false
                    this.$eventHub.$emit('create:store:success', response.data)
                }
                this.$refs['storeForm'].validate((valid) => {
                    if (valid) {
                        storeService.create(this.storeForm, onSuccess, this.showErrorField)
                    } else {
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>