<template>
    <div id="ManageShift">
        <div>
            <!-- add a secondary navigation bar with the filter dropdown, assignment buttons, refresh and clear selection -->
            <b-navbar class="secondaryBar" type="dark" variant="info">
                <b-navbar-brand>Employee Shifts</b-navbar-brand>
                <b-navbar-nav>

                    <!-- Navbar dropdowns -->
                    <b-nav-item-dropdown text="Shift Time" right>
                        <!-- assign the refresh table showMorningShifts showAfternoonShifts and showEveningShifts functions to the buttons -->
                        <b-dropdown-item @click="refreshTable()">Any</b-dropdown-item>
                        <b-dropdown-item @click="showMorningShifts()">Morning</b-dropdown-item>
                        <b-dropdown-item @click="showAfternoonShifts()">Afternoon</b-dropdown-item>
                        <b-dropdown-item @click="showEveningShifts()">Evening</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <!-- assign the doSetToMorning doSetToAfternoon and doSetToEvening functions to the buttons -->
                    <b-button variant="success" @click="doSetToMorning(selectedEmployees)">Assign to morning</b-button>
                    <b-button variant="warning" @click="doSetToAfternoon(selectedEmployees)">Assign to afternoon</b-button>
                    <b-button variant="dark" @click="doSetToEvening(selectedEmployees)">Assign to evening</b-button>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <!-- assign the refreshTable and clearSelected functions to the buttons -->
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="clearSelected()"> Clear Selection</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        <b-table ref="EmployeeTable" striped hover sticky-header="200px" :items="employees" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>





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
    name: 'ManageShift',
    // error handler component
    components: {
        ErrorHandler
    },
    // data: employees (array of employees), errorMessage (to be filled in with the error message), selectedEmployees(array of selectedEmployees selected on the table)
    //       selectMode (table parameter), shiftId (shiftId of the user), request (contains relevant dto information)
    data() {
        return {
            employees: [],
            errorMessage: '',
            selectMode: 'multi',
            selectedEmployees: [],
            request: {
                userName: '',
                shiftTime: ''
            },
            shiftId: ''

        }
    },
    created: function () {
        // fill the employees array
        AXIOS.get('/employee', {}, {})
            .then(response => {
                // add response to all employees
                this.employees = response.data
            })
            .catch(error => {
                this.errorMessage = error.response.data
            });
    },
    methods: {
        // update the selected notifications array with what is selected
        onRowSelected(selectedRows) {
            this.selectedEmployees = selectedRows;
        },
        // clear the array of selected notifications
        clearSelected() {
            this.selectedEmployees = [];
            this.$refs.EmployeeTable.clearSelected();
        },
        // refresh the notifications array through the database
        refreshTable() {
            AXIOS.get('/employee', {}, {})
                .then(response => {
                    // add response to all employees
                    this.employees = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        // show all employees with the specific shift id argument and replacing populating the employees array with the response
        showEmployees(shiftId) {
            AXIOS.get('/employee/byShift?id=' + shiftId, {}, {})
                .then(response => {
                    // replace employees with response
                    this.employees = response.data;
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
        // show all Morning employees by getting the shiftId of the Morning shift and inputting it to showEmployees
        async showMorningShifts() {
            await this.doGetShiftId("Morning");
            this.showEmployees(this.shiftId);
        },
        // show all Afternoon employees by getting the shiftId of the Afternoon shift and inputting it to showEmployees
        async showAfternoonShifts() {
            await this.doGetShiftId("Afternoon");
            this.showEmployees(this.shiftId);
        },
        // show all Evening employees by getting the shiftId of the Evening shift and inputting it to showEmployees
        async showEveningShifts() {
            await this.doGetShiftId("Evening");
            this.showEmployees(this.shiftId);
        },
        // obtain the shift Id from the shiftTime argument by going through the database
        async doGetShiftId(shiftTime) {
            await AXIOS.get('/shift/' + shiftTime, {}, {})
                .then(response => {
                    // replace loans with response
                    console.log(response.data.shiftId);
                    this.shiftId = response.data.shiftId;
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
        // sequentially update each selected notification and refresh
        async doUpdateEmployee(selectedEmployees, shiftTime) {
            for (var i = 0; i < selectedEmployees.length; i++) {
                this.request.userName = selectedEmployees[i].userName;
                this.request.shiftTime = shiftTime;
                await this.doGetShiftId(shiftTime);
                await AXIOS.put('/shift/assign?shiftId=' + this.shiftId + "&username=" + this.request.userName, {})
                    .then(response => {
                        // do nothing
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
                this.refreshTable();
            }

        },
        // use the doUpdateEmployee method with the selected employees and the Morning shift, then clear selection and refresh
        doSetToMorning() {
            this.doUpdateEmployee(this.selectedEmployees, "Morning");
            this.clearSelected();
            this.refreshTable();
        },
        // use the doUpdateEmployee method with the selected employees and the Afternoon shift, then clear selection and refresh
        doSetToAfternoon() {
            this.doUpdateEmployee(this.selectedEmployees, "Afternoon");
            this.clearSelected();
            this.refreshTable();
        },
        // use the doUpdateEmployee method with the selected employees and the Evening shift, then clear selection and refresh
        doSetToEvening() {
            this.doUpdateEmployee(this.selectedEmployees, "Evening");
            this.clearSelected();
            this.refreshTable();
        }
    },
}







</script>

<style>
</style>