<template>
    <!-- A form will popup to update manager-->
    <div id="UpdateManagerForm">
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

            <!-- Input for the input for the password, with the error underneath-->
            <b-form-group id="input-group-1" label="Old Password:" label-for="input-1">
                <b-form-input id="input-1" v-model="request.passWord" type="password" required
                    :state="passwordState"></b-form-input>
                <span v-if="passwordError" style="color: red;">{{ passwordError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <!-- Input for the new Password, with the error underneath-->
            <b-form-group id="input-group-2" label="New Password:" label-for="input-2">
                <b-form-input id="input-2" v-model="request.newPassword" type="password" required :state="newPasswordState">
                </b-form-input>
                <span v-if="newPasswordError" style="color: red;">{{ newPasswordError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <!-- Buttons to submit or reset the form-->
            <b-button type="submit" variant="primary" v-bind:disabled="!submitState">Update
                Manager </b-button>
            <b-button type="reset" variant="danger" @click="resetVariables">Reset</b-button>
        </b-form>

        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />
    </div>
</template>
  
<script>
import axios from 'axios'
// Import the component that displays the error message
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component

// setup axios
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
// script
export default {
    name : 'UpdateManager',
    // declare the components
    components: {
        ErrorHandler,
    },
    // the data 
    data() {
        return {
            // request to update a manager
            request: {
                userName: '',
                passWord: '',
                newPassword: '',
            },
            // other data
            errorMessage: '',
            show: true,
            usernameError: '',
            passwordError: '',
            newPasswordError: '',
        }
    },
    // no need to do anything on creation
    created: function () {
        this.request.userName = JSON.parse(sessionStorage.getItem('loggedInManager')).userName
    },

    // METHODS 
    methods: {
        // when the form is submitted 
        onSubmit(event) {
            event.preventDefault()
            const self = this
            // send a put request
            //console.log(self.request)
            AXIOS.put('/manager', {}, { params: { oldPassword: self.request.passWord, newPassword: self.request.newPassword} })
                .then((response) => {
                    // Empty the form
                    self.resetVariables()

                })
                .catch((error) => {
                    // catch error and display it in a popup                    
                    if (error.response.status >= 450) {
                        self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                    } else {
                        self.errorMessage = error.response.data;
                    }
                    // call the error handler component modal (named errorPopUp) to display the error message
                    self.$bvModal.show('errorPopUp');
                });
        },
        // when the form is reset
        onReset(event) {
            event.preventDefault()
            const self = this
            // Reset our form values
            self.resetVariables()
            // Trick to reset/clear native browser form validation state
            self.show = false
            self.$nextTick(() => {
                self.show = true
            })
        },
        // reset all the variables
        resetVariables() {
            const self = this
            self.request.userName = ''
            self.request.passWord = ''
            self.request.newPassword = ''
            self.errorMessage = ''
            self.usernameError = ''
            self.passwordError = ''
            self.newPasswordError = ''
        },
    },
    // COMPUTED PROPERTIES for states and errors
    computed: {
        // check if the username is valid
        usernameState() {
            this.usernameError = '';
            if (this.request.userName.trim() === '') {
                return false;
            }
            else if (!this.request.userName.includes('@')) {
                this.usernameError = 'Please enter a valid email address';
                return false;
            } else {
                this.usernameError = "";
                return true;
            };
        },
        // check if the password is valid
        passwordState() {
            const upper = /[A-Z]/;
            const number = /[0-9]/;
            if (this.request.passWord.trim() === '') {
                return false;
            } else if (!upper.test(this.request.passWord)) {
                this.passwordError = "The password must contain at least one uppercase letter";
                return false;

            } else if (!number.test(this.request.passWord)) {
                this.passwordError = "The password must contain at least one number";
                return false;
            } else if (this.request.passWord.length < 8) {
                this.passwordError = "The password must be at least 8 characters long";
                return false;
            } else {
                this.passwordError = "";
                return true;
            }
        },
        // check if the new password is valid
        newPasswordState() {
            const upper = /[A-Z]/;
            const number = /[0-9]/;
            if (this.request.newPassword.trim() === '') {
                return false;
            } else if (!upper.test(this.request.newPassword)) {
                this.newPasswordError = "The new password must contain at least one uppercase letter";
                return false;

            } else if (!number.test(this.request.newPassword)) {
                this.newPasswordError = "The new password must contain at least one number";
                return false;
            } else if (this.request.newPassword.length < 8) {
                this.newPasswordError = "The new password must be at least 8 characters long";
                return false;
            } else {
                this.newPasswordError = "";
                return true;
            }
        },
        // check if the form is valid for submission
        submitState() {
            return this.usernameState && this.passwordState && this.newPasswordState 
        }
    }
}
</script>