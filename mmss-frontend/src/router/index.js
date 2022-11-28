import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginVisitor from '@/components/LoginVisitor'
import LoginManager from '@/components/LoginManager'
import LoginEmployee from '@/components/LoginEmployee'
import CreateArtefactForm from '@/components/CreateArtefactForm'
import RoomsTable from '@/components/RoomsTable'

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

function requireStaff(from, to, next) { 
  // check that either the employee or manager is logged in
  if (!sessionStorage.getItem('loggedInEmployee') && !sessionStorage.getItem('loggedInManager')) {
    next({ name: 'LoginManager' });
  }
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
      path: '/artefact/createForm',
      name: 'CreateArtefactForm',
      component: CreateArtefactForm,
      beforeEnter: requireStaff
    },
    {
      path: '/room',
      name: 'RoomsTable',
      component: RoomsTable
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
