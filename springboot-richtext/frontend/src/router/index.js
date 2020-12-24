import Vue from 'vue'
import Router from 'vue-router'
import PubService from '@/components/PubService.vue'
import PageEdit from '@/components/PageEdit.vue'
import PageView from '@/components/PageView.vue'
import PageGit from '@/components/PageGit.vue'
import PageGitEdit from '@/components/PageGitEdit.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/online',
      component: PubService
    },
    {
      path: '/pageedit/:id',
      component: PageEdit
    },
    {
      path: '/pageview/:id',
      component: PageView
    },
    {
      path: '/pagegit',
      component: PageGit
    },
    {
      path: '/pagegitedit',
      component: PageGitEdit
    }
  ]
})
