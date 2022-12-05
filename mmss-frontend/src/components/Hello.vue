<template>
  <!-- Adding the image -->
  <div id="mainPage">
    <img id="mainLogo" src="../assets/logo.png">
  </div>
</template>

<script>
export default {
  name: 'hello',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App'
    }
  }
}
</script>

<!-- Styling and centering the logo image on the main page -->
<style>

  #mainPage{
    display: flex;
    justify-content: center;
  }
  #mainLogo{
    margin: auto;
    padding: 130px;
  }
</style>
