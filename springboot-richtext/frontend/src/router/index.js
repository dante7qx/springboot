import Vue from 'vue'
import Router from 'vue-router'
import PubService from '@/components/PubService.vue'
import PageEdit from '@/components/PageEdit.vue'
import PageView from '@/components/PageView.vue'


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'PubService',
      component: PubService
    },
    {
      name: 'PageEdit',
      path: '/pageEdit/:id',
      component: PageEdit
    },
    {
      path: '/pageView/:id',
      name: 'PageView',
      component: PageView
    },
  ]
})
