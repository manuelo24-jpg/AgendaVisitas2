<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";
import { useToast } from "vue-toast-notification";

const visitas = ref([]);
const visitaAEliminar = ref(null);
const selectedVisita = ref(null);
const router = useRouter();
const toast = useToast();

const fetchVisitas = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/visitas");
    visitas.value = response.data;
  } catch (error) {
    console.error("Error fetching visitas:", error);
  }
};

const formatFecha = (fecha) => {
  return new Date(fecha).toLocaleDateString("es-ES", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

const verDetalles = (visita) => {
  selectedVisita.value = visita;
};

const cerrarModal = () => {
  selectedVisita.value = null;
};

const editarVisita = (id) => {
  router.push({
    path: `/visitas/editar/${id}`,
    query: {
      allowEditCliente: true, // Permitir editar el cliente
    },
  });
};

const crearVisita = () => {
  router.push("/visitas/crear");
};

const confirmarEliminacion = (visita) => {
  visitaAEliminar.value = visita;
};

const eliminarVisita = async () => {
  try {
    await axios.delete(
      `http://localhost:8080/api/visitas/${visitaAEliminar.value.id}`
    );
    toast.success(`Visita eliminada con éxito`);
    visitaAEliminar.value = null;
    fetchVisitas(); // Recargar la lista de visitas
  } catch (error) {
    console.error("Error eliminando visita:", error);
    toast.error("Error al eliminar la visita");
  }
};

const cancelarEliminacion = () => {
  visitaAEliminar.value = null;
};

onMounted(() => {
  fetchVisitas();
});
</script>

<template>
  <div class="container">
    <button class="btn btn-create" @click="crearVisita" title="Crear visita">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 24 24"
        width="24"
        height="24"
        color="#000000"
        fill="none"
      >
        <path
          d="M14.0022 4.5C14.3777 4.16667 14.6984 4 15.0682 4C15.7664 4 16.2898 4.59409 17.3368 5.78228L19.9115 8.70448C21.3038 10.2847 22 11.0747 22 12C22 12.9253 21.3038 13.7153 19.9115 15.2955L17.3368 18.2177C16.2898 19.4059 15.7664 20 15.0682 20C14.6984 20 14.3777 19.8333 14.0022 19.5"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
        />
        <path
          d="M7 5.4398C7.86196 4.47993 8.35392 4 8.98862 4C9.69478 4 10.2243 4.59409 11.2832 5.78228L13.8875 8.70448C15.2959 10.2847 16 11.0747 16 12C16 12.9253 15.2959 13.7153 13.8876 15.2955L11.2832 18.2177C10.2243 19.4059 9.69478 20 8.98862 20C8.35392 20 7.86196 19.5201 7 18.5602"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
        />
        <path
          d="M5.5 15.5L5.5 8.5M2 12H9"
          stroke="currentColor"
          stroke-width="1.5"
          stroke-linecap="round"
        />
      </svg>
    </button>
    <div class="visitas-container">
      <div v-for="visita in visitas" :key="visita.id" class="visita-card">
        <h2>{{ visita.clienteNombre }}</h2>
        <p>Fecha : {{ formatFecha(visita.fecha) }} {{ visita.hora }}</p>
        <div class="actions">
          <button
            class="btn btn-details"
            @click="verDetalles(visita)"
            title="Ver datos de la visita"
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
            class="btn btn-edit"
            @click="editarVisita(visita.id)"
            title="Editar visita"
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
            class="btn btn-delete"
            @click="confirmarEliminacion(visita)"
            title="Eliminar visita"
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
        </div>
      </div>
    </div>

    <div v-if="selectedVisita" class="modal">
      <div class="modal-content">
        <button class="close" @click="cerrarModal">&times;</button>
        <h2>{{ selectedVisita.clienteNombre }}</h2>
        <p>Fecha: {{ formatFecha(selectedVisita.fecha) }}</p>
        <p>Hora: {{ selectedVisita.hora }}</p>
        <p>Notas: {{ selectedVisita.notas }}</p>
        <p>Duración: {{ selectedVisita.duracion }} minutos</p>
        <p>Tipo de Visita: {{ selectedVisita.tipoVisita }}</p>
      </div>
    </div>

    <!-- Modal de confirmación de eliminación -->
    <div v-if="visitaAEliminar" class="modal-eliminacion">
      <div class="modal-content-eliminacion">
        <p>
          ¿Estás seguro de que deseas eliminar la visita del cliente
          {{ visitaAEliminar.clienteNombre }}?
        </p>
        <div class="modal-actions">
          <button class="btn btn-confirm" @click="eliminarVisita">
            Confirmar
          </button>
          <button class="btn btn-cancel" @click="cancelarEliminacion">
            Cancelar
          </button>
        </div>
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
  margin-top: 10px;
}

.visitas-container {
  display: flex;
  flex-wrap: wrap;
  gap: 16px;
  justify-content: center;
  margin-top: 10px;
}

.visita-card {
  background-color: #f0f0f0; /* Color amarillo fuerte */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 25px;
  width: 300px; /* Tamaño más grande */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.actions {
  margin-top: 16px;
  display: flex;
  justify-content: space-around;
}

.btn {
  padding: 8px 12px; /* Tamaño más pequeño */
  border: none;
  border-radius: 20px;
  cursor: pointer;
  width: 70px; /* Tamaño fijo */
  color: white;
  margin: 0 5px; /* Espacio entre botones */
}

.btn-create {
  background-color: #28a745;
}

.btn-create:hover {
  background-color: #218838;
}

.btn-details {
  background-color: #8bc34a; /* Verde suave */
}

.btn-details:hover {
  background-color: #7cb342;
}

.btn-edit {
  background-color: #ff9800; /* Naranja suave */
}

.btn-edit:hover {
  background-color: #fb8c00;
}

.btn-delete {
  background-color: #f44336; /* Rojo suave */
}

.btn-delete:hover {
  background-color: #e53935;
}
.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: #ffeb3b; /* Color amarillo fuerte */
  padding: 20px;
  border-radius: 8px;
  width: 300px;
  text-align: center;
  position: relative;
}

.close {
  position: absolute;
  top: 10px;
  right: 10px;
  font-size: 24px;
  background-color: red;
  color: white;
  border: none;
  border-radius: 50%;
  width: 30px;
  height: 30px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

/* Estilos para el modal de confirmación de eliminación */
.modal-eliminacion {
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

.modal-content-eliminacion {
  background: white;
  padding: 20px;
  border-radius: 5px;
  text-align: center;
}

.modal-actions {
  display: flex;
  justify-content: space-around;
  margin-top: 20px;
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
</style>
