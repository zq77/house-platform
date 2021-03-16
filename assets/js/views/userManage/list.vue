<template>
    <div>
        <div class="ctrl-filter">
            <router-link to="/users/add">
                <el-button type="primary">新增</el-button>
            </router-link>
            <div>
                <div class="filter">
                    <el-select v-model="conditions.city" placeholder="全部" clearable @change="loadData(1)">
                        <el-option v-for="city in cities" :key="city.code" :label="city.name" :value="city.code"></el-option>
                    </el-select>
                </div>
                <div class="filter">
                    <el-input placeholder="请输入关键字" v-model="conditions.keywords" @keyup.enter.native="loadData(1)">
                        <i slot="suffix" class="el-input__icon el-icon-search"></i>
                    </el-input>
                </div>
            </div>
        </div>
        <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
            <el-table-column prop="name" label="名称" width="180"></el-table-column>
            <el-table-column label="价格">
                <template slot-scope="scope">
                    <div>{{ scope.row.total_price }}</div>
                    <div>{{ scope.row.price }} 元/平米</div>
                </template>
            </el-table-column>
            <el-table-column label="面积">
                <template slot-scope="scope">
                    <div>{{ scope.row.floorage }} 平米</div>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <router-link :to="'/resold-house/' + scope.row.id">
                        <el-button type="text" size="small">编辑</el-button>
                    </router-link>
                    <el-button @click="deleteHouse(scope.row)" type="text" size="small">删除</el-button>
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
    import userService from '@/api/user'

    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                conditions: {}
            }
        },
        computed: {
        },
        created() {
            this.loadData()
        },
        methods: {
            deleteHouse(row) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    resoldHouseService.delete(row.id, () => {
                        this.$message({type: 'success', message: '删除成功!'});
                        this.loadData()
                    })
                }).catch(() => {
                    this.$message({type: 'info', message: '已取消删除'});
                });
            },
            loadData(page) {
                let params = {
                    page: page ? page : this.currentPage,
                    size: this.pageSize,
                    keywords: this.conditions.keywords,
                }
                userService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
