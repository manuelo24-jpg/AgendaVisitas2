<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { useToast } from "vue-toast-notification";

const cliente = ref(null);
const selectedVisita = ref(null);
const visitaAEliminar = ref(null);
const route = useRoute();
const router = useRouter();
const toast = useToast();
const isLoading = ref(true); // Estado de carga

const fetchClienteDetalles = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/clientes/${route.params.id}`
    );
    cliente.value = response.data;
    isLoading.value = false; // Datos cargados
    console.log("Detalles del cliente:", cliente.value);
  } catch (error) {
    console.error("Error fetching cliente detalles:", error);
    isLoading.value = false; // Finalizar carga en caso de error
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
      clienteId: cliente.value.id,
      clienteNombre: cliente.value.nombre,
    },
  });
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
    fetchClienteDetalles(); // Recargar los detalles del cliente
  } catch (error) {
    console.error("Error eliminando visita:", error);
    toast.error("Error al eliminar la visita");
  }
};

const cancelarEliminacion = () => {
  visitaAEliminar.value = null;
};

const volverAtras = () => {
  router.back();
};

const crearNuevaVisita = () => {
  router.push({
    path: "/visitas/crear",
    query: {
      clienteId: cliente.value.id,
      clienteNombre: cliente.value.nombre,
    },
  });
};

onMounted(() => {
  fetchClienteDetalles();
});
</script>

<template>
  <div class="container">
    <div v-if="isLoading">Cargando...</div>
    <div v-else>
      <div class="header-buttons">
        <button @click="volverAtras">Volver Atrás</button>
        <button @click="crearNuevaVisita">Crear Nueva Visita</button>
      </div>
      <div class="content">
        <div class="cliente-detalles">
          <div class="cliente-card">
            <h1>Cliente</h1>
            <div v-if="cliente">
              <h2>{{ cliente.nombre }}</h2>
              <p>Email: {{ cliente.email }}</p>
              <p>Teléfono: {{ cliente.telefono }}</p>
              <p>Dirección: {{ cliente.direccion }}</p>
              <p>Ciudad: {{ cliente.ciudad }}</p>
              <p>País: {{ cliente.pais }}</p>
              <p>Código Postal: {{ cliente.codigoPostal }}</p>
              <p>Notas: {{ cliente.notas }}</p>
            </div>
          </div>
        </div>
        <div class="visitas-container">
          <h3>Visitas</h3>
          <div class="visitas-cards">
            <div
              v-for="visita in cliente.visitas"
              :key="visita.id"
              class="visita-card"
            >
              <h2>{{ visita.clienteNombre }}</h2>
              <p>{{ formatFecha(visita.fecha) }} {{ visita.hora }}</p>
              <div class="actions">
                <button @click="verDetalles(visita)">Ver Detalles</button>
                <button @click="editarVisita(visita.id)">Editar</button>
                <button @click="confirmarEliminacion(visita)">Eliminar</button>
              </div>
            </div>
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
  </div>
</template>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 40px; /* Aumentar el padding */
  margin: 0 auto;
  max-width: 1200px; /* Ancho máximo para centrar el contenido */
}

.header-buttons {
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 20px;
}

.header-buttons button {
  padding: 10px 20px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.header-buttons button:hover {
  background-color: #0056b3;
}

.content {
  display: flex;
  width: 100%;
  gap: 20px; /* Añadir espacio entre columnas */
}

.cliente-detalles {
  flex: 0 0 300px; /* Ancho fijo para los detalles del cliente */
}

.cliente-card {
  background-color: #f0f0f0; /* Fondo gris claro */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 30px; /* Aumentar el padding */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: left;
}

.visitas-container {
  flex: 1; /* Ocupa el resto del espacio disponible */
}

.visitas-cards {
  display: flex;
  flex-wrap: wrap;
  gap: 20px; /* Aumentar el espacio entre las cartas */
  justify-content: flex-start;
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
