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
              <button class="modal-default-button" @click="requestLogin">Login</button>
              <button class="modal-default-button" @click="close">Cancel</button>
            </slot>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<script>
import loginRequest from "../messages/login.json";
import store from '../store/store.js';

export default {
  data() {
    return {
      username: null,
      password: null
    };
  },
  methods: {
    close(){
      store.commit('updateModalState', {name:'showLogin', newState:false});
    },

    requestLogin() {
      console.log("attempting login");
      loginRequest.body.username = this.username;
      loginRequest.body.password = this.password;
      this.$socket.sendObj(loginRequest);
    }
    
  }
  
};
</script>
