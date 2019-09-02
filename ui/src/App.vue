<template>
    <v-app>
        <v-app-bar app clipped-left v-if="isLoggedIn">
            <v-app-bar-nav-icon @click.stop="showConversationsTab = !showConversationsTab"></v-app-bar-nav-icon>
            <v-toolbar-title>MChat</v-toolbar-title>
            <v-spacer />
            <v-btn @click.stop="logout">Logout</v-btn>
            <v-btn @click.stop="changeTheme">Theme</v-btn>
        </v-app-bar>

        <v-navigation-drawer v-if="isLoggedIn" v-model="showConversationsTab" app clipped>
            <v-list dense two-line>
                <v-list-item
                    v-for="conversation in conversations"
                    v-bind:key="conversation.uuid"
                    @click.stop="selectConversation(conversation)"
                >
                    <v-list-item-action>
                        <v-icon>mdi-face</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title v-if="conversation.uuid == selectedConversation.uuid">
                            <h3>{{ conversation.name }}</h3>
                        </v-list-item-title>
                        <v-list-item-title
                            v-if="conversation.uuid != selectedConversation.uuid"
                        >{{ conversation.name }}</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
                <v-list-item @click.stop="clickNewConversation">
                    <v-list-item-action>
                        <v-icon>mdi-comment-plus</v-icon>
                    </v-list-item-action>
                    <v-list-item-content>
                        <v-list-item-title>New conversation</v-list-item-title>
                    </v-list-item-content>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>

        <v-content>
            <conversations v-if="isLoggedIn" />
            <landing-page v-if="!isLoggedIn" />
        </v-content>
    </v-app>
</template>

<script>
import { mapState } from "vuex";
import Vue from 'vue'

import store from "./store.js";
import LandingPage from "./components/LandingPage";
import Conversations from "./components/Conversations";
import VueNativeSock from "vue-native-websocket";

const WS_PORT = 7070;

var WS_URL = "wss://" + window.location.hostname + "/ws";

if(window.location.hostname === "localhost"){
    WS_URL = "ws://" + window.location.hostname + ":" + WS_PORT + "/ws";
}


Vue.use(VueNativeSock, WS_URL, {
    format: "json",
    reconnection: true,
    store: store
});

export default {
    name: "App",
    components: {
      LandingPage,
      Conversations
    },
    computed: {
        ...mapState({
            isLoggedIn: state => state.currentUser.loggedIn,
            conversations: state => state.conversations,
            selectedConversation: state => state.selectedConversation,
            users: state => state.users
        })
    },

    data() {
        return {
            showConversationsTab: null,
            darkMode: false
        };
    },

    methods: {
        logout() {
            store.commit("logout");
        },
        changeTheme() {
            this.darkMode = !this.darkMode;
            this.$vuetify.theme.dark = this.darkMode;
        },
        selectConversation(conversation) {
            store.dispatch("setNewConversation", false);
            store.dispatch("setSelectedConversation", conversation);
        },
    }
};
</script>
