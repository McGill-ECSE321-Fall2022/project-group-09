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

        <b-form-checkbox id="checkbox-1" v-model="status" name="checkbox-1" value="True" unchecked-value="False">
        Can Loan?
        </b-form-checkbox>

        <div>State: <strong>{{ status }}</strong></div>
        <b-button variant="success" @click="doApproveDonation(selectedDonations)">Approve</b-button>
        <b-button variant="danger" @click="doDeclineDonation(selectedDonations)">Decline</b-button>

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
                exchangeId: '',
                exchangeStatus: ''
            }
        }
    },
    created: function () {
        const self = this
        AXIOS.get('/donations', {}, {})
            .then(response => {
                // add response to all loans
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
                    // add response to all loans
                    this.loans = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        doGetVisitor(username) {
            AXIOS.get('/donation/visitor/?username=' + username, {}, {})
                .then(response => {
                    // replace loans with response
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
        async doUpdateLoan(selectedLoans, status) {
            for (var i = 0; i < selectedLoans.length; i++) {
                this.request.exchangeId = selectedLoans[i].exchangeId;
                this.request.exchangeStatus = status;
                AXIOS.put('/donation/', this.request, {})
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

        },
        async doApproveLoan() {
            await this.doUpdateLoan(this.selectedDonations, "Approved");
            this.clearSelected();
            this.refreshTable();
        },
        async doDeclineLoan() {
           await this.doUpdateLoan(this.selectedDonations, "Declined");
            this.clearSelected();
            this.refreshTable();
        }
    },
}



</script>

<style>

</style>