package cl.javalin.api.service;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import cl.javalin.api.configuration.HIbernateConfig;
import cl.javalin.api.model.CursoModel;

public class ServicioCurso {

    public void guardar(CursoModel curso){
        Transaction tran = null;
        try (Session session = HIbernateConfig.getSession().openSession()){
            tran = session.beginTransaction();
            session.persist(curso);
            tran.commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
        }
    }

    public List<CursoModel> listar_cursos(){
        Transaction tx = null;
        try (Session session = HIbernateConfig.getSession().openSession()){
            tx = session.beginTransaction();
            List<CursoModel> cursos = session.createQuery("FROM CursoModel ORDER BY id", CursoModel.class).list();
            
            //List<CursoModel> cursos = session.createNativeQuery("SELECT * FROM curso", CursoModel.class).getResultList();
            tx.commit();
            return cursos;
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }

        return null;
    }

    public CursoModel buscar_curso(Integer id){
        Transaction tx = null;
        try (Session session = HIbernateConfig.getSession().openSession()){
            tx = session.beginTransaction();
            CursoModel curso = session.get(CursoModel.class, id);
            tx.commit();
            return curso;
        } catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        return null;
    }

    public void eliminar(Integer id){
        Transaction tx = null;
        try (Session session = HIbernateConfig.getSession().openSession()){
            tx = session.beginTransaction();
            CursoModel curso = session.get(CursoModel.class, id);
            session.remove(curso);
            tx.commit();  
            
        } catch (Exception e){
            if (tx!=null) {
                tx.rollback();
            }
        }
    }

    public void actualizar(Integer id, CursoModel curso){
        Transaction tx = null;
        try (Session session = HIbernateConfig.getSession().openSession()){
            tx = session.beginTransaction();
            //CursoModel cursoUp = session.get(CursoModel.class, id);
            //cursoUp.setNombre(curso.getNombre());
            //session.persist(cursoUp);
            Query query = session.createNativeQuery("UPDATE curso set nombre = :nombre where id = :id",CursoModel.class);
            query.setParameter("nombre", curso.getNombre());
            query.setParameter("id", id);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e){
            if (tx!=null) {
                tx.rollback();
            }
        }
    }
}
