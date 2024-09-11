<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRoute } from "vue-router";

const cliente = ref(null);
const selectedVisita = ref(null);
const route = useRoute();

const fetchClienteDetalles = async () => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/clientes/${route.params.id}`
    );
    cliente.value = response.data;
    console.log("Detalles del cliente:", cliente.value);
  } catch (error) {
    console.error("Error fetching cliente detalles:", error);
  }
};

const formatFecha = (fecha) => {
  return new Date(fecha).toLocaleDateString("es-ES", {
    year: "numeric",
    month: "long",
    day: "numeric",
  });
};

const verDetallesVisita = (visita) => {
  selectedVisita.value = visita;
};

const cerrarModal = () => {
  selectedVisita.value = null;
};

onMounted(() => {
  fetchClienteDetalles();
});
</script>

<template>
  <div class="detalles-container">
    <div class="cliente-detalles">
      <h2>Detalles del Cliente</h2>
      <p><strong>Nombre:</strong> {{ cliente?.nombre }}</p>
      <p><strong>Email:</strong> {{ cliente?.email }}</p>
      <p><strong>Teléfono:</strong> {{ cliente?.telefono }}</p>
      <p><strong>Dirección:</strong> {{ cliente?.direccion }}</p>
      <p><strong>Ciudad:</strong> {{ cliente?.ciudad }}</p>
      <p><strong>País:</strong> {{ cliente?.pais }}</p>
      <p><strong>Código Postal:</strong> {{ cliente?.codigoPostal }}</p>
      <p>
        <strong>Fecha de Registro:</strong>
        {{ formatFecha(cliente?.fechaRegistro) }}
      </p>
      <!-- Agrega más detalles del cliente según sea necesario -->
    </div>
    <div class="visitas-detalles">
      <h2>Visitas</h2>
      <div class="visitas-grid">
        <div
          v-for="visita in cliente?.visitas"
          :key="visita.id"
          class="visita-card"
        >
          <h3>{{ visita.clienteNombre }}</h3>
          <p>
            {{ formatFecha(visita.fecha) }}
            {{ visita.hora }}
          </p>
          <div class="actions">
            <button @click="verDetallesVisita(visita)">Ver Detalles</button>
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
  </div>
</template>

<style scoped>
.detalles-container {
  display: flex;
  justify-content: center;
  padding: 20px;
}

.cliente-detalles {
  width: 30%;
  margin-right: 20px;
  text-align: center;
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
}

.visitas-detalles {
  width: 65%;
}

.visitas-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

.visita-card {
  background-color: #ffeb3b; /* Color amarillo fuerte */
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 25px;
  width: 300px; /* Tamaño más grande */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  text-align: center;
  margin-bottom: 10px;
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
</style>
