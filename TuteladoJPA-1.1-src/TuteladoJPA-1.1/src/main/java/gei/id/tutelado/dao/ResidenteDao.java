package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Residente;

public interface ResidenteDao {

    void setup (Configuracion config);

    // OPERACIONS CRUD BASICAS
    Residente almacena (Residente residente);
    Residente modifica (Residente residente);
    void elimina (Residente residente);
    Residente recuperaPorNif (String nif);

    //QUERIES ADICIONAIS
    List<Residente> recuperaTodos();
}

