export default {
    init(context) {
        this.http = context.http
    },
    get(houseId, onSuccess, onError) {
        return this.http.get(`/houses/${houseId}`).then(onSuccess, onError);
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/houses`, {params: params}).then(onSuccess, onError);
    },
    create(data, onSuccess, onError) {
        return this.http.post('/houses', data).then(onSuccess, onError);
    },
    update(houseId, data, onSuccess, onError) {
        return this.http.put(`/houses/${houseId}`, data).then(onSuccess, onError);
    },
    batchSetFeatured(data, onSuccess, onError) {
        return this.http.put(`/houses/set-featured`, data).then(onSuccess, onError);
    },
    batchUnsetFeatured(data, onSuccess, onError) {
        return this.http.put(`/houses/unset-featured`, data).then(onSuccess, onError);
    },
    delete(houseId, onSuccess, onError) {
        return this.http.delete(`/houses/${houseId}`).then(onSuccess, onError);
    },
    addHouseType(houseId, data) {
        return this.http.post(`/houses/${houseId}/housetype`, data)
    },
    updateHouseType(houseId, houseTypeId, data) {
        return this.http.put(`/houses/${houseId}/housetype/${houseTypeId}`, data)
    },
    deleteHouseType(houseId, houseTypeId) {
        return this.http.delete(`/houses/${houseId}/housetype/${houseTypeId}`)
    },
    getHouseTypes(houseId) {
        return this.http.get(`/houses/${houseId}/housetypes`)
    },
    addHouseImage(houseId, data) {
        return this.http.post(`/houses/${houseId}/images`, data)
    },
    deleteHouseImage(houseId, imageId) {
        return this.http.delete(`/houses/${houseId}/images/${imageId}`)
    },
    sortHouseImage(houseId, imageId, index) {
        return this.http.post(`/houses/${houseId}/images/${imageId}/sort`, {index: index})
    }
}
