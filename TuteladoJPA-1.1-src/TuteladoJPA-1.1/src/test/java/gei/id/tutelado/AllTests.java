package gei.id.tutelado;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ T01_Personas.class, T02_Empleados.class, T03_Residentes.class, T04_Habitacion.class, T05_Consultas_Comprobaciones.class } )
public class AllTests {

}
