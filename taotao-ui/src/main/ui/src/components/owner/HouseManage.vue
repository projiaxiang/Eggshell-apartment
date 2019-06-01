<template>
  <div v-if="houses.length !== 0">
    <div @click="showHouse(entity)" v-for="(entity, index) in houses" :key="index" class="div-top" style="cursor: pointer;">
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
            <el-button type="text" style="float: right" @click="delHouse(entity.id)">删除</el-button>
          </div>
        </el-col>
      </el-card>
    </div>
    <div class="block" style="float: right">
      <el-pagination
        style="margin: 0 auto"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage1"
        :page-size="pageSize"
        background
        layout="total, prev, pager, next"
        :total="total">
      </el-pagination>
    </div>
  </div>
  <div v-else>
    <label style="margin-left: 33%;margin-top: 30%;font-size: 20px">
      还没有用户发布房屋
    </label>
  </div>
</template>

<script>
  import UserRedis from '../../utils/RedisUtil.js'
  import HouseUtil from '../../utils/HouseUtil.js'

  export default {
    mixins: [UserRedis, HouseUtil],
    data() {
      return {
        total: 0,
        currentPage1: 1,
        pageSize: 4,
        houses: [],
        user: {
          id: null,
          username: null
        },
        baseUrl: 'http://127.0.0.1:9091/image/'
      }
    },
    created() {
      this.init()
    },
    methods: {
      async init() {
        let key = localStorage.getItem('user_session')
        if (key) {
          await this.getUserInfoFromRedis(key)
          await this.findHouse()
        }
      },
      findHouse() {
        let self = this
        let startPage = (this.currentPage1 - 1) * this.pageSize
        this.$axios({
          method: "post",
          url: "/find/house",
          data: this.$qs.stringify({
            userId: '',
            startPage: startPage,
            pageSize: self.pageSize,
          })
        }).then((res)=>{
          this.total = res.data.total
          this.houses = res.data.house
        })
      },
      handleCurrentChange() {
        this.findHouse()
      },
      showHouse(entity) {
        this.$router.push({name: 'HouseBasic', params: {id: entity.houseId}})
      },
      delHouse(houseId) {
        let self = this
        let e = window.event
        e.stopPropagation()
        this.$confirm('此操作将永久删除该收藏, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          self.$axios({
            method: "post",
            url: "/delete/house",
            data: this.$qs.stringify({
              id: houseId
            }),
          }).then((res)=>{
            if (res.data.success) {
              self.$message({message: '删除成功', type: 'success'})
              self.findHouse()
            } else {
              self.$message.error('删除失败')
            }
          })
        }).catch(() => {

        })
      }
    }
  }
</script>

<style>
  .div-top {
    margin-top: 5px;
  }
  .div-top-0 {
    margin-top: 0px;
  }
</style>
