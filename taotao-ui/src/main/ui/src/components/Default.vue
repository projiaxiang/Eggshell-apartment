<template>
    <div>
      <el-container style="margin-left: 100px">
        <el-aside width="410px">
          <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal">
            <el-menu-item index="1" @click="toIndex">首页</el-menu-item>
            <el-menu-item index="2">找房</el-menu-item>
            <el-menu-item index="3">合租</el-menu-item>
            <el-menu-item index="4">整租</el-menu-item>
            <el-menu-item index="5">月租</el-menu-item>
            <el-menu-item index="6" @click="toOwner">我的</el-menu-item>
          </el-menu>
        </el-aside>
        <el-main style="margin-left: 500px">
          <el-radio-group v-if="!user.id" v-model="tabPosition" style="margin-bottom: 30px;">
            <el-radio-button @click.native="toLogin" label="top">登录</el-radio-button>
            <el-radio-button label="right">注册</el-radio-button>
          </el-radio-group>
          <span v-else style="margin-bottom: 30px;">
          欢迎您，<el-button type="text">{{user.name}}</el-button>
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
  export default {
    computed: {
      activeIndex() {
        if (this.$route.name === 'index') {
          return '1'
        } else if (this.$route.name === 'owner') {
          return '6'
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
        }
      }
    },
    created() {
      //如果cookie中存在user_session，则从缓存中获取user相关信息
      let key = this.$cookie.get('user_session')

      if (key) {
        this.getUserFromRedis(key)
      }
    },
    methods: {
      getUserFromRedis(key) {
        this.$axios({
          method: "post",
          url: "/get/user/from/redis",
          data: this.$qs.stringify({
            key: key
          })
        }).then((res)=>{
          this.user = res.data
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
      toIndex() {
        this.$router.push({name: 'index'})
      }
    }
  }
</script>

<style scoped>

</style>
