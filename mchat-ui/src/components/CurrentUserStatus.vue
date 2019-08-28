<template>
    <v-content>
        <v-label>Username: {{ username }} Connected: {{ connected }} Logged in: {{ loggedIn }}</v-label>
    </v-content>
</template>

<script>
import store from "../store/store.js";
import currentUserGet from "../messages/currentUserGet.json";

import { VContent, VLabel } from "vuetify/lib";

export default {
    components: {
        VContent,
        VLabel
    },
    computed: {
        connected() {
            return store.getters.getCurrentUserInfo.connected;
        },
        loggedIn() {
            return store.getters.getCurrentUserInfo.loggedIn;
        },
        username() {
            return store.getters.getCurrentUserInfo.username;
        }
    },
    methods: {
        getCurrentUserStatus() {
            store.dispatch("sendMessage", currentUserGet);
        },
        logout() {
            store.commit("logout");
        }
    }
};
</script>