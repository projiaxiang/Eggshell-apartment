<template>
  <div style="width: 80%;margin: 0 auto">
    <el-container>
      <el-header style="margin-top: 10px">
        <el-breadcrumb separator-class="el-icon-arrow-right">
          <el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>
          <el-breadcrumb-item>我的</el-breadcrumb-item>
          <el-breadcrumb-item>{{tabName}}</el-breadcrumb-item>
        </el-breadcrumb>
      </el-header>
      <el-main>
        <el-tabs @tab-click="handleClick" tab-position="left" style="min-height: 408px">
          <el-tab-pane v-for="(tab, index) in tabs" :key="index" :label="tab.tab">
            <router-view style="margin-left: 100px;"></router-view>
          </el-tab-pane>
        </el-tabs>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  export default {
    created() {
      this.$router.push({name: 'personal'})
    },
    data() {
      return {
        tabs: [
          {tab: '个人中心', id: '1'},
          {tab: '我的收藏', id: '2'},
          {tab: '我的发布', id: '3'},
          {tab: '我的预定', id: '4'}
        ],
        tabName: '个人中心'
      }
    },
    methods: {
      handleClick(tab, event) {
        this.tabName = tab.$options.propsData.label
        if (tab.$options.propsData.label === '个人中心') {
          this.$router.push({name: 'personal'})
        } else if (tab.$options.propsData.label === '我的收藏') {
          this.$router.push({name: 'collection'})
        } else if (tab.$options.propsData.label === '我的发布') {
          this.$router.push({name: 'release'})
        } else if (tab.$options.propsData.label === '我的预定') {
          this.$router.push({name: 'order'})
        }
      }
    }
  }
</script>

<style scoped>

</style>
