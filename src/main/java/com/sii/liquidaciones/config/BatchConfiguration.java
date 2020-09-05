package com.sii.liquidaciones.config;

import com.sii.liquidaciones.model.Cliente;
import com.sii.liquidaciones.model.FacturasConsolidadaModel;
import com.sii.liquidaciones.model.FacturasEntradaModel;
import com.sii.liquidaciones.processor.FacturaProcessor;
import com.sii.liquidaciones.processor.ProcessorCliente;
import com.sii.liquidaciones.writer.WriterFact;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public DataSource dataSource;

    @Bean
    public DataSource dataSource(){

        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/batch_ejemplo");
        dataSource.setUsername("root");
        dataSource.setPassword("Carora1990");

        return dataSource;
    }

    @Bean
    public JdbcCursorItemReader<FacturasEntradaModel> reader1(){
        JdbcCursorItemReader<FacturasEntradaModel> reader1 = new JdbcCursorItemReader<FacturasEntradaModel>();
        reader1.setDataSource(dataSource);
        reader1.setSql("SELECT a.nombre, a.apellido, a.rut, b.fecha, b.monto_total, b.fact_desde, b.fact_hasta FROM batch_ejemplo.clientes a, batch_ejemplo.facturas b\n" +
                "where a.idcliente = b.FK_id_cliente");
        reader1.setRowMapper(new FacturasRowMapper());
        return reader1;
    }

    public class FacturasRowMapper implements RowMapper<FacturasEntradaModel>{

        @Override
        public FacturasEntradaModel mapRow(ResultSet rs, int i) throws SQLException {
            FacturasEntradaModel facturas = new FacturasEntradaModel();

            facturas.setNombre(rs.getString("nombre"));
            facturas.setApellido(rs.getString("apellido"));
            facturas.setRut(rs.getString("rut"));
            facturas.setFecha(rs.getDate("fecha"));
            facturas.setFactDesde(rs.getInt("fact_desde"));
            facturas.setFactHasta(rs.getInt("fact_hasta"));
            facturas.setMonto(rs.getInt("monto_total"));

            return facturas;
        }
    }

    @Bean
    public JdbcCursorItemReader<Cliente> reader2(){
        JdbcCursorItemReader<Cliente> reader2 = new JdbcCursorItemReader<Cliente>();
        reader2.setDataSource(dataSource);
        reader2.setSql("SELECT nombre, apellido, rut, edad, sexo, direccion FROM batch_ejemplo.usuario");
        reader2.setRowMapper(new ClienteRowMapper());

        return  reader2;
    }

    public class ClienteRowMapper implements RowMapper<Cliente>{

        @Override
        public Cliente mapRow(ResultSet rs, int i) throws SQLException {

            Cliente cliente = new Cliente();

            cliente.setNombre(rs.getString("nombre"));
            cliente.setApellido(rs.getString("apellido"));
            cliente.setRut(rs.getString("rut"));
            cliente.setDireccion(rs.getString("direccion"));
            cliente.setEdad(rs.getInt("edad"));
            cliente.setSexo(rs.getString("sexo"));

            return cliente;
        }
    }

    @Bean
    public FacturaProcessor processor1(){
        return new FacturaProcessor();
    }

    @Bean
    public ProcessorCliente processor2() {
        return new ProcessorCliente();
    }

   @Bean
    public WriterFact write1(){
        return new WriterFact();
    }

    @Bean
    public JdbcBatchItemWriter<Cliente> writer2(DataSource dataSource){
        return new JdbcBatchItemWriterBuilder<Cliente>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("INSERT INTO batch_ejemplo.usuarios_nuevos (nombre, apellido, rut, edad, sexo, direccion) values (:nombre, :apellido, :rut, :edad, :sexo, :direccion)")
                .build();
    }

    @Bean
    public Step step1(JdbcCursorItemReader<FacturasEntradaModel> reader1, WriterFact write1, FacturaProcessor processor1){
        return stepBuilderFactory.get("step1").<FacturasEntradaModel, List<FacturasConsolidadaModel>> chunk(1100)
                .reader(reader1)
                .processor(processor1)
                .writer(write1)
                .build();
    }

    @Bean
    public Step step2(JdbcBatchItemWriter<Cliente> writer2, JdbcCursorItemReader<Cliente> reader2, ProcessorCliente processor2){
        return stepBuilderFactory.get("step2").<Cliente, Cliente> chunk(1100)
                .reader(reader2)
                .processor(processor2)
                .writer(writer2)
                .build();
    }

    @Bean
    public Job insercionFacturasJob(Step step1, Step step2){
        return jobBuilderFactory.get("insercionFacturasJob")
                .incrementer(new RunIdIncrementer())
                .start(step1)
                .next(step2)
                .build();
    }
}
