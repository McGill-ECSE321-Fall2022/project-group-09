<template>
    <div id="DonationVisitor">
        <br>
        <h1>
            Donations
        </h1>
        <div>
            <b-navbar class="secondaryBar" type="dark" variant="info">
                <b-navbar-brand>Your Pending Donations</b-navbar-brand>
                
                <b-navbar-nav class="ml-auto">
                    <b-button class="my-2 my-sm-0" @click="refreshTable()">Refresh Table</b-button>
                    <b-button variant="success" class="my-2 my-sm-0" type="submit" @click="$bvModal.show('CreateDonationForm')">Create</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        
        <b-table ref="DonationTable" striped hover sticky-header="600px" :items="donations"></b-table>

        <b-modal 
            id='CreateDonationForm'
            title="Create a Donation"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <create-form @submitted="refreshTable()"/>
        </b-modal> 

    </div>

</template>

<script>
import ErrorHandler from './ErrorPopUp.vue'
import axios from 'axios'
import CreateDonationForm from './CreateDonationForm.vue'

var config = require('../../config')
var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort
var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    name: "DonationVisitor",
    components: {
        "create-form": CreateDonationForm,
        ErrorHandler
    },
    data() {
    return {
        donations: [],
        errorMessage: '',
        loggedInVisitor: ''
        
    }},
    created: function () {
        // Get session info
        this.loggedInVisitor = JSON.parse(sessionStorage.getItem("loggedInVisitor")).userName;
        AXIOS.get('/donation/visitor/?username=' + this.loggedInVisitor, {}, {})
            .then(response => {
                // add response to all donations
                this.donations = response.data
            })
        .catch(error => {
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
            if (error.response.status >= 450) {
                self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                self.errorMessage = error.response.data;
            }
            // call the error handler component modal (named errorPopUp) to display the error message
            self.$bvModal.show('errorPopUp');
        })
    },
    methods: {
        refreshTable() {
            this.loggedInVisitor = JSON.parse(sessionStorage.getItem("loggedInVisitor")).userName;
            AXIOS.get('/donation/visitor/?username=' + this.loggedInVisitor, {}, {})
            .then(response => {
                // add response to all donations
                this.donations = response.data
            })
            .catch(error => {
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
    }
}
</script>