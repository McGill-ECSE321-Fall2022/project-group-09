<template>
    <div id="CreateTicket">
        <b-card bg-variant="light">
            <b-form id="createTicketForm" @submit="onSubmit" @reset="onReset" v-if="show">

                <b-form-group id="booking-datepicker">
                    <label for="booking-datepicker">Choose a Ticket Date</label>
                    <b-form-datepicker id="booking-datepicker" v-model="request.date" class="mb-2"
                        required></b-form-datepicker>
                    <p>Date: <strong>{{ request.date }}</strong></p>
                </b-form-group>

                <b-form-group id="booking-submit">
                    <b-button id="booking-submit" type="submit" class="mt-4" variant="primary">Book
                        Ticket</b-button>
                </b-form-group>

                <b-form-group id="booking-reset">
                    <b-button id="booking-reset" type="reset" variant="danger">Reset</b-button>
                </b-form-group>

            </b-form>
        </b-card>

        <ErrorHandler :message="errorMessage" />
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
            request: {
                visitorUsername: '',
                date: '',
            },
            errorMessage: '',
            show: true
        }
    },
    created: function () {
    },
    methods: {
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.post('/ticket', self.request, {}).then((response) => {
                self.resetVariables() // Empty the form
            })
                .catch((error) => {
                    if (error.response.status >= 450) {
                        self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                    } else {
                        self.errorMessage = error.response.data;
                    }
                    self.$bvModal.show('errorPopUp'); // call the error handler component modal (named errorPopUp) to display the error message
                });
        },

        onReset(event) {
            event.preventDefault()
            self.resetVariables() // Reset our form values

            // Trick to reset/clear native browser form validation state
            self.show = false
            self.$nextTick(() => {
                self.show = true
            })
        },
        resetVariables() {
            const self = this
            self.request.visitorUsername = ''
            self.request.date = ''
        }
    }
}
</script>

<style>
#createTicketForm {
    width: 50% !important;
    margin: 0 auto !important;
}
</style>