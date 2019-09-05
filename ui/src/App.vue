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
                    <v-list-item-action v-if="conversation.uuid == selectedConversationUuid">
                        <v-icon class="mdi-spin">mdi-face-outline</v-icon>
                    </v-list-item-action>
                    <v-list-item-action v-if="conversation.uuid != selectedConversationUuid">
                        <v-icon>mdi-face-outline</v-icon>
                    </v-list-item-action>

                    <v-list-item-content>
                        <v-list-item-title v-if="showNotification(conversation.uuid)">
                            <div class="text-primary body-2">{{ conversation.name }}*</div>
                        </v-list-item-title>
                        <v-list-item-title>
                            <div class="text-primary body-2">{{ conversation.name }}</div>
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
            <new-conversation v-if="newConversation" />
            <chat-conversation v-if="selectedConversationUuid != undefined" />

            <v-container
                v-if="selectedConversationUuid == undefined && !newConversation && isLoggedIn"
            >
                <v-row align="center" justify="center">
                    <v-card>
                        <v-card-text>Select a conversation</v-card-text>
                    </v-card>
                </v-row>
            </v-container>
        </v-content>
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
        ChatConversation
    },

    computed: {
        ...mapState({
            isLoggedIn: state => state.currentUser.loggedIn,
            conversations: state => state.conversations,
            selectedConversationUuid: state => state.selectedConversationUuid,
            users: state => state.users,
            darkTheme: state => state.darkTheme,
            newConversation: state => state.newConversation,
            conversationsInView: state => state.conversationsInView
        })
    },

    data() {
        return {
            showConversationsTab: null
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
            store.dispatch("setSelectedConversation", undefined);
            store.dispatch("setNewConversation", false);
        },
        changeTheme() {
            store.dispatch("changeTheme", !this.darkTheme);
        },
        selectConversation(conversationUuid) {
            store.dispatch("setSelectedConversation", conversationUuid);
            store.dispatch("setNewConversation", false);
        },
        clickNewConversation() {
            store.dispatch("setSelectedConversation", undefined);
            store.dispatch("setNewConversation", true);
        },
        showNotification(conversationUuid) {
            if (this.conversationsInView[conversationUuid] == undefined) {
                return false;
            }

            if (
                this.conversationsInView[conversationUuid].isInView ==
                    undefined ||
                this.conversationsInView[conversationUuid].isInView ||
                this.conversationsInView[conversationUuid].newMessages == 0
            ) {
                return false;
            }

            if(!this.conversationsInView[conversationUuid].isInView && this.conversationsInView[conversationUuid].newMessages > 0){
                return true;
            }
        }
    }
};
</script>
