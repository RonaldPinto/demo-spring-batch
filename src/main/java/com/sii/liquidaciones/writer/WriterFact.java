package com.sii.liquidaciones.writer;

import com.sii.liquidaciones.model.FacturasConsolidadaModel;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class WriterFact implements ItemWriter<List<FacturasConsolidadaModel>> {

    @Autowired
    public DataSource dataSource;

    @Override
    public void write(List<? extends List<FacturasConsolidadaModel>> lists) throws Exception {

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("factura_consolidada");

        List<FacturasConsolidadaModel> a = lists.stream().flatMap(Collection::stream).collect(Collectors.toList());

        System.out.println(a);

        for(int i = 0; i<a.size(); i++){
            FacturasConsolidadaModel b = new FacturasConsolidadaModel();
            Map<String, Object> parameters = new HashMap<String, Object>();
            b.setNombre(a.get(i).getNombre());
            b.setApellido(a.get(i).getApellido());
            b.setRut(a.get(i).getRut());
            b.setFecha(a.get(i).getFecha());
            b.setNumFactura(a.get(i).getNumFactura());
            b.setMonto(a.get(i).getMonto());
            System.out.println(b);

            parameters.put("nombre", b.getNombre());
            parameters.put("apellido", b.getApellido());
            parameters.put("rut", b.getRut());
            parameters.put("fecha", b.getFecha());
            parameters.put("monto", b.getMonto());
            parameters.put("num_factura", b.getNumFactura());

            simpleJdbcInsert.execute(parameters);


        }





    }

}
