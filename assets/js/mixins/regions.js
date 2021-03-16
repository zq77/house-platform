import locationService from '@/api/location'

export default {
    data() {
        return {
            regions: []
        }
    },
    created() {
        Promise.all([locationService.getProvinces(), locationService.getCities(), locationService.getAreas(), locationService.getStreets()])
            .then(([provinces, cities, areas, streets]) => {
            let streetMap = _(streets.body).map(street => {
                return {value: street.code, label: street.name, parent_code: street.parent_code}
            }).groupBy('parent_code').value();

            let areaMap = _(areas.body).map(area => {
                return {value: area.code, label: area.name, children: streetMap[area.code], parent_code: area.parent_code}
            }).groupBy('parent_code').value();

            let cityMap = _(cities.body).map(city => {
                return {value: city.code, label: city.name, children: areaMap[city.code], parent_code: city.parent_code}
            }).groupBy('parent_code').value();

            this.regions = _(provinces.body).map((province) => {
                return {value: province.code, label: province.name, children: cityMap[province.code]};
            }).value()
        })
    },
    methods: {
        parseRegion(obj) {
            if (!obj.region) {
                return
            }
            if (obj.region[0]) {
                obj.province = obj.region[0]
            }
            if (obj.region[1]) {
                obj.city = obj.region[1]
            }
            if (obj.region[2]) {
                obj.area = obj.region[2]
            }
            if (obj.region[3]) {
                obj.street = obj.region[3]
            }
        },
        generateRegion(obj) {
            obj.region = [obj.province, obj.city, obj.area, obj.street]
        }
    }
}