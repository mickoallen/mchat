<template>
    <v-content>
        <v-container grid-list-md text-left>
            <v-layout>
                <v-flex xs12>
                    <v-card>
                        Name: <v-text-field v-model="conversationName" />
                        Bitches: <v-select
                            v-model="selectedUsers"
                            :items="users"
                            item-text="username"
                            item-value="uuid"
                            label="Select"
                            multiple
                            chips
                            hint="Select bitches"
                        ></v-select>
                        
                        <v-btn @click.stop="createConversation">Create conversation</v-btn>
                    </v-card>
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import store from "../store.js";
import { mapState } from "vuex";
import createConversationRequest from "../messages/createConversation.json";

export default {
    components: {
    },

    data() {
        return {
            selectedUsers: [],
            conversationName:""
        };
    },

    computed: {
        ...mapState({
            users: state => state.users
        })
    },

    methods: {
        createConversation() {
            //yourmom
            console.log("creating conversation");
            var createRequest = createConversationRequest;
            createRequest.users = this.selectedUsers;
            createRequest.name = this.conversationName;
            store.dispatch("sendMessage", createRequest);
        }
    }
};
</script>