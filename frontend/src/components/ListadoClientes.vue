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
    <button class="btn btn-create" title="Crear cliente" @click="crearCliente">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        width="24"
        height="40"
        color="#000000"
        fill="none"
      >
        <path
          d="M12 7.5C12 9.433 10.433 11 8.5 11C6.567 11 5 9.433 5 7.5C5 5.567 6.567 4 8.5 4C10.433 4 12 5.567 12 7.5Z"
          stroke="currentColor"
          stroke-width="1.5"
        />
        <path
          d="M13.5 11C15.433 11 17 9.433 17 7.5C17 5.567 15.433 4 13.5 4"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
        />
        <path
          d="M13.1429 20H3.85714C2.83147 20 2 19.2325 2 18.2857C2 15.9188 4.07868 14 6.64286 14H10.3571C11.4023 14 12.3669 14.3188 13.1429 14.8568"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M19 14V20M22 17L16 17"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
        />
      </svg>
    </button>
    <table class="clientes-table">
      <thead>
        <tr>
          <th>Nombre</th>
          <th>Dirección</th>
          <th>Teléfono</th>
          <th>Email</th>
          <th></th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="cliente in clientes" :key="cliente.id">
          <td>{{ cliente.nombre }}</td>
          <td>{{ cliente.direccion }}</td>
          <td>{{ cliente.telefono }}</td>
          <td>{{ cliente.email }}</td>
          <td>
            <button
              class="btn btn-edit"
              @click="editarCliente(cliente.id)"
              title="Editar cliente"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="24"
                height="24"
                color="#000000"
                fill="none"
              >
                <path
                  d="M7.01428 16.0029H11.0143M7.01428 11.0053H15.0143"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M10.0143 21.9999H11.0143"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M7.51083 21.9999C6.33921 21.9749 5.53921 21.9 4.76421 21.6751C3.71491 21.4002 2.94905 20.4757 2.72421 19.1763C2.43921 17.9519 2.53921 14.9284 2.51416 11.3302C2.51623 8.53874 2.3892 5.85787 2.8392 4.33362C3.1142 3.83386 3.3392 2.00976 7.06421 2.03475C7.68921 2.00976 14.3142 1.95978 15.3642 2.05973C18.8642 2.1347 19.2142 3.93381 19.4142 5.63298C19.541 6.88236 19.5142 8.88138 19.5142 11.0053M7.01421 2.00976C7.31421 3.63396 7.28921 4.68344 8.38921 4.93332C8.86421 5.00828 9.9495 5.00628 11.1142 5.00828C12.1538 5.01007 13.2142 5.01945 13.6892 4.90833C14.8642 4.63347 14.7392 3.18418 15.0392 2.00976"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M18.2786 14.3787C16.9045 15.778 14.2563 18.2768 14.2563 18.4517C14.0427 18.7483 13.8565 19.3512 13.7316 20.2008C13.5747 20.9884 13.3868 21.6751 13.6067 21.875C13.8266 22.0749 14.6545 21.9064 15.5304 21.7251C16.2299 21.6501 16.8795 21.4002 17.2043 21.1504C17.679 20.7304 20.9018 17.4772 21.2766 17.0524C21.5514 16.6776 21.5764 15.9779 21.3365 15.5531C21.2016 15.2533 20.3522 14.4536 20.0774 14.2288C19.5777 13.9289 18.8782 13.8789 18.2786 14.3787Z"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
              </svg>
            </button>
            <button
              class="btn btn-details"
              @click="verDetalles(cliente.id)"
              title="Ver detalles del cliente"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="24"
                height="24"
                color="#000000"
                fill="none"
              >
                <path
                  d="M21.544 11.045C21.848 11.4713 22 11.6845 22 12C22 12.3155 21.848 12.5287 21.544 12.955C20.1779 14.8706 16.6892 19 12 19C7.31078 19 3.8221 14.8706 2.45604 12.955C2.15201 12.5287 2 12.3155 2 12C2 11.6845 2.15201 11.4713 2.45604 11.045C3.8221 9.12944 7.31078 5 12 5C16.6892 5 20.1779 9.12944 21.544 11.045Z"
                  stroke="currentColor"
                  stroke-width="1.5"
                />
                <path
                  d="M15 12C15 10.3431 13.6569 9 12 9C10.3431 9 9 10.3431 9 12C9 13.6569 10.3431 15 12 15C13.6569 15 15 13.6569 15 12Z"
                  stroke="currentColor"
                  stroke-width="1.5"
                />
              </svg>
            </button>
            <button
              class="btn btn-delete"
              @click="confirmarEliminacion(cliente)"
              :disabled="cliente.visitas && cliente.visitas.length > 0"
              :class="{
                disabled: cliente.visitas && cliente.visitas.length > 0,
              }"
              title="Eliminar cliente"
            >
              <svg
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="24"
                height="24"
                color="#000000"
                fill="none"
              >
                <path
                  d="M19.5 5.5L18.8803 15.5251C18.7219 18.0864 18.6428 19.3671 18.0008 20.2879C17.6833 20.7431 17.2747 21.1273 16.8007 21.416C15.8421 22 14.559 22 11.9927 22C9.42312 22 8.1383 22 7.17905 21.4149C6.7048 21.1257 6.296 20.7408 5.97868 20.2848C5.33688 19.3626 5.25945 18.0801 5.10461 15.5152L4.5 5.5"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M3 5.5H21M16.0557 5.5L15.3731 4.09173C14.9196 3.15626 14.6928 2.68852 14.3017 2.39681C14.215 2.3321 14.1231 2.27454 14.027 2.2247C13.5939 2 13.0741 2 12.0345 2C10.9688 2 10.436 2 9.99568 2.23412C9.8981 2.28601 9.80498 2.3459 9.71729 2.41317C9.32164 2.7167 9.10063 3.20155 8.65861 4.17126L8.05292 5.5"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M9.5 16.5L9.5 10.5"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
                <path
                  d="M14.5 16.5L14.5 10.5"
                  stroke="currentColor"
                  stroke-width="1.5"
                  stroke-linecap="round"
                />
              </svg>
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
        <button class="btn btn-confirm" @click="eliminarCliente">
          Confirmar
        </button>
        <button class="btn btn-cancel" @click="cancelarEliminacion">
          Cancelar
        </button>
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

.btn {
  padding: 10px 20px; /* Tamaño uniforme */
  border: none;
  border-radius: 5px;
  cursor: pointer;
  color: white;
  font-size: 14px;
  margin: 0 5px; /* Espacio entre botones */
}

.btn-create {
  background-color: #007bff;
  margin-bottom: 20px;
}

.btn-create:hover {
  background-color: #0056b3;
}

.btn-details {
  background-color: #17a2b8;
}

.btn-details:hover {
  background-color: #138496;
}

.btn-edit {
  background-color: #ffc107;
}

.btn-edit:hover {
  background-color: #e0a800;
}

.btn-delete {
  background-color: #dc3545;
}

.btn-delete:hover {
  background-color: #c82333;
}

.btn-confirm {
  background-color: #28a745;
}

.btn-confirm:hover {
  background-color: #218838;
}

.btn-cancel {
  background-color: #6c757d;
}

.btn-cancel:hover {
  background-color: #5a6268;
}

button.disabled {
  opacity: 0.5;
  background-color: #ccc;
  cursor: not-allowed;
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
