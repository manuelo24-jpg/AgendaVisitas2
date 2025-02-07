import { createRouter, createWebHistory } from "vue-router";
import Home from "@/components/Home.vue";
import ListadoClientes from "@/components/ListadoClientes.vue";
import ListadoVisitas from "@/components/ListadoVisitas.vue";
import FormularioClientes from "@/components/FormularioClientes.vue"; // Importamos el componente del formulario
import DetallesCliente from "@/components/DetallesCliente.vue";
import FormularioVisitas from "@/components/FormularioVisitas.vue"; // Importamos el componente del formulario de visitas

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
    {
      path: "/clientes/detalles/:id",
      name: "DetallesCliente",
      component: DetallesCliente,
      props: true,
    },
    {
      path: "/visitas/crear",
      name: "CrearVisita",
      component: FormularioVisitas,
    },
    {
      path: "/visitas/editar/:id",
      name: "EditarVisita",
      component: FormularioVisitas,
      props: true,
    },
  ],
});

export default router;
