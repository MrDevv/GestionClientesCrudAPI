package com.mrdevv.model.dto;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
public class ClienteDto implements Serializable {
    private Integer idCliente;
    private String nombre;
    private String apellido;
    private String correo;
    private Date fechaRegistro;
}
