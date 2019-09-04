<template>
    <v-container>
        <v-row justify="center">
            <v-col md="auto">
                <v-card>
                    <v-card-title class="ma-2 pa-5">Create new conversation</v-card-title>
                    <v-card-text>
                        <v-row justify="center">
                            <div class="ma-2 pa-2">
                                Conversation name:
                                <v-text-field v-model="conversationName" />
                            </div>
                        </v-row>
                        <v-row justify="center">
                            <div class="ma-2 pa-2">
                                <v-combobox
                                    v-model="selectedUsers"
                                    :items="users"
                                    item-text="username"
                                    item-value="uuid"
                                    label="Select users"
                                    multiple
                                    chips
                                ></v-combobox>
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
            selectedUsers: [],
            conversationName: ""
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
            createRequest.users = this.selectedUsers.map(user => user.uuid);
            createRequest.name = this.conversationName;
            store.dispatch("sendMessage", createRequest);
        }
    }
};
</script>