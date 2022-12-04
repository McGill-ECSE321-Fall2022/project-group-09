<template>
    <div id="deleteConfirmation">
        <p class="center"> Are you sure you want to delete these accounts? This action cannot be
            undone</p>

        <b-button block variant="danger" @click="doDelete(selectedAccounts, method)"> Confirm </b-button>
    </div>
</template>

<script>
import axios from 'axios'
var config = require('../../config')

var frontendUrl = 'http://' + config.dev.host + ':' + config.dev.port
var backendUrl = 'http://' + config.dev.backendHost + ':' + config.dev.backendPort

var AXIOS = axios.create({
    baseURL: backendUrl,
    headers: { 'Access-Control-Allow-Origin': frontendUrl }
})

export default {
    props: {
        method: '',
        selectedAccounts: []
    },

    methods: {
        async doDelete(selected, accountType) {
            for (var i = 0; i < selected.length; i++) {
                await AXIOS.delete('/' + accountType + '/' + selected[i].userName)
                    .then(response => {

                    })
                    .catch(error => {

                    });
                this.$emit('delete');
                this.$bvModal.hide('deleteConfirmation');

            }
        },

    }
}


</script>

<style>
.center {
    text-align: center;
}
</style>