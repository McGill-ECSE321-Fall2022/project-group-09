<template>
    <div id="ArtefactsView">
        <b-navbar class="secondaryBar" toggleable="lg" type="dark" variant="info">
        <b-navbar-brand href="#">Artefact Navigation</b-navbar-brand>
        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
        <b-collapse id="nav-collapse" is-nav>
            <!-- Create artefact button for staff only -->
            <b-navbar-nav>
                <b-button v-if="(loggedInEmployee || loggedInManager)" size="sm" class="my-2 my-sm-0" type="submit" @click="$bvModal.show('CreateArtefactForm')">Create</b-button>
            </b-navbar-nav>
            <b-navbar-nav class="ml-auto">
            <!-- Select values to search artefacts -->
            <b-form-select v-model="selectedAvailability" :options="availabilities" @change="getArtefacts(selectedAvailability, selectedRoomId)"></b-form-select>
            <b-form-select v-model="selectedRoomId" :options="rooms" @change="getArtefacts(selectedAvailability, selectedRoomId)"></b-form-select>
            </b-navbar-nav>
        </b-collapse>
        </b-navbar>
        <!-- List artefacts dynamically -->
        <b-container>
            <b-row align-v="center">
                <custom-card v-for="artefact in artefacts" :key="artefact.artefactId" :artefact="artefact" />
            </b-row>
        </b-container>   
        <!-- Form to create an artefact, called by Create button -->
        <b-modal 
            id='CreateArtefactForm'
            title="Create an Artefact"
            centered 
            size="xl" 
            scrollable
            hide-footer>
            <create-form/>
        </b-modal>  
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
        availabilities: [
            {value: true, text: 'Available'},
            {value: false, text: 'Not available'},
            {value: null, text: 'Both'},
        ],
        rooms: [
            {value: 'all', text: 'All rooms'}, 
            {value: null, text: 'Display'}, 
            {value: '', text: 'Storage'},
            // Rest to be populated
        ],
        errorMessage: '',
        loggedInVisitor: '',
        loggedInEmployee: '',
        loggedInManager: '',
        selectedRoomId: 'all',
        selectedAvailability: null

        
    }},
    created: function () {
        // Get session info
        this.loggedInVisitor = sessionStorage.getItem('loggedInVisitor')
        this.loggedInEmployee = sessionStorage.getItem('loggedInEmployee')
        this.loggedInManager = sessionStorage.getItem('loggedInManager')
        // console.log(this.loggedInEmployee)
        // console.log(this.loggedInManager)
        // console.log(this.loggedInVisitor)
    
        const self = this
        self.populateRooms()
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
        //Push the room of the museum in the rooms select
        populateRooms() {
            const self = this
            AXIOS.get('/room', {}, {})
            .then(response => {
            const roomList = response.data
            for (let i = 0; i < roomList.length; i++) {
                const room = roomList[i]
                // Storage id
                if (room.roomtype == 'Storage') {
                    self.rooms[2].value = room.roomId
                    continue
                }
                self.rooms.push({value: room.roomId, text: room.roomName})
            }
            // console.log(roomList)
            // console.log(self.rooms)
            })
            .catch(error => {
                console.log(error)
                if (error.response.status >= 450) {
                    this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    this.errorMessage = error.response.data;
                }
                this.$bvModal.show('errorPopUp');
            })
        },
        // Update the list, artefacts, depending on inputs
        async getArtefacts(availability, roomId) {
            // console.log(availability)
            // console.log(roomId)
            // All rooms
            if (roomId == 'all') {
                if (availability == null) {
                    await this.getAllArtefacts()
                    return
                }
                else {
                    await this.getAllArtefactsByCanLoan(availability) 
                    return
            }   }
            // Display
            else if (roomId == null) {
                if (availability == null) {
                   await this.getAllArtefactsInDisplay()
                    return                     
                }
                else {
                    await this.getAllArtefactsInDisplayByCanLoan(availability)
                    return
                }
            }
            // Individual room
            else {
                if (availability == null) {
                    await this.getAllArtefactsByRoom(roomId)
                    return
                }
                else {
                    await this.getAllArtefactsByRoomAndByCanLoan(roomId, availability)
                    return
                }
            }
        },
        // Get all artefacts in the museum
        async getAllArtefacts() {
            const self = this
            AXIOS.get('/artefact', {}, {})
            .then(response => {
            self.artefacts = response.data
            // console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })
        },
        // Get all artefacts by their loan availability
        async getAllArtefactsByCanLoan(availability) {
            const self = this
            AXIOS.get('/artefact/canLoan', { params: { canLoan: availability} }, {})
            .then(response => {
            self.artefacts = response.data
            // console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })
        },
        // Get all artefacts in a room
        async getAllArtefactsByRoom(id) {
            const self = this
            AXIOS.get('/artefact/room', { params: { roomId: id} }, {})
            .then(response => {
            self.artefacts = response.data
            // console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })            
        },
        // Get all artefacts in the display
        async getAllArtefactsInDisplay() {
            const self = this
            AXIOS.get('/artefact/display', {}, {})
            .then(response => {
            self.artefacts = response.data
            // console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })    
        },
        // Get all artefacts in the display by their loan availability
        async getAllArtefactsInDisplayByCanLoan(availability) {
            const self = this
            AXIOS.get('/artefact/display/canLoan', { params: { canLoan: availability} }, {})
            .then(response => {
            self.artefacts = response.data
            // console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })
        },
        // Get all artefacts in a room by the display by their loan availability
        async getAllArtefactsByRoomAndByCanLoan(id, availability) {
            const self = this
            AXIOS.get('/artefact/roomAndCanLoan', { params: { roomId: id, canLoan: availability} }, {})
            .then(response => {
            self.artefacts = response.data
            console.log(self.artefacts)
            })
            .catch(error => {
                console.log("Hello form errror")
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                self.$bvModal.show('errorPopUp');
            })            
        }
    }
}
</script>