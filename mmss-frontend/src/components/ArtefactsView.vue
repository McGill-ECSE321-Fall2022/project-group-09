<template>
    <div id="ArtefactsView">
        <b-navbar toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Artefact Navigation</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
            <b-navbar-nav>
                <b-button v-if="(loggedInEmployee != null || loggedInManager != null)" size="sm" class="my-2 my-sm-0" type="submit" @click="$bvModal.show('CreateArtefactForm')">Create</b-button>
            </b-navbar-nav>
            <!-- <b-navbar-nav>
                <b-button size="sm" class="my-2 my-sm-0" type="submit" @click="$bvModal.show('UpdateArtefactForm')">Update</b-button>
            </b-navbar-nav> -->
            <!-- Right aligned nav items -->
            <b-navbar-nav class="ml-auto">
            <b-nav-item-dropdown text="Loan Availability" right>
                <b-dropdown-item>Available</b-dropdown-item>
                <b-dropdown-item href="#">Not available</b-dropdown-item>
                <b-dropdown-item href="#">Both</b-dropdown-item>
            </b-nav-item-dropdown>
    
            <b-nav-item-dropdown right>
                <!-- Using 'button-content' slot -->
                <template #button-content>
                <em>User</em>
                </template>
                <b-dropdown-item href="#">Profile</b-dropdown-item>
                <b-dropdown-item href="#">Sign Out</b-dropdown-item>
            </b-nav-item-dropdown>
            </b-navbar-nav>
        </b-collapse>
        </b-navbar>
        <b-container>
            <b-row align-v="center">
                <custom-card v-for="artefact in artefacts" :key="artefact.artefactId" :artefact="artefact" @click="selectedArtefact(artefact.artefactId)"/>
            </b-row>
        </b-container>   
        <b-modal 
            id='CreateArtefactForm'
            title="Create an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <create-form/>
        </b-modal>  
        <!-- <b-modal 
            id='UpdateArtefactForm'
            title="Update an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <update-artefact :artefactId="selected.artefactId" />
        </b-modal>  -->
        <!-- <ErrorHandler :message="errorMessage" />      -->
    </div>
</template>

<script>
import Card from '@/components/ArtefactCard'
import ErrorHandler from './ErrorPopUp.vue'
import axios from 'axios'
import CreateArtefactForm from './CreateArtefactForm.vue'
import UpdateArtefactForm from './UpdateArtefactForm.vue'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})
export default {
    name: "ArtefactsView",
    components: {
        "custom-card": Card,
        "create-form": CreateArtefactForm,
        "update-artefact": UpdateArtefactForm,
        ErrorHandler
    },
    data() {
    return {
        artefacts: [],
        errorMessage: '',
        selectedArtefactId: 0,
        loggedInVisitor: '',
        loggedInEmployee: '',
        loggedInManager: '',
    }},
    created: function () {
        this.loggedInVisitor = sessionStorage.getItem('loggedInVisitor');
        this.loggedInEmployee = sessionStorage.getItem('loggedInEmployee');
        this.oggedInManager = sessionStorage.getItem('loggedInManager');
        console.log(this.loggedInEmployee)
        console.log(this.loggedInManager)
        console.log(this.loggedInVisitor)
        const self = this
        AXIOS.get('/artefact', {}, {})
        .then(response => {
        self.artefacts = response.data
        console.log(self.artefacts)
        })
        .catch(error => {
            console.log("Hello form errror")
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
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
        selectedArtefact(id) {
            this.selectedArtefactId = id
            console.log(this.selectedArtefact)
        },
    }
}
</script>