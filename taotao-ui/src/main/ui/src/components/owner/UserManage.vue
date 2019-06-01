<template>
  <div v-if="users.length !== 0">
    <el-table
      :data="users"
      style="width: 100%">
      <el-table-column
        prop="username"
        label="账号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="name"
        label="昵称"
        width="180">
      </el-table-column>
      <el-table-column
        prop="sex"
        label="性別"
        width="180">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.sex === '1'">男</el-tag>
          <el-tag v-else type="danger">女</el-tag>
        </template>
      </el-table-column>
      <el-table-column
        prop="phone"
        label="手机号"
        width="180">
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button @click="deleteUser(scope.row)" type="text" size="small">刪除</el-button>
        </template>
      </el-table-column>
    </el-table>
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
      还没有用户注册该网站
    </label>
  </div>
</template>

<script>
    export default {
      data() {
        return {
          total: 0,
          currentPage1: 1,
          pageSize: 5,
          users: [],
          baseUrl: 'http://127.0.0.1:9091/image/'
        }
      },
      created() {
        this.init()
      },
      methods: {
        init() {
          this.findUsers()
        },
        findUsers() {
          let self = this
          let startPage = (this.currentPage1 - 1) * this.pageSize
          this.$axios({
            method: "post",
            url: "/list/user",
            data: this.$qs.stringify({
              startPage: startPage,
              pageSize: self.pageSize
            })
          }).then((res)=>{
            this.total = res.data.total
            this.users = res.data.user
          })
        },
        handleCurrentChange() {
          this.findUsers()
        },
        deleteUser(row) {
          let self = this
          const h = this.$createElement;
          this.$msgbox({
            title: '提示',
            message: h('p', null, [
              h('span', null, '是否永久刪除用戶 '),
              h('i', { style: 'color: red' }, row.name)
            ]),
            showCancelButton: true,
            confirmButtonText: '确定',
            cancelButtonText: '取消',
          }).then(action => {
            self.$axios({
              method: "post",
              url: "/delete/user",
              data: self.$qs.stringify({
                id: row.id
              }),
            }).then((res)=>{
              if (res.data.success) {
                self.$message({message: '删除成功', type: 'success'})
                self.findUsers()
              } else {
                self.$message.error('删除失败')
              }
            })
          });
        }
      }
    }
</script>

<style scoped>

</style>
