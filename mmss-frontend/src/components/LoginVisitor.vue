<template>
    <div id="LoginVisitor">
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Visitor Login Page
        </h2>

        <table class="center">
            <tr>
                <td>
                    Username:
                </td>
                <td>
                    <input type="text" v-model="visitorUsername" @keydown.space.prevent placeholder="Username">
                </td>
            </tr>
            <tr>
                <td>
                    Password:
                </td>
                <td>
                    <input type="password" v-model="visitorPassword" placeholder="Password">
                </td>

                <td>
                    <!-- Button is disabled untill there is non whitespace text. Clicking triggers login-->
                    <b-button v-bind:disabled="!visitorUsername.trim() || !visitorPassword.trim()"
                        @click="doLoginVisitor(visitorUsername, visitorPassword)">Login</b-button>
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
            errorMessage: ''

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
            const self = this;
            AXIOS.get('/login', { params: { username, password } }, {})
                .then((response) => {
                    // empty the feilds
                    self.visitorUsername = '';
                    self.visitorPassword = '';
                    // store the logged in visitor
                    sessionStorage.setItem('loggedInVisitor', JSON.stringify(response.data));
                    // send to homepage
                    self.$router.push('/');
                })
                .catch((error) => {
                    // empty the password
                    self.visitorPassword = '';
                    // logic on the error status. Display backend error message if status is below 450
                    // otherwise display something went wrong
                    if (error.response.status >= 450) {
                        self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                    } else {
                        self.errorMessage = error.response.data;
                    }
                    // call the error handler component modal (named errorPopUp) to display the error message
                    self.$bvModal.show('errorPopUp');
                });

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


