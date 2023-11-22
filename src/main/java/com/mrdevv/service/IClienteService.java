package com.mrdevv.service;

import com.mrdevv.model.dto.ClienteDto;
import com.mrdevv.model.entity.Cliente;

import java.util.List;

public interface IClienteService {

    List<Cliente> listClients();

    Cliente save(ClienteDto clienteDto);

    Cliente findById(Integer id);

    void delete(Cliente cliente);

    boolean existsById(Integer id);

}
