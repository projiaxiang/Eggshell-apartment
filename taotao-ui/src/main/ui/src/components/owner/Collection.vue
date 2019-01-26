<template>
  <div>
    <div v-for="(o, index) in 5" class="div-top" style="cursor: pointer">
      <el-card shadow="hover">
        <div style="display: inline-block">
          <span style="display: block;font-size: 20px">太阳宫 西坝河北里202号院精装两居室 10号线 国美电器</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">2室1厅  75㎡  整租</span>
          <span style="display: block;margin-top: 5px;font-size: 15px">朝阳  西坝河  西坝河北里</span>
        </div>
        <div style="display: inline-block;float: right">
          <span style="display: block">2018-12-20 07:43</span>
          <span style="display: block;margin-left: 34.5px;margin-top: 10px"><span style="color: red;font-size: 25px">2399</span>元/月</span>
          <el-button type="text" style="float: right">删除</el-button>
        </div>
      </el-card>
    </div>
  </div>
</template>

<script>
  export default {
    name: "Collection"
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
