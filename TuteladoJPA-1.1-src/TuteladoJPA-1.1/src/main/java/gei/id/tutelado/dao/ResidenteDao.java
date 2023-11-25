package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Habitacion;
import gei.id.tutelado.model.Residente;

public interface ResidenteDao extends PersonaDao{
    
    void setupRes (Configuracion config);

    //QUERIES ADICIONAIS
    List<Residente> recuperaResidentes();
    List<Residente> recuperaPorHabitacion(Habitacion habitacion);
    Residente recuperaConHabitacion(Residente residente);
}

