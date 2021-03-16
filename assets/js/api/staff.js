export default {
    init(context) {
        this.http = context.http
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/api/staffs`, {params: params}).then(onSuccess, onError);
    },
    delete(staffId, onSuccess, onError) {
        return this.http.delete(`/api/staffs/${staffId}`).then(onSuccess, onError);
    },
    create(data, onSuccess, onError) {
        return this.http.post('/api/staffs', data).then(onSuccess, onError);
    },
    update(staffId, data, onSuccess, onError) {
        return this.http.put(`/api/staffs/${staffId}`, data).then(onSuccess, onError);
    },
    get(staffId, onSuccess, onError) {
        return this.http.get(`/api/staffs/${staffId}`).then(onSuccess, onError);
    },
}
