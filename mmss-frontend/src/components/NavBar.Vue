<template>
    <div id="NarBar">
        <b-navbar class="NavBar" type="dark" variant="dark">
            <!-- band with the clickable logo and description -->
            <b-navbar-brand class="icon_band">
            <a href="/#">
                <img src="../assets/icon.png">
            </a>
            <!-- label containing a welcome text with the name of the user -->
            <label for="welcomeText">{{ welcomeText }}</label>
            </b-navbar-brand>
            <!-- Common buttons-->
            <b-button v-if="loginStatus === 'out'" href="/" variant="outline-light" class="my-2 my-sm-0">Home</b-button>
            <b-button v-if="loginStatus === 'out'" href="/#/artefacts" variant="outline-light" class="my-2 my-sm-0">Artefacts</b-button>
            <b-navbar-nav v-if="loginStatus !== 'out'" class="ml-auto">
                <b-button href="/" variant="outline-light" class="my-2 my-sm-0">Home</b-button>
                <b-button href="/#/artefacts" variant="outline-light" class="my-2 my-sm-0">Artefacts</b-button>
                <!-- Visitor buttons-->
                <b-button v-if="loginStatus === 'visitor'" href="/#/donation/visitor" variant="outline-light"
                    class="my-2 my-sm-0">Donations</b-button>

                <b-nav-item-dropdown v-if="loginStatus === 'visitor'" text="Booking" right>
                    <b-dropdown-item href="/#/bookings/ticket">Tickets</b-dropdown-item>
                    <b-dropdown-item href="/#/bookings/tour">Tours</b-dropdown-item>
                </b-nav-item-dropdown>

                <!-- Staff buttons-->
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/rooms"
                    variant="outline-light" class="my-2 my-sm-0">Rooms</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/loans/manage"
                    variant="outline-light" class="my-2 my-sm-0">Loans</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/donations/manage"
                    variant="outline-light" class="my-2 my-sm-0">Donations</b-button>
                <b-button v-if="loginStatus === 'employee' || loginStatus === 'manager'" href="/#/bookings/manage"
                    variant="outline-light" class="my-2 my-sm-0">Bookings</b-button>
                <!-- Manager buttons-->
                <b-button v-if="loginStatus === 'manager'" href="/#/openday" variant="outline-light"
                    class="my-2 my-sm-0">Open Days</b-button>
                <b-button v-if="loginStatus === 'manager'" href="/#/shift/manage" variant="outline-light"
                    class="my-2 my-sm-0">Shifts</b-button>
                <b-button v-if="loginStatus === 'manager'" href="/#/accounts/manage" variant="outline-light"
                    class="my-2 my-sm-0">Accounts</b-button>
            </b-navbar-nav>

            <!-- Buttons to refresh, clear selections, and select all in table-->
            <b-navbar-nav v-if="loginStatus !== 'out'" class="ml-auto">
                <!-- Inbox and User settings for the logged in -->
                <b-button href="/#/notification/manage" variant="outline-light" class="my-2 my-sm-0" > Inbox</b-button>
                <b-nav-item-dropdown text="User" right>
                    <b-dropdown-item @click="update()">Edit Account</b-dropdown-item>
                    <b-dropdown-item @click="logout()">Logout</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
            <!-- Account buttons for the logged out -->
            <b-navbar-nav v-if="loginStatus === 'out'" class="ml-auto">
                <b-nav-item-dropdown text="Account" right>
                    <b-dropdown-item href= "/#/login/visitor" @click="$bvModal.show('CreateVisitorForm')">Create</b-dropdown-item>
                    <b-dropdown-item href="/#/login/visitor">Login</b-dropdown-item>
                </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-navbar>

        <!-- The component to create an account-->
        <b-modal id='CreateVisitorForm' title="Create an Account" centered size="xl" scrollable hide-footer>
            <create-form />
        </b-modal>

        <!-- The component to edit the managers account -->
        <b-modal id='UpdateManagerForm' title="Update Manager Details" centered size="xl" scrollable hide-footer>
            <update-manager-form />
        </b-modal>

    </div>
</template>

<script>

import CreateForm from './CreateVisitor.vue'
import UpdateManagerForm from './UpdateManager.vue'
export default {
    name: 'NavBar',
    components: {
        "create-form": CreateForm, 
        "update-manager-form": UpdateManagerForm
    },
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
            let firstName = JSON.parse(loggedInVisitor).firstName;
            // let userName = JSON.parse(loggedInVisitor).userName;
            // let firstName = userName.substr(0, userName.indexOf('@'));
            // if (firstName.indexOf('.') > 0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName;
        }
        else if (loggedInEmployee) {
            // redirect to page for employees
            this.loginStatus = 'employee';
            let firstName = JSON.parse(loggedInEmployee).firstName;
            // let userName = JSON.parse(loggedInEmployee).userName;
            // let firstName = userName.substr(0, userName.indexOf('@'));
            // if (firstName.indexOf('.') > 0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName;

        }
        else if (loggedInManager) {
            // redirect to page for the manager
            this.loginStatus = 'manager';
            let userName = JSON.parse(loggedInManager).userName;
            let firstName = userName.substr(0, userName.indexOf('@'));
            if (firstName.indexOf('.') > 0) firstName = firstName.substr(0, firstName.indexOf('.'));
            this.welcomeText = 'Welcome, ' + firstName.charAt(0).toUpperCase() + firstName.slice(1);
        }
        else {
            // redirect to page for logged out users
            this.loginStatus = 'out';
            this.welcomeText = 'Welcome, stranger';
        }
    }, 
    // logout function for the logout button
    methods: { 
        logout () { 
        sessionStorage.removeItem('loggedInEmployee');
        sessionStorage.removeItem('loggedInVisitor');
        sessionStorage.removeItem('loggedInManager');
        this.$router.push('/login/visitor');
        location.reload();
        }, update() {
            if (this.loginStatus === 'manager')
            this.$bvModal.show('UpdateManagerForm');
        }
    }
}
</script>

<!-- Styling the image and other buttons of the navigation bar -->
<style>
.NavBar {
    width: 100%;
    height: 10px;
}

.NavBar label {
    color: goldenrod !important;
}

.icon_band {
    padding: 0px;
    height: 100%;
}

.icon_band img {
    height: 100%;
    padding: 5px;
}

.NavBar .navbar-nav .nav-link {
    display: inline-block !important;
    font-weight: 400 !important;
    color: goldenrod !important;
    border-color: goldenrod !important;
    text-align: center !important;
    vertical-align: middle !important;
    -webkit-user-select: none !important;
    -moz-user-select: none !important;
    -ms-user-select: none !important;
    user-select: none !important;
    background-color: transparent !important;
    border: 1px solid !important;
    padding: 0.375rem 0.75rem !important;
    font-size: 1rem !important;
    line-height: 1.5 !important;
    border-radius: 0.25rem !important;
    transition: color .15s ease-in-out, background-color .15s ease-in-out, border-color .15s ease-in-out, box-shadow .15s ease-in-out !important;
    margin: 7px !important;
}

.NavBar .navbar-nav .nav-link:hover {
    color: #212529 !important;
    background-color: #f8f9fa !important;
}

.NavBar .btn {
    color: goldenrod !important;
    border-color: goldenrod !important;
    margin: 7px !important;
}
</style>