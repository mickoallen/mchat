<template>
    <v-app>
        <v-app-bar app clipped-left v-if="isLoggedIn">
            <v-app-bar-nav-icon @click.stop="showConversationsTab = !showConversationsTab"></v-app-bar-nav-icon>
            <v-toolbar-title>MChat</v-toolbar-title>
            <v-spacer />
            <current-user-status />
            <v-menu bottom left>
                <template v-slot:activator="{ on }">
                    <v-btn icon v-on="on">
                        <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                </template>

                <v-list>
                    <v-list-item @click.stop="changeTheme">
                        <v-list-item-icon>
                            <v-icon>mdi-theme-light-dark</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Theme</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click.stop="openProfile">
                        <v-list-item-icon>
                            <v-icon>mdi-account</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Profile</v-list-item-title>
                    </v-list-item>
                    <v-list-item @click.stop="logout">
                        <v-list-item-icon>
                            <v-icon>mdi-logout</v-icon>
                        </v-list-item-icon>
                        <v-list-item-title>Logout</v-list-item-title>
                    </v-list-item>
                </v-list>
            </v-menu>
        </v-app-bar>

        <v-navigation-drawer v-if="isLoggedIn" v-model="showConversationsTab" app clipped>
            <v-list dense>
                <v-list-item
                    v-for="conversation in conversations"
                    v-bind:key="conversation.uuid"
                    @click.stop="selectConversation(conversation.uuid)"
                    :class="`${conversation.uuid == selectedConversationUuid ? 'primary' : null} elevation-${conversation.uuid == selectedConversationUuid ? 2 : 0}` "
                >
                    <v-list-item-action>
                        <v-badge color="primary" overlap mode="out-in">
                            <template v-slot:badge>
                                <span v-if="conversationsUnreadMessage[conversation.uuid] > 0">{{conversationsUnreadMessage[conversation.uuid]}}</span>
                            </template>
                            <v-icon
                                :class="`${conversation.uuid == selectedConversationUuid ? 'mdi-spin' : null}`"
                            >mdi-face-outline</v-icon>
                        </v-badge>
                    </v-list-item-action>

                    <v-list-item-content>
                        <v-list-item-title>
                            <div
                                class="text-primary body-2"
                            >{{ conversation.name }}</div>
                        </v-list-item-title>
                    </v-list-item-content>
                </v-list-item>

                <!-- new conversation -->
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
            <landing-page v-if="!isLoggedIn" />
            <new-conversation v-if="activeScreen == 'NEW_CONVERSATION' && isLoggedIn" />
            <chat-conversation
                :conversationUuid="this.selectedConversationUuid"
                v-if="activeScreen == 'CHAT_CONVERSATIONS' && isLoggedIn"
            />
            <profile v-if="activeScreen == 'PROFILE' && isLoggedIn" />

            <v-container v-if="activeScreen == 'NOTHING' && isLoggedIn">
                <v-row align="center" justify="center">
                    <v-card>
                        <v-card-text>Select a conversation</v-card-text>
                    </v-card>
                </v-row>
            </v-container>
        </v-content>

        <v-snackbar color="error" v-model="errorSnackbar">
            <v-row align="center" justify="center">{{ error.message }}</v-row>
        </v-snackbar>
        <v-snackbar color="success" v-model="successSnackbar" v-if="!errorSnackbar">
            <v-row align="center" justify="center">{{ success.message }}</v-row>
        </v-snackbar>
    </v-app>
</template>

<script>
import { mapState } from "vuex";
import Vue from "vue";
import VueNativeSock from "vue-native-websocket";

import store from "./store.js";
import LandingPage from "./components/LandingPage";
import CurrentUserStatus from "./components/CurrentUserStatus";
import NewConversation from "./components/NewConversation";
import ChatConversation from "./components/ChatConversation";
import Profile from "./components/Profile";
import { setTimeout } from "timers";

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
        CurrentUserStatus,
        NewConversation,
        ChatConversation,
        Profile
    },

    computed: {
        ...mapState({
            isLoggedIn: state => state.currentUser.loggedIn,
            conversations: state => state.conversations,
            selectedConversationUuid: state => state.selectedConversationUuid,
            users: state => state.users,
            darkTheme: state => state.darkTheme,
            newConversation: state => state.newConversation,
            conversationInView: state => state.conversationInView,
            conversationsUnreadMessage: state =>
                state.conversationsUnreadMessage,
            error: state => state.error,
            success: state => state.success,
            activeScreen: state => state.activeScreen
        })
    },

    data() {
        return {
            showConversationsTab: null,
            successSnackbar: false,
            errorSnackbar: false
        };
    },

    mounted() {
        store.dispatch("serverUrl", SERVER_URL);
        this.$vuetify.theme.dark = this.darkTheme;
    },

    watch: {
        darkTheme() {
            this.$vuetify.theme.dark = this.darkTheme;
        },
        error() {
            this.errorSnackbar = true;
            setTimeout(function() {
                3000, (this.errorSnackbar = false);
            });
        },
        success() {
            this.successSnackbar = true;
            setTimeout(function() {
                3000, (this.successSnackbar = false);
            });
        }
    },
    methods: {
        logout() {
            store.commit("logout");
            store.commit("changeActiveScreen", "NOTHING");
        },
        changeTheme() {
            store.dispatch("changeTheme", !this.darkTheme);
        },
        selectConversation(conversationUuid) {
            store.commit("setSelectedConversation", conversationUuid);
        },
        clickNewConversation() {
            store.commit("setSelectedConversation", undefined);
            store.commit("changeActiveScreen", "NEW_CONVERSATION");
        },
        openProfile() {
            store.commit("changeActiveScreen", "PROFILE");
        }
    }
};
</script>
