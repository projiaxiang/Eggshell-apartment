<template>
  <div style="width: 80%;margin: 0 auto;" v-if="collections.length !== 0">
    <div @click="showHouse(entity)" v-for="(entity, index) in collections" :key="index" class="div-top" style="cursor: pointer;">
      <el-card shadow="hover">
        <el-col :span="6">
          <img v-if="entity.picture && entity.picture.length" width="110px" height="110px" :src="baseUrl + entity.picture[0].name"/>
          <img v-else width="110px" height="110px" :src="baseUrl + '1.jpg'"/>
        </el-col>
        <el-col :span="12">
          <span style="display: block;font-size: 20px">{{entity.title}}</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">{{toApartment(entity.apartment)}}  {{entity.size}}㎡  {{entity.rent == '1' ? '合租' : '整租'}}</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">{{entity.subway}}</span>
        </el-col>
        <el-col :span="6">
          <div style="float: right">
            <span style="display: block">{{$moment(entity.createTime).format('YYYY-MM-DD HH:mm:ss')}}</span>
            <span style="display: block;margin-left: 34.5px;margin-top: 10px"><span style="color: red;font-size: 25px">{{entity.money}}</span>元/月</span>
          </div>
        </el-col>
      </el-card>
    </div>
    <div class="block" style="float: right">
      <el-pagination
        style="margin: 0 auto"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        background
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
  <div style="width: 80%;margin: 0 auto" v-else>
    <label style="margin-left: 33%;margin-top: 30%;font-size: 20px">
      没有符合条件的房源
    </label>
  </div>
</template>

<script>
    import UserRedis from '../../utils/RedisUtil.js'
    import HouseUtil from '../../utils/HouseUtil.js'

    export default {
      mixins: [UserRedis, HouseUtil],
      name: "house",
      data () {
        return {
          baseUrl: 'http://127.0.0.1:9091/image/',
          currentPage: 0,
          pageSize: 5,
          total: 0,
          collections: []
        }
      },
      created() {
        this.init()
      },
      methods: {
        init() {
          let address = this.$route.params.address
          let rent = this.$route.params.rent
          let self = this
          this.$axios({
            method: "post",
            url: "/find/house/by/address",
            data: this.$qs.stringify({
              address: address,
              rent: rent,
              startPage: this.currentPage,
              pageSize: this.pageSize
            })
          }).then((res)=>{
            console.log(res.data.house)
            self.collections = res.data.house
            self.total = res.data.total
          })
        },
        showHouse(entity) {
          this.$router.push({name: 'HouseBasic', params: {id: entity.houseId}})
        },
        handleCurrentChange() {
          this.init()
        },
      }
    }
</script>

<style scoped>
  .div-top {
    margin-top: 5px;
  }
  .div-top-0 {
    margin-top: 0px;
  }
</style>
