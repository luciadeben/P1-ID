package gei.id.tutelado.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.model.Habitacion;
import gei.id.tutelado.model.Residente;


public class ResidenteDaoJPA extends PersonaDaoJPA implements ResidenteDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public List<Residente> recuperaResidentes() {
        List <Residente> residentes=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            residentes = em.createNamedQuery("Residente.recuperaTodos", Residente.class).getResultList();

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

        return residentes;
    }

    public List<Residente> recuperaPorHabitacion(Habitacion habitacion){
        List <Residente> residentes= new ArrayList<>();

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            residentes = em.createNamedQuery("Residente.recuperaHabitacion", Residente.class)
                .setParameter("habitacionId", habitacion.getId())
                .getResultList();

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

        return residentes;
    }

}
