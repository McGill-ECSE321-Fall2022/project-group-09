<template>
    <div id="LoginVisitor">
        <br>
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Visitor Login Page
        </h2>

        <!-- Table that displays the login buttons-->
        <table class="center" width="30%">
            <tr>

                <!-- Username input-->
                <td>
                    <div align="left"><i>Username:</i></div>
                    <b-input id="usernameInput" type="email" v-model="visitorUsername" :state="usernameState"
                        @keydown.space.prevent placeholder=""></b-input>
                    <span v-if="usernameError" style="color: red;">{{ usernameError }}</span>
                    <span v-else> <br> </span>
                </td>
            </tr>
            <tr>
                <!-- Password input-->
                <td>
                    <div align="left"><i>Password:</i></div>
                    <b-input id="emailInput" type="password" v-model="visitorPassword" placeholder=""
                        @keyup.enter="doLoginManager(managerUsername, managerPassword)"></b-input>
                </td>
            </tr>

            <tr>
                <td>
                    <br>
                    <!-- Button is disabled untill there is non whitespace text. Clicking triggers login-->
                    <b-button block variant="success"
                        v-bind:disabled="((!visitorUsername.trim() || !visitorPassword.trim()) || !usernameState)"
                        @click="doLoginVisitor(visitorUsername, visitorPassword)">Login</b-button>

                    <hr>
                    <b-button block variant="primary" @click="$router.push({ name: 'Hello' })">Create an
                        Account</b-button>
                    <hr>
                    <b-button block @click="$router.push({ name: 'LoginManager' })">Login as the manager</b-button>
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
// Import the component that displays the error message
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
    name: 'LoginVisitor',
    // Add the error handler component to the components list
    components: {
        ErrorHandler
    },
    data() {
        return {
            // username and password for visitor
            visitorUsername: '',
            visitorPassword: '',
            // the error message to be displayed by the Error Handler component
            errorMessage: '',
            // errors in input
            usernameError: '',


        }
    },
    created: function () {
        // checking if the user is already logged in 
        const loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        const loggedInEmployee = sessionStorage.getItem('loggedInVisitor');
        const loggedInManager = sessionStorage.getItem('loggedInManager');

        // check to see if an account is already logged in

        if (loggedInVisitor) {
            // redirect to home page for visitors
            this.$router.push('/');
        }
        else if (loggedInEmployee) {
            // redirect to home page for employees
            this.$router.push('/');
        }
        else if (loggedInManager) {
            // redirect to home page for managers
            this.$router.push('/');
        }

    },
    methods: {
        // the visitor login method
        /**
         * This method is called when the user clicks the login button.
         * @author Shidan Javaheri
         * @param {String} username the username of the visitor
         * @param {String} password the password of the visitor
         */
        doLoginVisitor(username, password) {

            AXIOS.get('/login', { params: { username, password } }, {})
                .then((response) => {
                    // empty the feilds
                    this.visitorUsername = '';
                    this.visitorPassword = '';
                    // store the logged in visitor
                    sessionStorage.setItem('loggedInVisitor', JSON.stringify(response.data));
                    // send to homepage
                    this.$router.push('/');
                })
                .catch((error) => {
                    // empty the password
                    this.visitorPassword = '';
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
    computed: {
        usernameState() {
            this.usernameError = '';
            if (this.visitorUsername.includes("@")) {
                return true;
            } else if (this.visitorUsername.trim() === '') {
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


