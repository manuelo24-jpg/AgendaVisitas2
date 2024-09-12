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
  router.push(`/visitas/editar/${id}`);
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
    <button @click="crearVisita">Crear</button>
    <div class="visitas-container">
      <div v-for="visita in visitas" :key="visita.id" class="visita-card">
        <h2>{{ visita.clienteNombre }}</h2>
        <p>{{ formatFecha(visita.fecha) }} {{ visita.hora }}</p>
        <div class="actions">
          <button @click="verDetalles(visita)">Ver Detalles</button>
          <button @click="editarVisita(visita.id)">Editar</button>
          <button @click="confirmarEliminacion(visita)">Eliminar</button>
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
        <button @click="eliminarVisita">Confirmar</button>
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
  background-color: #ffeb3b; /* Color amarillo fuerte */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 25px;
  width: 300px; /* Tamaño más grande */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
}

.actions {
  margin-top: 16px;
}

button {
  margin: 4px;
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
</style>
