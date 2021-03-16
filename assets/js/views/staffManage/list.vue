<template>
    <div>
        <div class="ctrl-filter">
            <router-link to="/staffs/add" v-if="isStoreAdmin">
                <el-button type="primary">新增</el-button>
            </router-link>
            <div>
                <div class="filter" v-if="isAdmin">
                    <el-select v-model="conditions.storeId" placeholder="全部门店" clearable @change="loadData(1)">
                        <el-option v-for="item in stores" :key="item.id" :label="item.name" :value="item.id"></el-option>
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
            <el-table-column prop="name" label="姓名"></el-table-column>
            <el-table-column label="性别">
                <template slot-scope="scope">
                    <div>{{ scope.row.gender | genderFormat }}</div>
                </template>
            </el-table-column>
            <el-table-column label="手机号">
                <template slot-scope="scope">
                    <div>{{ scope.row.phone }}</div>
                </template>
            </el-table-column>
            <el-table-column label="岗位">
                <template slot-scope="scope">
                    <div>{{ scope.row.role | roleFormat }}</div>
                </template>
            </el-table-column>
            <el-table-column label="门店">
                <template slot-scope="scope">
                    <div>{{ scope.row.storeName }}</div>
                </template>
            </el-table-column>
            <el-table-column label="入职日期">
                <template slot-scope="scope">
                    <div>{{ scope.row.entryDate }}</div>
                </template>
            </el-table-column>
            <el-table-column label="工号">
                <template slot-scope="scope">
                    <div>{{ scope.row.jobNumber }}</div>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <router-link :to="'/staffs/' + scope.row.id">
                        <el-button type="text" size="small">编辑</el-button>
                    </router-link>
                    <el-button @click="deleteStaff(scope.row.id)" type="text" size="small">删除</el-button>
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
    import staffService from '@/api/staff'
    import storeService from '@/api/store'

    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                conditions: {},
                stores: []
            }
        },
        computed: {
            isAdmin() {
                return this.$currentUser.isAdmin;
            },
            isStoreAdmin() {
                return this.$currentUser.isAdmin || this.$currentUser.isStoreAdmin;
            },
        },
        created() {
            this.loadData()
            storeService.searchAll((response) => {
                this.stores = response.data
            })
        },
        methods: {
            deleteStaff(id) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    staffService.delete(id, () => {
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
                    keywords: this.conditions.keywords,
                    storeId: this.conditions.storeId
                }
                staffService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
