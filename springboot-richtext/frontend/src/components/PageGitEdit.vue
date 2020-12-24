<template>
  <div>
    <el-table
      :data="tableData"
      stripe
      style="width: 100%">
      <el-table-column
        type="index"
        width="50">
      </el-table-column> 
      <el-table-column
        label="文档标题">
        <template slot-scope="scope">
          <el-link type="primary" @click="docChapter(scope.row.id)">{{ scope.row.title }}</el-link>
        </template>
      </el-table-column>
      <el-table-column
        align="right">
        <template slot="header">
          <el-button type="success" @click="docTitleFormVisible = true; form = {}">新增</el-button>
        </template>
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="editRow(scope.$index, scope.row)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteRow(scope.$index, scope.row)">删除</el-button>
          <el-button
            size="mini"
            type="info"
            @click="deleteRow(scope.$index, scope.row)">查看文档</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="文档" :visible.sync="docTitleFormVisible" required>
      <el-form :model="form">
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="docTitleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitDoc">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog title="文档目录" :visible.sync="docChapterVisible">
      <el-container>
        <el-aside width="200px">
          <p>文档目录</p>
        </el-aside>
        <el-main>Main</el-main>
      </el-container>
      <el-form :model="detailForm">
        <el-form-item label="标题" :label-width="formLabelWidth">
          <el-input v-model="form.title" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="docTitleFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitDoc">确 定</el-button>
      </div>
    </el-dialog>
  </div>
  
</template>

<script>
export default {
  data () {
    return {
      tableData: [],
      form: {
        id: '',
        title: '',
      },
      detailForm: {
        id: '',
        title: ''
      },
      docTitleFormVisible: false,
      docChapterVisible: false,
      formLabelWidth: '120px'
    }
  },
  methods: {
    loadTable() {
      const that = this
      fetch('/api/git/doc_titles', {
        headers: {
          'content-type': 'application/json'
        },
        method: 'POST'
      }).then(function(response) {
        return response.text();
      }).then(function(result) {
        that.tableData = JSON.parse(result)
      }).then(function(e) {
        if(e != null) console.log("err: " + e)
      })
    },
    editRow(index, row) {
      this.docTitleFormVisible = true
      this.form = row
    },
    deleteRow(index, row) {
      const that = this
      this.$confirm('确认删除？')
          .then(_ => {
            fetch('/api/git/delete/'+row.id, {
              headers: {
                'content-type': 'application/json'
              },
              method: 'DELETE'
            }).then(function(response) {
              return response.text();
            }).then(function(result) {
              that.tableData.splice(index, 1)
            }).then(function(e) {
              if(e != null) console.log("err: " + e)
            })
          })
      .catch(_ => {});
    },
    submitDoc() {
      const that = this
      fetch('/api/git/persist_doc_title', {
        body: JSON.stringify(this.form),
        headers: {
          'content-type': 'application/json'
        },
        method: 'POST'
      }).then(function(response) {
        return response.text();
      }).then(function(result) {
        that.docTitleFormVisible = false
        that.form = {}
        that.loadTable()
      }).then(function(e) {
        if(e != null) console.log("err: " + e)
      })
    },
    docChapter(id) {
      this.docChapterVisible = true
    }
  },
  mounted () {
    this.loadTable()
  }
}
</script>

<style scoped>
.el-row {
  margin: 10px;
}
</style>
