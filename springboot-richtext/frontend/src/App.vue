<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      mode="horizontal"
      @select="handleSelect"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
      :router="true">
      <el-menu-item index="/online">在线编辑</el-menu-item>
      <el-menu-item index="/pagegitedit">结合 GitHub</el-menu-item>
    </el-menu>
    <router-view class="main"></router-view>
  </div>
  
</template>

<script>
export default {
  name: 'app',
  data () {
    return {
      activeIndex: '/online'
      
    }
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
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
    height: 100vh;
    overflow-x: hidden;
    overflow-y: auto;
  }
</style>
