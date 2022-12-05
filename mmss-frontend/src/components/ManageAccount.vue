<template>
    <div id="ManageAccount">
        <br>
        <h1>
            Visitors
        </h1>

        <!-- Tool bar for visitor accounts -->
        <div id=visitorTableToolBar>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav>
                    <b-nav-form>
                        <!-- Input for search by username-->
                        <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="visitorUsername"
                            @keyup.enter="doGetVisitor(visitorUsername)"></b-form-input>

                        <!-- Search button-->
                        <b-button v-bind:disabled="(!visitorUsername.trim())" variant="success" class="my-2 my-sm-0"
                            @click="doGetVisitor(visitorUsername)">Search</b-button>

                        <span></span>
                        <!-- Toggle outstabding balance. Filters the array-->
                        <b-form-checkbox v-model="status" name="check-button" value="yes" @change="filterArray(status)"
                            switch>
                            Outstanding Balance
                        </b-form-checkbox>
                    </b-nav-form>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">

                    <!-- Button Group to refresh table, select all, and clear table-->
                    <b-button-group>
                        <b-button class="my-2 my-sm-0" @click="refreshVisitorTable()"> Refresh Table</b-button>
                        <br>
                        <b-button variant="primary" class="my-2 my-sm-0" @click="selectAllVisitorTable()"> Select
                            All</b-button>
                        <br>
                        <b-button class="my-2 my-sm-0" @click="clearVisitorsSelected()"> Clear
                            Selected</b-button>
                    </b-button-group>
                </b-navbar-nav>
            </b-navbar>
        </div>

        <!-- Table that displays the visitor accounts-->
        <b-table ref="VisitorTable" striped hover sticky-header="600px" :items="visitors" selectable
            :select-mode="selectMode" @row-selected="onVisitorRowSelected"></b-table>

        <!-- Buttons to edit selected visitor accounts - send notificaiton, clear balance, and delete-->
        <b-button variant="success" @click="doRaiseNotification(selectedVisitors)"
            :disabled="(this.selectedVisitors.length === 0)">Send Notification</b-button>
        <b-button variant="primary" @click="doClearBalance(selectedVisitors)"
            :disabled="(this.selectedVisitors.length === 0)">Clear Balance</b-button>
        <b-button variant="danger" @click="callDelete(visitorType)"
            :disabled="this.selectedVisitors.length === 0">Delete </b-button>
        <br>
        <br>
        <br>
        <br>
        <h1>
            Employees
        </h1>

        <!-- Tool bar for employee accounts -->
        <div id=visitorTableToolBar>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav>
                    <b-nav-form>
                        <!-- Input for search by username-->
                        <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="employeeUsername"
                            @keyup.enter="doGetEmployee(employeeUsername)"></b-form-input>

                        <!-- Search button-->
                        <b-button v-bind:disabled="(!employeeUsername.trim())" variant="success" class="my-2 my-sm-0"
                            @click="doGetEmployee(employeeUsername)">Search</b-button>
                    </b-nav-form>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
                    <!-- Button Group to refresh table, select all, and clear table-->
                    <b-button-group>
                        <b-button class="my-2 my-sm-0" @click="refreshEmployeeTable()"> Refresh Table</b-button>
                        <br>
                        <b-button variant="primary" class="my-2 my-sm-0" @click="selectAllEmployeeTable()"> Select
                            All</b-button>
                        <b-button class="my-2 my-sm-0" @click="clearEmployeesSelected()"> Clear
                            Selected</b-button>
                    </b-button-group>
                </b-navbar-nav>
            </b-navbar>
        </div>

        <!-- Table that displays the employee accounts-->
        <b-table ref="EmployeeTable" striped hover sticky-header="600px" :items="employees" selectable
            :select-mode="selectMode" @row-selected="onEmployeeRowSelected"></b-table>

        <!-- Buttons to edit selected employee accounts - send notificaiton and delete-->
        <b-button variant="success" @click="doRaiseNotification(selectedEmployees)"
            :disabled="(this.selectedEmployees.length === 0)">Send Notification</b-button>
        <b-button variant="danger" @click="callDelete(employeeType)" :disabled="this.selectedEmployees.length === 0">
            Delete </b-button>

        <br>
        <br>
        <b-button variant="success" @click="$bvModal.show('CreateEmployeeForm')"> Create New Employee </b-button>
        <br>

        <br>
        <br>
        <br>
        <br>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />
        <notification :selectedAccounts="selectedForNotification" />

        <!-- The component to create an employee-->
        <b-modal id='CreateEmployeeForm' title="Create an Employee" centered size="xl" scrollable hide-footer>
            <create-form @submitted="refresh()"/>
        </b-modal>
        <!-- The component to confirm to delete accounts -->
        <b-modal id="deleteConfirmation" title="Delete Confirmation" centered hide-footer>
            <delete-confirmation @delete="refresh()" :method="accountType" :selectedAccounts="selectedForDelete" />
        </b-modal>


    </div>

