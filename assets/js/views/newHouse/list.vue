<template>
    <div>
        <div class="ctrl-filter">
            <div>
                <router-link to="/new-house/add">
                    <el-button type="primary">新增</el-button>
                </router-link>
            </div>
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
                <div class="filter">
                    <el-dropdown  @command="handleBatchAction">
                        <el-button type="primary">
                            批量处理<i class="el-icon-arrow-down el-icon--right"></i>
                        </el-button>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="enable">设置精选</el-dropdown-item>
                            <el-dropdown-item command="disable">取消精选</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </div>
        <el-table :data="tableData" border fit highlight-current-row style="width: 100%" @row-click="goDetail" ref="multipleTable" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="35"></el-table-column>
            <el-table-column label="名称" width="180">
                <template slot-scope="scope">
                    <span v-if="scope.row.isFeatured" class="label-featured">精选</span>
                    <span>{{ scope.row.name }}</span>
                </template>
            </el-table-column>
            <el-table-column prop="price" label="均价"></el-table-column>
            <el-table-column prop="location" label="地址"></el-table-column>
            <el-table-column prop="developers" label="开发商"></el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
                <template slot-scope="scope">
                    <el-button @click.stop="editHouse(scope.row)" type="text" size="small">编辑</el-button>
                    <el-button @click.stop="deleteHouse(scope.row)" type="text" size="small">删除</el-button>
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
    import houseService from '@/api/house'
    import locationService from '@/api/location'


    export default {
        mixins: [paginationMixin],
        data: function () {
            return {
                tableData: [],
                cities: [],
                conditions: {},
                selectedIds: ''
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
            goDetail(row) {
                this.$router.push({ path: `/new-house/${row.id}` })
            },
            editHouse(row) {
                this.$router.push({ path: `/new-house/${row.id}/edit` })
            },
            deleteHouse(row) {
                this.$confirm('此操作将永久删除, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    houseService.delete(row.id, () => {
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
                    status: 'INIT',
                    city: this.conditions.city,
                    keywords: this.conditions.keywords,
                }
                houseService.search(params, (response) => {
                    this.tableData = response.data.content
                    this.totalSize = response.data.total
                })
            },
            handleSelectionChange(val) {
                this.selectedIds = val.map(house => house.id).join(',');
            },
            handleBatchAction(action) {
                if(!this.selectedIds) {
                    return;
                }
                if(action == 'enable') {
                    houseService.batchSetFeatured({houseIds: this.selectedIds}, () => {
                        this.$message({message: '批量设置精选成功！', type: 'success'});
                        this.$router.go(0);
                    });
                } else if(action == 'disable') {
                    houseService.batchUnsetFeatured({houseIds: this.selectedIds}, () => {
                        this.$message({message: '批量取消精选成功！', type: 'success'});
                        this.$router.go(0);
                    });
                }
            }
            
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .label-featured {
        padding: 2px;
        background: #409EFF;
        color: #fff;
        font-size: 10px;
    }
</style>
