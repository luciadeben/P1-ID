package gei.id.tutelado;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.*;

public class ProdutorDatosProba {


	// Crea un conxunto de obxectos para utilizar nos casos de proba
	
	private EntityManagerFactory emf=null;
	
	public Usuario u0, u1;
	public List<Usuario> listaxeU;
	
	public EntradaLog e1A, e1B;
	public List<EntradaLog> listaxeE;

	public Empleado e0, e1;
	public List<Empleado> listaE;
	
	
	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	public void creaUsuariosSoltos() {

		// Crea dous usuarios EN MEMORIA: u0, u1
		// SEN entradas de log
		
		this.u0 = new Usuario();
        this.u0.setNif("000A");
        this.u0.setNome("Usuario cero");
        this.u0.setDataAlta(LocalDate.now());

        this.u1 = new Usuario();
        this.u1.setNif("111B");
        this.u1.setNome("Usuaria un");
        this.u1.setDataAlta(LocalDate.now());

        this.listaxeU = new ArrayList<Usuario> ();
        this.listaxeU.add(0,u0);
        this.listaxeU.add(1,u1);        

	}

	public void creaEmpleados() {

		// Crea dos empleados EN MEMORIA: e0, e1
		
		this.e0 = new Empleado();
        this.e0.setNif("000A");
        this.e0.setNombre("Empleado cero");
		this.e0.setApellidos("Gomez Pedreira");
		this.e0.setTelefono("610692944");
		this.e0.setFechaNacimiento(LocalDate.of(1992, 06, 02));
		this.e0.setGenero("Mujer");
		this.e0.setDireccion("Rua de las Flores, 2");

		this.e0.setNss("000123");
		this.e0.setSalario(1500.00);
		this.e0.setPuesto("supervisor");
		this.e0.setFechaContratacion(LocalDate.of(2010, 02, 10));
		this.e0.setExperiencia(13);
		this.e0.setHorario("8:00 a 12:00 - 16:00 a 20:00");


		this.e1 = new Empleado();
        this.e1.setNif("000B");
        this.e1.setNombre("Empleado uno");
		this.e1.setApellidos("Santos del Carmen");
		this.e1.setTelefono("610001202");
		this.e1.setFechaNacimiento(LocalDate.of(1990, 02, 9));
		this.e1.setGenero("Hombre");
		this.e1.setDireccion("Avenida de Jose Luis Perales, 35");
		
		this.e1.setNss("123456");
		this.e1.setSalario(1200.00);
		this.e1.setPuesto("cocinero");
		this.e1.setFechaContratacion(LocalDate.of(2020, 02, 21));
		this.e1.setExperiencia(20);
		this.e0.setHorario("22:00 a 06:00");

        this.listaE = new ArrayList<Empleado> ();
        this.listaE.add(0,e0);
        this.listaE.add(1,e1);        

	}
	
	public void creaEntradasLogSoltas () {

		// Crea duas entradas de log EN MEMORIA: e1a, e1b
		// Sen usuario asignado (momentaneamente)
		
		this.e1A=new EntradaLog();
        this.e1A.setCodigo("E001");
        this.e1A.setDescricion ("Modificado contrasinal por defecto");
        this.e1A.setDataHora(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        this.e1B=new EntradaLog();
        this.e1B.setCodigo("E002");
        this.e1B.setDescricion ("Acceso a zona reservada");
        this.e1B.setDataHora(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));

        this.listaxeE = new ArrayList<EntradaLog> ();
        this.listaxeE.add(0,this.e1A);
        this.listaxeE.add(1,this.e1B);        

	}
	
	public void creaUsuariosConEntradasLog () {

		this.creaUsuariosSoltos();
		this.creaEntradasLogSoltas();
		
        this.u1.engadirEntradaLog(this.e1A);
        this.u1.engadirEntradaLog(this.e1B);

	}
	
	public void gravaUsuarios() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Usuario> itU = this.listaxeU.iterator();
			while (itU.hasNext()) {
				Usuario u = itU.next();
				em.persist(u);
				// DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itEL.hasNext()) {
					em.persist(itEL.next());
				}
				*/
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}	
	}

	public void gravaEmpleados() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Empleado> itE = this.listaE.iterator();
			while (itE.hasNext()) {
				Empleado e = itE.next();
				em.persist(e);
				// DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itEL.hasNext()) {
					em.persist(itEL.next());
				}
				*/
			}
			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}	
	}
	
	public void limpaBD () {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();
			
			Iterator <Usuario> itU = em.createNamedQuery("Usuario.recuperaTodos", Usuario.class).getResultList().iterator();
			while (itU.hasNext()) em.remove(itU.next());
			Iterator <EntradaLog> itL = em.createNamedQuery("EntradaLog.recuperaTodas", EntradaLog.class).getResultList().iterator();
			while (itL.hasNext()) em.remove(itL.next());		
			Iterator <Empleado> itE = em.createNamedQuery("Empleado.recuperaTodos", Empleado.class).getResultList().iterator();
			while (itE.hasNext()) em.remove(itE.next());			

			
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idUsuario'" ).executeUpdate();
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idEntradaLog'" ).executeUpdate();
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idPersona'" ).executeUpdate();

			em.getTransaction().commit();
			em.close();
		} catch (Exception e) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw (e);
			}
		}
	}
	
	
}
