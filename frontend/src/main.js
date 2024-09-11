import "vue3-toastify/dist/index.css";

import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import ToastPlugin from "vue-toast-notification";
import "vue-toast-notification/dist/theme-bootstrap.css";

const app = createApp(App);

app.use(ToastPlugin);
app.use(router);
app.mount("#app");
