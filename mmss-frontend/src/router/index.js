import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginVisitor from '@/components/LoginVisitor'
import LoginManager from '@/components/LoginManager'
import LoginEmployee from '@/components/LoginEmployee'
import ManageLoan from '@/components/ManageLoan'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Hello',
      component: Hello
    },
    {
      path: '/login/visitor',
      name: 'LoginVisitor',
      component: LoginVisitor
    },
    {
      path: '/login/manager',
      name: 'LoginManager',
      component: LoginManager
    },
    {
      path: '/login/employee',
      name: 'LoginEmployee',
      component: LoginEmployee
    },
    {
      path: '/loans/manage', 
      name: 'ManageLoan',
      component: ManageLoan
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
