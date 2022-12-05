import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginVisitor from '@/components/LoginVisitor'
import LoginManager from '@/components/LoginManager'
import LoginEmployee from '@/components/LoginEmployee'
import ManageLoan from '@/components/ManageLoan'
import ManageAccount from '@/components/ManageAccount'
import ManageDonation from '@/components/ManageDonation'
import DonationVisitor from '@/components/DonationVisitor'
import ManageOpenDay from '@/components/ManageOpenDay.vue'

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
      path: '/donations/manage', 
      name: 'ManageDonation',
      component: ManageDonation
    },
    {
      path: '/accounts/manage',
      name: 'ManageAccount',
      component: ManageAccount
    },
    {
      path: '/donation/visitor',
      name: 'DonationVisitor',
      component: DonationVisitor
    },
    {
      path: '/openday',
      name: 'ManageOpenDay',
      component: ManageOpenDay
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
