<template>
  <div class="deployProcess">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="流程名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入流程名称"
          clearable
          size="small"
          
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" >搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" >重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8" style="margin-bottom:10px;">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
    </el-row>

    <el-table
      :data="tableData"
      border
      style="width: 100%">
      <el-table-column
        prop="deploymentId"
        label="部署编号"
        header-align="center"
        width="400">
      </el-table-column>
      <el-table-column
        prop="processDefinitionId"
        label="流程定义编号"
        header-align="center"
        width="480">
      </el-table-column>
      <el-table-column
        prop="processName"
        label="流程名称"
        header-align="center"
        align="center"
        width="180">
        <template slot-scope="scope">
          <el-button type="text" @click="handleReadImage(scope.row.deployProcessId)">
            <span>{{ scope.row.processName }}</span>
          </el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="部署时间"
        header-align="center"
        align="center">
      </el-table-column>
    </el-table>
    <el-dialog title="部署流程定义" :visible.sync="open" width="700px" append-to-body>
       <el-form ref="form" :model="form" :rules="rules" label-width="150px">
         <el-form-item label="流程名称" prop="processName">
          <el-input v-model="form.processName" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程文件" prop="attachments">
          <el-upload
            ref="upload"
            class="upload-demo"
            name="attachments"
            action="http://localhost:8100/api/flow/create_flow_define"
            accept=".xml"
            :on-change="onChange"
            :on-success="onSuccess"
            :limit="1"
            :file-list="fileList"
            :auto-upload="false"
            :data="form">
            <el-button size="small" type="primary">选取文件</el-button>
            <div slot="tip" class="el-upload__tip">只能上传.bpmn20.xml文件，且不超过2mb</div>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">确定</el-button>
          <el-button @click="onCancel">取消</el-button>
        </el-form-item>
       </el-form>
    </el-dialog>

    <!-- 流程图 -->
    <el-dialog :title="readImage.title" :visible.sync="readImage.open" width="70%" append-to-body>
      <el-image :src="readImage.src"></el-image>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: 'DeployProcess',
  data () {
    return {
      open: false,
      queryParams: {},
      tableData: [],
      fileList: [],
      form: {
        processName: ''
      },
      rules: {
        processName: [
          { required: true, message: '请输入流程名称', trigger: 'blur' }
        ],
      },
      readImage:{
        title: '流程图',
        open: false,
        src: "",
      },
    }
  },
  created() {
    this.loadTable();
  },
  methods: {
    handleAdd() {
      this.open = true;

    },
    handleDelete() {

    },
    onChange(file, fileList) {
      if (fileList.length > 0) {
        this.fileList = [fileList[fileList.length - 1]] 
      }
    },
    onSuccess(response, file, fileList) {//文件上传成功时的钩子
        if(response) {
            this.$message.success('导入成功');
            this.open=false;
            this.loadTable();
        } else {
            this.$message.error('导入失败');
        }
        this.fileList = [];
        this.$refs['form'].resetFields();
        this.$refs['upload'].clearFiles();
    },
    onSubmit() {
      if(this.fileList.length < 1) {
        this.$message({type: 'error', message: '请上传流程定义文件!'});
      } 
      this.$refs.form.validate((valid) => {
        if (valid) {
            this.$refs.upload.submit();//主要是这里
        } else {
            console.log('error submit!!');
            return false;
        }
      })
    },
    onCancel() {
      this.open=false;
      this.fileList = [];
      this.$refs['form'].resetFields();
      this.$refs['upload'].clearFiles();
    },
    loadTable() {
      const that = this;
      this.$axios.get('/api/flow/query_flow_define')
        .then(function (response) {
          that.tableData = response.data
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    handleReadImage(deployProcessId) {
      const that = this;
      this.readImage.open=true;
      this.$axios.post(`/api/flow/find_flow_chart/${deployProcessId}`)
        .then(function (response) {
          // console.log(response)
          that.readImage.src = "data:image/jpeg;base64,"+response.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    }

  }
}
</script>

<style scoped>
.deployProcess {
  margin: 10px;
}
</style>
