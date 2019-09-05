<template>
    <div>
    </div>
</template>

<script>
import store from "../store.js";
import currentUserGet from "../messages/currentUserGet.json";
import { setInterval } from "timers";

export default {
    components: {},
    computed: {

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