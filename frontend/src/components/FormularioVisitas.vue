<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { useToast } from "vue-toast-notification"; // Importamos el toast

const route = useRoute();
const router = useRouter();
const toast = useToast(); // Inicializamos el toast

const visita = ref({
  clienteId: route.query.clienteId || "", // Cambiamos clienteNombre a clienteId
  fecha: route.query.fecha || "",
  hora: route.query.hora || "",
  notas: route.query.notas || "",
  duracion: route.query.duracion || "",
  tipoVisita: route.query.tipoVisita || "",
});
const clientes = ref([]);
const clienteNombre = ref(route.query.clienteNombre || ""); // Añadimos una variable para el nombre del cliente

const fetchClientes = async () => {
  try {
    const response = await axios.get("http://localhost:8080/api/clientes");
    clientes.value = response.data;
  } catch (error) {
    console.error("Error fetching clientes:", error);
  }
};

const fetchVisita = async () => {
  if (route.params.id) {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/visitas/${route.params.id}`
      );
      visita.value = response.data;
      visita.value.clienteId = visita.value.cliente.id; // Asegurarse de que el clienteId esté establecido
      clienteNombre.value = visita.value.cliente.nombre; // Establecer el nombre del cliente
      visita.value.hora = visita.value.hora.slice(0, 5); // Asegurarse de que la hora esté en formato HH:mm
    } catch (error) {
      console.error("Error fetching visita:", error);
    }
  }
};

const saveVisita = async () => {
  try {
    const visitaData = {
      ...visita.value,
      cliente: { id: visita.value.clienteId }, // Enviamos el ID del cliente
    };
    const cliente = clientes.value.find((c) => c.id === visita.value.clienteId);
    clienteNombre.value = cliente ? cliente.nombre : ""; // Establecer el nombre del cliente

    if (route.params.id) {
      await axios.put(
        `http://localhost:8080/api/visitas/${route.params.id}`,
        visitaData
      );
      toast.success(
        `Visita actualizada correctamente para ${clienteNombre.value}`
      );
    } else {
      await axios.post("http://localhost:8080/api/visitas", visitaData);
      toast.success(`Visita creada correctamente para ${clienteNombre.value}`);
    }
    router.go(-1); // Redirigir a la ruta anterior
  } catch (error) {
    console.error("Error saving visita:", error);
    toast.error("Error al guardar la visita");
  }
};

const cancelar = () => {
  router.back();
};

onMounted(() => {
  fetchClientes();
  fetchVisita();
});
</script>

<template>
  <div class="form-wrapper">
    <div class="form-container">
      <h2>{{ route.params.id ? "Editar Visita" : "Crear Visita" }}</h2>
      <form @submit.prevent="saveVisita">
        <div class="form-group">
          <label for="cliente">Cliente</label>
          <select
            v-model="visita.clienteId"
            :disabled="!!route.query.clienteId || !!route.params.id"
            required
          >
            <option
              v-for="cliente in clientes"
              :key="cliente.id"
              :value="cliente.id"
            >
              {{ cliente.nombre }}
            </option>
          </select>
          <p v-if="route.query.clienteNombre || clienteNombre">
            Cliente: {{ route.query.clienteNombre || clienteNombre }}
          </p>
        </div>
        <div class="form-group">
          <label for="fecha">Fecha</label>
          <input type="date" v-model="visita.fecha" required />
        </div>
        <div class="form-group">
          <label for="hora">Hora</label>
          <input type="time" v-model="visita.hora" required />
        </div>
        <div class="form-group">
          <label for="notas">Notas</label>
          <textarea v-model="visita.notas"></textarea>
        </div>
        <div class="form-group">
          <label for="duracion">Duración (minutos)</label>
          <input type="number" v-model="visita.duracion" required />
        </div>
        <div class="form-group">
          <label for="tipoVisita">Tipo de Visita</label>
          <select v-model="visita.tipoVisita" required>
            <option value="CONSULTA">Consulta</option>
            <option value="REVISION">Revisión</option>
            <option value="SEGUIMIENTO">Seguimiento</option>
            <option value="OTRO">Otro</option>
          </select>
        </div>
        <div class="form-actions">
          <button type="submit">
            {{ route.params.id ? "Actualizar" : "Guardar" }}
          </button>
          <button type="button" @click="cancelar">Cancelar</button>
        </div>
      </form>
    </div>
  </div>
</template>

<style scoped>
.form-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  height: calc(100vh - 80px);
}

.form-container {
  max-width: 500px; /* Aumentamos el ancho */
  padding: 40px; /* Aumentamos el padding */
  background-color: #f9f9f9;
  border: 1px solid #ddd;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.form-group {
  margin-bottom: 15px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input,
select,
textarea {
  width: 100%;
  padding: 12px; /* Aumentamos el padding */
  box-sizing: border-box;
  border-radius: 20px; /* Hacemos los inputs circulares */
}

input[type="number"] {
  text-align: right; /* Alineamos los números a la derecha */
}

.form-actions {
  display: flex;
  justify-content: space-between;
}

button {
  padding: 10px 15px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 20px; /* Hacemos los botones circulares */
  cursor: pointer;
}

button:hover {
  background-color: #0056b3;
}

button[type="button"] {
  background-color: #6c757d;
}

button[type="button"]:hover {
  background-color: #5a6268;
}
</style>
