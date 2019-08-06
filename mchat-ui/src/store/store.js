import * as MESSAGE_TYPES from '../constants/inboundMessageTypes.js';

import Vue from 'vue';
import Vuex from 'vuex';
import VueCookie from "vue-cookie";

const AUTH_TOKEN_COOKIE_NAME = "mchat-authentication";

Vue.use(Vuex);
Vue.use(VueCookie);

export default new Vuex.Store({
  state: {
    socket: {
      isConnected: false,
      message: '',
      reconnectError: false,
    },
    currentUser: {
      loggedIn: false,
      username: "",
      authenticationToken: ""
    },
    modals: {
      showLogin: false,
      createAccount: false
    },
    allChat: "",
    users: [],
    conversations: []
  },
  mutations: {
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      state.socket.isConnected = true;

      //check if there is an existing auth token we can save
      var authenticationToken = VueCookie.get(AUTH_TOKEN_COOKIE_NAME);
      if (authenticationToken != undefined) {
        state.currentUser.authenticationToken = authenticationToken;
      }
    },
    SOCKET_ONCLOSE(state) {
      state.socket.isConnected = false;
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    SOCKET_ONMESSAGE(state, message) {
      console.log("Received message: " + JSON.stringify(message));

      if (message.type == MESSAGE_TYPES.ERROR) {
        console.error("Error from server: " + message.body.message);
      }
      else if (message.type == MESSAGE_TYPES.CONVERSATIONS_GET_ALL_RESPONSE) {
        state.conversations = message.body.conversations;

      } else if (message.type == MESSAGE_TYPES.LOGIN_RESPONSE) {
        console.log("Login success!");
        VueCookie.set(message.body.cookieName, message.body.authenticationToken, message.body.cookieExpiry);
        state.currentUser.authenticationToken = message.body.authenticationToken;
        state.currentUser.loggedIn = true;

      } else if (message.type == MESSAGE_TYPES.CHAT_MESSAGE) {
        state.allChat = state.allChat + "\n" + message.body.message;

      } else if (message.type == MESSAGE_TYPES.CURRENT_USER) {
        state.currentUser.loggedIn = true;
        state.currentUser.username = message.body.username;
      }
    },
    // mutations for reconnect methods
    SOCKET_RECONNECT(state, count) {
      console.info(state, count);
    },
    SOCKET_RECONNECT_ERROR(state) {
      state.socket.reconnectError = true;
    },
    updateModalState(state, namedModalState) {
      console.log("Modal: " + namedModalState.name + " is now " + namedModalState.newState);
      state.modals[namedModalState.name] = namedModalState.newState;
    },
    logout(state) {
      console.log("logging out");
      VueCookie.delete("mchat-authentication");
      state.currentUser.loggedIn = false;
      state.currentUser.authenticationToken = null;
    }
  },
  actions: {
    sendMessage: (context, message) => {
      //add auth token to message
      message.authenticationToken = context.state.currentUser.authenticationToken;
      console.log("Sending message: " + JSON.stringify(message));
      Vue.prototype.$socket.sendObj(message);
    }
  },
  getters: {
    getConversations: (state) => {
      return state.conversations;
    },
    getLoginModalState: (state) => {
      return state.modals.showLogin;
    },
    getAllChat: (state) => {
      return state.allChat;
    },
    getCurrentUserInfo: (state) => {
      return {
        connected: state.socket.isConnected,
        loggedIn: state.currentUser.loggedIn,
        username: state.currentUser.username,
      };
    },
    getIsLoggedIn: (state) => {
      return state.currentUser.loggedIn;
    },
    getCreateAccountModalState: (state) => {
      return state.modals.createAccount;
    }
  }
});