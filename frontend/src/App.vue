<script></script>

<template>
  <div class="container">
    <header>
      <nav>
        <div class="divBandera">
          <img src="../public/bandera.png" alt="bandera" class="bandera" />
        </div>
        <div class="rutas">
          <RouterLink to="/" class="nav-link">Inicio</RouterLink>
          <RouterLink to="/lista-clientes" class="nav-link"
            >Clientes</RouterLink
          >
          <RouterLink to="/lista-visitas" class="nav-link">Visitas</RouterLink>
        </div>
        <div>
          <img src="../public/Logo2.png" alt="logo" class="logo" />
        </div>
      </nav>
    </header>

    <main class="main-content">
      <router-view />
    </main>
  </div>
</template>

<style scoped>
html,
body,
#app,
.container {
  height: 100%;
  margin: 0;
}

.container {
  display: flex;
  flex-direction: column;
  height: 100%;
}

header {
  margin: 0;
  background-color: #f4f4f4; /* Fondo gris */
  color: white;
  padding: 8px;
}

nav {
  display: flex;
  justify-content: space-between;
  gap: 50px;
  align-items: center;
  height: 80px;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #f4f4f4;
  z-index: 1000;
}

.main-content {
  flex: 1;
  margin-top: 65px;
  padding: 0;
  overflow-y: auto;
  box-sizing: border-box;
}

.rutas {
  display: flex;
  gap: 24px;
}

.router-link-active {
  font-weight: bold;
  text-decoration: underline;
  font-size: 26px;
}

.nav-link {
  color: black; /* Enlaces en negro */
  text-decoration: none;
  font-size: 20px;
  padding: 5px;
}

.nav-link:hover {
  font-size: 24px;
  color: blueviolet;
  text-decoration: underline;
}

@media (max-width: 768px) {
  nav {
    flex-direction: column;
    gap: 20px;
    height: auto;
    padding: 10px;
    position: relative;
  }

  .main-content {
    margin-top: 100px;
  }

  .bandera,
  .logo {
    display: none;
  }
}
</style>
