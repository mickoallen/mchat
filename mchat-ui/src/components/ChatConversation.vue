<template>
    <v-content>
        <v-container grid-list-md text-left>
            <v-layout>
                <v-flex xs12>
                    <v-card>
                        <v-sheet v-for="message in orderedMessages" v-bind:key="message.uuid">
                            <v-sheet><b>{{ getUsername(message.userUuid) }}</b> {{ message.message }}</v-sheet>
                        </v-sheet>
                    </v-card>
                    <span>
                    <v-textarea outlined v-model="newMessage"></v-textarea><v-btn @click.stop="sendMessage">Send</v-btn>
                    </span>
                </v-flex>
            </v-layout>
        </v-container>
    </v-content>
</template>

<script>
import store from "../store/store.js";
import sendMessage from "../messages/sendMessage.json";
import { mapState } from "vuex";

import {
    VContent,
    VContainer,
    VLayout,
    VFlex,
    VBtn,
    VTextarea,
    VSheet,
    VCard
} from "vuetify/lib";

export default {
    components: {
        VContent,
        VContainer,
        VLayout,
        VFlex,
        VBtn,
        VTextarea,
        VSheet,
        VCard
    },

    data() {
        return {
            newMessage: ""
        };
    },

    computed: {
        ...mapState({
            selectedConversation: state => state.selectedConversation,
            currentUser: state => state.currentUser,
            users: state => state.users
        }),
        orderedMessages: function() {
            return this.lodash.orderBy(
                this.selectedConversation.messages,
                "dateCreated"
            );
        }
    },

    methods: {
        sendMessage() {
            var sendMessageRequest = sendMessage;
            sendMessageRequest.conversationUuid = this.selectedConversation.uuid;
            sendMessageRequest.message = this.newMessage;
            store.dispatch("sendMessage", sendMessageRequest);
        },
        getUsername(uuid) {
            return this.users.filter(user => user.uuid == uuid)[0].username;
        }
    }
};
</script>