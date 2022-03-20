<template>
  <div class="taskList">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="请假申请" name="leaveAppl">
        <el-form ref="leaveApplForm" label-position="right" label-width="80px" :model="leaveApplForm" :rules="rules">
          <el-form-item label="开始时间" prop="startTime">
            <el-date-picker
              v-model="leaveApplForm.startTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="结束时间" prop="endTime">
            <el-date-picker
              v-model="leaveApplForm.endTime"
              type="date"
              placeholder="选择日期"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="请假原因" prop="leaveReason">
            <el-input v-model="leaveApplForm.leaveReason"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="submitForm('I')">保存</el-button>
            <el-button type="primary" @click="submitForm('A')">提交</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="待办任务" name="toDoTask">
        <el-table
          :data="todoTableData"
          style="width: 100%">
          <el-table-column
            prop="date"
            label="日期"
            width="180">
          </el-table-column>
          <el-table-column
            prop="name"
            label="姓名"
            width="180">
          </el-table-column>
          <el-table-column
            prop="address"
            label="地址">
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="已办任务" name="doneTask">已办任务</el-tab-pane>
      <el-tab-pane label="我发起的任务" name="myInitiateTask">我发起的任务</el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'TaskList',
  data () {
    var validateStartTime = (rule, value, callback) => {
      if(!this.leaveApplForm.startTime) {
        callback(new Error('请输入时间!'));
      } else if(Date.parse(this.leaveApplForm.startTime) >= Date.parse(this.leaveApplForm.endTime)) {
        callback(new Error('开始时间不能大于结束时间!'));
      } else {
        callback();
      }
    };
    var validateEndTime = (rule, value, callback) => {
      if(!this.leaveApplForm.endTime) {
        callback(new Error('请输入时间!'));
      } else if(Date.parse(this.leaveApplForm.startTime) >= Date.parse(this.leaveApplForm.endTime)) {
        callback(new Error('开始时间不能大于结束时间!'));
      } else {
        callback();
      }
    };
    return {
      activeName: 'leaveAppl',
      leaveApplForm: {},
      todoTableData: [],
      rules: {
        startTime: [
          { type: 'date', required: true, trigger: 'change', validator: validateStartTime }
        ],
        endTime: [
          { type: 'date', required: true, trigger: 'change', validator: validateEndTime }
        ],
        leaveReason: [
          { required: true, message: '请输入请假原因', trigger: 'blur' },
        ]
      }
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab.name)
    },
    submitForm(type) {
      this.leaveApplForm.operType = type;
      this.$refs.leaveApplForm.validate((valid) => {
          if (valid) {
            const that = this;
            this.$axios.post('/api/leave/add', this.leaveApplForm,
              { 
                headers: {
                  'Content-Type': 'application/json'
                }
              })
              .then(function (response) {
                that.activeName = "doneTask";
                that.$refs.leaveApplForm.resetFields();
              })
              .catch(function (error) {
                console.log(error);
              });
            
          } else {
            return false;
          }
        });
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
  .taskList {
    margin: 15px;
  }
</style>
