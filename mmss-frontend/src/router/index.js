import Vue from 'vue'
import Router from 'vue-router'
import Hello from '@/components/Hello'
import LoginVisitor from '@/components/LoginVisitor'
import LoginManager from '@/components/LoginManager'
import LoginEmployee from '@/components/LoginEmployee'
import RoomsTable from '@/components/RoomsTable'
import ArtefactsView from '@/components/ArtefactsView'
import ManageLoan from '@/components/ManageLoan'
import ManageShift from '@/components/ManageShift'
import ManageNotification from '@/components/ManageNotification'
import ManageAccount from '@/components/ManageAccount'
import CreateVisitor from '@/components/CreateVisitor'
import UpdateVisitor from '@/components/UpdateVisitor'
import UpdateEmployee from '@/components/UpdateEmployee'
import UpdateManager from '@/components/UpdateManager'
import NavBar from '@/components/NavBar'
import BookTicket from '@/components/BookTicket'
import BookTour from '@/components/BookTour'
import ManageBooking from '@/components/ManageBooking'
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
      path: '/rooms',
      name: 'RoomsTable',
      component: RoomsTable
    },
    {
      path: '/artefacts',
      name: 'ArtefactsView',
      component: ArtefactsView
    },
    {
      path: '/loans/manage', 
      name: 'ManageLoan',
      component: ManageLoan
    },
    {
      path: '/shift/manage', 
      name: 'ManageShift',
      component: ManageShift
    },
    {
      path: '/notification/manage', 
      name: 'ManageNotification',
      component: ManageNotification
    },
    {
       path: '/navbar',
       name: 'NavBar',
       component: NavBar
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
    {
      path: '/bookings/ticket',
      name: 'BookTicket',
      component: BookTicket
    },
    {
      path: '/bookings/tour',
      name: 'BookTour',
      component: BookTour
    },
    {
      path: '/bookings/manage',
      name: 'ManageBooking',
      component: ManageBooking
    },
    {
      path: "*",
      redirect: "/404"
    }
  ]
})
