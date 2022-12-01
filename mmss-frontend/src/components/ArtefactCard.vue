<template>
    <div>
        <!-- img-src="link" img-alt="Image" img-top 
        Add eventually for pictures-->
        <b-col>
            <b-card
            @click="$bvModal.show(String(artefactId))"
            :title=artefactName
            img-src="https://picsum.photos/600/300/?image=25"
            img-alt="Image"
            img-top
            tag="artefact"
            style="max-width: 20rem;"
            class="mb-2"
            >
            <b-list-group flush>
                <b-list-group-item>{{ (canLoan && !currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }}</b-list-group-item>
                <b-list-group-item>{{ roomId }}</b-list-group-item>
            </b-list-group>
            <!-- Eventually add buttons depending on the user 
            <b-button href="#" variant="primary">Go somewhere</b-button> -->
            </b-card>  
        </b-col> 
        <b-modal :id=String(artefactId) :title=artefactName centered size="xl" scrollable>
            <b-container fluid>
                <b-row>
                    <b-col>
                        <b-img fluid-grow src="https://picsum.photos/600/300/?image=25" alt="Image"></b-img>
                    </b-col>
                    <b-col fluid>
                        <!-- <b-container fluid> -->
                            <b-row align-self="stretch">{{ description }}</b-row>
                            <b-row align-v="end">{{ (canLoan && !currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }}</b-row>
                            <b-row align-v="end">
                                <b-list-group horizontal flex-fill>
                                    <b-list-group-item>Insurance fee: {{ insuranceFee }}</b-list-group-item>
                                    <b-list-group-item>Loan fee: {{ loanFee }}</b-list-group-item>
                                </b-list-group>
                            </b-row>
                            <b-row align-v="end">Room: {{ roomId }}</b-row>
                        <!-- </b-container> -->
                    </b-col>
                        <!-- <b-list-group flush>
                            <b-list-group-item>{{ (canLoan && !currentlyOnLoan)? 'Available for loan' : 'Not available for loan' }}</b-list-group-item>
                            <b-list-group-item>{{ roomId }}</b-list-group-item>
                        </b-list-group> -->
                        <!-- Eventually add buttons depending on the user 
                        <b-button href="#" variant="primary">Go somewhere</b-button> -->
                </b-row>
            </b-container>
        </b-modal>         
    </div>    
</template>


<script>
export default {
  props: {
    artefactId: Number,
    artefactName: String,
    description: String,
    canLoan: Boolean,
    insuranceFee: Number,
    loanFee: Number,
    currentlyOnLoan: Boolean,
    roomId: Number,
  },
}
</script>