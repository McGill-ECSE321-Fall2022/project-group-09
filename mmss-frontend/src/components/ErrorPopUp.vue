<template>
    <!-- PopUp that will be used throughout the application to display error messages-->
    <div id="popup">
        <b-modal ok-only id="errorPopUp" centered title="An Error Occured!">
            <p class="my-4">{{ message }}</p>
        </b-modal>
    </div>

</template>

<script>

export default {
    // the error message that the parent component will supply
    props: ['message'],

}

</script>

<style>

</style>