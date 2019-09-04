<template>
    <v-container>
        <v-row justify="center">
            <v-col md="auto">
                <v-card class="elevation-10">
                    <v-card-text>
                        <v-img :src="require('../assets/logo.png')" contain height="200"></v-img>
                        <v-form
                            v-if="!createAccountOptions"
                            ref="form"
                            v-model="valid"
                            :lazy-validation="false"
                        >
                            <v-text-field class="ma-2" v-model="username" label="Username" required></v-text-field>
                            <v-text-field
                                class="ma-2"
                                v-model="passwordOne"
                                :type="'password'"
                                label="Password"
                                required
                            ></v-text-field>
                            <v-row justify="center">
                                <v-btn
                                    class="ma-2"
                                    small
                                    color="primary"
                                    rounded
                                    @click.stop="login"
                                >Login</v-btn>
                            </v-row>
                            <v-row justify="center">
                                <v-btn
                                    class="ma-2"
                                    small
                                    color="primary"
                                    rounded
                                    outlined
                                    @click.stop="createAccountOptions = !createAccountOptions"
                                >Need to create an account?</v-btn>
                            </v-row>
                        </v-form>

                        <v-form
                            v-if="createAccountOptions"
                            ref="form"
                            v-model="valid"
                            :lazy-validation="false"
                        >
                            <v-text-field class="ma-2" v-model="username" label="Username" required></v-text-field>
                            <v-text-field
                                class="ma-2"
                                v-model="passwordOne"
                                :type="'password'"
                                label="Password"
                                required
                            ></v-text-field>
                            <v-text-field
                                class="ma-2"
                                v-model="passwordTwo"
                                label="Re-enter Password"
                                :type="'password'"
                                required
                            ></v-text-field>
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
                                    rounded
                                    @click.stop="createAccount"
                                >Create account</v-btn>
                            </v-row>
                            <v-row justify="center">
                                <v-btn
                                    small
                                    class="ma-2"
                                    color="primary"
                                    rounded
                                    outlined
                                    @click.stop="createAccountOptions = !createAccountOptions"
                                >Back to login</v-btn>
                            </v-row>
                        </v-form>
                    </v-card-text>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import conversationsGet from "../messages/conversationsGet.json";
import store from "../store.js";
import loginRequest from "../messages/login.json";
import createAccount from "../messages/createAccount.json";

import { mapState } from "vuex";

export default {
    components: {},

    data() {
        return {
            createAccountOptions: false,
            username: "",
            passwordOne: "",
            passwordTwo: "",
            valid: false,
            newConversations: false,
            avatarCode: 12345,
            selectedGender: 0,
            availableGenders: ["Male", "Other", "Female"],
            avatarGender: "MALE"
        };
    },

    computed: {
        ...mapState({
            serverUrl: state => state.serverUrl
        }),
        isLoggedIn() {
            return store.getters.getCurrentUserInfo.isLoggedIn;
        },
        getConversations() {
            return store.getters.getConversations;
        }
    },

    methods: {
        randomizeAvatar() {
            this.avatarCode = Math.floor(Math.random(64) * 10000000000);
        },
        getAvatarUrl() {
            return this.serverUrl + this.getAvatarUrlPath();
        },
        getAvatarUrlPath() {
            var gender = this.availableGenders[
                this.selectedGender
            ].toLowerCase();

            return "/avatar/" + this.avatarCode + "-" + gender + ".png";
        },
        login() {
            console.log("attempting login");
            loginRequest.username = this.username;
            loginRequest.password = this.passwordOne;
            store.dispatch("sendMessage", loginRequest);
        },
        loadConversations() {
            this.$socket.sendObj(conversationsGet);
        },
        createAccount() {
            console.log("creating account");
            createAccount.username = this.username;
            createAccount.password = this.passwordOne;
            createAccount.avatarUrl = this.getAvatarUrlPath();
            store.dispatch("sendMessage", createAccount);
        }
    }
};
</script>