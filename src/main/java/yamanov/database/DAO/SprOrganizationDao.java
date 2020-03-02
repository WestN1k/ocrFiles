package yamanov.database.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import yamanov.database.HibernateUtils;
import yamanov.database.entities.Inbox;
import yamanov.database.entities.SprOrganization;

import java.util.List;

public class SprOrganizationDao implements DaoInterface<SprOrganization, Integer>{
    private Session currentSession;
    private Transaction currentTransaction;

    public SprOrganizationDao(){ }

    public SessionFactory getSessionFactory(){
        return HibernateUtils.getSessionFactory();
    }

    public void openCurrentSession() {
        currentSession = getSessionFactory().openSession();
    }

    public void closeCurrentSession() {
        currentSession.close();
    }

    public void openCurrentSessionWithTransaction() {
        currentSession = getSessionFactory().openSession();
        currentTransaction = currentSession.beginTransaction();
    }

    public void closeCurrentSessionWithTransaction() {
        currentTransaction.commit();
        currentSession.close();
    }

    public Session getCurrentSession() {
        return currentSession;
    }

    @Override
    public void insert(SprOrganization entity) {
        openCurrentSessionWithTransaction();
        getCurrentSession().save(entity);
        closeCurrentSessionWithTransaction();
    }

    @Override
    public void update(SprOrganization entity) {
        openCurrentSessionWithTransaction();
        getCurrentSession().update(entity);
        closeCurrentSessionWithTransaction();
    }

    @Override
    public SprOrganization findById(Integer id) {
        return getCurrentSession().get(SprOrganization.class, id);
    }

    public List<SprOrganization> getOrganizationsByLike(String likeTemplate) {
        String query = "FROM SprOrganization as p WHERE p.name LIKE :orgInput";
        openCurrentSession();
        try {
            List<SprOrganization> orgs = currentSession
                    .createQuery(query, SprOrganization.class)
                    .setParameter("orgInput", "%" + likeTemplate + "%").list();
            closeCurrentSession();
            return orgs;
        } catch (NullPointerException e) {
            System.out.println("not found object from spr_organizations");
            closeCurrentSession();
            return null;
        }
    }

}