</template>

<script>
import axios from 'axios'
// Import the notification popup component
import notification from './NotificationPopUp.vue';
// Import the components
import ErrorHandler from './ErrorPopUp.vue'; // This is the error component
import CreateForm from './CreateEmployeeForm.vue'; // the create form component
import DeleteConfirmation from './DeleteConfirmation.vue'; // the confirmation component
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})



export default {
    name: 'ManageAccount',
    // components 
    components: {
        ErrorHandler,
        notification,
        "create-form": CreateForm,
        deleteConfirmation: DeleteConfirmation
    },
    // variables
    data() {
        return {
            // display in tables
            visitors: [],
            employees: [],
            // axios errors 
            errorMessage: '',
            // table select modes
            selectMode: 'multi',
            // array lists for selected items
            selectedVisitors: [],
            selectedEmployees: [],
            selectedForNotification: [],
            selectedForDelete: [],
            // inputs for search
            visitorUsername: '',
            employeeUsername: '',
            accountType: '',
            //types for delete methods
            employeeType: 'employee',
            visitorType: 'visitor',
            status: false,
            request: {
                username: '',
            }

        }
    },
    created: function () {
        const self = this
        // initialize tables
        AXIOS.get('/visitor', {}, {})
            .then(response => {
                // add response to all visitors
                self.visitors = response.data
            })
            .catch(error => {
                self.errorMessage = error.response.data
            });

        AXIOS.get('/employee', {}, {})
            .then(response => {
                // add response to all loans
                self.employees = response.data
            })
            .catch(error => {
                self.employees = error.response.data
            });





    },
    methods: {
        // adding items as they are selected
        onVisitorRowSelected(selectedRows) {
            this.selectedVisitors = selectedRows;
        },
        onEmployeeRowSelected(selectedRows) {
            this.selectedEmployees = selectedRows;
        },
        // clearing selections
        clearVisitorsSelected() {
            this.selectedVisitors = [];
            this.$refs.VisitorTable.clearSelected();
        },
        clearEmployeesSelected() {
            this.selectedEmployees = [];
            this.$refs.EmployeeTable.clearSelected();
        },
        // refresh tables asyncronously
        async refreshVisitorTable() {

            await AXIOS.get('/visitor', {}, {})
                .then(response => {

                    this.visitors = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
            this.status = false;
        },
        refreshEmployeeTable() {
            AXIOS.get('/employee', {}, {})
                .then(response => {
                    this.employees = response.data
                })
                .catch(error => {
                    this.errorMessage = error.response.data
                });
        },
        // select all methods
        selectAllEmployeeTable() {
            this.$refs.EmployeeTable.selectAllRows();
        },
        selectAllVisitorTable() {
            this.$refs.VisitorTable.selectAllRows();
        },
        // get the visitor by username ( for search functionality )
        async doGetVisitor(username) {
            await AXIOS.get('/visitor/' + username, {}, {})
                .then(response => {
                    // replace loans with response
                    this.visitors = [response.data];
                    //clear the input
                    this.visitorUsername = '';

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
            this.status = false;
        },
        // get the employee by username ( for search functionality )
        doGetEmployee(username) {
            AXIOS.get('/employee/' + username, {}, {})
                .then(response => {
                    // replace loans with response
                    this.employees = [response.data];
                    //clear the input
                    this.employeeUsername = '';

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
        // call the delete popup method
        callDelete(input) {
            this.selectedForDelete = '';
            this.accountType = '';

            // set the inputs to the modal
            if (input === 'visitor') {
                this.selectedForDelete = this.selectedVisitors;
                this.accountType = 'visitor';
            } else {
                this.selectedForDelete = this.selectedEmployees;
                this.accountType = 'employee';
            }
            // call the modal
            this.$bvModal.show('deleteConfirmation');
        },
        // send notification popup
        doRaiseNotification(selected) {
            this.selectedForNotification = selected;
            this.$bvModal.show('NotificationPopUp');
            // clear table
            this.clearVisitorsSelected();
            this.clearEmployeesSelected();
        },
        // filter array for people with outstanding balance
        filterArray(status) {
            this.status = status;
            if (status === false) {
                this.refreshVisitorTable();
            } else {
                this.visitors = this.visitors.filter(visitor => visitor.balance > 0);
            }
        },
        // refresh tables
        refresh() {
            this.refreshVisitorTable();
            this.refreshEmployeeTable();
        },
        // clear balance methods
        async doClearBalance() {
            const self = this;
            for (var i = 0; i < self.selectedVisitors.length; i++) {
                await AXIOS.put('/visitor/' + self.selectedVisitors[i].userName + '/?amount=0', {}, {})
                    .then(response => {

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
            }
            this.refreshVisitorTable();
            this.clearVisitorsSelected();
        }
    },
}




</script>

<style>

</style>