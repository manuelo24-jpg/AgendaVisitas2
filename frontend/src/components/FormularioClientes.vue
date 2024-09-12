<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import { useRouter, useRoute } from "vue-router";
import { useToast } from "vue-toast-notification";

const cliente = ref({
  nombre: "",
  direccion: "",
  telefono: "",
  email: "",
  fechaRegistro: "",
  notas: "",
  ciudad: "",
  pais: "",
  codigoPostal: "",
  fechaNacimiento: "",
});

const isEditMode = ref(false);
const router = useRouter();
const route = useRoute();
const toast = useToast();

const fetchCliente = async (id) => {
  try {
    const response = await axios.get(
      `http://localhost:8080/api/clientes/${id}`
    );
    cliente.value = response.data;
  } catch (error) {
    console.error("Error fetching cliente:", error);
  }
};

const saveCliente = async () => {
  try {
    if (isEditMode.value) {
      await axios.put(
        `http://localhost:8080/api/clientes/${route.params.id}`,
        cliente.value
      );
      toast.success(`Cliente ${cliente.value.nombre} actualizado con éxito`);
    } else {
      await axios.post("http://localhost:8080/api/clientes", cliente.value);
      toast.success(`Cliente ${cliente.value.nombre} creado con éxito`);
    }
    router.push("/lista-clientes");
  } catch (error) {
    toast.error("Error saving cliente:", error);
  }
};

const cancel = () => {
  router.back();
};

onMounted(() => {
  if (route.params.id) {
    isEditMode.value = true;
    fetchCliente(route.params.id);
  }
});
</script>

<template>
  <div class="container">
    <div class="form-box">
      <h1>{{ isEditMode ? "Editar Cliente" : "Crear Cliente" }}</h1>
      <form @submit.prevent="saveCliente" class="form">
        <div class="form-row">
          <div class="form-group">
            <label for="nombre">Nombre</label>
            <input type="text" id="nombre" v-model="cliente.nombre" required />
          </div>
          <div class="form-group">
            <label for="direccion">Dirección</label>
            <input type="text" id="direccion" v-model="cliente.direccion" />
          </div>
          <div class="form-group">
            <label for="telefono">Teléfono</label>
            <input type="text" id="telefono" v-model="cliente.telefono" />
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="fechaRegistro">Fecha de Registro</label>
            <input
              type="date"
              id="fechaRegistro"
              v-model="cliente.fechaRegistro"
              required
            />
          </div>
          <div class="form-group">
            <label for="fechaNacimiento">Fecha de Nacimiento</label>
            <input
              type="date"
              id="fechaNacimiento"
              v-model="cliente.fechaNacimiento"
            />
          </div>
          <div class="form-group">
            <label for="email">Email</label>
            <input type="email" id="email" v-model="cliente.email" />
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="ciudad">Ciudad</label>
            <input type="text" id="ciudad" v-model="cliente.ciudad" />
          </div>
          <div class="form-group">
            <label for="pais">País</label>
            <input type="text" id="pais" v-model="cliente.pais" />
          </div>
          <div class="form-group">
            <label for="codigoPostal">Código Postal</label>
            <input
              type="text"
              id="codigoPostal"
              v-model="cliente.codigoPostal"
            />
          </div>
        </div>
        <div class="form-row">
          <div class="form-group">
            <label for="notas">Notas</label>
            <textarea id="notas" v-model="cliente.notas"></textarea>
          </div>
        </div>
        <div class="form-actions">
          <button type="submit" class="btn-save">Guardar</button>
          <button type="button" class="btn-cancel" @click="cancel">
            Cancelar
          </button>
        </div>
      </form>
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

.form-box {
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  width: 60%;
  border: 1px solid #ddd;
}

.form {
  display: flex;
  flex-direction: column;
}

.form-row {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.form-group {
  flex: 1;
  margin: 10px;
  padding: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
  color: #333;
}

.form-group input,
.form-group textarea {
  width: 100%;
  padding: 6px;
  border: 1px solid #ddd;
  border-radius: 20px;
  font-size: 14px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* Sombra */
}

.form-group input {
  height: 25px;
}

.form-group input[type="number"] {
  text-align: right; /* Alinear a la derecha los inputs de tipo número */
}

.form-actions {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.btn-save {
  padding: 10px 20px;
  background-color: #28a745;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  margin-right: 10px;
  font-size: 16px;
}

.btn-save:hover {
  background-color: #218838;
}

.btn-cancel {
  padding: 10px 20px;
  background-color: #dc3545;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn-cancel:hover {
  background-color: #c82333;
}
</style>
