<template>
    <div id="ManageBooking">
        <h1> Bookings </h1>

        <h2>Tours</h2>
        <b-navbar type="dark" variant="info">
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

        <!-- Table that displays the bookings-->
        <b-table ref="TourBookingTable" striped hover sticky-header="200px" :items="tours" selectable
            :select-mode="selectMode" @row-selected="onRowSelected"></b-table>

        <b-button variant="success" @click="updateTours(selectedTours)"> Update Booking </b-button>
        <b-button variant="danger" @click="deleteBooking(selectedBookings)"> Delete Booking </b-button>

    </div>
</template>

<script>
import ErrorHandler from './ErrorPopUp.vue';

import axios from 'axios'
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
            tours: [],
            tickets: [],
            errorMessage: '',

            // table selection mode
            selectMode: 'multi',
            selectedBookings: [],
            visitorUsername: '',

            // to update the status of a booking
            request: {
                bookingId: '',
                date: '',
                numberOfParticipants: ''
            }
        }
    },
    created: function () {
        const self = this
        AXIOS.get('/tour', {}, {})
            .then(response => {
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
            this.$refs.BookingTable.clearSelected();
        },
        // refresh table
        refreshTable() {
            AXIOS.get('/tour', {}, {})
                .then(response => {
                    // add response to all tours
                    this.tours = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        // select all
        selectAll() {
            this.$refs.BookingTable.selectAllRows();
        },
        // get a tour by a visitor
        doGetTour(username) {
            AXIOS.get('/tour/visitor/?username=' + username, {}, {})
                .then(response => {
                    // replace loans with response
                    this.tours = response.data;
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
        // update the status of all selected tours
        async doUpdateTour(selectedTours, date, numberOfParticipants) {
            for (var i = 0; i < selectedTours.length; i++) {
                this.request.date = selectedTours[i].date;
                this.request.numberOfParticipants = numberOfParticipants;
                // send request to backend up update loan
                AXIOS.put('/tour', this.request, {})
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
        // updating a booking
        async updateTours() {
            await this.doUpdateTour(this.selectedTours, "Updated");
            this.clearSelected();
            this.refreshTable();
        },
        // deleting a booking
        async deleteBooking() {
            await this.doDeleteBooking(this.selectedBookings, "Deleted");
            this.clearSelected();
            this.refreshTable();
        }
    },
}
</script>