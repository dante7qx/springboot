<template>
  <div>
    <span v-html="htmlData">{{htmlData}}</span>
    <div id="docEditor" style="display: none;"></div>
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
  	  },
  	  htmlData: ''
    }
  },
  methods: {
    initServiceDoc() {
      const that = this
      let id = this.$route.params.id
      let act = this.$route.params.act
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
      this.htmlData = editorInstance.getHtml()
    },
  },
  mounted () {
    this.initServiceDoc()
  }
}
</script>

<style scoped>
</style>
