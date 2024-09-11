<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useToast } from "vue-toast-notification";

const clientes = ref([]);
const clienteAEliminar = ref(null);
const router = useRouter();
const toast = useToast();

const fetchClientes = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/clientes");
    clientes.value = response.data;
  } catch (error) {
    console.error("Error fetching clientes:", error);
  }
};

const verDetalles = (id) => {
  router.push(`/clientes/detalles/${id}`);
};

const editarCliente = (id) => {
  router.push(`/clientes/editar/${id}`);
};

const crearCliente = () => {
  router.push("/clientes/crear");
};

const confirmarEliminacion = (cliente) => {
  if (!cliente.visitas || cliente.visitas.length === 0) {
    clienteAEliminar.value = cliente;
  }
};

const eliminarCliente = async () => {
  try {
    await axios.delete(
      `http://localhost:8080/api/clientes/${clienteAEliminar.value.id}`
    );
    toast.success(
      `Cliente ${clienteAEliminar.value.nombre} eliminado con éxito`
    );
    clienteAEliminar.value = null;
    fetchClientes(); // Recargar la lista de clientes
  } catch (error) {
    console.error("Error eliminando cliente:", error);
  }
};

const cancelarEliminacion = () => {
  clienteAEliminar.value = null;
  location.reload();
  fetchClientes(); // Recargar la lista de clientes
};

onMounted(() => {
  fetchClientes();
});
</script>

<template>
  <div class="container">
    <button class="btn-crear" @click="crearCliente">Crear</button>
    <table class="clientes-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Dirección</th>
          <th>Teléfono</th>
          <th>Email</th>
          <th>Acciones</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cliente in clientes" :key="cliente.id">
          <td>{{ cliente.nombre }}</td>
          <td>{{ cliente.direccion }}</td>
          <td>{{ cliente.telefono }}</td>
          <td>{{ cliente.email }}</td>
          <td>
            <button @click="editarCliente(cliente.id)">Editar</button>
            <button @click="verDetalles(cliente.id)">Ver Detalles</button>
            <button
              @click="confirmarEliminacion(cliente)"
              :disabled="cliente.visitas && cliente.visitas.length > 0"
              :class="{
                disabled: cliente.visitas && cliente.visitas.length > 0,
              }"
            >
              Eliminar
            </button>
          </td>
        </tr>
      </tbody>
    </table>

    <!-- Modal de confirmación -->
    <div v-if="clienteAEliminar" class="modal">
      <div class="modal-content">
        <p>
          ¿Estás seguro de que deseas eliminar al cliente
          {{ clienteAEliminar.nombre }}?
        </p>
        <button @click="eliminarCliente">Confirmar</button>
        <button @click="cancelarEliminacion">Cancelar</button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  padding: 20px;
}

.btn-crear {
  margin-bottom: 20px;
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-crear:hover {
  background-color: #0056b3;
}

.clientes-table {
  width: 80%;
  border-collapse: collapse;
  margin: 0 auto;
}

th,
td {
  padding: 12px;
  border: 1px solid #ddd;
  text-align: left;
}

th {
  background-color: #f4f4f4;
}

tr:nth-child(even) {
  background-color: #f9f9f9;
}

button {
  margin-right: 5px;
  padding: 5px 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

button.disabled {
  opacity: 0.5;
  background-color: #ccc;
  cursor: not-allowed;
}

button:hover:not(.disabled) {
  background-color: #0056b3;
}

.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}
</style>
