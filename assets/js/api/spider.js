export default {
    init(context) {
        this.http = context.http
    },
    grabHouseData(params, onSuccess, onError) {
        return this.http.get(`/spider/grab/newHouse`, {params: params}).then(onSuccess, onError);
    },
    grabResoldHouseData(params, onSuccess, onError) {
        return this.http.get(`/spider/grab/resoldHouse`, {params: params}).then(onSuccess, onError);
    }
}
