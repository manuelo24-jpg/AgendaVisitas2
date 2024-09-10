<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const clientes = ref([]);
const router = useRouter();

const fetchClientes = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/clientes");
    clientes.value = response.data;
  } catch (error) {
    console.error("Error fetching clientes:", error);
  }
};

const verDetalles = (id) => {
  router.push(`/clientes/${id}`);
};

const editarCliente = (id) => {
  router.push(`/clientes/editar/${id}`);
};

const crearCliente = () => {
  router.push("/clientes/crear");
};

onMounted(() => {
  fetchClientes();
});
</script>

<template>
  <div>
    <h1>Listado de Clientes</h1>
    <button @click="crearCliente">Crear Cliente</button>
    <table>
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Dirección</th>
          <th>Teléfono</th>
          <th>Email</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cliente in clientes" :key="cliente.id">
          <td>{{ cliente.id }}</td>
          <td>{{ cliente.nombre }}</td>
          <td>{{ cliente.direccion }}</td>
          <td>{{ cliente.telefono }}</td>
          <td>{{ cliente.email }}</td>
          <td>
            <button @click="verDetalles(cliente.id)">Ver Detalles</button>
            <button @click="editarCliente(cliente.id)">Editar</button>
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
table {
  width: 100%;
  border-collapse: collapse;
}

th,
td {
  border: 1px solid #ddd;
  padding: 8px;
}

th {
  background-color: #f4f4f4;
  text-align: left;
}

button {
  margin-right: 5px;
}
</style>
