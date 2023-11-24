package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.model.Empleado;


public class EmpleadoDaoJPA extends PersonaDaoJPA implements EmpleadoDao {

	private EntityManagerFactory emf; 
	private EntityManager em;

	@Override
	public List<Empleado> recuperaEmpleados() {
		List <Empleado> empleados=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			empleados = em.createNamedQuery("Empleado.recuperaTodos", Empleado.class).getResultList(); 

			em.getTransaction().commit();
			em.close();	

		}
		catch (Exception ex ) {
			if (em!=null && em.isOpen()) {
				if (em.getTransaction().isActive()) em.getTransaction().rollback();
				em.close();
				throw(ex);
			}
		}

		return empleados;
	}



}
