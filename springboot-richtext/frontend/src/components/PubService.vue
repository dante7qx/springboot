<template>
  <div id="pubService" align="center">
  	<h2 align="center">文档在线编辑</h2>
    <el-card :style="{ width: '280px' }" :body-style="{ padding: '0px' }">
      <img src="static/image/icr.png" class="image">
      <div style="padding: 14px;">
        <span>{{ msg }}</span>
        <div class="bottom clearfix">
          <router-link tag="a" target="_blank" to="/pageview/1"><el-button type="primary" plain>查看</el-button></router-link>
          <router-link tag="a" target="_blank" to="/pageedit/1"><el-button type="success" plain>编辑</el-button></router-link>
          <router-link tag="a" target="_blank" to="/pagegitlab"><el-button type="danger" plain>Gitlab</el-button></router-link>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  data () {
    return {
      msg: 'ICR智能识别服务'
    }
  }
}
</script>

<style scoped>
.image {
  width: 100%;
}
.bottom {
  margin-top: 13px;
  line-height: 12px;
}
</style>
