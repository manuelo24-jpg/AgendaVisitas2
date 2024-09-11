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

const confirmarEliminacion = (cliente) => {
  clienteAEliminar.value = cliente;
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
  width: 50%; /* Un poco más de la mitad de la pantalla */
  max-height: 600px; /* Altura máxima del contenedor */
  overflow-y: auto; /* Hacer el contenedor scrolleable */
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

table {
  width: 100%;
  border-collapse: collapse;
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
  cursor: not-allowed;
}

button:hover {
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
