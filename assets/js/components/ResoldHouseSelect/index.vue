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
            <el-table-column label="小区名称" width="180">
                <template slot-scope="scope">
                    <span>{{ scope.row.name }}</span>
                </template>
            </el-table-column>
            <el-table-column label="价格">
                <template slot-scope="scope">
                    <div>{{ scope.row.totalPrice ? scope.row.totalPrice + '万元' : '' }}{{ scope.row.price ? ' / ' + scope.row.price + '元/平米' : ''}} </div>
                </template>
            </el-table-column>
            <el-table-column label="面积">
                <template slot-scope="scope">
                    <div>{{ scope.row.floorage }} 平米</div>
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
    import resoldHouseService from '@/api/resoldHouse'
    import locationService from '@/api/location'

    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                cities: [],
                conditions: {},
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
                    status: 'INIT',
                    size: this.pageSize,
                    city: this.conditions.city,
                    keywords: this.conditions.keywords,
                }
                resoldHouseService.search(params, (response) => {
                    this.tableData = response.data.content
                    _.each(this.tableData, item => {
                        item.address = `${item.provinceStr} ${item.cityStr} ${item.areaStr} ${item.streetStr} ${item.address}`
                    })
                    this.totalSize = response.data.total
                })
            },
            handleCurrentChange(val) {
                this.$emit('setSelectedResoldHouse', val);
            }
        }
    }
</script>
<style rel="stylesheet/scss" lang="scss">
    .single-table-select .el-table__body-wrapper .el-table__row {
        cursor: pointer;
    }
</style>
