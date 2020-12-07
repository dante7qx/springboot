<template>
  <el-container class="viewer">
    <el-aside width="200px">
      <el-tree :data="docTree" :props="defaultProps" default-expand-all></el-tree>
    </el-aside>
    <el-main>
      <div id="viewer"></div>
    </el-main>
  </el-container>
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

var viewer;
export default {
  data () {
    return {
      serviceDoc: {
        id: '',
        serviceTitle: '',
        docContent: ''
  	  },
      docTree: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      htmlData: '',
      data: []
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
        that.docTree = that.buildNavTree()
        that.initEditor()
      }) .then(function(e) {
        if(e != null) console.log("err: " + e)
      });
    },
    initEditor() {
      viewer = new Viewer({
        el: document.querySelector('#viewer'),
        previewStyle: 'vertical',
        height: '700px',
        initialValue: this.serviceDoc.docContent,
        plugins: [[codeSyntaxHighlight, { hljs }]]
      })
    },
    getTitle(content) {
      let nav = [];
      let tempArr = [];
      content.replace(/(#+)[^#][^\n]*?(?:\n)/g, function(match, m1, m2) {
        let title = match.replace('\n', '').replace('\\', '');
        let level = m1.length;
        tempArr.push({
          title: title.replace(/^#+\s+/, '').replace(/\([^)]*?\)/, ''),
          level: level,
          children: [],
        });
        nav = tempArr.filter(item => item.level <= 3);
        let index = 0;
        return nav = nav.map(item => {
          item.index = index++;
          return item;
        });
      });
      return nav;
    },
    buildNavTree() {
      let navTree = [];
      let childNodeIndex = 0;
      let titleArr = this.getTitle(this.serviceDoc.docContent)
      for(let i = 0; i < titleArr.length - 1; i++) {
        let nodeI = titleArr[i];
        if(nodeI.level == 1) {
          if(i > 0) {
            nodeI.index = navTree[0].index + 1;
          }
          navTree.push(nodeI)
        }    
        for (let j = i+1; j < titleArr.length; j++) {
          let nodeJ = titleArr[j];
          if(nodeI.level == nodeJ.level) {
            childNodeIndex = 0;
            break;
          }
          if(nodeI.level == nodeJ.level - 1) {
            nodeJ.index = childNodeIndex++;
            nodeI.children.push(nodeJ)
          }
        }
      }
      return navTree;
    }
  },
  mounted () {
    this.initServiceDoc()
  }
}
</script>

<style scoped>
.viewer {
  margin: 10px;
  border: 1px solid #eee;
}
.el-aside {
  background-color: #D3DCE6;
  color: #333;
  text-align: center;
}
.el-tree {
  background-color: #D3DCE6;
  color: #333;
  margin-top: 10px;
}
</style>
