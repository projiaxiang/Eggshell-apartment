<template>
  <div style="width: 80%;margin: 0 auto">
    <el-row>
      <el-col :span="10">
        <el-carousel :interval="5000" arrow="always">
          <el-carousel-item v-for="(item, index) in house.picture" :key="index">
            <img :src="baseUrl + item.name" class="image">
          </el-carousel-item>
        </el-carousel>
      </el-col>
      <el-col :span="13" style="margin-left: 40px">
        <el-row style="font-size: 25px;font-weight: bold">
          {{house.title}}
        </el-row>
        <el-row class="margin-top-1">
          <el-tag>{{house.heating === '1' ? '集中供暖' : '自采暖'}}</el-tag>
          <el-tag>已通风30天</el-tag>
        </el-row>
        <el-row class="margin-top-1">
          月租金 ：
          <span style="font-size: 25px;color: red">{{house.money}}元/月</span>
        </el-row>
        <el-row class="margin-top-1">
          建筑面积 ：约{{house.size}}㎡（以现场勘察为准）
        </el-row>
        <el-row class="margin-top-1">
          户型 ：{{toApartment(house.apartment)}}
          <el-tag>{{house.rent === '1' ? '合' : '整'}}</el-tag>
          <span style="margin-left: 100px">付款 ：可支持分期月付</span>
        </el-row>
        <el-row class="margin-top-1">
          朝向 ：{{toDirection(house.direction)}}
        </el-row>
        <el-row class="margin-top-1">
          楼层 ：{{house.floor}}层
        </el-row>
        <el-row class="margin-top-1">
          区域 ：
          <el-cascader
            :options="options"
            filterable
            disabled
            v-model="selectLocation"
            change-on-select/>
        </el-row>
        <el-row class="margin-top-1">
          地铁 ：{{house.subway}}
        </el-row>
        <el-row class="margin-top-1">
          <el-button v-if="!isCollection" @click="saveCollection" type="primary" plain round icon="el-icon-star-off">收藏</el-button>
          <el-button  disabled v-else type="success" plain round icon="el-icon-star-off">已收藏</el-button>
          <el-button v-if="!isOrder" type="primary" @click="saveOrder" round style="width: 54%">预定</el-button>
          <el-button  disabled v-else type="success" plain round icon="el-icon-star-off">已预定</el-button>
        </el-row>
      </el-col>
    </el-row>
    <el-row style="margin-top: 30px">
      <el-col :span="24">
        <el-card shadow="always">
          <el-row>
            <span style="margin: 0 5px 0 5px;font-size: 25px">房屋简介</span>
          </el-row>
          <el-row style="margin-top: 2%">
            {{house.introduction}}
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row style="margin-top: 30px">
      <hr style="width: 41%;" class="line"/>
      <span style="margin: 0 5px 0 5px;font-size: 25px">蛋壳承诺服务</span>
      <hr style="width: 41%;" class="line"/>
    </el-row>
    <el-row style="margin-top: 30px">
      <el-col :span="24">
        <el-card shadow="always">
          <el-row>
            <el-col :span="4" v-for="(picture, index) in pictures" :key="index">
              <el-tooltip class="item" effect="dark" :content="picture.label" placement="top">
                <img :src="picture.url" style="display: block;margin: 0 auto">
              </el-tooltip>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <el-row style="margin-top: 30px">
      <hr style="width: 43%;" class="line"/>
      <span style="margin: 0 5px 0 5px;font-size: 25px">房屋配置</span>
      <hr style="width: 43%;" class="line"/>
    </el-row>
    <el-row style="margin-top: 30px">
      <el-col :span="24">
        <el-card shadow="always">
          <el-row style="margin-left: 10%">
            <el-col :span="4" v-for="(picture, index) in configPictures" :key="index">
              <el-tooltip class="item" effect="dark" :content="picture.label" placement="top">
                <img :src="picture.url" style="display: block;margin: 0 auto">
              </el-tooltip>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import location from '../../config/China_Province_City.json'
  import UserRedis from '../../utils/RedisUtil.js'

  export default {
    mixins: [UserRedis],
    data() {
      return {
        house: {
          picture: null
        },
        user: {
          id: null,
          username: null
        },
        baseUrl: 'http://127.0.0.1:9091/image/',
        options: location,
        selectLocation: null,
        pictures: [
          {url: require('../../assets/5.jpg'), label: '百分百真实房源'},
          {url: require('../../assets/6.jpg'), label: '不收取中介费'},
          {url: require('../../assets/7.jpg'), label: '双周保洁安心省心'},
          {url: require('../../assets/8.jpg'), label: '售后维修及时上门'},
          {url: require('../../assets/9.jpg'), label: '智能密码锁'},
          {url: require('../../assets/10.jpg'), label: '免费WIFI'}
        ],
        configPictures: [
          {url: require('../../assets/11.jpg'), label: '床'},
          {url: require('../../assets/12.jpg'), label: 'WIFI'},
          {url: require('../../assets/13.jpg'), label: '洗衣机'},
          {url: require('../../assets/14.jpg'), label: '空调'},
          {url: require('../../assets/15.jpg'), label: '智能锁'}
        ],
        isCollection: false,
        isOrder: false
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
          await this.findHasCollection()
          await this.findHasOrder()
        }
        let id = this.$route.params.id
        this.loadHouseById(id)
      },
      async findHasCollection() {
        let self = this
        this.$axios({
          method: "post",
          url: "/find/collection/by/userId",
          data: this.$qs.stringify({
            token: localStorage.getItem('user_session'),
            userId: this.user.id,
            houseId: self.$route.params.id,
            startPage: 0,
            pageSize: 1
          })
        }).then((res)=>{
          if (res.data.collection.length > 0) {
            this.isCollection = true
          }
        })
      },
      async findHasOrder() {
        let self = this
        this.$axios({
          method: "post",
          url: "/find/order/by/userId",
          data: this.$qs.stringify({
            userId: this.user.id,
            houseId: self.$route.params.id,
            startPage: 0,
            pageSize: 1
          })
        }).then((res)=>{
          if (res.data.order.length > 0) {
            this.isOrder = true
          }
        })
      },
      loadHouseById(id) {
        this.$axios({
          method: "post",
          url: "/find/house/by/id",
          data: this.$qs.stringify({
            id: id
          })
        }).then((res)=>{
          this.house = res.data
          this.selectLocation = Array.from({
            0: res.data.province,
            1: res.data.city,
            2: res.data.county,
            length: 3
          })
        })
      },
      saveCollection() {
        let self = this

        this.$axios({
          method: "post",
          url: "/api/collection/insertCollection",
          data: {
            userId: self.user.id,
            houseId: self.$route.params.id
          },
        }).then((res)=>{
          if (res.data.success) {
            self.$message({message: '收藏成功', type: 'success'})
            self.isCollection = true
          } else {
            self.$message.error('收藏失败')
          }
        })
      },
      saveOrder() {
        let self = this

        this.$axios({
          method: "post",
          url: "/api/order/insert/order",
          data: {
            userId: self.user.id,
            houseId: self.$route.params.id
          },
        }).then((res)=>{
          if (res.data.success) {
            self.$message({message: '预定成功', type: 'success'})
            self.isOrder = true
          } else {
            self.$message.error('预定失败')
          }
        })
      },
      toApartment(str) {
        if (str === 'yi') {
          return '1室1厅'
        } else if (str === 'er') {
          return '2室1厅'
        } else if (str === 'san') {
          return '3室1厅'
        } else if (str === 'si') {
          return '4室1厅'
        } else if (str === 'wu') {
          return '5室1厅'
        } else if (str === 'liu') {
          return '6室1厅'
        } else if (str === 'qi') {
          return '7室1厅'
        }
      },
      toDirection(str) {
        if (str === 'east') {
          return '东'
        } else if (str === 'south') {
          return '南'
        } else if (str === 'west') {
          return '西'
        } else if (str === 'north') {
          return '北'
        }
      }
    }
  }
</script>

<style scoped>
  .image {
    width: 100%;
    display: block;
  }
  .margin-top-1 {
    margin-top: 10px;
  }
  .line {
    display: inline-block;
    background-color: #DCDFE6;
    height: 1px;
    border: none
  }
</style>
