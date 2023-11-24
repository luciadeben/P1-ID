package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.ResidenteDao;
import gei.id.tutelado.dao.ResidenteDaoJPA;
import gei.id.tutelado.model.Residente;

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
public class T03_Residentes {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static Configuracion cfg;
    private static ResidenteDao resDao;

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

        resDao = new ResidenteDaoJPA();
        resDao.setup(cfg);

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

        Residente r;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de recuperación desde a BD de usuario (sen entradas asociadas) por nif\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Recuperación por nif existente\n"
                + "\t\t\t\t b) Recuperacion por nif inexistente\n");

        // Situación de partida:
        // u0 desligado

        log.info("Probando recuperacion por nif EXISTENTE --------------------------------------------------");

        r = (Residente) resDao.recuperaPorNif(produtorDatos.r0.getNif());
        Assert.assertEquals(produtorDatos.r0.getNif(),      r.getNif());
        Assert.assertEquals(produtorDatos.r0.getNombre(),     r.getNombre());
        Assert.assertEquals(produtorDatos.r0.getApellidos(),     r.getApellidos());
        Assert.assertEquals(produtorDatos.r0.getTelefono(), r.getTelefono());
        Assert.assertEquals(produtorDatos.r0.getFechaNacimiento(), r.getFechaNacimiento());
        Assert.assertEquals(produtorDatos.r0.getGenero(), r.getGenero());
        Assert.assertEquals(produtorDatos.r0.getDireccion(), r.getDireccion());
        Assert.assertEquals(produtorDatos.r0.getFechaIngreso(), r.getFechaIngreso());
        Assert.assertEquals(produtorDatos.r0.getEstadosalud(), r.getEstadosalud());
        Assert.assertEquals(produtorDatos.r0.getContactosEmergencia(), r.getContactosEmergencia());
        //Assert.assertEquals(produtorDatos.r0.getHabitacion(), r.getHabitacion());

        log.info("");
        log.info("Probando recuperacion por nif INEXISTENTE -----------------------------------------------");

        r = (Residente) resDao.recuperaPorNif("iwbvyhuebvuwebvi");
        Assert.assertNull (r);

    }

    @Test
    public void test02_Alta() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de novo usuario (sen entradas de log asociadas)\n");

        // Situación de partida:
        // u0 transitorio

        Assert.assertNull(produtorDatos.r0.getId());
        resDao.almacena(produtorDatos.r0);
        Assert.assertNotNull(produtorDatos.r0.getId());
    }

    @Test
    public void test03_Eliminacion() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();


        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de eliminación da BD de usuario sen entradas asociadas\n");

        // Situación de partida:
        // r0 desligado

        Assert.assertNotNull(resDao.recuperaPorNif(produtorDatos.r0.getNif()));
        resDao.elimina(produtorDatos.r0);
        Assert.assertNull(resDao.recuperaPorNif(produtorDatos.r0.getNif()));
    }

    @Test
    public void test04_Modificacion() {

        Residente r1, r2;
        String nuevoNombre;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de modificación da información básica dun usuario sen entradas de log\n");

        // Situación de partida:
        // r0 desligado

        nuevoNombre = new String ("Nombre nuevo");

        r1 = (Residente) resDao.recuperaPorNif(produtorDatos.r0.getNif());
        Assert.assertNotEquals(nuevoNombre, r1.getNombre());
        r1.setNombre(nuevoNombre);

        resDao.modifica(r1);

        r2 = (Residente) resDao.recuperaPorNif(produtorDatos.r0.getNif());
        Assert.assertEquals (nuevoNombre, r2.getNombre());

    }

    @Test
    public void test09_Excepcions() {

        Boolean excepcion;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        resDao.almacena(produtorDatos.r0);

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de violación de restricións not null e unique\n"
                + "\t\t\t\t Casos contemplados:\n"
                + "\t\t\t\t a) Gravación de usuario con nif duplicado\n"
                + "\t\t\t\t b) Gravación de usuario con nif nulo\n");

        // Situación de partida:
        // r0 desligado, r1 transitorio

        log.info("Probando gravacion de usuario con Nif duplicado -----------------------------------------------");
        produtorDatos.r1.setNif(produtorDatos.r0.getNif());
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Nif nulo
        log.info("");
        log.info("Probando gravacion de usuario con Nif nulo ----------------------------------------------------");
        produtorDatos.r1.setNif(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Fecha de ingreso nulo
        log.info("");
        log.info("Probando gravacion de usuario con fecha ingreso nulo ----------------------------------------------------");
        produtorDatos.r1.setFechaIngreso(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Estado salud nulo
        log.info("");
        log.info("Probando gravacion de usuario con Estado salud nulo ----------------------------------------------------");
        produtorDatos.r1.setEstadosalud(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Contactos Emergencia nulo
        log.info("");
        log.info("Probando gravacion de usuario con contactos emergencia nulo ----------------------------------------------------");
        produtorDatos.r1.setContactosEmergencia(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Habitacion nulo
        log.info("");
        log.info("Probando gravacion de usuario con habitacion nulo ----------------------------------------------------");
        //produtorDatos.r1.setHabitacion(null);
        try {
            produtorDatos.r1.setHabitacion(null);
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);


        // Nombre nulo
        log.info("");
        log.info("Probando gravacion de usuario con Nombre nulo ----------------------------------------------------");
        produtorDatos.r1.setNombre(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Apellidos nulo
        log.info("");
        log.info("Probando gravacion de usuario con Apellidos nulo ----------------------------------------------------");
        produtorDatos.r1.setApellidos(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Fecha de Nacimiento nula
        log.info("");
        log.info("Probando gravacion de usuario con Fecha de nacimiento nulo ----------------------------------------------------");
        produtorDatos.r1.setFechaNacimiento(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Genero nulo
        log.info("");
        log.info("Probando gravacion de usuario con Genero nulo ----------------------------------------------------");
        produtorDatos.r1.setGenero(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);

        // Direccion nula
        log.info("");
        log.info("Probando gravacion de usuario con Direccion nula ----------------------------------------------------");
        produtorDatos.r1.setDireccion(null);
        try {
            resDao.almacena(produtorDatos.r1);
            excepcion=false;
        } catch (Exception ex) {
            excepcion=true;
            log.info(ex.getClass().getName());
        }
        Assert.assertTrue(excepcion);
    }
}
