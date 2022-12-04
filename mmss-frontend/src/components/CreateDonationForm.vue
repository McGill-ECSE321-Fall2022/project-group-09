<template>
    <div id="CreateDonationForm">
      <b-form @submit="onSubmit" @reset="onReset" v-if="show">
        <!-- Name -->
        <b-form-group 
            id="input-group-1" 
            label="Item Name:" 
            label-for="input-1">
            <b-form-input 
                id="input-1" 
                v-model="request.itemName" 
                type="text" 
                :state="request.artefactName.length <= 50"
                placeholder="Enter item's name (maximum 50 characters)" 
                required>
            </b-form-input>
        </b-form-group>
        <!-- Description -->
        <b-form-group
            id="input-group-2" 
            label="Description:" 
            label-for="textarea-1">
            <b-form-textarea
                id="textarea-1"
                v-model="request.description"
                :state="request.description.length <= 300"
                placeholder="Enter items's description (maximum 300 characters)"
                rows="3"
                required>
            </b-form-textarea>
        </b-form-group>
  
        <b-button type="submit" variant="primary">Create</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-form>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />    
    </div>
  </template>
  
<script>
import axios from 'axios'

// Import the component that displays the error message
import ErrorHandler from './ErrorPopUp.vue' // This is the error component
var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    components: {
        ErrorHandler
    },
    data() {
        return {
            request: {
                itemName: '',
                description: '',
                visitorUsername: ''
            },
            errorMessage: '',
            show: true
        }
      },
    created: function () {
        this.visitorUserName = this.loggedInVisitor = JSON.parse(sessionStorage.getItem("loggedInVisitor")).userName;
    },
    methods: {
        // When the Create button is clicked
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.post('/donation', self.request, {})
            .then((response) => {
                // Show response
                alert('The donation was successfully created.')
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
        // When the Reset button is clicked
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
        // Reset the request fields with default values
        resetVariables() {
            const self = this
            self.request.itemName = ''
            self.request.description = ''
        }
      }
    }
  </script>