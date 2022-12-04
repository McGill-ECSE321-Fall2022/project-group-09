<template>
    <div id="ManageDonation">
        <br>
        <h1>
            Donations
        </h1>
        <div>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav>
                    <b-nav-form>
                        <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="username"
                        @keyup.enter="doGetVisitor(username)"></b-form-input>
                        <b-button v-bind:disabled="(!username.trim())" variant="success" class="my-2 my-sm-0"
                            @click="doGetVisitor(username)" >Search</b-button>
                    </b-nav-form>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh Table</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="clearSelected()"> Clear Selected</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        
        <b-table ref="DonationTable" striped hover sticky-header="200px" :items="donations" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>

        <b-form-checkbox id="checkbox-1" v-model="request.canLoan" name="checkbox-1" value='true'>
        Can Loan?
        </b-form-checkbox>

        <b-form-group 
            id="input-group-4" 
            label="Insurance Fee:" 
            label-for="input-2">
            <b-form-input
                id="input-2"
                v-model="request.insuranceFee"
                type="number"
                placeholder="Enter artefact's insurance fee"
                required
            ></b-form-input>
        </b-form-group> 

        <b-form-group 
            id="input-group-5" 
            label="Loan Fee:" 
            label-for="input-3">
            <b-form-input
                id="input-3"
                v-model="request.loanFee"
                type="number"
                placeholder="Enter artefact's loan fee"
                required
            ></b-form-input>
        </b-form-group>

        <b-button variant="success" @click="doApproveDonation(selectedDonations)">Approve</b-button>
        <b-button variant="danger" @click="doDeclineDonation(selectedDonations)">Decline</b-button>

        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />
        <b-card class="mt-3" header="Form Data Result">
        <pre class="m-0">{{ request }}</pre>
      </b-card>
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
    name: 'ManageDonation',
    components: {
        ErrorHandler
    },
    data() {
        return {
            donations: [],
            errorMessage: '',
            selectMode: 'multi',
            selectedDonations: [],
            username: '',
            request: {
                canLoan: false,
                insuranceFee: '0.00',
                loanFee: '0.00',
            }
        }
    },
    created: function () {
        const self = this
        AXIOS.get('/donation', {}, {})
            .then(response => {
                // add response to all donations
                self.donations = response.data
            })
            .catch(error => {
                self.errorMessage = error.response.data
            });
    },
    methods: {
        onRowSelected(selectedRows) {
            this.selectedDonations = selectedRows;
        },
        clearSelected() {
            this.selectedDonations = [];
            this.$refs.DonationTable.clearSelected();
        },
        refreshTable() {
            AXIOS.get('/donation', {}, {})
                .then(response => {
                    // add response to all donations
                    console.log(response.data)
                    this.donations = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        doGetVisitor(username) {
            AXIOS.get('/donation/visitor/?username=' + username, {}, {})
                .then(response => {
                    // replace donations with response
                    this.donations = response.data;
                    //clear the input
                    this.username = '';
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
        },
        async doApproveDonation() {
            for (let i = 0; i < this.selectedDonations.length; i++) {
                let exchangeId = this.selectedDonations[i].exchangeId;
                AXIOS.put('/donation/' + exchangeId, this.request, {})
                    .then(response => {
                        //refresh the table on the last request
                        this.refreshTable();
                    })
                    .catch(error => {
                        if (error.response.status >= 450) {
                            this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                        } else {
                            this.errorMessage = error.response.data;
                        }
                        // call the error handler component modal (named errorPopUp) to display the error message
                        this.$bvModal.show('errorPopUp');
                    });
            }
            this.clearSelected();
            this.refreshTable();
        },
        async doDeclineDonation() {
            for (let i = 0; i < this.selectedDonations.length; i++) {
                let exchangeId = this.selectedDonations[i].exchangeId;
                AXIOS.put('/donation/?id=' + exchangeId, {}, {})
                    .then(response => {
                        //refresh the table on the last request
                        this.refreshTable();
                    })
                    .catch(error => {
                        if (error.response.status >= 450) {
                            this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                        } else {
                            this.errorMessage = error.response.data;
                        }
                        // call the error handler component modal (named errorPopUp) to display the error message
                        this.$bvModal.show('errorPopUp');
                    });
            }
            this.clearSelected();
            this.refreshTable();
        }
    },
}
</script>

<style>
</style>