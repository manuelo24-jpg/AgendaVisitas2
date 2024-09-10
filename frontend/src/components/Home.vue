<script setup></script>

<template>
  <div id="body">
    <div class="welcome-message">
      <h1>Bienvenido a la gestión de la universidad de CIC</h1>
      <p>Aqui podrás gestionar los cursos con sus respectivas asignaturas</p>
    </div>
  </div>
</template>

<style scoped>
#body {
  background-image: url("../../public/inicio.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  height: calc(100vh - 80px);
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  color: white;
}

.welcome-message {
  background-color: rgba(0, 0, 0, 0.5);
  padding: 20px;
  border-radius: 10px;
  width: 100vh;
  box-sizing: border-box;
}
</style>
