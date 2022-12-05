<template>
    <div id="ManageBooking">
        <h1> Bookings </h1>

        <b-card bg-variant="light">
            <h2>Tickets</h2>
            <b-navbar type="dark" variant="info">
                <!-- Buttons to refresh, clear selections, and select all in table-->
                <b-navbar-nav class="ml-auto">
                    <b-button-group>
                        <b-button class="my-2 my-sm-0" @click="refreshTicketTable()"> Refresh Table</b-button>
                        <br>
                        <b-button variant="primary" class="my-2 my-sm-0" @click="selectAllTickets()"> Select
                            All</b-button>
                        <br>
                        <b-button class="my-2 my-sm-0" @click="clearSelectedTickets()"> Clear Selected</b-button>
                    </b-button-group>
                </b-navbar-nav>
            </b-navbar>


            <!-- Table that displays the ticket bookings-->
            <b-table ref="TicketBookingTable" striped hover sticky-header="200px" :items="tickets" selectable
                :select-mode="selectMode" @row-selected="onTicketRowSelected"></b-table>

            <b-button variant="danger" @click="deleteTickets(selectedTickets)"> Delete Booking </b-button>
        </b-card>

        <b-card bg-variant="light">
            <h2>Tours</h2>
            <b-navbar type="dark" variant="info">
                <!-- Buttons to refresh, clear selections, and select all in table-->
                <b-navbar-nav class="ml-auto">
                    <b-button-group>
                        <b-button class="my-2 my-sm-0" @click="refreshTourTable()"> Refresh Table</b-button>
                        <br>
                        <b-button variant="primary" class="my-2 my-sm-0" @click="selectAllTours()"> Select
                            All</b-button>
                        <br>
                        <b-button class="my-2 my-sm-0" @click="clearSelectedTours()"> Clear Selected</b-button>
                    </b-button-group>
                </b-navbar-nav>
            </b-navbar>

            <!-- Table that displays the tour bookings-->
            <b-table ref="TourBookingTable" striped hover sticky-header="200px" :items="tours" selectable
                :select-mode="selectMode" @row-selected="onTourRowSelected"></b-table>

            <b-button variant="danger" @click="deleteTours(selectedTours)"> Delete Booking </b-button>
        </b-card>
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
            selectedTickets: [],
            selectedTours: [],
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
        AXIOS.get('/ticket', {}, {})
            .then(response => {
                self.tickets = response.data
            })
            .catch(error => {
                self.errorMessage = error.response.data
            });

        AXIOS.get('/tour', {}, {})
            .then(response => {
                self.tours = response.data
            })
            .catch(error => {
                self.errorMessage = error.response.data
            });
    },
    methods: {
        // select rows
        onTicketRowSelected(selectedRows) {
            this.selectedTickets = selectedRows;
        },
        onTourRowSelected(selectedRows) {
            this.selectedTours = selectedRows;
        },
        // clear selected rows
        clearSelectedTickets() {
            this.selectedTickets = [];
            this.$refs.TicketBookingTable.clearSelected();
        },
        // clear selected rows
        clearSelectedTours() {
            this.selectedTours = [];
            this.$refs.TourBookingTable.clearSelected();
        },
        // refresh table
        refreshTicketTable() {
            AXIOS.get('/ticket', {}, {})
                .then(response => {
                    // add response to all tours
                    this.tickets = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        refreshTourTable() {
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
        selectAllTickets() {
            this.$refs.TicketBookingTable.selectAllRows();
        },
        selectAllTours() {
            this.$refs.TourBookingTable.selectAllRows();
        },
        // update all selected tours
        async doUpdateTour(selectedTours, date, numberOfParticipants) {
            for (var i = 0; i < selectedTours.length; i++) {
                this.request.date = selectedTours[i].date;
                this.request.numberOfParticipants = numberOfParticipants;
                // send request to backend up update tour
                AXIOS.put('/tour', this.request, {})
                    .then(response => {
                        //refresh the table on the last request
                        this.refreshTourTable();
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
        // update all selected tickets
        async doUpdateTicket(selectedTickets, date) {
            for (var i = 0; i < selectedTickets.length; i++) {
                this.request.date = selectedTickets[i].date;
                // send request to backend up update ticket
                AXIOS.put('/ticket', this.request, {})
                    .then(response => {
                        //refresh the table on the last request
                        this.refreshTicketTable();
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
        // delete all selected tours
        async doDeleteTour(selectedTours) {
            for (var i = 0; i < selectedTours.length; i++) {
                AXIOS.delete('/tour/' + selectedTours[i].bookingId, {}, {})
                    .then(response => {
                        this.refreshTourTable(); //refresh the table on the last request
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
        // delete all selected tickets
        async doDeleteTicket(selectedTickets) {
            for (var i = 0; i < selectedTickets.length; i++) {
                AXIOS.delete('/ticket/' + selectedTickets[i].bookingId, {}, {})
                    .then(response => {
                        this.refreshTicketTable(); //refresh the table on the last request
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
        // updating a tour booking
        async updateTours() {
            await this.doUpdateTour(this.selectedTours);
            this.clearSelectedTours();
            this.refreshTourTable();
        },
        // updating a ticket booking
        async updateTickets() {
            await this.doUpdateTicket(this.selectedTickets);
            this.clearSelectedTickets();
            this.refreshTicketTable();
        },
        // deleting a tour booking
        async deleteTours() {
            await this.doDeleteTour(this.selectedTours);
            this.clearSelectedTours();
            this.refreshTourTable();
        },
        // deleting a ticket booking
        async deleteTickets() {
            await this.doDeleteTicket(this.selectedTickets);
            this.clearSelectedTickets();
            this.refreshTicketTable();
        }
    },
}
</script>