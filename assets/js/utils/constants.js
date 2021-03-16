// export default function date(str) {
//     return moment(str).format('YYYY-MM-DD');
// };

exports.houseTypes = [{name: '普通住宅', value: 'NORMAL'}, {name: '公寓', value: 'APARTMENT'}, {name: '别墅', value: "VILLA"}, {name: '写字楼', value: 'OFFICE'}, {name: '商铺', value: 'SHOP'}, {name: '其他', value: 'OTHERS'}]
exports.QINIU_WEBSITE = 'http://image.jr-hf.cn';
exports.houseStatus = [{key: '在售', value: 'FOR_SELL'}, {key: '待售', value: 'WILL_SELL'}, {key: '售罄', value: 'SOLD_OUT'}]

// Decoration
exports.houseOrientation = ['东', '南', '西', '北', '东南', '南北', '东北', '东西', '西南', '西北' ];
exports.houseDecoration = ['毛坯', '简装', '普装', '精装', '豪装' ];
exports.houseUseYear = ['满五年', '满两年', '不满两年'];
exports.housePropertyNature = ['商品房', '商住两用', '经济适用房', '安置房', '公房', '小产权', '其他'];
exports.housePropertyYear = ['70年', '50年', '40年'];
exports.houseMortgageStatus = ['有', '无'];
exports.houseSellingStatus = ['在售', '待售', '售罄'];

exports.roleType = {
    ADMIN: 'ADMIN',
    AGENT: 'AGENT',
    STORE_ADMIN: 'STORE_ADMIN',
    WECHAT_USER: 'WECHAT_USER',
    NORMAL_USER: 'NORMAL_USER'
}

exports.educations = [
    {name: '大学专科', value: 'COLLEGE_DEGREE'},
    {name: '大学本科', value: 'BACHELOR_DEGREE'},
    {name: '高中', value: "HIGH_SCHOOL"},
    {name: '初中', value: 'JUNIOR_HIGH_SCHOOL'},
    {name: '小学', value: 'PRIMARY_SCHOOL'},
    {name: '硕士', value: 'MASTER_DEGREE'},
    {name: '博士', value: 'DOCTOR'}
]

exports.politicalStatuses = [
    {name: '党员', value: 'COMMUNIST_PARTY_MEMBER'},
    {name: '团员', value: 'LEAGUE_MEMBER'},
    {name: '群众', value: "PUBLIC_PEOPLE"},
    {name: '无党派人士', value: 'NON'}
]

exports.genderTypes = [
    {name: '男', value: 'MALE'},
    {name: '女', value: 'FEMALE'}
]

exports.maritalStatuses = [
    {name: '已婚', value: 'MARRIED'},
    {name: '未婚', value: 'UNMARRIED'}
]

exports.LABEL_SPLIT = '&;';

exports.grabWebsites = [
    {
        name: '链家',
        value: 'LJ'
    },
    {
        name: '贝壳',
        value: 'BK'
    },
    // {
    //     name: '安居客',
    //     value: 'AJK'
    // },
    // {
    //     name: '优优好房',
    //     value: 'YYHF'
    // },
];

exports.roomTypeList = [
    {value: '',key: '不限'},
    {value: '1',key: '1室'},
    {value: '2',key: '2室'},
    {value: '3',key: '3室'},
    {value: '4',key: '4室'},
    {value: '5',key: '5室及以上'}
];

exports.houseKeyTypes = [{ key: '普通住宅', value: 'NORMAL' }, { key: '公寓', value: 'APARTMENT' }, { key: '别墅', value: "VILLA" }, { key: '写字楼', value: 'OFFICE' }, { key: '商铺', value: 'SHOP' }, { key: '其他', value: 'OTHERS' }];

exports.genderList = [{ key: '女士', value: 'FEMALE' }, { key: '先生', value: 'MALE' }];

exports.levelList = [{ key: 'A级', value: 'A' }, { key: 'B级', value: 'B' }, { key: 'C级', value: 'C' }];

exports.houseNeedList = [{ key: '新房', value: 'XF' }, { key: '二手房', value: 'ESF' }];

exports.customerSourceList = [{ key: '今日好房', value: 'JRHF' }, { key: '安居客', value: 'AJK' }, { key: '58同城', value: 'WBTC' }, { key: '上门', value: 'SM' }, { key: '驻守', value: 'ZS' }, { key: '其他', value: 'OTHERS' }];

exports.buyPurposeList = [{ key: '自住', value: 'ZZ' }, { key: '投资', value: 'TZ' }, { key: '改善', value: 'GS' }, { key: '婚房', value: 'HF' }, { key: '养老', value: 'YL' }, { key: '度假', value: 'DJ' }];

exports.customerSearchTypeList = [{ key: '待跟进', value: 'DGJ' }, { key: 'AB客', value: 'ABK' }, { key: '本月带看', value: 'BYDK' }];