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
    <el-row>
      <el-col :span="8">
        <el-input v-model="form.secondPassword" type="password" placeholder="请输入密码">
          <template slot="prepend">再次输入</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-input v-model="form.name" placeholder="请输入密码">
          <template slot="prepend">用户名</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <el-input v-model="form.phone" placeholder="请输入密码">
          <template slot="prepend">手机号</template>
        </el-input>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="8">
        <template> 性别
          <el-radio v-model="form.sex" label="1">男</el-radio>
          <el-radio v-model="form.sex" label="2">女</el-radio>
        </template>
      </el-col>
    </el-row>
    <el-row style="margin-bottom: 0">
      <el-col :span="8">
        <el-button @click="onSubmit" style="width:100%" type="primary">立即注册</el-button>
      </el-col>
    </el-row>
    <el-row style="margin-top: 10px">
      <el-col :span="8">
        <el-button @click="goBack" style="width:100%" type="primary">返回</el-button>
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
          password: null,
          secondPassword: null,
          name: null,
          phone: null,
          sex: null
        }
      }
    },
    methods: {
      goBack() {
        this.$router.go(-1)
      },
      onSubmit() {
        let self = this

        if (self.form.password !== self.form.secondPassword) {
          self.$message.warning('两次密码输入不一样')
          return
        }

        self.$axios({
          method: "post",
          url: "/register",
          data: {
            username:self.form.username,
            password:self.form.password,
            name: self.form.name,
            phone: self.form.phone,
            sex: self.form.sex
          }
        }).then((res)=>{
          if (res.data.success) {
            self.$router.push({name: 'login'})
          } else {
            self.$message.error('系统错误')
          }
        })
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
