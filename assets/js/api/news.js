export default {
    init(context) {
        this.http = context.http
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/api/news`, {params: params}).then(onSuccess, onError);
    },
    delete(newsId, onSuccess, onError) {
        return this.http.delete(`/api/news/${newsId}`).then(onSuccess, onError);
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
    /** news ad start */
    getGrabNews(grabUrl, onSuccess, onError) {
        return this.http.get(`/api/news/grab?grabUrl=${grabUrl}`).then(onSuccess, onError);
    },
    getNewsAdTmpl(onSuccess, onError) {
        return this.http.get('/api/newsAd/templates').then(onSuccess, onError);
    },
    getNewsAdTmpl(onSuccess, onError) {
        return this.http.get('/api/newsAd/templates').then(onSuccess, onError);
    },
    createNewsAdTmpl(data, onSuccess, onError) {
        return this.http.post('/api/newsAd', {
            newsAdFormArrayJson: data
        }).then(onSuccess, onError);
    },
}
