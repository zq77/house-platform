<template>
    <div class="common-list">
        <div class="ctrl-filter">
            <div>
                <div class="filter">
                    <el-input placeholder="请输入关键字" v-model="keywords" @keyup.enter.native="loadData(1)">
                        <i slot="suffix" class="el-input__icon el-icon-search"></i>
                    </el-input>
                </div>
            </div>
        </div>
        <el-table :data="tableData" border fit class="single-table-select" ref="singleTable" highlight-current-row @current-change="handleCurrentChange">
            <el-table-column prop="name" label="门店名称"></el-table-column>
            <el-table-column prop="contactName" label="联系人"></el-table-column>
            <el-table-column prop="contactPhone" label="联系电话"></el-table-column>
            <el-table-column prop="openDate" label="开店时间"></el-table-column>
            <el-table-column prop="address" label="地址"></el-table-column>
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
            },
            handleCurrentChange(val) {
                this.$emit('setSelectedStore', val);
            }
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    .single-table-select .el-table__body-wrapper .el-table__row {
        cursor: pointer;
    }
</style>
