import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginVisitor from '@/components/LoginVisitor'
import LoginManager from '@/components/LoginManager'
import LoginEmployee from '@/components/LoginEmployee'
import ManageLoan from '@/components/ManageLoan'
import ManageAccount from '@/components/ManageAccount'
import CreateVisitor from '@/components/CreateVisitor'
import UpdateVisitor from '@/components/UpdateVisitor'
import UpdateEmployee from '@/components/UpdateEmployee'
import UpdateManager from '@/components/UpdateManager'
// import NavBar from '@/components/NavBar'

Vue.use(Router);

// Methods to restirct access to certain pages to only users who are logged in

// require a visitor to be logged in
function requireVisitor(from, to, next) {
  // check that the visitor is logged in
  if (!sessionStorage.getItem('loggedInVisitor')) {
    next({ name: 'LoginVisitor' });
  }
  // else continue to requested page
  else {
    next();

  }
}

// require a manager to be logged in

function requireManager(from, to, next) {
  // check that the manager is logged in
  if (!sessionStorage.getItem('loggedInManager')) {
    next({ name: 'LoginManager' });
  }
  // else continue to requested page
  else {
    next();

  }
}

// require an employee to be logged in 
function requireEmployee(from, to, next) {
  // check that the employee is logged in
  if (!sessionStorage.getItem('loggedInEmployee')) {
    next({ name: 'LoginEmployee' });
  }
  // else continue to requested page
  else {
    next();

  }
}

// routes to all pages

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
      path: '/accounts/manage',
      name: 'ManageAccount',
      component: ManageAccount
    }, 
    {
      path: '/createVisitor',
      name: 'CreateVisitor',
      component: CreateVisitor
    }, 
    {
      path: '/updateVisitor',
      name: 'UpdateVisitor',
      component: UpdateVisitor
    }, 
    {
      path: '/updateEmployee',
      name: 'UpdateEmployee',
      component: UpdateEmployee
    },
    {
      path: '/updateManager',
      name: 'UpdateManager',
      component: UpdateManager
    },
    // {
    //   path: '/navbar',
    //   name: 'NavBar',
    //   component: NavBar
    // }, 
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
