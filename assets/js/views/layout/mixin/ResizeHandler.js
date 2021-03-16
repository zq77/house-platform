const { body } = document
const WIDTH = 1024
const RATIO = 3

export default {
    watch: {
        $route(route) {
            if (this.device === 'mobile' && this.sidebar) {
                this.$eventHub.$emit("closeSideBar");
            }
        }
    },
    beforeMount() {
        window.addEventListener('resize', this.resizeHandler)
    },
    mounted() {
        const isMobile = this.isMobile()
        if (isMobile) {
            this.$eventHub.$emit("toggleDevice", 'mobile');
            this.$eventHub.$emit("closeSideBar");
        }
    },
    methods: {
        isMobile() {
            const rect = body.getBoundingClientRect()
            return rect.width - RATIO < WIDTH
        },
        resizeHandler() {
            if (!document.hidden) {
                const isMobile = this.isMobile()
                this.$eventHub.$emit("toggleDevice", isMobile ? 'mobile' : 'desktop');

                if (isMobile) {
                    this.$eventHub.$emit("closeSideBar");
                }
            }
        }
    }
}
