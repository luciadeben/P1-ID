package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Persona;


public class PersonaDaoJPA implements PersonaDao {

	private EntityManagerFactory emf; 
	private EntityManager em;

	@Override
	public void setup (Configuracion config) {
		this.emf = (EntityManagerFactory) config.get("EMF");
	}

    @Override
	public Persona recuperaPorNif(String nif) {
		List <Persona> personas=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			personas = em.createNamedQuery("Persona.recuperaPorNif", Persona.class).setParameter("nif", nif).getResultList(); 

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

		return (personas.size()!=0?personas.get(0):null);
	}


	@Override
	public List<Persona> recuperaTodos() {
		List <Persona> personas=null;

		try {
			em = emf.createEntityManager();
			em.getTransaction().begin();

			personas = em.createNamedQuery("Usuario.recuperaTodos", Persona.class).getResultList(); 

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

		return personas;
	}



}

