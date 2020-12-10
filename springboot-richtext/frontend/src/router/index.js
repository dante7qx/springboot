import Vue from 'vue'
import Router from 'vue-router'
import PubService from '@/components/PubService.vue'
import PageEdit from '@/components/PageEdit.vue'
import PageView from '@/components/PageView.vue'
import PageGitlab from '@/components/PageGitlab.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'PubService',
      component: PubService
    },
    {
      path: '/pageedit/:id',
      name: 'PageEdit',
      component: PageEdit
    },
    {
      path: '/pageview/:id',
      name: 'PageView',
      component: PageView
    },
    {
      path: '/pagegitlab',
      name: 'PageGitlab',
      component: PageGitlab
    }
  ]
})
