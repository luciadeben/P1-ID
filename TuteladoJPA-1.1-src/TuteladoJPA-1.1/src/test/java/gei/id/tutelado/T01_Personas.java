package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.PersonaDao;
import gei.id.tutelado.dao.PersonaDaoJPA;
import gei.id.tutelado.model.Persona;

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
public class T01_Personas {
    
    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();
    
    private static Configuracion cfg;
    private static PersonaDao perDao;
    
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

    	perDao = new PersonaDaoJPA();
    	perDao.setup(cfg);
    	
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
    	
    	Persona p;
    	
    	log.info("");	
		log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

		produtorDatos.creaEmpleados();
    	produtorDatos.gravaEmpleados();
    	
    	log.info("");	
		log.info("Inicio do test --------------------------------------------------------------------------------------------------");
    	log.info("Obxectivo: Proba de recuperación desde a BD de personas (sen entradas asociadas) por nif\n"   
    			+ "\t\t\t\t Casos contemplados:\n"
    			+ "\t\t\t\t a) Recuperación por nif existente\n"
    			+ "\t\t\t\t b) Recuperacion por nif inexistente\n");

    	// Situación de partida:
    	// u0 desligado    	

    	log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

    	p = perDao.recuperaPorNif(produtorDatos.e0.getNif());
    	Assert.assertEquals(produtorDatos.e0.getNif(),      p.getNif());
    	Assert.assertEquals(produtorDatos.e0.getNombre(),     p.getNombre());
    	Assert.assertEquals(produtorDatos.e0.getTelefono(), p.getTelefono());
        Assert.assertEquals(produtorDatos.e0.getFechaNacimiento(), p.getFechaNacimiento());
        Assert.assertEquals(produtorDatos.e0.getGenero(), p.getGenero());
        Assert.assertEquals(produtorDatos.e0.getDireccion(), p.getDireccion());
		Assert.assertEquals(produtorDatos.e0.getApellidos(), p.getApellidos());		

    	log.info("");	
		log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");
    	
    	p = perDao.recuperaPorNif("iwbvyhuebvuwebvi");
    	Assert.assertNull (p);
    }
    
}
