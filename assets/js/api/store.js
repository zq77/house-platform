export default {
    init(context) {
        this.http = context.http
    },
    searchAll(onSuccess, onError) {
        return this.http.get(`/api/stores/all`).then(onSuccess, onError);
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/api/stores`, {params: params}).then(onSuccess, onError);
    },
    delete(storeId, onSuccess, onError) {
        return this.http.delete(`/api/stores/${storeId}`).then(onSuccess, onError);
    },
    create(data, onSuccess, onError) {
        return this.http.post('/api/stores', data).then(onSuccess, onError);
    },
    update(storeId, data, onSuccess, onError) {
        return this.http.put(`/api/stores/${storeId}`, data).then(onSuccess, onError);
    },
    get(storeId, onSuccess, onError) {
        return this.http.get(`/api/stores/${storeId}`).then(onSuccess, onError);
    },
}
