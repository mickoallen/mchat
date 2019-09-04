<template>
    <v-app>
        <v-app-bar app clipped-left v-if="isLoggedIn">
            <v-app-bar-nav-icon @click.stop="showConversationsTab = !showConversationsTab"></v-app-bar-nav-icon>
            <v-toolbar-title>MChat</v-toolbar-title>
            <v-spacer />
            <current-user-status />
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
            <conversations v-if="isLoggedIn && !showNewConversation" />
            <landing-page v-if="!isLoggedIn" />
            <new-conversation v-if="showNewConversation" />
        </v-content>
    </v-app>
</template>

<script>
import { mapState } from "vuex";
import Vue from "vue";
import VueNativeSock from "vue-native-websocket";

import store from "./store.js";
import LandingPage from "./components/LandingPage";
import Conversations from "./components/Conversations";
import CurrentUserStatus from "./components/CurrentUserStatus";
import NewConversation from "./components/NewConversation";

const WS_PORT = 7070;

var SERVER_URL = "//" + window.location.hostname;
var WS_URL = "wss:" + SERVER_URL + "/ws";

if (window.location.hostname === "localhost") {
    SERVER_URL = "//" + window.location.hostname + ":" + WS_PORT;
    WS_URL = "ws:" + SERVER_URL + "/ws";
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
        Conversations,
        CurrentUserStatus,
        NewConversation
    },

    computed: {
        ...mapState({
            isLoggedIn: state => state.currentUser.loggedIn,
            conversations: state => state.conversations,
            selectedConversation: state => state.selectedConversation,
            users: state => state.users,
            darkTheme: state => state.darkTheme
        })
    },

    data() {
        return {
            showConversationsTab: null,
            showNewConversation: false
        };
    },

    mounted() {
        store.dispatch("serverUrl", SERVER_URL);
        this.$vuetify.theme.dark = this.darkTheme;
    },

    watch: {
        darkTheme() {
            this.$vuetify.theme.dark = this.darkTheme;
        }
    },

    methods: {
        logout() {
            store.commit("logout");
            this.showNewConversation = false;
        },
        changeTheme() {
            store.dispatch("changeTheme", !this.darkTheme);
        },
        selectConversation(conversation) {
            store.dispatch("setSelectedConversation", conversation);
            this.showNewConversation = false;
        },
        clickNewConversation() {
            store.dispatch("setSelectedConversation", {});
            this.showNewConversation = !this.showNewConversation;
        }
    }
};
</script>
