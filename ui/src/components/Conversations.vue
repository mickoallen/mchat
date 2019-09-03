<template>
    <v-app>
        <chat-conversation v-if="selectedConversation.uuid != undefined" />

        <v-container v-if="selectedConversation.uuid == undefined">
            <v-row align="center" justify="center">
                <v-card>
                    <v-card-text>Select a conversation biatch</v-card-text>
                </v-card>
            </v-row>
        </v-container>
    </v-app>
</template>

<script>
import ChatConversation from "./ChatConversation";
// import store from "../store.js";
import { mapState } from "vuex";

export default {
    components: {
        ChatConversation
    },

    data() {
        return {
            newConversations: false,
            selectedUsers: []
        };
    },

    computed: {
        ...mapState({
            conversations: state => state.conversations,
            isLoggedIn: state => state.currentUser.loggedIn,
            selectedConversation: state => state.selectedConversation,
            users: state => state.users
        })
    },

    methods: {
        
    }
};
</script>