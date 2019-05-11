<template>
    <div @click="closeShowMenu">
      <el-container style="margin-left: 100px">
        <el-aside width="410px">
          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
            <el-menu-item index="1" @click="toIndex">首页</el-menu-item>
            <el-menu-item index="2" @click="toHouse">找房</el-menu-item>
            <el-menu-item index="6" @click="toOwner">我的</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="margin-left: 500px">
          <el-radio-group v-if="!user.id" v-model="tabPosition" style="margin-bottom: 30px;">
            <el-radio-button @click.native="toLogin" label="top">登录</el-radio-button>
            <el-radio-button @click.native="goRegister" label="right">注册</el-radio-button>
          </el-radio-group>
          <span v-else style="margin-bottom: 30px;">
            欢迎您，
            <el-button type="text" @click="showMenu">{{user.name}}</el-button>
            <div v-show="isShowMenu" class="el-card is-always-shadow card-div">
              <div class="text" @click="toOwner">个人信息</div>
              <div class="text" style="margin-top: 5px" @click="goOut">安全退出</div>
            </div>
          </span>
        </el-main>
      </el-container>
      <div>
        <router-view></router-view>
      </div>
      <!--版权所有-->
      <div style="margin-top: 30px;padding-bottom: 50px;height: 50px;background-color: #3C3C3C;text-align: center;line-height: 100px">
        <label style="color: beige;">© 2019 崔迪租房</label>
      </div>
    </div>
</template>

<script>
  import UserRedis from '../utils/RedisUtil.js'

  export default {
    mixins: [UserRedis],
    computed: {
      activeIndex() {
        let route = this.$route.name
        if (route === 'index') {
          return '1'
        } else if (route === 'owner' || route === 'release' || route === 'collection' || route === 'personal' ) {
          return '6'
        } else if (route === 'House') {
          return '2'
        }
      }
    },
    data() {
      return {
        tabPosition: 'top',
        user: {
          id: null,
          username: null,
          name: null,
          phone: null,
          sex: null
        },
        isShowMenu: false
      }
    },
    async created() {
      //如果cookie中存在user_session，则从缓存中获取user相关信息
      let key = localStorage.getItem('user_session')

      if (key) {
        await this.getUserInfoFromRedis(key)
      }
    },
    methods: {
      goOut() {
        event.stopPropagation()
        this.$cookie.remove('user_session')
        localStorage.clear()
        this.$router.push({name: 'login'})
      },
      toLogin() {
        this.$router.push({name: 'login'})
      },
      toOwner() {
        this.$router.push({name: 'owner'})
      },
      toIndex() {
        this.$router.push({name: 'index'})
      },
      showMenu() {
        event.stopPropagation()
        this.isShowMenu = !this.isShowMenu
      },
      closeShowMenu() {
        this.isShowMenu = false
      },
      goRegister() {
        let self = this
        self.$router.push({name: 'register'})
      },
      toHouse() {
        let address = "all"
        let rent = "all"
        this.$router.push({name: 'House', params: {address: address, rent: rent}})
      }
    }
  }
</script>

<style scoped>
  .text {
    font-size: 14px;
    cursor: pointer;
    padding-left: 15%;
  }

  .card-div {
    position:absolute;
    top:65px;
    z-index:100;
    padding: 10px 5px 10px 5px;
    width: 7%;
    margin-left: 3.2%;
  }

  .el-card div:hover {
    background-color: #EBEEF5;
  }
</style>
