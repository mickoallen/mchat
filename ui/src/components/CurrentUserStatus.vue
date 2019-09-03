<template>
    <div>
        <span>Username: {{ username }} Connected: {{ connected }} Logged in: {{ loggedIn }}</span>
    </div>
</template>

<script>
import store from "../store.js";
import currentUserGet from "../messages/currentUserGet.json";
import { setInterval } from "timers";

export default {
    components: {},
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
    mounted() {
        store.dispatch("sendMessage", currentUserGet);
        setInterval(function() {
            if (this.loggedIn) {
                store.dispatch("sendMessage", currentUserGet);
            }
        }, 4000);
    },
    methods: {
        logout() {
            store.commit("logout");
        }
    }
};
</script>