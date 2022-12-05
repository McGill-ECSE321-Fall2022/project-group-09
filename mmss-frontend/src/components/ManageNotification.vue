<template>
    <div id="ManageNotification">
        <br>
        <h1>
            Notifications
        </h1>
        <div>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav class="ml-auto">
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="clearSelected()"> Clear Selection</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        <b-table ref="NotificationTable" striped hover sticky-header="200px" :items="notifications" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>

        <b-button variant="outline-danger" @click="doDeleteNotification(selectedNotifications)">Delete</b-button>





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
    name: 'ManageNotification',
    components: {
        ErrorHandler
    },
    data() {
        return {
            notifications: [],
            errorMessage: '',
            selectMode: 'multi',
            selectedNotifications: [],
            username: '',
            request: {
                notificationId: '',
                message: '',
                date: ''
            }

        }
    },
    created: function () {
        // checking if the user is already logged in 
        const loggedInEmployee = sessionStorage.getItem('loggedInEmployee');
        const loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        const loggedInManager = sessionStorage.getItem('loggedInManager');

        //check to see if an account is already logged in

        if (loggedInVisitor) {
            // get visitor username
            this.username = JSON.parse(loggedInEmployee).userName;
        }
        else if (loggedInEmployee) {
            // get employee username
            this.username = JSON.parse(loggedInEmployee).userName;
        }
        else if (loggedInManager) {
            // get manager username
            this.username = JSON.parse(loggedInManager).userName;
        }

        AXIOS.get('/notification/username/?username=' + this.username, {}, {})
            .then(response => {
                // add response to all notifications
                this.notifications = response.data
            })
            .catch(error => {
                this.errorMessage = error.response.data
            });
    },
    methods: {
        onRowSelected(selectedRows) {
            this.selectedNotifications = selectedRows;
        },
        clearSelected() {
            this.selectedNotifications = [];
            this.$refs.NotificationTable.clearSelected();
        },
        refreshTable() {
            AXIOS.get('/notification/username/?username=' + this.username, {}, {})
                .then(response => {
                    // add response to all notifications
                    this.notifications = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        async doDeleteNotification(selectedNotifications) {
            for (var i = 0; i < selectedNotifications.length; i++) {
                await AXIOS.delete('/notification/' + selectedNotifications[i].notificationId)
                    .then(response => {
                        
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
        }
    },
}







</script>

<style>

</style>