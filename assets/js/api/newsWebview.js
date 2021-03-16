export default {
    init(context) {
        this.http = context.http
    },
    create(data, onSuccess, onError) {
        return this.http.post('/api/news', data).then(onSuccess, onError);
    },
    update(newsId, data, onSuccess, onError) {
        return this.http.put(`/api/news/${newsId}`, data).then(onSuccess, onError);
    },
    get(newsId, onSuccess, onError) {
        return this.http.get(`/api/news/${newsId}`).then(onSuccess, onError);
    },
}
