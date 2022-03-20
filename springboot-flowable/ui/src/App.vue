<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      :router="true">
      <el-menu-item index="/">用户登陆</el-menu-item>
      <el-menu-item index="/deployprocess">流程部署</el-menu-item>
      <el-menu-item index="/tasklist">请假管理</el-menu-item>
    </el-menu>
    <router-view class="main"></router-view>
  </div>
</template>

<script>
export default {
  name: 'App',
  data() {
    return {
      activeIndex: '/'
    }
  },
  watch:{
    $route(to,from) {
      this.activeIndex = to.path;
    }
  }
}
</script>

<style>
  html, body {
    margin: 0;
    padding: 0;
    overflow-y: hidden;
  }
  h1 {
    text-align: center;
  }
  .main {
    height: 610px;
    overflow-x: hidden;
    overflow-y: auto;
  }
</style>
