<template>
    <div>
        <b-navbar class="NavBar" type="dark" variant="dark">
            <b-navbar-brand class="icon_band">
            <a href="/#">
                <img src="../assets/icon.png">
            </a>
            <label for="welcomeText">{{ welcomeText }}</label>
            </b-navbar-brand>
            <b-button v-if="loginStatus === 'out'" href="/" variant="outline-light" class="my-2 my-sm-0">Home</b-button>
            <b-navbar-nav v-if="loginStatus !== 'out'" class="ml-auto">
            <b-button href="/" variant="outline-light" class="my-2 my-sm-0">Home</b-button>
                <!-- Visitor buttons-->
                <b-button v-if="loginStatus === 'visitor'" href="/#/artefact/view" variant="outline-light" class="my-2 my-sm-0">Loans</b-button>
                <b-button v-if="loginStatus === 'visitor'" href="/#/donation/request" variant="outline-light" class="my-2 my-sm-0">Donations</b-button>
                <b-button v-if="loginStatus === 'visitor'" href="/#/booking/manage" variant="outline-light" class="my-2 my-sm-0">Bookings</b-button>
                <!-- Staff buttons-->
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/booking/manage" variant="outline-light" class="my-2 my-sm-0">Artefacts</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/loans/manage" variant="outline-light" class="my-2 my-sm-0">Loans</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/booking/manage" variant="outline-light" class="my-2 my-sm-0">Donations</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/booking/manage" variant="outline-light" class="my-2 my-sm-0">Bookings</b-button>
                <!-- Manager buttons-->
                <b-button v-if="loginStatus === 'manager'" href="/#/opendays/manage" variant="outline-light" class="my-2 my-sm-0">Open Days</b-button>
                <b-button v-if="loginStatus === 'manager'" href="/#/shift/manage" variant="outline-light" class="my-2 my-sm-0">Shifts</b-button>
                <b-button v-if="loginStatus === 'manager'" href="/#/accounts/manage" variant="outline-light" class="my-2 my-sm-0">Accounts</b-button>
            </b-navbar-nav>

            <!-- Buttons to refresh, clear selections, and select all in table-->
            <b-navbar-nav v-if="loginStatus !== 'out'" class="ml-auto">
                <b-button href="/#/notification/manage" variant="outline-light" class="my-2 my-sm-0" > Inbox</b-button>
                <b-nav-item-dropdown text="User" right>
                    <b-dropdown-item href="/#/opendays/manage">Edit Account</b-dropdown-item>
                    <b-dropdown-item @click="logout()">Logout</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
            <b-navbar-nav v-if="loginStatus === 'out'" class="ml-auto">
                <b-nav-item-dropdown text="Account" right>
                    <b-dropdown-item href="/#/opendays/manage">Create</b-dropdown-item>
                    <b-dropdown-item href="/#/opendays/manage">Login</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-navbar>
    </div>
</template>

<script>
export default {
    name: 'NavBar',
    // variables
    data() {
        return {
            loginStatus: '',
            welcomeText: ''
        }
    },
    created: function () {
        // checking if the user is already logged in 
        const loggedInEmployee = sessionStorage.getItem('loggedInEmployee');
        const loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        const loggedInManager = sessionStorage.getItem('loggedInManager');

        //check to see if an account is already logged in

        if (loggedInVisitor) {
            // redirect to page for visitors
            this.loginStatus = 'visitor';
            let userName = JSON.parse(loggedInVisitor).userName;
            let firstName = userName.substr(0, userName.indexOf('@'));
            if(firstName.indexOf('.')>0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName.charAt(0).toUpperCase() + firstName.slice(1);
        }
        else if (loggedInEmployee) {
            // redirect to page for employees
            this.loginStatus = 'employee';
            let userName = JSON.parse(loggedInEmployee).userName;
            let firstName = userName.substr(0, userName.indexOf('@'));
            if(firstName.indexOf('.')>0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName.charAt(0).toUpperCase() + firstName.slice(1);

        }
        else if (loggedInManager) {
            // redirect to page for the manager
            this.loginStatus = 'manager';
            let userName = JSON.parse(loggedInManager).userName;
            let firstName = userName.substr(0, userName.indexOf('@'));
            if(firstName.indexOf('.')>0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName.charAt(0).toUpperCase() + firstName.slice(1);
        }
        else {
            // redirect to page for logged out users
            this.loginStatus = 'out';
            this.welcomeText= 'Welcome, stranger';
        }
        console.log(this.welcomeText)
    }, 
    methods: { 
        logout () { 
        sessionStorage.removeItem('loggedInEmployee');
        sessionStorage.removeItem('loggedInVisitor');
        sessionStorage.removeItem('loggedInManager');
        this.$router.push('/login/visitor');
        location.reload();
        }
    }
}
</script>

<style>
.NavBar{
    position: fixed;
    top: 0;
    width: 100%;
    height: 7%;
    bottom: 0px;
    }
.icon_band{
    padding: 0px;
    height: 100%;
}
.icon_band img{
    height: 100%;
    padding: 5px;
}
.NavBar .navbar-nav .nav-link{
    display: inline-block!important;
    font-weight: 400!important;
    color: #f8f9fa!important;
    border-color: #f8f9fa!important;
    text-align: center!important;
    vertical-align: middle!important;
    -webkit-user-select: none!important;
    -moz-user-select: none!important;
    -ms-user-select: none!important;
    user-select: none!important;
    background-color: transparent!important;
    border: 1px solid!important;
    padding: 0.375rem 0.75rem!important;
    font-size: 1rem!important;
    line-height: 1.5!important;
    border-radius: 0.25rem!important;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out!important;
    margin: 7px!important;
}
.NavBar .navbar-nav .nav-link:hover{
    color: #212529!important;
    background-color: #f8f9fa!important;
}
.NavBar .btn {
    margin: 7px!important;
}
</style>