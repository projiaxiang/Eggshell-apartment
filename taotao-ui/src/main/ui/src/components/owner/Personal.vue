<template>
  <el-form label-width="80px" style="width: 50%;">
    <el-form-item label="用户名">
      <el-input v-model="user.username" :disabled="true"></el-input>
    </el-form-item>
    <el-form-item label="姓名">
      <el-input v-model="user.name"></el-input>
    </el-form-item>
    <el-form-item label="手机号">
      <el-input v-model="user.phone"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <template>
        <el-radio v-model="user.sex" label="1">男</el-radio>
        <el-radio v-model="user.sex" label="2">女</el-radio>
      </template>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="onSubmit">保存</el-button>
      <el-button type="primary" @click="showPassword">修改密码</el-button>
    </el-form-item>
    <!--修改密码模态框-->
    <el-dialog title="修改密码" :visible.sync="dialogFormVisible">
      <el-form style="width: 80%;margin-left: 30px">
        <el-form-item label="新密码" :label-width="formLabelWidth">
          <el-input type="password" v-model="password"></el-input>
        </el-form-item>
        <el-form-item label="再输入一次" :label-width="formLabelWidth">
          <el-input type="password" v-model="secondPassword"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updatePassword">确 定</el-button>
      </div>
    </el-dialog>
  </el-form>
</template>

<script>
  import UserRedis from '../../utils/RedisUtil.js'

  export default {
    mixins: [UserRedis],
    data() {
      return {
        token: null,
        user: {
          id: null,
          username: null,
          password: null,
          name: null,
          sex: null,
          phone: null
        },
        formLabelWidth: '120px',
        dialogFormVisible: false,
        password: null,
        secondPassword: null
      }
    },
    async created() {
      //如果cookie中存在user_session，则从缓存中获取user相关信息
      let key = this.$cookie.get('user_session')

      if (key) {
        this.token = key
        await this.getUserInfoFromRedis(key)
      }
    },
    methods: {
      onSubmit() {
        let self = this

        this.$axios({
          method: "post",
          url: "/update/user",
          params: {
            token: this.token
          },
          data: this.user,
        }).then((res)=>{
          if (res.data === 1) {
            self.$message({message: '修改成功', type: 'success'})
          } else if (res.data === 0) {
            self.$message.error('修改失败')
          }
        })
      },
      showPassword() {
        this.dialogFormVisible = true
      },
      updatePassword() {
        let self = this

        if (this.password === null) {
          this.$message({message: '密码不能为空', type: 'warning'})
          return
        } else if (this.password !== this.secondPassword) {
          this.$message({message: '两次密码输入不一致', type: 'warning'})
          return
        }
        this.$axios({
          method: "post",
          url: "/update/user/password",
          data: this.$qs.stringify({
            id: this.user.id,
            password: this.password
          }),
        }).then((res)=>{
          if (res.data === 1) {
            self.$message({message: '密码修改成功', type: 'success'})
            self.dialogFormVisible = false
          } else if (res.data === 0) {
            self.$message.error('密码修改失败')
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
