package gei.id.tutelado;

import gei.id.tutelado.configuracion.ConfiguracionJPA;
import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.dao.EmpleadoDao;
import gei.id.tutelado.dao.EmpleadoDaoJPA;
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

import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class T05_Consultas_Comprobaciones {

    private Logger log = LogManager.getLogger("gei.id.tutelado");

    private static ProdutorDatosProba produtorDatos = new ProdutorDatosProba();

    private static Configuracion cfg;
    private static HabitacionDao habDao;
    private static ResidenteDao resDao;
    private static EmpleadoDao empDao;

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(Description description) {
            log.info("");
            log.info(
                    "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            log.info("Iniciando test: " + description.getMethodName());
            log.info(
                    "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        }

        protected void finished(Description description) {
            log.info("");
            log.info(
                    "-----------------------------------------------------------------------------------------------------------------------------------------");
            log.info("Finalizado test: " + description.getMethodName());
            log.info(
                    "-----------------------------------------------------------------------------------------------------------------------------------------");
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
        resDao.setupRes(cfg);
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
        log.info(
                "Limpando BD --------------------------------------------------------------------------------------------");
        produtorDatos.limpaBD();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test01_AddEmptoHab() {

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaEmpleados();
        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova habitacion (sen entradas de log asociadas)\n");

        // Situación de partida:
        // h0 transitorio

        Assert.assertNull(produtorDatos.h0.getEmpleado());
        produtorDatos.h0.addEmpleado(produtorDatos.e0);
        habDao.almacena(produtorDatos.h0);
        Set<Empleado> empleados = produtorDatos.h0.getEmpleado();
        Assert.assertTrue(empleados.contains(produtorDatos.e0));
    }

    @Test
    public void test02_AddRestoHab() {

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba de gravación na BD de nova habitacion (sen entradas de log asociadas)\n");

        // Situación de partida:
        // h0 transitorio

        produtorDatos.r0.setHabitacion(produtorDatos.h1);
        resDao.almacena(produtorDatos.r0);
        Assert.assertEquals(produtorDatos.h1, produtorDatos.r0.getHabitacion());
        Set<Residente> residentes = produtorDatos.h1.getResidente();
        Assert.assertTrue(residentes.contains(produtorDatos.r0));
    }

    @Test
    public void test03_ResidentesH() {

        List<Residente> listaR;

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaHabitacionessinResidentes();
        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();
        produtorDatos.gravaHabitacionessinR();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        listaR = resDao.recuperaPorHabitacion(produtorDatos.h2);
        Assert.assertEquals(0, listaR.size());

        listaR = resDao.recuperaPorHabitacion(produtorDatos.h0);
        Assert.assertEquals(1, listaR.size());
        Assert.assertEquals(produtorDatos.r0, listaR.get(0));

    }

    @Test
    public void test04_HabitacionResidente() {
        List<Habitacion> listaAux;

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaHabitacionessinResidentes();
        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();
        produtorDatos.gravaHabitacionessinR();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        listaAux = habDao.recuperaHabitacionYResidente();
        Assert.assertEquals(produtorDatos.h0, listaAux.get(0));
        Assert.assertTrue(listaAux.get(0).getResidente().contains(produtorDatos.r0));

        Assert.assertEquals(produtorDatos.h1, listaAux.get(1));
        Assert.assertTrue(listaAux.get(1).getResidente().contains(produtorDatos.r1));

        Assert.assertEquals(produtorDatos.h2, listaAux.get(2));
        Assert.assertTrue(listaAux.get(2).getResidente().isEmpty());

    }

    @Test
    public void test05_HabitaciontotalEmp() {

        List<Object[]> listaAux;

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitacionesconEmpleados();
        produtorDatos.creaHabitacionessinResidentes();
        produtorDatos.gravaHabitaciones();
        produtorDatos.gravaHabitacionessinR();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        listaAux = habDao.recuperaconTotalEmpleados();
        Assert.assertEquals(produtorDatos.h1, listaAux.get(0)[0]);
        Assert.assertEquals(1L, listaAux.get(0)[1]);

        Assert.assertEquals(produtorDatos.h2, listaAux.get(1)[0]);
        Assert.assertEquals(0L, listaAux.get(1)[1]);

        Assert.assertEquals(produtorDatos.h0, listaAux.get(2)[0]);
        Assert.assertEquals(2L, listaAux.get(2)[1]);
    }

    @Test
    public void test06_BorraHConResidentes() {


        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        Assert.assertNotNull(habDao.recuperaPorNumero(produtorDatos.h0.getNumero()));  
        produtorDatos.h0.desvincularResidentes(produtorDatos.h999);
        resDao.modifica(produtorDatos.r0);   
        habDao.elimina(produtorDatos.h0);
        Assert.assertNull(habDao.recuperaPorNumero(produtorDatos.h0.getNumero()));
    }

    @Test
    public void test07_BorraEmpleadoasoHabitacion() {


        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitacionesconEmpleados();
        produtorDatos.gravaHabitaciones();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        Assert.assertNotNull(empDao.recuperaPorNif(produtorDatos.e0.getNif()));
        for(int i=0;i<produtorDatos.listaH.size();i++){
                if(produtorDatos.listaH.get(i).getEmpleado().contains(produtorDatos.e0)){
                     produtorDatos.listaH.get(i).removeEmpleado(produtorDatos.e0);           
                     habDao.modifica(produtorDatos.listaH.get(i));
                }
        }    
        empDao.elimina(produtorDatos.e0);
        Assert.assertNull(empDao.recuperaPorNif(produtorDatos.e0.getNif()));
    }
    
    @Test
    public void test08_RecuperaSiResyEmp() {

        List<Habitacion> habitaciones;

        log.info("");
        log.info(
                "Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.creaHabitacionessinResidentes();
        produtorDatos.creaResidentes();
        produtorDatos.creaResidenteExtra();
        produtorDatos.gravaHabitacionessinR();
        produtorDatos.gravaResidentes();

        log.info("");
        log.info(
                "Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba da consulta Residente.recuperaHabitacion\n");

        // Situación de partida:
        // u1, e1A, e1B desligados

        habitaciones = habDao.recuperaSiResidente();
        Assert.assertFalse(habitaciones.isEmpty());
        for(int i=0;i<habitaciones.size();i++){
            Assert.assertTrue(habitaciones.get(i).getResidente().size()>1);
        }
    }

    @Test
    public void test09_CargaEager() {
        Habitacion h;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaHabitaciones();
        produtorDatos.gravaHabitaciones();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba carga Eager\n");

        h = habDao.recuperaPorNumero(produtorDatos.h0.getNumero());

        Assert.assertTrue(h.getEmpleado().isEmpty());

    }

    @Test
    public void test10_CargaLazy() {
        Residente r;
        Boolean excepcion;

        log.info("");
        log.info("Configurando situación de partida do test -----------------------------------------------------------------------");

        produtorDatos.creaResidentes();
        produtorDatos.gravaResidentes();

        log.info("");
        log.info("Inicio do test --------------------------------------------------------------------------------------------------");
        log.info("Obxectivo: Proba carga Lazy\n");

        r = (Residente) resDao.recuperaPorNif(produtorDatos.r0.getNif());
        try {
        	Assert.assertNull(r.getHabitacion());
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertTrue(excepcion);

        r = (Residente) resDao.recuperaConHabitacion(produtorDatos.r0);
        try {
        	Assert.assertNotNull(r.getHabitacion());
        	excepcion=false;
    	} catch (Exception ex) {
    		excepcion=true;
    		log.info(ex.getClass().getName());
    	}
    	Assert.assertFalse(excepcion);

    }

    
}
