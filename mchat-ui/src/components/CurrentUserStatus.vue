<template>
    <div>
        <button class="modal-default-button" @click="getCurrentUserStatus">Get current user status</button>
        <button class="modal-default-button" @click="logout">Logout</button>
        <p>Username: {{ username }}</p>
        <p>Connected: {{ connected }}</p>
        <p>Logged in: {{ loggedIn }}</p>
    </div>
</template>

<script>
import store from "../store/store.js";
import currentUserGet from "../messages/currentUserGet.json";

export default {
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
            this.$toasted.show('hello billo');
            store.dispatch('sendMessage', currentUserGet);
        },
        logout(){
            store.commit('logout');
        }
    }
};
</script>