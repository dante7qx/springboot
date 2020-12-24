<template>
  <el-container class="viewer">
    <el-aside width="200px">
      <el-tree :data="docTree" 
        :props="defaultProps" 
        default-expand-all 
        :expand-on-click-node="false"
        @node-click="docNavHandler">
      </el-tree>
    </el-aside>
    <el-container>
      <el-main>
        <div id="viewer"></div>
        <div id="docEditor" style="display: none;"></div>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import Editor from '@toast-ui/editor'
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
      docTree: [],
      defaultProps: {
        children: 'children',
        label: 'title'
      },
      htmlData: ''
    }
  },
  methods: {
    initServiceDoc() {
      const that = this
      // let mdUrl = 'http://localhost:8700/dante/dante-gitlab-cms/-/raw/master/%E4%BA%A7%E5%93%81%E8%AF%B4%E6%98%8E/%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B.md?inline=false'
      let mdUrl = 'https://raw.githubusercontent.com/AlibabaCloudDocs/ecs/master/intl.zh-CN/%E4%BA%A7%E5%93%81%E7%AE%80%E4%BB%8B/%E4%BB%80%E4%B9%88%E6%98%AF%E4%BA%91%E6%9C%8D%E5%8A%A1%E5%99%A8ECS.md'
      fetch('/api/doc/fetch_remote/', {
        body: JSON.stringify({"url": mdUrl}),
        headers: {
          'content-type': 'application/json'
        },
        method: 'POST'
      }).then(function(response) {
          return response.text();
        }).then(function(result) {
          that.serviceDoc.docContent = result
          // that.docTree = that.buildNavTree()
          that.initViewer()
        }).then(function(e) {
          if(e != null) console.log("err: " + e)
        })
    },
    initViewer() {
      const editor = new Editor({
        el: document.querySelector('#docEditor'),
        initialValue: this.serviceDoc.docContent
      })
      this.htmlData = editor.getHtml()
      this.filterHtml(this.htmlData)
      const viewer = new Viewer({
        el: document.querySelector('#viewer'),
        previewStyle: 'vertical',
        initialValue: this.htmlData,
        plugins: [[codeSyntaxHighlight, { hljs }]]
      })
    },
    filterHtml(htmlContent) {
      let i = -1
      let filterHtmlContent = htmlContent.replace(/<[hH]([1-3])>/g, function(match, m1, m2) {
        i++;
        return match.replace(match, '<h' + m1 + ' id="index-'+m1+'-' + i +'">')
      })
      this.htmlData = filterHtmlContent
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
      let titleArr = this.getTitle(this.serviceDoc.docContent)
      for(let i = 0; i < titleArr.length - 1; i++) {
        let nodeI = titleArr[i];
        if(nodeI.level == 1) {
          navTree.push(nodeI)
        }    
        for (let j = i+1; j < titleArr.length; j++) {
          let nodeJ = titleArr[j];
          if(nodeI.level == nodeJ.level) {
            break;
          }
          if(nodeI.level == nodeJ.level - 1) {
            nodeI.children.push(nodeJ)
          }
        }
      }
      return navTree;
    },
    docNavHandler(data) {
      let selector = '#index-' + data.level + '-' + data.index
      document.querySelector(selector).scrollIntoView()  
    }
  },
  mounted () {
    this.initServiceDoc()
  }
}
</script>

<style scoped>
.viewer {
  margin: 0px;
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
  margin-top: 15px;
}
.el-main {
  overflow: auto;
  height: 100vh;
}
</style>
