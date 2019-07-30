import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    socket: {
      isConnected: false,
      message: '',
      reconnectError: false,
    },
    currentUser: {
      loggedIn: false
    },
    modals: {
      showLogin: false
    },
    users: [],
    conversations: []
  },
  mutations: {
    SOCKET_ONOPEN(state, event) {
      Vue.prototype.$socket = event.currentTarget;
      state.socket.isConnected = true;
    },
    SOCKET_ONCLOSE(state) {
      state.socket.isConnected = false;
    },
    SOCKET_ONERROR(state, event) {
      console.error(state, event);
    },
    SOCKET_ONMESSAGE(state, message) {
      console.log("Store Message: " + message);

      if (message.messageType === "CONVERSATIONS_GET_RESPONSE") {
        state.conversations = message.conversationList;

      } else if (message.messageType === "ADD_COOKIE") {
        console.log("Login success!");
        console.log(
          "Setting cookie: " + message.name + " with value: " + message.value
        );
        this.$cookie.set(message.name, message.value, 356);
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
    }
  },
  actions: {
    sendMessage: function (context, message) {
      Vue.prototype.$socket.send(message);
    }
  }
});