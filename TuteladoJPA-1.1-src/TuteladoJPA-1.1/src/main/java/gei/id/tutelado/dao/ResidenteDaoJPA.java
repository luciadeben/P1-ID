package gei.id.tutelado.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import gei.id.tutelado.configuracion.Configuracion;
import gei.id.tutelado.model.Residente;


public class ResidenteDaoJPA implements ResidenteDao {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Override
    public void setup (Configuracion config) {
        this.emf = (EntityManagerFactory) config.get("EMF");
    }

    @Override
    public Residente almacena(Residente residente) {

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            em.persist(residente);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return residente;
    }

    @Override
    public Residente modifica(Residente residente) {

        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            residente = em.merge (residente);

            em.getTransaction().commit();
            em.close();

        } catch (Exception ex ) {
            if (em!=null && em.isOpen()) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                em.close();
                throw(ex);
            }
        }
        return (residente);
    }

    @Override
    public void elimina(Residente residente) {
        try {

            em = emf.createEntityManager();
            em.getTransaction().begin();

            Residente residenteTmp = em.find (Residente.class, residente.getId());
            em.remove (residenteTmp);
            //cuando este aplicaciones hay que asegurarse de que: o el residente no este asociado a ninguna aplicacion o eliminar la asociacion con las habitaciones :D

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
    public residente recuperaPorNif(String nif) {
        List <Residente> residentes=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            residentes = em.createNamedQuery("Residente.recuperaPorNif", Residente.class).setParameter("nif", nif).getResultList();

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

        return (residentes.size()!=0?residentes.get(0):null);
    }

    @Override
    public List<Residente> recuperaTodos() {
        List <Residente> residentes=null;

        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();

            residentes = em.createNamedQuery("Residente.recuperaTodos", residente.class).getResultList();

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
