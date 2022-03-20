<template>
  <div id="user">
    <h2>用户登陆</h2>
      <div>
        <span>学生</span>
        <el-tag
          v-for="item in stus"
          :key="item.label"
          effect="dark"
          @click="login(item.label)">
          {{ item.label }}
        </el-tag>
      </div>
      <div>
        <span>老师</span>
        <el-tag
          v-for="item in techs"
          :key="item.label"
          type="success"
          effect="dark"
          @click="login(item.label)">
          {{ item.label }}
        </el-tag>
      </div>
  </div>
</template>

<script>
export default {
  name: 'User',
  data () {
    return {
      stus: [
        { label: 'stu_user1' },
        { label: 'stu_user2' },
      ],
      techs: [
        { label: 'tech_user1' },
        { label: 'tech_user2' },
      ]
    }
  },
  methods: {
    login(userId) {
      const that = this;
      this.$axios.get(`/api/user/login/${userId}`)
        .then(function (response) {
          that.$message({
            type: 'success',
            duration: 700,
            message: '登录成功'
          });
          that.$router.push('tasklist');
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
div {
    margin: 15px;
    text-align: center;
}
span {
  margin-right: 10px;
  cursor: pointer;
}
</style>
