<template>
  <transition name="modal">
    <div class="modal-mask">
      <div class="modal-wrapper">
        <div class="modal-container">
          <div class="modal-header">
            <slot name="header">
              <h3>Login</h3>
            </slot>
          </div>

          <form>
            <div class="modal-body">
              <slot name="body">
                <input v-model="username" placeholder="Username" />
                <br />
                <input v-model="password" placeholder="Password" type="password" />
              </slot>
            </div>
          </form>

          <div class="modal-footer">
            <slot name="footer">
              <button class="modal-default-button" @click="login">Login</button>
              <button class="modal-default-button" @click="$emit('close')">Cancel</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import loginRequest from "../messages/login.json";

export default {
  data() {
    return {
      username: null,
      password: null
    };
  },
  beforeMount() {
    this.$socket.onmessage = message => this.handleMessage(message);
  },
  methods: {
    handleMessage(message) {
        console.log("LOGIN_MODAL - MESSAGE RECEIVED : " + message);
      var messageBody = JSON.parse(message.data);
      if (messageBody.body.messageType === "ADD_COOKIE") {
        console.log(
          "Setting cookie: " +
            messageBody.body.name +
            " with value: " +
            messageBody.body.value
        );
        this.$cookie.set(messageBody.body.name, messageBody.body.value, 356);
      }
    },
    login() {
      console.log("attempting login");
      loginRequest.body.username = this.username;
      loginRequest.body.password = this.password;
      this.$socket.sendObj(loginRequest);
    }
  }
};
</script>
