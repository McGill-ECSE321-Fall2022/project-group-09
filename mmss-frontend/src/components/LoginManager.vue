<template>
    <div id="LoginManager">
        <h1>
            Welcome to Marwan's Museum!
        </h1>
        <h2>
            Manager Login Page
        </h2>

        <table class="center">
            <tr>
                <td>
                    Username:
                </td>
                <td>
                    <input type="text" v-model="managerUsername" @keydown.space.prevent placeholder="Username">
                </td>
            </tr>
            <tr>
                <td>
                    Password:
                </td>
                <td>
                    <input type="password" v-model="managerPassword" placeholder="Password">
                </td>

                <td>
                    <!-- Button is disabled untill there is non whitespace text. Clicking triggers login-->
                    <b-button v-bind:disabled="!managerUsername.trim() || !managerPassword.trim()"
                        @click="doLoginManager(managerUsername, managerPassword)">Login</b-button>
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
            errorMessage: ''
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
        doLoginManager(username, password) {
            const self = this;
            // empty feilds
            self.managerUsername = '';
            self.managerPassword = '';
            AXIOS.get('/login/manager/', { params: { username, password } }, {})
                .then((response) => {
                    sessionStorage.setItem('loggedInManager', JSON.stringify(response.data));
                    // send to home page
                    self.$router.push('/');
                })
                .catch((error) => {
                    // empty the password
                    self.managerPassword = '';
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


