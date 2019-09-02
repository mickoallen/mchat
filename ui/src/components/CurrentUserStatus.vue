<template>
<div>
    <span>Username: {{ username }} Connected: {{ connected }} Logged in: {{ loggedIn }}</span>        
</div>

</template>

<script>
import store from "../store.js";
import currentUserGet from "../messages/currentUserGet.json";

export default {
    components: {
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
    mounted() {
            this.getCurrentUserStatus();
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