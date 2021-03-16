<template>
    <div>
        <div class="ctrl-filter">
            <router-link to="/news/add">
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
            <el-table-column prop="title" label="标题"></el-table-column>
            <el-table-column prop="author" label="作者" width="200"></el-table-column>
            <el-table-column prop="publishDate" label="发布时间" width="200"></el-table-column>
            <el-table-column prop="viewTimes" label="浏览次数" width="80"></el-table-column>
            <el-table-column prop="likeTimes" label="点赞次数" width="80"></el-table-column>
            <el-table-column fixed="right" label="操作" width="140">
                <template slot-scope="scope">
                    <el-button @click="gotoShareNews(scope.row.id)" type="text" size="small">
                        <el-button type="text" size="small">查看</el-button>
                    </el-button>
                    <router-link :to="'/news/' + scope.row.id">
                        <el-button type="text" size="small">编辑</el-button>
                    </router-link>
                    <el-button @click="deleteNews(scope.row.id)" type="text" size="small">删除</el-button>
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
    import newsService from '@/api/news'

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
            deleteNews(id) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    newsService.delete(id, () => {
                        this.$message({type: 'success', message: '删除成功!'});
                        this.loadData()
                    })
                }).catch(() => {
                    this.$message({type: 'info', message: '已取消删除'});
                });
            },
            loadData(page) {
                let params = {
                    currentPage: page ? page : this.currentPage,
                    pageSize: this.pageSize,
                    keywords: this.keywords,
                    type: 'NORMAL'
                }
                newsService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            },
            gotoShareNews(id) {
                window.open(`/view/news/${id}`, '_blank');
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
