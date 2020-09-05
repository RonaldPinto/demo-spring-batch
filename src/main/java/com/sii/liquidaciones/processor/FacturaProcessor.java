package com.sii.liquidaciones.processor;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.sii.liquidaciones.model.FacturasConsolidadaModel;
import com.sii.liquidaciones.model.FacturasEntradaModel;
import org.springframework.batch.item.ItemProcessor;

import java.util.ArrayList;
import java.util.List;

public class FacturaProcessor implements ItemProcessor<FacturasEntradaModel, List<FacturasConsolidadaModel>> {

    @Override
    public List<FacturasConsolidadaModel> process(FacturasEntradaModel item) throws Exception {

        List<FacturasConsolidadaModel> facturasConsolidadaModel = new ArrayList<FacturasConsolidadaModel>();

        if(item.getFactDesde() != item.getFactHasta()){
            for (int i = item.getFactDesde(); i <= item.getFactHasta(); i++){
                FacturasConsolidadaModel factura = new FacturasConsolidadaModel();
                factura.setNombre(item.getNombre());
                factura.setApellido(item.getApellido());
                factura.setRut(item.getRut());
                factura.setFecha(item.getFecha());
                factura.setMonto(item.getMonto());
                factura.setNumFactura(i);

                facturasConsolidadaModel.add(factura);
            }

        }else{
            FacturasConsolidadaModel factura = new FacturasConsolidadaModel();
            factura.setNombre(item.getNombre());
            factura.setApellido(item.getApellido());
            factura.setRut(item.getRut());
            factura.setFecha(item.getFecha());
            factura.setMonto(item.getMonto());
            factura.setNumFactura(item.getFactDesde());

            facturasConsolidadaModel.add(factura);
        }

        System.out.println(facturasConsolidadaModel);
        return facturasConsolidadaModel;
    }
}
