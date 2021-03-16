export default {
    data() {
        return {
            errors: []
        }
    },
    methods: {
        showErrorField(response) {
            if (_.isArray(response.data)) {
                _.each(response.data, data => {
                    this.$set(this.errors, data.field, data.tip)
                })
            } else {
                this.$set(this.errors, response.data.field, response.data.tip)
            }
        }
    }
}