export default {
    init(context) {
        this.http = context.http
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/api/customer/search`, {params: params}).then(onSuccess, onError);
    },
    delete(customerId, onSuccess, onError) {
        return this.http.delete(`/api/customer/delete/${customerId}`).then(onSuccess, onError);
    },
    create(data, onSuccess, onError) {
        return this.http.post('/api/customer/create', data).then(onSuccess, onError);
    },
    update(customerId, data, onSuccess, onError) {
        return this.http.put(`/api/customer/edit/${customerId}`, data).then(onSuccess, onError);
    },
    get(customerId, onSuccess, onError) {
        return this.http.get(`/api/customer/detail/${customerId}`).then(onSuccess, onError);
    },
}
