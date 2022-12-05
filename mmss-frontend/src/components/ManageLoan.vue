<template>
    <div id="ManageLoan">
        <div>
            <!-- ToolBar to manage loans-->
            <b-navbar class="secondaryBar" type="dark" variant="info">
                <b-navbar-brand>Loans</b-navbar-brand>
                <b-navbar-nav>

                    <!-- Navbar dropdowns - any status, approved loans, pending loans-->
                    <b-nav-item-dropdown text="Exchange Status" right>
                        <b-dropdown-item @click="refreshTable()">Any</b-dropdown-item>
                        <b-dropdown-item @click="showApprovedLoans()">Approved</b-dropdown-item>
                        <b-dropdown-item @click="showPendingLoans()">Pending</b-dropdown-item>
                    </b-nav-item-dropdown> <b-nav-form>
                        <!-- Search for loans by username-->
                        <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="username"
                            @keyup.enter="doGetVisitor(username)"></b-form-input>
                        <b-button v-bind:disabled="(!username.trim())" variant="success" class="my-2 my-sm-0"
                            @click="doGetVisitor(username)">Search</b-button>
                    </b-nav-form>
                </b-navbar-nav>

                <!-- Buttons to refresh, clear selections, and select all in table-->
                <b-navbar-nav class="ml-auto">
                    <b-button-group>
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh Table</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="selectAll()"> Select
                        All</b-button>
                    <br>
                    <b-button class="my-2 my-sm-0" @click="clearSelected()"> Clear Selected</b-button>
                    </b-button-group>
                </b-navbar-nav>
            </b-navbar>
        </div>

        <!-- Table that displays the loans-->
        <b-table ref="LoanTable" striped hover sticky-header="200px" :items="loans" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>

        <b-button variant="success" @click="doApproveLoan(selectedLoans)">Approve</b-button>
        <b-button variant="danger" @click="doDeclineLoan(selectedLoans)">Decline</b-button>





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



export default {
    name: 'ManageLoan',
    components: {
        ErrorHandler
    },
    // variables
    data() {
        return {
            // to display in table
            loans: [],
            errorMessage: '',
            // table selection mode
            selectMode: 'multi',
            selectedLoans: [],
            username: '',
            // to update the status of a loan
            request: {
                exchangeId: '',
                exchangeStatus: ''
            }

        }
    },
    // initialize tables
    created: function () {
        const self = this
        AXIOS.get('/loan', {}, {})
            .then(response => {
                // add response to all loans
                self.loans = response.data
            })
            .catch(error => {
                self.errorMessage = error.response.data
            });



    },
    methods: {
        // select rows
        onRowSelected(selectedRows) {
            this.selectedLoans = selectedRows;
        },
        // clear selected rows
        clearSelected() {
            this.selectedLoans = [];
            this.$refs.LoanTable.clearSelected();
        },
        // refresh table
        refreshTable() {
            AXIOS.get('/loan', {}, {})
                .then(response => {
                    // add response to all loans
                    this.loans = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        // select all
        selectAll() {
            this.$refs.LoanTable.selectAllRows();
        },
        // get a loan by a visitor
        doGetVisitor(username) {
            AXIOS.get('/loan/visitor/?username=' + username, {}, {})
                .then(response => {
                    // replace loans with response
                    this.loans = response.data;
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
        // show loans with a particular status
        showLoans(status) {
            AXIOS.get('/loan/status/?status=' + status, {}, {})
                .then(response => {
                    // replace loans with response
                    this.loans = response.data;
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
        },
        // show loans with pending status
        showPendingLoans() {
            this.showLoans("Pending");
        },
        // show loans with approved status
        showApprovedLoans() {
            this.showLoans("Approved");
        },
        // update the status of all selected loans
        async doUpdateLoan(selectedLoans, status) {
            for (var i = 0; i < selectedLoans.length; i++) {
                this.request.exchangeId = selectedLoans[i].exchangeId;
                this.request.exchangeStatus = status;
                // send request to backend up update loan
                 await AXIOS.put('/loan/', this.request, {})
                    .then(response => {
                        //refresh the table on the last request

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
            this.refreshTable();

        },
        // approving a loan
        async doApproveLoan() {
            await this.doUpdateLoan(this.selectedLoans, "Approved");
            this.clearSelected();
            this.refreshTable();
        },
        // declining a loan
        async doDeclineLoan() {
            await this.doUpdateLoan(this.selectedLoans, "Declined");
            this.clearSelected();
            this.refreshTable();
        }
    },
}



</script>

<style>

</style>