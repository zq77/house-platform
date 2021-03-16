export default {
    init(context) {
        this.http = context.http
    },
    getProvinces(onSuccess, onError) {
        return this.http.get("/api/provinces").then(onSuccess, onError);
    },
    getCities(onSuccess, onError) {
        return this.http.get("/api/cities").then(onSuccess, onError);
    },
    getAreas(onSuccess, onError) {
        return this.http.get("/api/areas").then(onSuccess, onError);
    },
    getStreets(onSuccess, onError) {
        return this.http.get('/api/streets').then(onSuccess, onError)
    }
}
