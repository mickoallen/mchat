<template>
    <v-container>
        <v-row justify="center">
            <v-col md="auto">
                <v-card class="elevation-10">
                    <v-card-text>
                        <v-img :src="require('../assets/logo.png')" contain height="200"></v-img>
                        <v-row align="center" justify="center">
                            <v-avatar class="ma-2" tile width="120" height="120">
                                <v-img v-bind:src="getAvatarUrl()" contain></v-img>
                            </v-avatar>
                        </v-row>
                        <v-row align="center" justify="center">
                            <v-btn
                                class="ma-2"
                                small
                                color="secondary"
                                rounded
                                @click.stop="randomizeAvatar"
                            >Randomize</v-btn>
                        </v-row>
                        <v-row align="center">
                            <v-slider
                                class="ma-2"
                                v-model="selectedGender"
                                :tick-labels="availableGenders"
                                max="2"
                                step="1"
                                ticks="always"
                                tick-size="4"
                            ></v-slider>
                        </v-row>
                        <v-row justify="center">
                            <v-btn
                                small
                                class="ma-2"
                                color="primary"
                                :value="!this.avatarHasChanged"
                                rounded
                                @click.stop="saveProfile"
                            >Save profile</v-btn>
                        </v-row>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import store from "../store.js";
import { mapState } from "vuex";
import updateUser from "../messages/updateUser.json"

export default {
    components: {},

    data() {
        return {
            avatarCode: this.existingAvatarCode,
            avatarHasChanged: false,
            selectedGender: 0,
            availableGenders: ["Male", "Other", "Female"],
            avatarGender: "MALE"
        };
    },

    computed: {
        ...mapState({
            currentUser: state => state.currentUser,
            users: state => state.users,
            serverUrl: state => state.serverUrl
        }),
        existingAvatarUrl() {
            return this.users.filter(
                user => user.uuid == this.currentUser.uuid
            )[0].avatarUrl;
        }
    },

    methods: {
        randomizeAvatar() {
            this.avatarHasChanged = true;
            this.avatarCode = Math.floor(Math.random(64) * 10000000000);
        },
        getAvatarUrl() {
            return this.avatarHasChanged
                ? this.serverUrl + this.getAvatarUrlPath()
                : this.serverUrl + this.existingAvatarUrl;
        },
        getAvatarUrlPath() {
            var gender = this.availableGenders[
                this.selectedGender
            ].toLowerCase();
            store;
            return "/avatar/" + this.avatarCode + "-" + gender + ".png";
        },
        saveProfile(){
            var updateUserRequest = updateUser;
            updateUserRequest.avatarUrl = this.getAvatarUrlPath();
            store.dispatch('sendMessage', updateUserRequest);
            store.commit('changeActiveScreen', 'CHAT_CONVERSATIONS');
            store.commit('showSuccess', 'Profile updated');
        }
    }
};
</script>