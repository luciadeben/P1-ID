package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;

public interface PersonaDao {
    
	void setup (Configuracion config);
	
	// OPERACIONS CRUD BASICAS
	Persona recuperaPorNif (String nif);

	//QUERIES ADICIONAIS
	List<Persona> recuperaTodos();	
}