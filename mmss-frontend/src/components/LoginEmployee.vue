<template>
    <div id="LoginEmployee">
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Employee Login Page
        </h2>

        <table class="center" width="30%">
            <tr>
                <td>
                    Username:
                </td>
                <td>
                    <b-input type="email" :state="usernameState" v-model="employeeUsername" @keydown.space.prevent placeholder="Username"></b-input>
                </td>
            </tr>
            <tr>
                <td>
                    Password:
                </td>
                <td>
                    <b-input type="password" :state="passwordState" v-model="employeePassword" placeholder="Password"></b-input>
                </td>
            </tr>
        </table>
        <table class="center">
            <tr>
                <td>
                    <br>
                    <!-- Button is disabled untill there is non whitespace text. Clicking triggers login-->
                    <b-button variant="success" v-bind:disabled="!employeeUsername.trim() || !employeePassword.trim()"
                        @click="doLoginEmployee(employeeUsername, employeePassword)">Login</b-button>
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
    name: 'LoginEmployee',
    // Add the error handler component to the components list
    components: {
        ErrorHandler
    },
    data() {
        return {
            employeeUsername: '',
            employeePassword: '',
            errorMessage: ''
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
        // the employee login method
        doLoginEmployee(username, password) {

            // empty feilds
            this.employeeUsername = '';
            this.employeePassword = '';
            AXIOS.get('/login/employee', { params: { username, password } }, {})
                .then((response) => {
                    sessionStorage.setItem('loggedInEmployee', JSON.stringify(response.data));
                    // send to home page
                    this.$router.push('/');
                })
                .catch((error) => {
                    // empty the password
                    this.employeePassword = '';
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
    // functions that display input state ( simple checks )
    computed: {
        usernameState() {
            return this.employeeUsername.includes("@");
        },
        passwordState() {
            const hasNumber = /\d/;
            const upper = /[A-Z]/;
         
            return hasNumber.test(this.employeePassword) && upper.test(this.employeePassword) && this.employeePassword.length >= 8;
        }
    }
}

</script> 

<style>
.center {
    margin-left: auto;
    margin-right: auto;
}
</style>



