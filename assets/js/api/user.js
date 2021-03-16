export default {
    init(context) {
        this.http = context.http
    },
    login(loginForm, onSuccess, onError) {
        return this.http.post("/api/login", {username: loginForm.username, password: loginForm.password})
            .then(onSuccess, onError);
    },
    logout(onSuccess, onError) {
        return this.http.post("/api/logout").then(onSuccess, onError);
    },
    getCurrentUser(onSuccess, onError) {
        return this.http.get("/api/current-user").then(onSuccess, onError);
    },
    updateCurrentUser(userForm, onSuccess, onError) {
        return this.http.put("/api/update-user", userForm).then(onSuccess, onError);
    },
    updatePwd(userForm, onSuccess, onError) {
        return this.http.put("/api/update-pwd", userForm).then(onSuccess, onError);
    },
    search(params, onSuccess, onError) {
        return this.http.get(`/users`, {params: params}).then(onSuccess, onError);
    },
}
