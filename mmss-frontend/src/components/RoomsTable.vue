<!-- Show the rooms' artefact counts -->
<template>
    <div id="RoomsTable">
        <br>
        <b-container fluid>
            <b-row>
                <b-col><p><b>Small Rooms</b></p></b-col>
                <b-col><p><b>Large Rooms</b></p></b-col>
                <b-col><p><b>Display & Storage</b></p></b-col>
            </b-row>
            <b-row>
                <b-col>
                    <b-table striped hover :items="smallRooms"></b-table>
                </b-col>
                <b-col>
                    <b-table striped hover :items="largeRooms"></b-table>
                </b-col>
                <b-col>
                    <b-table striped hover :items="storageAndDisplay"></b-table>
                </b-col>
            </b-row>
        </b-container>        
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
    components: {
        ErrorHandler
    },
    data() {
    return {
        smallRooms: [],
        largeRooms: [],
        storageAndDisplay: [],
        errorMessage:'',
        smallCount: 0,
        largeCount: 0,
        count: 0,
        displayCount: 0,
    }
    },
    created: function () {
        const self = this
        // Call getAllRooms
        AXIOS.get('/room', {}, {})
        .then(response => {
        const rooms = response.data
        // Build the infor for the tables
        for (let i = 0; i < rooms.length; i++)
            {
                const room = rooms[i]
                if (room.roomtype == 'Small') {
                    self.displayCount += room.artefactCount
                    self.smallCount += room.artefactCount
                    self.smallRooms.push({roomName: room.roomName, artefactCount: room.artefactCount})
                }
                if (room.roomtype == 'Large') {
                    self.displayCount += room.artefactCount
                    self.largeCount += room.artefactCount
                    self.largeRooms.push({roomName: room.roomName, artefactCount: room.artefactCount})
                }
                if (room.roomtype == 'Storage') {
                    self.count += room.artefactCount
                    console.log(room.artefactCount)
                    console.log(self.count)
                    self.storageAndDisplay.push({roomName: room.roomName, artefactCount: room.artefactCount})
                }
            }
        self.smallRooms.push({roomName: 'Total: ', artefactCount: self.smallCount})
        self.largeRooms.push({roomName: 'Total: ', artefactCount: self.largeCount})
        self.storageAndDisplay.push({roomName: 'Display', artefactCount: self.displayCount})
        self.storageAndDisplay.push({roomName: 'Total: ', artefactCount: self.count + self.displayCount})
        })
        .catch(error => {
            console.log(error)
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
        // Get the artefact count in display
        getDisplayCapacity() {
            const self = this
            AXIOS.get('/room/displayCapacity', {}, {})
            .then(response => {
            console.log(response.data + ' from display')
            return response.data
            })
            .catch(error => {
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

<style>
    #RoomsTable{
        margin: 20px;
    }
</style>