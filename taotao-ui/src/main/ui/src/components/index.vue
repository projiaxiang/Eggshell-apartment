<template>
  <div>
    <!--轮播图-->
    <el-carousel style="margin-top: 10px" :interval="5000" arrow="always">
      <el-carousel-item v-for="(picture, index) in pictures" :key="index">
        <img :src="picture.url" width="100%" height="100%">
      </el-carousel-item>
    </el-carousel>
    <!--搜索框-->
    <div style="width: 60%;margin: 15px auto;">
      <el-input placeholder="例如：回龙观、双井、天通苑、青年路、望京等" v-model="input5" class="input-with-select">
        <el-select clearable v-model="select" slot="prepend" placeholder="请选择">
          <el-option label="全部" value="all"></el-option>
          <el-option label="整租" value="WholeRent"></el-option>
          <el-option label="合租" value="RentSharing"></el-option>
        </el-select>
        <el-button style="background-color: #00BFFF" slot="append" icon="el-icon-search"></el-button>
      </el-input>
    </div>
    <!--合租-->
    <div>
      <div style="margin: 30px 0 0 50px">
        <span style="font-size: 30px;font-weight: bold;display: block">合租</span>
        <label style="margin: 10px 0 0 0">复古和颓废艺术范的格调颇受大众欢迎</label>
        <el-button style="margin-left: 887px;font-size: 15px;" size="large">更多</el-button>
      </div>
      <hr width="93%" style="margin-top: 10px"/>
      <div style="margin: 30px 0 0 80px">
        <el-row>
          <el-col :span="6" v-for="(sh, index) in sharingHouse" :key="index" style="margin: 10px 0 0 50px">
            <el-card style="cursor: pointer" shadow="hover" :body-style="{ padding: '0px' }">
              <div @click="toHouseBasic(sh.id)">
                <img width="50px" height="250px" :src="baseUrl + sh.picture[0].name" class="image">
                <div style="padding: 14px;">
                <span><span style="color: red;font-size: 25px">{{sh.money}}</span>元/月</span>
                <div class="bottom clearfix">
                  <span style="font-size: 5px">{{sh.title}}</span>
                  <label style="float: right">约{{sh.size}}㎡</label>
                  <span style="font-size: 5px;display: block;margin-top: 7px;color: #909399">{{sh.subway}}</span>
                </div>
              </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
    <!--整租-->
    <div>
      <div style="margin: 30px 0 0 50px">
        <span style="font-size: 30px;font-weight: bold;display: block">整租</span>
        <label style="margin: 10px 0 0 0">随意租，随意住，给你空间自主权</label>
        <el-button style="margin-left: 917px;font-size: 15px;" size="large">更多</el-button>
      </div>
      <hr width="93%" style="margin-top: 10px"/>
      <div style="margin: 30px 0 0 80px">
        <el-row>
          <el-col :span="6" v-for="(wh, index) in wholeHouse" :key="index" style="margin: 10px 0 0 50px">
            <el-card @click="toHouseBasic(wh.id)" style="cursor: pointer" shadow="hover" :body-style="{ padding: '0px' }">
              <div @click="toHouseBasic(sh.id)">
                <img width="50px" height="250px" :src="baseUrl + wh.picture[0].name" class="image">
                <div style="padding: 14px;">
                <span><span style="color: red;font-size: 25px">{{wh.money}}</span>元/月</span>
                <div class="bottom clearfix">
                  <span style="font-size: 5px">{{wh.title}}</span>
                  <label style="float: right">约{{wh.size}}㎡</label>
                  <span style="font-size: 5px;display: block;margin-top: 7px;color: #909399">{{wh.subway}}</span>
                </div>
              </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>

  export default {
    data () {
      return {
        pictures: [
          {url: require('../assets/1.png')},
          {url: require('../assets/2.png')},
          {url: require('../assets/3.png')},
          {url: require('../assets/4.png')}
        ],
        activeIndex: '1',
        select: '',
        conditionText: '',
        input5: '',
        tabPosition: 'top',
        currentDate: new Date(),
        user: {
          id: null,
          username: null
        },
        sharingHouse: [],
        wholeHouse: [],
        baseUrl: 'http://127.0.0.1:9091/image/'
      }
    },
    created() {
      this.init()
    },
    methods: {
      async init() {
        //如果cookie中存在user_session，则从缓存中获取user相关信息
        let key = this.$cookie.get('user_session')

        if (key) {
          sessionStorage.setItem('user_session', key)
          await this.getUserFromRedis(key)
        }
        this.loadSharingHouse()
        this.loadWholeHouse()
      },
      async getUserFromRedis(key) {
        await this.$axios({
          method: "post",
          url: "/get/user/from/redis",
          data: this.$qs.stringify({
            key: key
          })
        }).then((res)=>{
          this.user = res.data
        })
        .catch(function () {

        })
      },
      loadSharingHouse() {
        this.$axios({
          method: "post",
          url: "/find/index/house",
          data: this.$qs.stringify({
            rent: "1"
          })
        }).then((res)=>{
          this.sharingHouse = res.data
        })
        .catch(function () {
          self.$message.error('服务器端错误')
        })
      },
      loadWholeHouse() {
        this.$axios({
          method: "post",
          url: "/find/index/house",
          data: this.$qs.stringify({
            rent: "2"
          })
        }).then((res)=>{
          this.wholeHouse = res.data
        })
        .catch(function () {
          self.$message.error('服务器端错误')
        })
      },
      toLogin() {
        this.$router.push({name: 'login'})
      },
      toOwner() {
        this.$router.push({name: 'owner'})
      },
      toHouseBasic(id) {
        this.$router.push({name: 'HouseBasic', params: {id: id}})
      }
    }
  }
</script>

<style scoped>
  .el-carousel__item h3 {
    color: #475669;
    font-size: 18px;
    opacity: 0.75;
    line-height: 300px;
    margin: 0;
  }

  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }

  .el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
  }
  .el-select {
    width: 130px;
  }

  .time {
    font-size: 13px;
    color: #999;
  }

  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }

  .clearfix:after {
    clear: both
  }
</style>
