package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Habitacion;

public interface HabitacionDao{

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    Habitacion almacena (Habitacion habitacion);
    Habitacion modifica (Habitacion habitacion);
    void elimina (Habitacion habitacion);
    Habitacion recuperaPorNumero (int numero);

    //QUERIES ADICIONAIS
    List<Habitacion> recuperaTodos();
    List<Object[]> recuperaconTotalEmpleados();
    List<Habitacion> recuperaHabitacionYResidente();
    List<Habitacion> recuperaSiResidente();
}

