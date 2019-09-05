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
                                    :items="users"
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
            users: state => state.users
        })
    },

    methods: {
        createConversation() {
            var createRequest = createConversationRequest;
            createRequest.users = this.selectedUsers;

            var selectedUsernames = this.users.map(user => user.username);

            createRequest.name = selectedUsernames.join(", ");
            store.dispatch("sendMessage", createRequest);
            store.dispatch("setNewConversation", false);
        }
    }
};
</script>