package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.HabitacionDao;
import gei.id.tutelado.dao.HabitacionDaoJPA;
import gei.id.tutelado.model.Habitacion;

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
public class T04_Habitacion {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static Configuracion cfg;
    private static HabitacionDao habDao;

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

        habDao = new HabitacionDaoJPA();
        habDao.setup(cfg);

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

    public void test01_Recuperacion() {

        Habitacion h;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.gravaHabitaciones();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de habitacion (sen entradas asociadas) por numero\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por numero existente\n"
                + "\t\t\t\t b) Recuperacion por numero inexistente\n");

        // Situación de partida:
        // h0 desligado

        log.info("Probando recuperacion por numero EXISTENTE --------------------------------------------------");

        h = habDao.recuperaPorNumero(produtorDatos.h0.getNumero());
        Assert.assertEquals(produtorDatos.h0.getNumero(),      h.getNumero());
        Assert.assertEquals(produtorDatos.h0.getPlanta(),     h.getPlanta());
        Assert.assertEquals(produtorDatos.h0.getCapacidad(),     h.getCapacidad());
        Assert.assertEquals(produtorDatos.h0.getTipo(), h.getTipo());
        Assert.assertEquals(produtorDatos.h0.getEmpleado(), h.getEmpleado());
        Assert.assertEquals(produtorDatos.h0.getResidente(), h.getResidente());
        Assert.assertEquals(produtorDatos.h0.getEstado(), h.getEstado());

        log.info("");
        log.info("Probando recuperacion por numero INEXISTENTE -----------------------------------------------");

        h = habDao.recuperaPorNumero(2);
        Assert.assertNull (h);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova habitacion (sen entradas de log asociadas)\n");

        // Situación de partida:
        // h0 transitorio

        Assert.assertNull(produtorDatos.h0.getId());
        habDao.almacena(produtorDatos.h0);
        Assert.assertNotNull(produtorDatos.h0.getId());
    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.gravaHabitaciones();


        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de habitacion sen entradas asociadas\n");

        // Situación de partida:
        // h0 desligado

        Assert.assertNotNull(habDao.recuperaPorNumero(produtorDatos.h0.getNumero()));
        habDao.elimina(produtorDatos.h0);
        Assert.assertNull(habDao.recuperaPorNumero(produtorDatos.h0.getNumero()));
    }

    @Test
    public void test04_Modificacion() {

        Habitacion h1, h2;
        int nuevoNumero;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.gravaHabitaciones();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dunha habitacion sen entradas de log\n");

        // Situación de partida:
        // h0 desligada

        nuevoNumero = new int ("Numero nuevo");

        h1 = habDao.recuperaPorNumero(produtorDatos.h0.getNumero());
        Assert.assertNotEquals(nuevoNumero, h1.getNumero());
        r1.setNumero(nuevoNumero);

        habDao.modifica(h1);

        h2 = habDao.recuperaPorNumero(produtorDatos.h0.getNumero());
        Assert.assertEquals (nuevoNumero, h2.getNumero());

    }

    @Test
    public void test09_Excepcions() {

        Boolean excepcion;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        habDao.almacena(produtorDatos.h0);

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de violación de restricións not null e unique\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Gravación de habitacion con numero duplicado\n"
                + "\t\t\t\t b) Gravación de habitacion con numero nulo\n");

        // Situación de partida:
        // h0 desligado, h1 transitorio

        log.info("Probando gravacion de habitacion con Numero duplicado -----------------------------------------------");
        produtorDatos.h1.setNumero(produtorDatos.h0.getNumero());
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


        // Numero nulo
        log.info("");
        log.info("Probando gravacion de habitacion con Numero nulo ----------------------------------------------------");
        produtorDatos.h1.setNumero(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Planta nulo
        log.info("");
        log.info("Probando gravacion de habitacion con planta nulo ----------------------------------------------------");
        produtorDatos.h1.setPlanta(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Capacidad nulo
        log.info("");
        log.info("Probando gravacion de habitacion con capacidad nulo ----------------------------------------------------");
        produtorDatos.h1.setCapacidad(null);
        try {
            resDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Tipo nulo
        log.info("");
        log.info("Probando gravacion de habitacion con tipo nulo ----------------------------------------------------");
        produtorDatos.h1.setTipo(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


        // Empleados nulo
        log.info("");
        log.info("Probando gravacion de habitacion con empleados nulo ----------------------------------------------------");
        produtorDatos.h1.setEmpleado(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


        // Residentes nulo
        log.info("");
        log.info("Probando gravacion de habitacion con residentes nulo ----------------------------------------------------");
        produtorDatos.h1.setResidente(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


        // Estado nulo
        log.info("");
        log.info("Probando gravacion de usuario con estado nulo ----------------------------------------------------");
        produtorDatos.r1.setEstado(null);
        try {
            habDao.almacena(produtorDatos.h1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


