package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.HabitacionDao;
import gei.id.tutelado.dao.HabitacionDaoJPA;
import gei.id.tutelado.dao.ResidenteDao;
import gei.id.tutelado.dao.ResidenteDaoJPA;
import gei.id.tutelado.model.Empleado;
import gei.id.tutelado.model.Habitacion;
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

import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T05_consultas {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static Configuracion cfg;
    private static HabitacionDao habDao;
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

        habDao = new HabitacionDaoJPA();
        habDao.setup(cfg);
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
    public void test01_AddEmptoHab() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaEmpleados();
        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova habitacion (sen entradas de log asociadas)\n");
        
        // Situación de partida:
        // h0 transitorio

        Assert.assertNull(produtorDatos.h0.getEmpleado());
        produtorDatos.h0.addEmpleado(produtorDatos.e0);
        habDao.almacena(produtorDatos.h0);
        Set<Empleado> empleados = produtorDatos.h0.getEmpleado();
        Assert.assertTrue(empleados.contains (produtorDatos.e0));
        }

        @Test
        public void test02_AddRestoHab() {

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova habitacion (sen entradas de log asociadas)\n");
        
        // Situación de partida:
        // h0 transitorio

        Habitacion h = produtorDatos.r0.getHabitacion();
        produtorDatos.r0.setHabitacion(produtorDatos.h1);
        resDao.almacena(produtorDatos.r0);
        Assert.assertEquals(produtorDatos.h1, produtorDatos.r0.getHabitacion());
        Set<Residente> residentes = produtorDatos.h1.getResidente();
        Assert.assertTrue(residentes.contains(produtorDatos.r0));
        }    

    }

