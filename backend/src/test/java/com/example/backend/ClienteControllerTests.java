package com.example.backend;

import com.example.backend.controller.ClienteController;
import com.example.backend.models.Cliente;
import com.example.backend.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Cliente Test");
        cliente.setDireccion("Dirección Test");
        cliente.setTelefono("123456789");
        cliente.setEmail("cliente@test.com");
        cliente.setFechaRegistro(new Date());
        cliente.setNotas("Notas de prueba");
        cliente.setCiudad("Ciudad Test");
        cliente.setPais("País Test");
        cliente.setCodigoPostal("12345");
        cliente.setFechaNacimiento(new Date());
    }

    @Test
    void testGetAllClientes() throws Exception {
        when(clienteService.findAll()).thenReturn(Arrays.asList(cliente));

        mockMvc.perform(get("/api/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Cliente Test"));
    }

    @Test
    void testGetClienteById() throws Exception {
        when(clienteService.findById(anyLong())).thenReturn(Optional.of(cliente));

        mockMvc.perform(get("/api/clientes/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cliente Test"));
    }

    @Test
    void testCreateCliente() throws Exception {
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cliente Test"));
    }

    @Test
    void testUpdateCliente() throws Exception {
        when(clienteService.findById(anyLong())).thenReturn(Optional.of(cliente));
        when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(put("/api/clientes/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Cliente Test"));
    }

    @Test
    void testDeleteCliente() throws Exception {
        when(clienteService.findById(anyLong())).thenReturn(Optional.of(cliente));
        doNothing().when(clienteService).deleteById(anyLong());

        mockMvc.perform(delete("/api/clientes/{id}", 1L))
                .andExpect(status().isNoContent());
    }

}
