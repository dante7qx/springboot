<template>
  <div id="viewer" class="viewer">
  </div>
</template>

<script>
import Viewer from '@toast-ui/editor/dist/toastui-editor-viewer'
import codeSyntaxHighlight from '@toast-ui/editor-plugin-code-syntax-highlight'
import hljs from 'highlight.js/lib/highlight'
import java from 'highlight.js/lib/languages/java'
import xml from 'highlight.js/lib/languages/xml'

import 'codemirror/lib/codemirror.css'
import 'highlight.js/styles/github.css';
import '@toast-ui/editor/dist/toastui-editor-viewer.css'

hljs.registerLanguage('java', java)
hljs.registerLanguage('xml', xml)

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
      const viewer = new Viewer({
        el: document.querySelector('#viewer'),
	    previewStyle: 'vertical',
	    height: '700px',
	    initialValue: this.serviceDoc.docContent,
	    plugins: [[codeSyntaxHighlight, { hljs }]]
      })
    },
  },
  mounted () {
    this.initServiceDoc()
  }
}
</script>

<style scoped>
.viewer {
  margin: 20px;
}
</style>
