<template>
    <div class="common-list">
        <div class="ctrl-filter">
            <div>
                <div class="filter">
                    <el-select v-model="conditions.city" placeholder="全部城市" filterable clearable @change="loadData(1)">
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
        <el-table :data="tableData" border fit class="single-table-select" ref="singleTable" highlight-current-row @current-change="handleCurrentChange">
            <el-table-column label="名称" width="180">
                <template slot-scope="scope">
                    <span>{{ scope.row.name }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="均价"></el-table-column>
            <el-table-column prop="location" label="地址"></el-table-column>
            <el-table-column prop="developers" label="开发商"></el-table-column>
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
    import houseService from '@/api/house'
    import locationService from '@/api/location'


    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                cities: [],
                conditions: {}
            }
        },
        computed: {
        },
        created() {
            this.loadData()
            locationService.getCities((response) => {
                this.cities = response.data
            })
        },
        methods: {
            loadData(page) {
                let params = {
                    page: page ? page : this.currentPage,
                    size: this.pageSize,
                    status: 'INIT',
                    city: this.conditions.city,
                    keywords: this.conditions.keywords,
                }
                houseService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            },
            handleCurrentChange(val) {
                this.$emit('setSelectedHouse', val);
            }
            
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss">
    .single-table-select .el-table__body-wrapper .el-table__row {
        cursor: pointer;
    }
</style>
