package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.EmpleadoDao;
import gei.id.tutelado.dao.EmpleadoDaoJPA;
import gei.id.tutelado.model.Empleado;

//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T02_Empleados {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();
    
    private static Configuracion cfg;
    private static EmpleadoDao empDao;
    
    @Rule
    public TestRule watcher = new TestWatcher() {
       protected void starting(Description description) {
    	   log.info("");
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
    	   log.info("Iniciando test: " + description.getMethodName());
    	   log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
       }
       protected void finished(Description description) {
    	   log.info("");
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
    	   log.info("Finalizado test: " + description.getMethodName());
    	   log.info("-----------------------------------------------------------------------------------------------------------------------------------------");
       }
    };
    
    @BeforeClass
    public static void init() throws Exception {
    	cfg = new ConfiguracionJPA();
    	cfg.start();

    	empDao = new EmpleadoDaoJPA();
    	empDao.setup(cfg);
    	
    	produtorDatos = new ProdutorDatosProba();
    	produtorDatos.Setup(cfg);
    }
    
    @AfterClass
    public static void endclose() throws Exception {
    	cfg.endUp();    	
    }
    
	@Before
	public void setUp() throws Exception {		
		log.info("");	
		log.info("Limpando BD --------------------------------------------------------------------------------------------");
		produtorDatos.limpaBD();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
    public void test01_Recuperacion() {
    	
    	Empleado e;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaEmpleados();
    	produtorDatos.gravaEmpleados();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de recuperación desde a BD de empleados por nif\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por nif existente\n"
    			+ "\t\t\t\t b) Recuperacion por nif inexistente\n");

    	// Situación de partida:
    	// u0 desligado    	

    	log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

    	e = (Empleado) empDao.recuperaPorNif(produtorDatos.e0.getNif());
    	Assert.assertEquals(produtorDatos.e0.getNif(),      e.getNif());
    	Assert.assertEquals(produtorDatos.e0.getNombre(),     e.getNombre());
        Assert.assertEquals(produtorDatos.e0.getApellidos(),     e.getApellidos());
    	Assert.assertEquals(produtorDatos.e0.getTelefono(), e.getTelefono());
        Assert.assertEquals(produtorDatos.e0.getFechaNacimiento(), e.getFechaNacimiento());
        Assert.assertEquals(produtorDatos.e0.getGenero(), e.getGenero());
        Assert.assertEquals(produtorDatos.e0.getDireccion(), e.getDireccion());
        Assert.assertEquals(produtorDatos.e0.getNss(), e.getNss());
        Assert.assertEquals(produtorDatos.e0.getSalario(), e.getSalario());
        Assert.assertEquals(produtorDatos.e0.getPuesto(), e.getPuesto());
        Assert.assertEquals(produtorDatos.e0.getFechaContratacion(), e.getFechaContratacion());
        Assert.assertEquals(produtorDatos.e0.getExperiencia(), e.getExperiencia());
        Assert.assertEquals(produtorDatos.e0.getHorario(), e.getHorario());
		

    	log.info("");	
		log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");
    	
    	e = (Empleado) empDao.recuperaPorNif("iwbvyhuebvuwebvi");
    	Assert.assertNull (e);

    } 	

    @Test 
    public void test02_Alta() {

    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");
  
		produtorDatos.creaEmpleados();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de gravación na BD de novo empleado \n");
    	
    	// Situación de partida:
    	// u0 transitorio    	
    	
    	Assert.assertNull(produtorDatos.e0.getId());
    	empDao.almacena(produtorDatos.e0);    	
    	Assert.assertNotNull(produtorDatos.e0.getId());
    }
    
    @Test 
    public void test03_Eliminacion() {
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaEmpleados();
    	produtorDatos.gravaEmpleados();

    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de eliminación da BD de empleado sen habitaciones asociadas\n");   
 
    	// Situación de partida:
    	// u0 desligado  

    	Assert.assertNotNull(empDao.recuperaPorNif(produtorDatos.e0.getNif()));
    	empDao.elimina(produtorDatos.e0);    	
    	Assert.assertNull(empDao.recuperaPorNif(produtorDatos.e0.getNif()));
    } 	

    @Test 
    public void test04_Modificacion() {
    	
    	Empleado e1, e2;
    	String nuevoNombre;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaEmpleados();
    	produtorDatos.gravaEmpleados();

    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de modificación da información básica dun empleado\n");

    	// Situación de partida:
    	// u0 desligado  

		nuevoNombre = new String ("Nombre nuevo");

		e1 = (Empleado) empDao.recuperaPorNif(produtorDatos.e0.getNif());
		Assert.assertNotEquals(nuevoNombre, e1.getNombre());
    	e1.setNombre(nuevoNombre);

    	empDao.modifica(e1);    	
    	
		e2 = (Empleado) empDao.recuperaPorNif(produtorDatos.e0.getNif());
		Assert.assertEquals (nuevoNombre, e2.getNombre());

    } 	
    
    @Test
    public void test09_Excepcions() {
    	
    	Boolean excepcion;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaEmpleados();
    	empDao.almacena(produtorDatos.e0);
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de violación de restricións not null e unique\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Gravación de usuario con nif duplicado\n"
    			+ "\t\t\t\t b) Gravación de usuario con nif nulo\n");

    	// Situación de partida:
    	// e0 desligado, e1 transitorio
    	
		log.info("Probando gravacion de usuario con Nif duplicado -----------------------------------------------");
    	produtorDatos.e1.setNif(produtorDatos.e0.getNif());
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    	
    	// Nif nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Nif nulo ----------------------------------------------------");
    	produtorDatos.e1.setNif(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // NSS nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con NSS nulo ----------------------------------------------------");
    	produtorDatos.e1.setNss(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Salario nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Salario nulo ----------------------------------------------------");
    	produtorDatos.e1.setSalario(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Puesto nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con puesto nulo ----------------------------------------------------");
    	produtorDatos.e1.setPuesto(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // fecha de contratacion nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Fecha de contratacion nulo ----------------------------------------------------");
    	produtorDatos.e1.setFechaContratacion(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Horario nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Horario nulo ----------------------------------------------------");
    	produtorDatos.e1.setHorario(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
        
        // Nombre nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Nombre nulo ----------------------------------------------------");
    	produtorDatos.e1.setNombre(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Apellidos nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Apellidos nulo ----------------------------------------------------");
    	produtorDatos.e1.setApellidos(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Fecha de Nacimiento nula
    	log.info("");	
		log.info("Probando gravacion de usuario con Fecha de nacimiento nulo ----------------------------------------------------");
    	produtorDatos.e1.setFechaNacimiento(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Genero nulo
    	log.info("");	
		log.info("Probando gravacion de usuario con Genero nula ----------------------------------------------------");
    	produtorDatos.e1.setGenero(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        // Direccion nula
    	log.info("");	
		log.info("Probando gravacion de usuario con Direccion nula ----------------------------------------------------");
    	produtorDatos.e1.setDireccion(null);
    	try {
        	empDao.almacena(produtorDatos.e1);
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);
    } 	
}
