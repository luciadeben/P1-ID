package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Habitacion;


public class HabitacionDaoJPA implements HabitacionDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Habitacion almacena(Habitacion habitacion) {

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(habitacion);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return habitacion;
    }

    @Override
    public Habitacion modifica(Habitacion habitacion) {

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            habitacion = em.merge (habitacion);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (habitacion);
    }

    @Override
    public void elimina(Habitacion habitacion) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Habitacion habitacionTmp = em.find (Habitacion.class, habitacion.getId());
            em.remove (habitacionTmp);
            //cuando este aplicaciones hay que asegurarse de que: o la habitacion no esta asociada a ninguna aplicacion o eliminar la asociacion con los residentes :D

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
    }


    @Override
    public Habitacion recuperaPorNumero(int numero) {
        List <Habitacion> habitaciones=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            habitaciones = em.createNamedQuery("Habitacion.recuperaPorNumero", Habitacion.class).setParameter("numero", numero).getResultList();

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

        return (habitaciones.size()!=0?habitaciones.get(0):null);
    }

    @Override
    public List<Habitacion> recuperaTodos() {
        List <Habitacion> habitaciones=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            habitaciones = em.createNamedQuery("Habitacion.recuperaTodos", Habitacion.class).getResultList();

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

        return habitaciones;
    }



}
