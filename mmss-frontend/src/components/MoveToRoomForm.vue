 <!-- Quick form to move an artefact to another room -->
 <template>
    <div>
      <b-form @submit="onSubmit" v-if="show">
        <p>Currently in {{ room.roomName }}</p>
        <b-form-group 
            id="input-group-6" 
            label="Move to:" 
            label-for="input-4">
            <b-form-select
                id="input-4"
                v-model="room.roomId"
                :options="roomOptions"
                required
            ></b-form-select>
        </b-form-group>
        <b-button type="submit" variant="primary">Update Room</b-button>
      </b-form>
        <!-- The component that displays the error message. Links the message of that component to -->
        <ErrorHandler :message="errorMessage" />    
    </div>
  </template>
  
  <script>
import axios from 'axios'
import ErrorHandler from './ErrorPopUp.vue'

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
            roomOptions:[],
            room: '',
            errorMessage: '',
            show: true
        }
    },
    props: {
        artefactId : Number,
        roomId: Number,
    },
    created: function () {
        const self = this   
        // Populate room dropdown and room data
        AXIOS.get('/room', {}, {})
        .then(response => {
        //Assign the roomId to the room names
        const rooms = response.data
        for (let i = 0; i < rooms.length; i++) {
            const room = rooms[i]
            if (room.roomId != self.roomId) {
                this.roomOptions.push({ value: room.roomId, text: room.roomName + ' - ' + room.artefactCount})
            }
            // Current room cannot be in the dropdown
            else {
                self.room = room
            }
        }
        // console.log(rooms)
        })
        .catch((error) => {
            if (error.response.status >= 450) {
                self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
            } else {
                self.errorMessage = error.response.data;
            }
            self.$bvModal.show('errorPopUp');
        })
    },
    methods: {
        // When the Update Room button is clicked
        onSubmit(event) {
            event.preventDefault()
            const self = this
            AXIOS.put('/artefact/move', {}, { params: { artefactId: self.artefactId , roomId: self.room.roomId } })
            .then((response) => {
                // Show response
                alert('The artefact was successfully moved to a room.')
            })
            .catch((error) => {
                if (error.response.status >= 450) {
                    self.errorMessage = "Oops! An error occured. Please contact the musuem directly.";
                } else {
                    self.errorMessage = error.response.data;
                }
                // call the error handler component modal (named errorPopUp) to display the error message
                self.$bvModal.show('errorPopUp');
            })
        },
      }
    }
  </script>