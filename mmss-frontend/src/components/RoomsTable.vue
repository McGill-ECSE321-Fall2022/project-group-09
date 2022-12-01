<template>
    <div id="RoomsTable">
        <b-table striped hover :items="rooms"></b-table>
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
        rooms: [],
        errorMessage:''
    }
    },
    created: function () {
        // Call getAllRooms, 3 args necessary
        AXIOS.get('/room', {}, {})
        .then(response => {
        //Assign the roomId to the room names
        const roomList = response.data
        this.storageRoom(roomList)
        this.display()
        this.smallRooms(roomList)
        this.largeRooms(roomList)
        for (let i = 0; i < roomList.length; i++) {
            const room = roomList[i]
            if (room.roomtype != 'Storage') {
                this.rooms.push({roomName: room.roomName, artefactCount: room.artefactCount})
            }
        }
        console.log(roomList)
        })
        .catch(error => {
            console.log(error)
            // logic on the error status. Display backend error message if status is below 450
            // otherwise display something went wrong
            if (error.response.status >= 450) {
                this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                this.errorMessage = error.response.data;
            }
            // call the error handler component modal (named errorPopUp) to display the error message
            this.$bvModal.show('errorPopUp');
        })
    },
    methods: {
        storageRoom(rooms) {
            for (let i = 0; i < rooms.length; i++)
            {
                const room = rooms[i]
                if (room.roomtype == 'Storage') {
                    return this.rooms.push({roomName: room.roomName, artefactCount: room.artefactCount})
                }
            }
        },
        smallRooms(rooms) {
            let count = 0
            for (let i = 0; i < rooms.length; i++)
            {
                const room = rooms[i]
                if (room.roomtype == 'Small') {
                    count += room.artefactCount
                }
            }
            return this.rooms.push({roomName: "Small Rooms", artefactCount: count})
        },
        largeRooms(rooms) {
            let count = 0
            for (let i = 0; i < rooms.length; i++)
            {
                const room = rooms[i]
                if (room.roomtype == 'Large') {
                    count += room.artefactCount
                }
            }
            return this.rooms.push({roomName: "Large Rooms", artefactCount: count})
        },
        display() {
            AXIOS.get('/room/displayCapacity', {}, {})
            .then(response => {
            const displayCount = response.data
            console.log(displayCount)
            return this.rooms.push({roomName: "Display", artefactCount: displayCount})
            })
            .catch(error => {
                if (error.response.status >= 450) {
                    this.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    this.errorMessage = error.response.data;
                }
                this.$bvModal.show('errorPopUp');
            })
        }
    }

}
</script>