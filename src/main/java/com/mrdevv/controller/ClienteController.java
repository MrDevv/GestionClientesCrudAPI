package com.mrdevv.controller;

import com.mrdevv.model.dto.ClienteDto;
import com.mrdevv.model.entity.Cliente;
import com.mrdevv.model.payload.MensajeResponse;
import com.mrdevv.service.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ClienteController {

    @GetMapping("clientes")
    public ResponseEntity<?> listClients() {
        List<Cliente> listClients = clienteService.listClients();
        if (listClients.isEmpty()) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("No hay registros")
                            .object(null).build(), HttpStatus.OK);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(listClients)
                        .build()
                , HttpStatus.OK);
    }

    @Autowired
    private IClienteService clienteService;

    @PostMapping("cliente")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody ClienteDto clienteDto) {
        Cliente clienteSave = null;
        try {
            clienteSave = clienteService.save(clienteDto);
            clienteDto = ClienteDto.builder()
                    .idCliente(clienteSave.getIdCliente())
                    .nombre(clienteSave.getNombre())
                    .apellido(clienteSave.getApellido())
                    .correo(clienteSave.getCorreo())
                    .fechaRegistro(clienteSave.getFechaRegistro())
                    .build();

            return new ResponseEntity<>(MensajeResponse.builder().mensaje("Guardo correctamente").object(clienteDto).build(), HttpStatus.CREATED);
        } catch (DataAccessException exception) {
            return new ResponseEntity<>(MensajeResponse.builder().mensaje(exception.getMessage()).object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @PutMapping("cliente/{id}")
    public ResponseEntity<?> update(@RequestBody ClienteDto clienteDto, @PathVariable Integer id) {
        Cliente clienteUpdate = null;

        try {
            if (clienteService.existsById(id)){
                clienteDto.setIdCliente(id);
                clienteUpdate = clienteService.save(clienteDto);
                clienteDto = ClienteDto.builder()
                        .idCliente(clienteUpdate.getIdCliente())
                        .nombre(clienteUpdate.getNombre())
                        .apellido(clienteUpdate.getApellido())
                        .correo(clienteUpdate.getCorreo())
                        .fechaRegistro(clienteUpdate.getFechaRegistro())
                        .build();

                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Actualizado correctamente").object(clienteDto).build(), HttpStatus.CREATED);
            }else {
                return new ResponseEntity<>(
                        MensajeResponse.builder()
                                .mensaje("El registro que intenta actualizar no se encuentra registrado en la base de datos")
                                .object(null).build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exception.getMessage())
                            .object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @DeleteMapping("cliente/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        try {
            Cliente clienteDelete = clienteService.findById(id);
            if (clienteDelete != null){
                clienteService.delete(clienteDelete);
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("Se eliminó correctamente").object(clienteDelete).build(), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(MensajeResponse.builder().mensaje("No se encontró el registro en la base de datos").object(clienteDelete).build(), HttpStatus.NOT_FOUND);
            }
        } catch (DataAccessException exception) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje(exception.getMessage())
                            .object(null).build(), HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    @GetMapping("cliente/{id}")
    public ResponseEntity<?> showById(@PathVariable Integer id) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            return new ResponseEntity<>(
                    MensajeResponse.builder()
                            .mensaje("El registro que intenta buscar no existe")
                            .object(null).build(), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(
                MensajeResponse.builder()
                        .mensaje("")
                        .object(ClienteDto.builder()
                                .idCliente(cliente.getIdCliente())
                                .nombre(cliente.getNombre())
                                .apellido(cliente.getApellido())
                                .correo(cliente.getCorreo())
                                .fechaRegistro(cliente.getFechaRegistro())
                                .build()).build(), HttpStatus.CREATED);
    }
}
