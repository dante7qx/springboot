<template>
  <div>
    <div class="btn">
      <el-button type="success" plain @click="release()">发布</el-button>
    </div>
    <div id="docEditor"></div>
  </div>
</template>

<script>
import Editor from '@toast-ui/editor'
import 'codemirror/lib/codemirror.css'
import '@toast-ui/editor/dist/toastui-editor.css'

var editorInstance
export default {
  data () {
    return {
      serviceDoc: {
  		id: '',
  		serviceTitle: '',
 	    docContent: ''
  	  }
    }
  },
  methods: {
    initServiceDoc() {
	  const that = this
	  let id = this.$route.params.id
	  fetch(`/api/doc/find_by_id/${id}`)
		.then(function(response) {
			return response.text();
		}).then(function(result) {
			that.serviceDoc = JSON.parse(result)
			that.initEditor()
		}) .then(function(e) {
			if(e != null) console.log("err: " + e)
	  });
    },
    initEditor() {
      editorInstance = new Editor({
        el: document.querySelector('#docEditor'),
  	    initialEditType: 'markdown',
	    previewStyle: 'vertical',
	    height: '700px',
	    initialValue: this.serviceDoc.docContent
      })
    },
    release() {
      this.serviceDoc.docContent = editorInstance.getMarkdown()
      let codeInit = {
		method: 'POST',
		body: JSON.stringify(this.serviceDoc), 
		headers: {
	      'content-type': 'application/json'
	    },
	  };
	  fetch(new Request('/api/doc/persist', codeInit)).then(function(response) {
		return response.text();
	  }).then(function(result) {
	  }) .then(function(e) {
		if(e != null) console.log("err: " + e)
	  });
    },
  },
  mounted () {
    this.initServiceDoc()
  }
}
</script>

<style scoped>
.btn {
  margin-top: 13px;
  margin-bottom: 13px;
  line-height: 12px;
}
</style>
