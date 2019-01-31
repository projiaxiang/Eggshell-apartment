export default {
  methods: {
    async getUserInfoFromRedis(key) {
      let self = this
      await this.$axios({
        method: "post",
        url: "/get/user/from/redis",
        data: this.$qs.stringify({
          key: key
        })
      }).then((res)=>{
        self.user = res.data
      })
    }
  }
}
