<template>
    <div>
        <div class="ctrl-filter">
            <router-link to="/store/add">
                <el-button type="primary">新增</el-button>
            </router-link>
            <div>
                <div class="filter">
                    <el-input placeholder="请输入关键字" v-model="keywords" @keyup.enter.native="loadData(1)">
                        <i slot="suffix" class="el-input__icon el-icon-search"></i>
                    </el-input>
                </div>
            </div>
        </div>
        <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
            <el-table-column prop="name" label="门店名称"></el-table-column>
            <el-table-column prop="contactName" label="联系人"></el-table-column>
            <el-table-column prop="contactPhone" label="联系电话"></el-table-column>
            <el-table-column prop="openDate" label="开店时间"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
            <el-table-column fixed="right" label="操作" width="140">
                <template slot-scope="scope">
                    <router-link :to="'/store/' + scope.row.id">
                        <el-button type="text" size="small">编辑</el-button>
                    </router-link>
                    <el-button @click="deleteStore(scope.row.id)" type="text" size="small">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @current-change="loadData"
                :current-page.sync="currentPage"
                :page-size="pageSize"
                layout="prev, pager, next, jumper"
                :total="totalSize">
        </el-pagination>
    </div>
</template>

<script>
    import paginationMixin from '@/mixins/pagination'
    import storeService from '@/api/store'

    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                keywords: ''
            }
        },
        computed: {
        },
        created() {
            this.loadData();
        },
        methods: {
            deleteStore(id) {
                this.$confirm('您确定要删除该门店吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    storeService.delete(id, () => {
                        this.$message({type: 'success', message: '删除成功!'});
                        this.loadData()
                    })
                }).catch(() => {
                    this.$message({type: 'error', message: '删除失败'});
                });
            },
            loadData(page) {
                let params = {
                    currentPage: page ? page : this.currentPage,
                    pageSize: this.pageSize,
                    keywords: this.keywords
                }
                storeService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
