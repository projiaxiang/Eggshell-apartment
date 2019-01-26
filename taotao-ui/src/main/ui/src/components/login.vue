<template>
  <div class="login-box">
    <el-row>
      <el-col :span="8">
        <el-input id="name"  v-model="form.username" placeholder="请输入帐号">
          <template slot="prepend">帐号</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-input id="password" v-model="form.password" type="password" placeholder="请输入密码">
          <template slot="prepend">密码</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 0">
      <el-col :span="8">
        <el-button @click="onSubmit" style="width:100%" type="primary">登录</el-button>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8" style="margin-left: 16%">
        <el-button type="text">还没有账号？立即注册</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  data() {
    return {
      form: {
        username: null,
        password: null
      }
    }
  },
  methods: {
    onSubmit() {
      let self = this

      this.$axios({
        method: "post",
        url: "/login",
        data: this.$qs.stringify({
          username:this.form.username,
          password:this.form.password
        })
      }).then((res)=>{
        if (res.data.success) {
          self.$router.push({name: 'index'})
          self.$cookie.set('user_session', res.data.message)
          //为了在跳转路由时验证是否登录
          sessionStorage.setItem('user_session', res.data.message)
        } else {
          self.$message.error('用户名或密码错误')
        }
      })
      .catch(function () {
        self.$message.error('服务器端错误')
      })
    },
    register() {
      console.log('注册新用户')
    }
  }
}
</script>

<style scoped>
  .el-row {
    margin-bottom: 20px;
  }
  .login-box {
    margin-top:10%;
    margin-left:40%;
  }

</style>
