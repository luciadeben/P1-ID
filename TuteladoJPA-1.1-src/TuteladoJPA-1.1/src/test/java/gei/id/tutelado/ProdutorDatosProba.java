package gei.id.tutelado;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.*;

public class ProdutorDatosProba {


	// Crea un conxunto de obxectos para utilizar nos casos de proba
	
	private EntityManagerFactory emf=null;

	public Empleado e0, e1;
	public List<Empleado> listaE;

	public Residente r0, r1, r2;
	public List<Residente> listaR;
	public List<Residente> listaREx;

	public Habitacion h0, h1, h2, h999;
	public List<Habitacion> listaH;
	public List<Habitacion> listaHsR;

	public List<String> contactos;
	
	Set<Empleado> empleados;
	Set<Residente> residentes;
	
	public void Setup (Configuracion config) {
		this.emf=(EntityManagerFactory) config.get("EMF");
	}
	
	
	public void listaContactos(){
		this.contactos = new ArrayList<>();
		contactos.add("686465736");
		contactos.add("653489234"); 
		contactos.add("676456345");
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

	public void creaResidentes() {

		// Crea dos residentes EN MEMORIA: r0, r1
		creaHabitaciones();
		
		this.r0 = new Residente();
        this.r0.setNif("010V");
        this.r0.setNombre("Residente cero");
		this.r0.setApellidos("Lechuga Esparrago");
		this.r0.setTelefono("623423986");
		this.r0.setFechaNacimiento(LocalDate.of(2000, 03, 02));
		this.r0.setGenero("Mujer");
		this.r0.setDireccion("Rua de las Cuevas, 6");

		this.r0.setFechaIngreso(LocalDate.of(2008, 02, 10));
		this.r0.setEstadosalud("delicado");
		listaContactos();
		this.r0.setContactosEmergencia(contactos);
		this.r0.setHabitacion(h0);

		this.r1 = new Residente ();
        this.r1.setNif("044C");
        this.r1.setNombre("Residente uno");
		this.r1.setApellidos("Amigo Bueno");
		this.r1.setTelefono("644567432");
		this.r1.setFechaNacimiento(LocalDate.of(1995, 02, 7));
		this.r1.setGenero("Hombre");
		this.r1.setDireccion("Avenida de Buenos Aires, 12");
		
		this.r1.setFechaIngreso(LocalDate.of(2008, 02, 10));
		this.r1.setEstadosalud("excelente");
		this.r1.setContactosEmergencia(contactos);
		this.r1.setHabitacion(h1);

        this.listaR = new ArrayList<Residente> ();
        this.listaR.add(0,r0);
        this.listaR.add(1,r1);        

	}
	
	public void creaResidenteExtra() {

		// Crea dos residentes EN MEMORIA: r0, r1
		
		this.r2 = new Residente();
        this.r2.setNif("11010V");
        this.r2.setNombre("Residente dos");
		this.r2.setApellidos("Santos Domingo");
		this.r2.setTelefono("6234231112");
		this.r2.setFechaNacimiento(LocalDate.of(1800, 03, 10));
		this.r2.setGenero("Mujer");
		this.r2.setDireccion("Rua de las Cuevas, 6");

		this.r2.setFechaIngreso(LocalDate.of(2008, 02, 10));
		this.r2.setEstadosalud("delicado");
		listaContactos();
		this.r2.setContactosEmergencia(contactos);
		this.r2.setHabitacion(this.h0);

        this.listaR.add(2,r2);

	}


	public void creaHabitaciones() {

		this.h0 = new Habitacion();
		this.h0.setNumero(2);
		this.h0.setPlanta(3);
		this.h0.setCapacidad(3);
		this.h0.setTipo("compartida");
		this.h0.setEstado("libre");

		this.h1 = new Habitacion();
		this.h1.setNumero(8);
		this.h1.setPlanta(2);
		this.h1.setCapacidad(1);
		this.h1.setTipo("individual");
		this.h1.setEstado("libre");

		this.h999 = new Habitacion();
		this.h999.setNumero(999999999);
		this.h999.setPlanta(999999999);
		this.h999.setCapacidad(999999999);
		this.h999.setTipo("en espera");
		this.h999.setEstado("libre");

        this.listaH = new ArrayList<Habitacion> ();
        this.listaH.add(0,h0);
        this.listaH.add(1,h1);
		this.listaH.add(2,h999);      

	}

	public void creaHabitacionessinResidentes() {

		this.h2 = new Habitacion();
		this.h2.setNumero(23);
		this.h2.setPlanta(2);
		this.h2.setCapacidad(3);
		this.h2.setTipo("compartida");
		this.h2.setEstado("libre");

		this.listaHsR = new ArrayList<Habitacion> ();
		this.listaHsR.add(0,h2);  
	}

	public void creaHabitacionesconEmpleados() {

		this.creaEmpleados();

		this.h0 = new Habitacion();
		this.h0.setNumero(2);
		this.h0.setPlanta(3);
		this.h0.setCapacidad(3);
		this.h0.setTipo("compartida");
		this.h0.setEstado("libre");
		this.h0.addEmpleado(e0);
		this.h0.addEmpleado(e1);

		this.h1 = new Habitacion();
		this.h1.setNumero(8);
		this.h1.setPlanta(2);
		this.h1.setCapacidad(1);
		this.h1.setTipo("individual");
		this.h1.setEstado("libre");
		this.h1.addEmpleado(e0);

        this.listaH = new ArrayList<Habitacion> ();
        this.listaH.add(0,h0);
        this.listaH.add(1,h1);
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

	public void gravaResidentes() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Residente> itR = this.listaR.iterator();
			while (itR.hasNext()) {
				Residente r = itR.next();
				em.persist(r);
				// DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itRL.hasNext()) {
					em.persist(itRL.next());
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

	public void gravaHabitaciones() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Habitacion> itH = this.listaH.iterator();
			while (itH.hasNext()) {
				Habitacion h = itH.next();
				em.persist(h);
				// DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itRL.hasNext()) {
					em.persist(itRL.next());
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

	public void gravaHabitacionessinR() {
		EntityManager em=null;
		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			Iterator<Habitacion> itH = this.listaHsR.iterator();
			while (itH.hasNext()) {
				Habitacion h = itH.next();
				em.persist(h);
				// DESCOMENTAR SE A PROPAGACION DO PERSIST NON ESTA ACTIVADA
				/*
				Iterator<EntradaLog> itEL = u.getEntradasLog().iterator();
				while (itRL.hasNext()) {
					em.persist(itRL.next());
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
			
			Iterator <Empleado> itE = em.createNamedQuery("Empleado.recuperaTodos", Empleado.class).getResultList().iterator();
			while (itE.hasNext()) em.remove(itE.next());
			Iterator <Residente> itR = em.createNamedQuery("Residente.recuperaTodos", Residente.class).getResultList().iterator();
			while (itR.hasNext()) em.remove(itR.next());
			Iterator <Habitacion> itH = em.createNamedQuery("Habitacion.recuperaTodos", Habitacion.class).getResultList().iterator();
			while (itH.hasNext()) em.remove(itH.next());			

			
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idPersona'" ).executeUpdate();
			em.createNativeQuery("UPDATE taboa_ids SET ultimo_valor_id=0 WHERE nombre_id='idHabitacion'" ).executeUpdate();

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