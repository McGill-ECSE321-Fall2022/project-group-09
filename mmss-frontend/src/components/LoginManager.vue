<template>
    <div id="LoginManager">
        <br>
        <!-- Welcome messages - can be changed-->
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Manager Login Page
        </h2>

        <!-- Table that contains login buttons-->
        <table class="center" width="30%">
            <tr>
                <td>
                    <div align="left"><i>Username:</i></div>
                    <b-input id="usernameInput" type="email" v-model="managerUsername" @keydown.space.prevent
                        :state="usernameState" placeholder=""></b-input>
                    <span v-if="usernameError" style="color: red;">{{ usernameError }}</span>
                    <span v-else> <br> </span>
                </td>
            </tr>
            <tr>
                <td>
                    <div align="left"><i>Password:</i></div>
                    <b-input id="passwordInput" type="password" v-model="managerPassword" placeholder="" @keyup.enter="doLoginManager(managerUsername, managerPassword)"></b-input>
                </td>
            </tr>
            <tr>
                <td>
                    <br>
                    <!-- Button is disabled untill there is non whitespace text. Clicking triggers login-->
                    <b-button block variant="success"
                        v-bind:disabled="!managerUsername.trim() || !managerPassword.trim()"
                        @click="doLoginManager(managerUsername, managerPassword)">Login</b-button>
                    <hr>
                    <b-button block variant="primary" @click="$router.push({ name: 'Hello' })">Create an
                        Account</b-button>
                    <hr>
                    <b-button block @click="$router.push({ name: 'LoginVisitor' })">Login as a visitor</b-button>
                    <hr>
                    <b-button block @click="$router.push({ name: 'LoginEmployee' })">Login as an employee</b-button>
                </td>
            </tr>

        </table>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />
    </div>
</template> 

<script>
import axios from 'axios'
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: 'LoginManager',
    // Add the error handler component to the components list
    components: {
        ErrorHandler
    },
    data() {
        return {
            managerUsername: '',
            managerPassword: '',
            errorMessage: '',
            usernameError: ''
        }
    },
    created: function () {
        // checking if the user is already logged in 
        const loggedInEmployee = sessionStorage.getItem('loggedInManager');
        const loggedInVisitor = sessionStorage.getItem('loggedInVisitor');

        const loggedInManager = sessionStorage.getItem('loggedInManager');

        // check to see if an account is already logged in

        if (loggedInVisitor) {
            // redirect to page for visitors
            this.$router.push('/');
        }
        else if (loggedInEmployee) {
            // redirect to page for employees
            this.$router.push('/');
        }
        else if (loggedInManager) {
            // redirect to page for managers
            this.$router.push('/');
        }

    },
    methods: {
        // the manager login method
        /**
         * This method is called when the login button is clicked. It sends a request to the backend to login the manager.
         * @param {String} username The username of the manager
         * @param {String} password The password of the manager
         * @author Shidan Javaheri
         */
        doLoginManager(username, password) {

            // empty feilds
            this.managerUsername = '';
            this.managerPassword = '';
            AXIOS.get('/login/manager/', { params: { username, password } }, {})
                .then((response) => {
                    sessionStorage.setItem('loggedInManager', JSON.stringify(response.data));
                    // send to home page
                    this.$router.push('/');
                    location.reload();
                })
                .catch((error) => {
                    // empty the password
                    this.managerPassword = '';
                    // logic on the error status. Display backend error message if status is below 450
                    // otherwise display something went wrong
                    if (error.response.status >= 450) {
                        this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                    } else {
                        this.errorMessage = error.response.data;
                    }
                    // call the error handler component modal (named errorPopUp) to display the error message
                    this.$bvModal.show('errorPopUp');
                });
        }
    },
    // computed functions
    computed: {
        // make sure username is valid
        usernameState() {
            this.usernameError = '';
            if (this.managerUsername.includes("@")) {
                return true;
            } else if (this.managerUsername.trim() === '') {
                return false;
            } else {
                this.usernameError = "Please enter a valid email address";
                return false;
            }
        },

    }
}

</script> 

<style>
.center {
    margin-left: auto;
    margin-right: auto;
}
</style>


