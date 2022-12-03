<template>
    <div id="ManageShift">
        <br>
        <h1>
            Employee Shifts
        </h1>
        <div>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav>

                    <!-- Navbar dropdowns -->
                    <b-nav-item-dropdown text="Shift Time" right>
                        <b-dropdown-item @click="refreshTable()">Any</b-dropdown-item>
                        <b-dropdown-item @click="showMorningShifts()">Morning</b-dropdown-item>
                        <b-dropdown-item @click="showAfternoonShifts()">Afternoon</b-dropdown-item>
                        <b-dropdown-item @click="showEveningShifts()">Evening</b-dropdown-item>
                    </b-nav-item-dropdown>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <b-button class="my-2 my-sm-0" @click="refreshTable()"> Refresh</b-button>
                    <br>
                    <b-button variant="primary" class="my-2 my-sm-0" @click="clearSelected()"> Clear Selection</b-button>
                </b-navbar-nav>
            </b-navbar>
        </div>
        <b-table ref="EmployeeTable" striped hover sticky-header="200px" :items="employees" selectable :select-mode="selectMode"
            @row-selected="onRowSelected"></b-table>

        <b-button variant="outline-success" @click="doSetToMorning(selectedShifts)">Assign to morning</b-button>
        <b-button variant="outline-warning" @click="doSetToAfternoon(selectedShifts)">Assign to afternoon</b-button>
        <b-button variant="outline-dark" @click="doSetToEvening(selectedShifts)">Assign to evening</b-button>





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
    components: {
        ErrorHandler
    },
    data() {
        return {
            employees: [],
            errorMessage: '',
            selectMode: 'multi',
            selectedEmployees: [],
            request: {
                username,
                shiftTime
            }

        }
    },
    created: function () {
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
        onRowSelected(selectedRows) {
            this.selectedEmployees = selectedRows;
        },
        clearSelected() {
            this.selectedEmployees = [];
            this.$refs.EmployeeTable.clearSelected();
        },
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
        showShifts(shiftTime) {
            AXIOS.get('/employee/byShift?id=' + shiftTime, {}, {})
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
        showMorningShifts() {
            this.showEmployees("Morning");
        },
        showAfternoonShifts() {
            this.showEmployees("Afternoon");
        },
        showEveningShifts() {
            this.showEmployees("Evening");
        },
        doUpdateEmployee(selectedEmployees, shiftTime) {
            for (var i = 0; i < selectedEmployees.length; i++) {
                this.request.username = selectedEmployees[i].username;
                this.request.shiftTime = shiftTime;
                AXIOS.put('/employee/', this.request, {})
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

            }

        },
        doSetToMorning() {
            this.doUpdateEmployee(this.selectedEmployees, "Morning");
            this.clearSelected();
            setTimeout(this.refreshTable, 10);
        },
        doSetToAfternoon() {
            this.doUpdateEmployee(this.selectedEmployees, "Afternoon");
            this.clearSelected();
            setTimeout(this.refreshTable, 10);
        },
        doSetToEvening() {
            this.doUpdateEmployee(this.selectedEmployees, "Evening");
            this.clearSelected();
            setTimeout(this.refreshTable, 10);
        }
    },
}







</script>

<style>

</style>