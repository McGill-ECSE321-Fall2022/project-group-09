<template>
    <div id="CreateArtefactForm">
        <b-form @submit="onSubmit" @reset="onReset" v-if="show">

            <b-form-group id="input-group-1" label="First Name:" label-for="input-1">
                <b-form-input id="input-1" v-model="request.firstName" type="text" :state="firstNameState" required>
                </b-form-input>
                <span v-if="firstNameError" style="color: red;">{{ firstNameError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <b-form-group id="input-group-2" label="Last Name:" label-for="input-2">
                <b-form-input id="input-2" v-model="request.lastName" type="text" required :state="lastNameState">
                </b-form-input>
                <span v-if="lastNameError" style="color: red;">{{ lastNameError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <b-form-group id="input-group-3" label="Username" label-for="input-3">
                <b-form-input id="input-3" v-model="request.username" type="text" required
                    :state="usernameState"></b-form-input>
                <span v-if="usernameError" style="color: red;">{{ usernameError }}</span>
                <span v-else> <br> </span>
            </b-form-group>


            <b-form-group id="input-group-4" label="Password:" label-for="input-4">
                <b-form-input id="input-4" v-model="request.password" type="text" required
                    :state="passwordState"></b-form-input>
                <span v-if="passwordError" style="color: red;">{{ passwordError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <b-form-group id="input-group-5" label="Phone Number:" label-for="input-5">
                <b-form-input id="input-4" v-model="request.phoneNumber" required
                    :state="phoneNumberState"></b-form-input>
                <span v-if="phoneNumberError" style="color: red;">{{ phoneNumberError }}</span>
                <span v-else> <br> </span>
            </b-form-group>

            <b-button type="submit" variant="primary" v-bind:disabled="!submitState">Create New
                Employee </b-button>
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
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
    components: {
        ErrorHandler,
    },
    data() {
        return {
            request: {
                username: '',
                password: '',
                firstName: '',
                lastName: '',
                phoneNumber: '',
            },
            errorMessage: '',
            show: true,
            usernameError: '',
            passwordError: '',
            firstNameError: '',
            lastNameError: '',
            phoneNumberError: ''

        }
    },
    created: function () {
        const self = this

    },
    methods: {
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.post('/employee', self.request, {})
                .then((response) => {
                    // Empty the form
                    self.resetVariables()
                    //onReset
                    // send to some page eventually
                    //this.$router.push('/');
                })
                .catch((error) => {
                    if (error.response.status >= 450) {
                        self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                    } else {
                        self.errorMessage = error.response.data;
                    }
                    // call the error handler component modal (named errorPopUp) to display the error message
                    self.$bvModal.show('errorPopUp');
                });
        },
        onReset(event) {
            event.preventDefault()
            // Reset our form values
            self.resetVariables()
            // Trick to reset/clear native browser form validation state
            self.show = false
            self.$nextTick(() => {
                self.show = true
            })
        },
        resetVariables() {
            const self = this
            self.request.username = ''
            self.request.password = ''
            self.request.firstName = ''
            self.request.lastName = ''
            self.request.phoneNumber = ''
            self.errorMessage = ''
            self.usernameError = ''
            self.passwordError = ''
            self.firstNameError = ''
            self.lastNameError = ''
            self.phoneNumberError = ''


        },
    },
    computed: {
        usernameState() {
            this.usernameError = '';
            if (this.request.username.trim() === '') {
                return false;
            }
            else if (!this.request.username.includes('@')) {
                this.usernameError = 'Please enter a valid email address';
                return false;
            } else {
                this.usernameError = "";
                return true;
            };
        },
        passwordState() {
            const upper = /[A-Z]/;
            const number = /[0-9]/;
            if (this.request.password.trim() === '') {
                return false;
            } else if (!upper.test(this.request.password)) {
                this.passwordError = "The password must contain at least one uppercase letter";
                return false;

            } else if (!number.test(this.request.password)) {
                this.passwordError = "The password must contain at least one number";
                return false;
            } else if (this.request.password.length < 8) {
                this.passwordError = "The password must be at least 8 characters long";
                return false;
            } else {
                this.passwordError = "";
                return true;
            }
        },
        phoneNumberState() {
            // a regex for a phone number of the form xxx-xxx-xxxx (help from copilot)
            const validRegex = /^\d{3}-\d{3}-\d{4}$/;
            this.phoneNumberError = '';
            if (this.request.phoneNumber.trim() === '') {
                return false;
            } else if (!validRegex.test(this.request.phoneNumber)) {
                this.phoneNumberError = 'Please enter a phone number in the form xxx-xxx-xxxx';
                return false;
            } else {
                this.phoneNumberError = "";
                return true;
            }
        },

        firstNameState() {
            this.firstNameError = '';
            return this.request.firstName.length > 0
        },
        lastNameState() {
            this.lastNameError = '';
            return this.request.lastName.length > 0

        },
        submitState() {
            return this.usernameState && this.passwordState && this.phoneNumberState && this.firstNameState && this.lastNameState
        }

    }

}
</script>