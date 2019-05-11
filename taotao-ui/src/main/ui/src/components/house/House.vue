<template>
    <span>我是找房页面</span>
</template>

<script>
    export default {
      name: "house",
      data () {
        return {
          baseUrl: 'http://127.0.0.1:9091/image/'
        }
      },
      created() {
        this.init()
      },
      methods: {
        init() {
          let address = this.$route.params.address
          let rent = this.$route.params.rent
          if (address === "all") {
            address = null
          }
          if (rent === "all") {
            rent = null
          }
          let self = this
          this.$axios({
            method: "post",
            url: "/find/house/by/address",
            data: this.$qs.stringify({
              address: address,
              rent: rent,
              startPage: 0,
              pageSize: 1
            })
          }).then((res)=>{
            if (res.data.house.length > 0) {
              console.log(res.data.house)
            }
          })
        }
      }
    }
</script>

<style scoped>

</style>
