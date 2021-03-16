export default {
    init(context) {
        this.http = context.http
    },
    getToken(onSuccess, onError) {
        return this.http.get("/api/upload/token").then(onSuccess, onError);
    },
    getTokenByFilekey(fileKey, onSuccess, onError) {
        return this.http.get(`/api/upload/token/${fileKey}`).then(onSuccess, onError);
    }
}
