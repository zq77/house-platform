<template>
    <div>
        <div class="ctrl-filter">
            <router-link to="/customer/add">
                <el-button type="primary">新增</el-button>
            </router-link>
            <div>
                <div class="filter">
                    <el-input placeholder="请输入关键字" v-model="keywords" @keyup.enter.native="loadData(1)">
                        <i slot="suffix" class="el-input__icon el-icon-search"></i>
                    </el-input>
                </div>
                <el-radio-group v-model="customerSearchType" size="medium" class="grab-website" @change="loadData(1)">
                    <el-radio-button label="">全部</el-radio-button>
                    <el-radio-button  v-for="item in customerSearchTypeList" :key="item.value" :label="item.value">
                        {{ item.key }}
                    </el-radio-button>
                </el-radio-group>
            </div>
        </div>
        <el-table :data="tableData" border fit highlight-current-row style="width: 100%">
            <el-table-column prop="name" label="姓名" width="150"></el-table-column>
            <el-table-column prop="level" label="等級" width="100"></el-table-column>
            <el-table-column label="求购">
                <template slot-scope="scope">
                    <span>{{scope.row.houseNeedStr}}</span>
                    <span>{{scope.row.houseName}}</span>
                    <span>{{scope.row.areaName}}</span>
                    <span class="detail-field">
                        <span v-if="scope.row.minArea">{{scope.row.minArea}}</span><span v-if="scope.row.minArea && scope.row.maxArea" class="intro-split">-</span><span 
                        v-if="scope.row.maxArea">{{scope.row.maxArea}}</span><span v-if="scope.row.minArea || scope.row.maxArea">平</span>
                    </span>
                    <span class="detail-field">
                        <span v-if="scope.row.minPrice">{{scope.row.minPrice}}</span><span v-if="scope.row.minPrice && scope.row.maxPrice" class="intro-split">-</span><span 
                        v-if="scope.row.maxPrice">{{scope.row.maxPrice}}</span><span v-if="scope.row.minPrice || scope.row.maxPrice">万</span>
                    </span>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="120">
                <template slot-scope="scope">
                    <router-link :to="'/customer/' + scope.row.id">
                        <el-button type="text" size="small">编辑</el-button>
                    </router-link>
                    <el-button @click.stop="deleteCustomer(scope.row.id)" type="text" size="small">删除</el-button>
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
    import customerService from '@/api/customer'
    import { customerSearchTypeList, houseNeedList } from '@/utils/constants'

    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                customerSearchTypeList,
                tableData: [],
                keywords: '',
                customerSearchType: ''
            }
        },
        computed: {
        },
        created() {
            this.loadData();
        },
        methods: {
            deleteCustomer(id) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    customerService.delete(id, () => {
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
                    customerSearchType: this.customerSearchType
                }
                customerService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                    if (this.tableData && this.tableData.length > 0) {
                        this.tableData.forEach(item => {
                            // house need
                            for (let i = 0; i < houseNeedList.length; i++) {
                                if (item.houseNeed == houseNeedList[i].value) {
                                    item.houseNeedStr = houseNeedList[i].key;
                                    break;
                                }
                            }
                        });
                    }
                })
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
