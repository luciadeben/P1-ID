package gei.id.tutelado.dao;

import java.util.List;

import gei.id.tutelado.model.Empleado;

public interface EmpleadoDao extends PersonaDao{
    
	//QUERIES ADICIONAIS
	List<Empleado> recuperaEmpleados();
}

