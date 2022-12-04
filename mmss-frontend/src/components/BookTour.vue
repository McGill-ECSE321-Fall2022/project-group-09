<template>
    <div id="CreateTour">
        <b-card bg-variant="light">
            <b-form @submit="onSubmit" @reset="onReset" v-if="show">

                <b-form-group id="booking-datepicker">
                    <label for="booking-datepicker">Choose a Tour Date</label>
                    <b-form-datepicker id="booking-datepicker" v-model="request.date" class="mb-2"
                        required></b-form-datepicker>
                    <p>Date: <strong>{{ request.date }}</strong></p>
                </b-form-group>

                <b-form-group id="booking-spinbutton">
                    <label for="booking-spinbutton">Choose the Number of Visitors</label>
                    <b-form-spinbutton id="booking-spinbutton" v-model="request.numberOfParticipants" min="1" max="20"
                        class="mb-2" required></b-form-spinbutton>
                    <p>Quantity: <strong>{{ request.numberOfParticipants }}</strong></p>
                </b-form-group>

                <b-form-group id="booking-timepicker">
                    <label for="booking-timepicker">Choose a Time Slot</label>
                    <b-form-select id="booking-timepicker" v-model="request.shiftTime" :options="timeSlotOptions"
                        class="mb-2" required></b-form-select>
                    <div>Time Slot: <strong>{{ request.shiftTime }}</strong></div>
                </b-form-group>

                <b-form-group id="booking-submit">
                    <b-button id="booking-submit" type="submit" class="mt-4" block variant="primary">Book
                        Tour</b-button>
                </b-form-group>

                <b-form-group id="booking-reset">
                    <b-button id="booking-reset" type="reset" variant="danger">Reset</b-button>
                </b-form-group>

            </b-form>
        </b-card>

        <ErrorHandler :message="errorMessage" />
        <b-card class="mt-3" header="Form Data Result">
            <pre class="m-0">{{ request }}</pre>
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
            request: {
                visitorUsername: '',
                date: '',
                numberOfParticipants: '',
                shiftTime: null,
            },
            timeSlotOptions: [
                { value: null, text: 'No time slot selected' },
                { value: 'Morning', text: 'Morning (7:30AM to 12PM)' },
                { value: 'Afternoon', text: 'Afternoon (12PM to 4:30PM)' },
                { value: 'Evening', text: 'Evening (4:30PM to 9PM)' },
            ],
            errorMessage: '',
            show: true
        }
    },
    created: function () {
        const self = this

        const visitor = {
            username: "jon.snow@got.com",
            password: "idontwantit",
            firstName: "Jon",
            lastName: "Snow",
        }
        sessionStorage.setItem('loggedInVisitor', JSON.stringify(visitor)); //store visitor
        const visitorUsername = JSON.parse(sessionStorage.getItem("loggedInVisitor")).username; //get visitor username

        AXIOS.post('/openday', { date: "2022-12-03" }, {}).then((response) => {
            alert(JSON.stringify(response)) // Show response
            console.log(response)
        })
            .catch((error) => {
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp'); // call the error handler component modal (named errorPopUp) to display the error message
            });

        self.request.visitorUsername = visitorUsername;

        AXIOS.get('/tour', {}, {}) // Initializing tours from backend
            .then(response => {
                // JSON responses are automatically parsed.
                const tours = response.data
                for (let i = 0; i < tours.length; i++) {
                    const tour = tour[i]
                    this.tourBookings.push({ value: tour.visitorUsername, text: tour.date + ' - ' + tour.numberOfParticipants + ' - ' + tour.shiftTime })
                }
                console.log(tours)
            })
            .catch((error) => {
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
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.post('/tour', self.request, {}).then((response) => {
                alert(JSON.stringify(response)) // Show response
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
            self.request.numberOfParticipants = ''
            self.request.shiftTime = ''
        }
    }
}
</script>

<style>

</style>