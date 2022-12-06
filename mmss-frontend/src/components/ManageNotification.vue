<template>
    <div id="ManageNotification">
        <div>
            <!-- add a secondary navigation bar with the delete, refresh, and clear selection options -->
            <b-navbar class="secondaryBar" type="dark" variant="dark">
                <b-navbar-brand>Notifications</b-navbar-brand>
                <b-navbar-nav class="ml-auto">
                    <b-button variant="danger" @click="doDeleteNotification(selectedNotifications)">Delete</b-button>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <!-- assign the refresh table and clearselected functions to the buttons -->
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="clearSelected()"> Clear Selection</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        <!-- table of sorted notifications -->
        <b-table ref="NotificationTable" striped hover sticky-header="500px" :items="sortedItems" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>





        <!-- The component that displays the error message if necessary. Links the message of that component to -->
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
    // error handler component
    components: {
        ErrorHandler
    },
    // data: notifications (array of notifications), errorMessage (to be filled in with the error message), selectedNotifications(array of notifications selected on the table)
    //       selectMode (table parameter), username (email of the user), request (contains relevant dto information)
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
            this.username = JSON.parse(loggedInVisitor).userName;
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
        // update the selected notifications array with what is selected
        onRowSelected(selectedRows) {
            this.selectedNotifications = selectedRows;
        },
        // clear the array of selected notifications
        clearSelected() {
            this.selectedNotifications = [];
            this.$refs.NotificationTable.clearSelected();
        },
        // refresh the notifications array through the database
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
        // sequentially delete each selected notification and refresh
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
    // determine the sorted items array
    computed: {
        sortedItems: function() {
            return this.notifications.sort((a, b) => b.notificationId - a.notificationId)
        }
    }
}







</script>

<style>

</style>