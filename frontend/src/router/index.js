import { createRouter, createWebHistory } from "vue-router";
import Home from "@/components/Home.vue";
import ListadoClientes from "@/components/ListadoClientes.vue";
import ListadoVisitas from "@/components/ListadoVisitas.vue";
import FormularioClientes from "@/components/FormularioClientes.vue"; // Importamos el componente del formulario

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
    {
      path: "/clientes/crear",
      name: "CrearCliente",
      component: FormularioClientes,
    },
    {
      path: "/clientes/editar/:id",
      name: "EditarCliente",
      component: FormularioClientes,
      props: true,
    },
  ],
});

export default router;