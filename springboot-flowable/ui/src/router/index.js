import Vue from 'vue'
import Router from 'vue-router'
import DeployProcess from '@/components/DeployProcess'
import TaskList from '@/components/TaskList'
import User from '@/components/User'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/deployprocess',
      name: 'DeployProcess',
      component: DeployProcess
    },
    {
      path: '/',
      name: 'Index',
      component: User
    },
    {
      path: '/tasklist',
      name: 'TaskList',
      component: TaskList
    }
  ]
})
