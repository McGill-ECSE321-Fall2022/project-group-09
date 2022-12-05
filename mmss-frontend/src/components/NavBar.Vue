<template>
    <div id = NavBar>
        <!-- Navigation Bar Displayed to Visitors-->
        <div id=visitorNavBar>
            <b-navbar type="dark" variant="info">
                <b-navbar-brand>Tool Bar</b-navbar-brand>
                <b-navbar-nav>
                    <b-nav-form>
                        <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="visitorUsername"
                            @keyup.enter="doGetVisitor(visitorUsername)"></b-form-input>
                        <b-button v-bind:disabled="(!visitorUsername.trim())" variant="success" class="my-2 my-sm-0"
                            @click="doGetVisitor(visitorUsername)">Search</b-button>
                        <span></span>
                        <b-form-checkbox v-model="status" name="check-button" value="yes" @change="filterArray(status)"
                            switch>
                            Outstanding Balance
                        </b-form-checkbox>
                    </b-nav-form>
                </b-navbar-nav>
                <b-navbar-nav class="ml-auto">
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

            <!-- Navigation Bar Displayed to the manager / employees -->
            <div id=visitorNavBar>
                <b-navbar type="dark" variant="info">
                    <b-navbar-brand>Tool Bar</b-navbar-brand>
                    <b-navbar-nav>
                        <b-nav-form>
                            <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="visitorUsername"
                                @keyup.enter="doGetVisitor(visitorUsername)"></b-form-input>
                            <b-button v-bind:disabled="(!visitorUsername.trim())" variant="success" class="my-2 my-sm-0"
                                @click="doGetVisitor(visitorUsername)">Search</b-button>
                            <span></span>
                            <b-form-checkbox v-model="status" name="check-button" value="yes"
                                @change="filterArray(status)" switch>
                                Outstanding Balance
                            </b-form-checkbox>
                        </b-nav-form>
                    </b-navbar-nav>
                    <b-navbar-nav class="ml-auto">
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

            <!-- Navition Bar Displayed to someone who is not logged in-->
            <div id=visitorNavBar>
                <b-navbar type="dark" variant="info">
                    <b-navbar-brand>Tool Bar</b-navbar-brand>
                    <b-navbar-nav>
                        <b-nav-form>
                            <b-form-input class="mr-sm-2" placeholder="Enter Username" v-model="visitorUsername"
                                @keyup.enter="doGetVisitor(visitorUsername)"></b-form-input>
                            <b-button v-bind:disabled="(!visitorUsername.trim())" variant="success" class="my-2 my-sm-0"
                                @click="doGetVisitor(visitorUsername)">Search</b-button>
                            <span></span>
                            <b-form-checkbox v-model="status" name="check-button" value="yes"
                                @change="filterArray(status)" switch>
                                Outstanding Balance
                            </b-form-checkbox>
                        </b-nav-form>
                    </b-navbar-nav>
                    <b-navbar-nav class="ml-auto">
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

</template>

<script>

</script>

<style>

</style>