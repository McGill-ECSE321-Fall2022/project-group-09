<template>
    <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">Artefact Navigation</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
            <b-button size="sm" class="my-2 my-sm-0" type="submit" @click="$router.push({name: 'CreateArtefactForm'})">Create</b-button>
        </b-navbar-nav>
        <!-- Right aligned nav items -->
        <b-navbar-nav class="ml-auto">
          <b-nav-item-dropdown text="Loan Availability" right>
            <b-dropdown-item @click="helloWorld">Available</b-dropdown-item>
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
  </div>
</template>

<script>
    export default {
        methods: {
            helloWorld () {
                console.log("Hello World")
            }
        }
    }
</script>