import { createRouter, createWebHistory } from "vue-router";
import Home from "@/components/Home.vue";
import ListadoClientes from "@/components/ListadoClientes.vue";
import ListadoVisitas from "@/components/ListadoVisitas.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Home,
    },

    {
      path: "/lista-clientes",
      name: "ListaClientes",
      component: ListadoClientes,
    },
    {
      path: "/lista-visitas",
      name: "ListaVisitas",
      component: ListadoVisitas,
    },
  ],
});

export default router;
