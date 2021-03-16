<template>
    <div>
        <div class="ctrl-filter">
            <div>
                <el-button type="primary" icon="iconfont icon-new-house-grab" @click="openGrapDialog">房源采集</el-button>
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
            <div>
                <div class="filter">
                    <el-radio-group v-model="conditions.grabSite" size="medium" class="grab-website" @change="loadData(1)">
                        <el-radio-button label="ALL">全部采集</el-radio-button>
                        <el-radio-button  v-for="grabWebsite in grabWebsites"
                                        :key="grabWebsite.value" 
                                        :label="grabWebsite.value">
                            {{ grabWebsite.name }}
                        </el-radio-button>
                    </el-radio-group>
                </div>
            </div>
        </div>
        <el-alert v-if="isGrabing" class="msg-info" title="数据采集中..." type="info" description="数据采集需要一段时间，请耐心等待。" show-icon></el-alert>
        <el-alert v-if="isGrabedSuccess" class="msg-info" title="数据采集成功" type="success" show-icon></el-alert>
        <el-alert v-if="isGrabedfail" class="msg-info" title="数据采集失败" type="error" show-icon></el-alert>
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
        <house-grab-dialog title="新房数据采集" 
            :visible.sync="grabDialogVisible" 
            :isNewHouse=true
            v-on:grabStarting="grabStarting"
            v-on:setVisible="setVisible"
            v-on:grabSuccess="grabSuccess"
            v-on:grabfailed="grabfailed"
        >
        </house-grab-dialog>
    </div>
</template>

<script>
    import { grabWebsites } from '@/utils/constants';
    import paginationMixin from '@/mixins/pagination'
    import houseService from '@/api/house'
    import locationService from '@/api/location'
    import houseGrabDialog from '@/components/HouseGrab/index.vue'


    export default {
        mixins: [paginationMixin],
        components: {
            'house-grab-dialog': houseGrabDialog
        },
        data: function () {
            return {
                tableData: [],
                cities: [],
                conditions: {},
                selectedIds: '',
                grabDialogVisible: false,
                grabWebsites,
                isGrabing: false,
                isGrabedSuccess: false,
                isGrabedfail: false
            }
        },
        computed: {
        },
        created() {
            this.loadData();
            locationService.getCities((response) => {
                this.cities = response.data
            })
        },
        methods: {
            goDetail(row) {
                this.$router.push({ path: `/new-house-grab/${row.id}` })
            },
            editHouse(row) {
                this.$router.push({ path: `/new-house-grab/${row.id}/edit` })
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
                    grabSite: this.conditions.grabSite || 'ALL',
                    size: this.pageSize,
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
            },
            openGrapDialog() {
                this.grabDialogVisible = true;
            },
            grabStarting() {
                this.setVisible(false);
                this.isGrabing = true;
                this.isGrabedSuccess = false;
                this.isGrabedfail = false;
            },
            grabSuccess() {
                this.isGrabing = false;
                this.isGrabedSuccess = true;
                this.isGrabedfail = false;
                this.loadData();
            },
            grabfailed() {
                this.isGrabing = false;
                this.isGrabedSuccess = false;
                this.isGrabedfail = true;
            },
            setVisible(isVisible) {
                this.grabDialogVisible = isVisible;
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
    .msg-info {
        margin-bottom: 20px;
    }
    
</style>
