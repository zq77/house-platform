export default {
    init(context) {
        this.http = context.http
    },
    get(id, onSuccess, onError) {
        return this.http.get(`/resold/${id}`).then(onSuccess, onError);
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/resold`, {params: params}).then(onSuccess, onError);
    },
    delete(houseId, onSuccess, onError) {
        return this.http.delete(`/resold/${houseId}`).then(onSuccess, onError);
    },
    create(data, onSuccess, onError) {
        return this.http.post('/resold', data).then(onSuccess, onError);
    },
    update(houseId, data, onSuccess, onError) {
        return this.http.put(`/resold/${houseId}`, data).then(onSuccess, onError);
    },
    batchSetFeatured(data, onSuccess, onError) {
        return this.http.put(`/resold/set-featured`, data).then(onSuccess, onError);
    },
    batchUnsetFeatured(data, onSuccess, onError) {
        return this.http.put(`/resold/unset-featured`, data).then(onSuccess, onError);
    },
    addHouseImage(houseId, data) {
        return this.http.post(`/resold/${houseId}/images`, data)
    },
    deleteHouseImage(houseId, imageId) {
        return this.http.delete(`/resold/${houseId}/images/${imageId}`)
    },
    sortHouseImage(houseId, imageId, index) {
        return this.http.post(`/resold/${houseId}/images/${imageId}/sort`, {index: index})
    }
}
