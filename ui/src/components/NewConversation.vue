<template>
    <v-container>
        <v-row justify="center">
            <v-col md="auto">
                <v-card>
                    <v-card-title class="ma-2 pa-5">Create new conversation</v-card-title>
                    <v-card-text>
                        <v-row justify="center">
                            <div class="ma-2 pa-2">
                                <v-select
                                    aria-autocomplete="off"
                                    v-model="selectedUsers"
                                    :items="usersForConversation"
                                    item-text="username"
                                    item-value="uuid"
                                    label="Select users"
                                    multiple
                                    chips
                                    deletable-chips
                                    
                                ></v-select>
                            </div>
                        </v-row>
                        <v-row justify="center">
                            <div class="ma-2 pa-2">
                                <v-btn
                                    class="ma-2"
                                    small
                                    color="primary"
                                    rounded
                                    @click.stop="createConversation"
                                >Create conversation</v-btn>
                            </div>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import store from "../store.js";
import { mapState } from "vuex";
import createConversationRequest from "../messages/createConversation.json";

export default {
    components: {},

    data() {
        return {
            selectedUsers: []
        };
    },

    computed: {
        ...mapState({
            users: state => state.users,
            currentUser: state => state.currentUser
        }),
        usersForConversation(){
            return this.users.filter(user => user.uuid != this.currentUser.uuid);
        }
    },

    methods: {
        createConversation() {
            var createRequest = createConversationRequest;
            createRequest.users = this.selectedUsers;
            createRequest.name = "this field is now deprecated";
            store.dispatch("sendMessage", createRequest);
            store.commit("changeActiveScreen", "NOTHING");
            store.commit("showSuccess","Conversation created");
            this.selectedUsers = [];
        }
    }
};
</script>