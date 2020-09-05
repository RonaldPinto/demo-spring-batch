package com.sii.liquidaciones.processor;

import com.sii.liquidaciones.model.Cliente;
import org.springframework.batch.item.ItemProcessor;

public class ProcessorCliente implements ItemProcessor<Cliente, Cliente> {

    @Override
    public Cliente process(Cliente cliente) throws Exception {
        Cliente cl = new Cliente(cliente.getNombre().toUpperCase(), cliente.getApellido().toUpperCase(), cliente.getRut(), cliente.getEdad(), cliente.getSexo().toUpperCase(), cliente.getDireccion());
        return cl;
    }
}
