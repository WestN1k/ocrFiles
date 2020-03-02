package yamanov.database.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import yamanov.database.HibernateUtils;
import yamanov.database.entities.Inbox;


public class InboxDao implements DaoInterface<Inbox, Integer> {
    private Session currentSession;
    private Transaction currentTransaction;

    public InboxDao() {
    }

    public SessionFactory getSessionFactory(){
        return HibernateUtils.getSessionFactory();
//        Configuration configuration = new Configuration().configure();
//        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
//                .applySettings(configuration.getProperties());
//        return configuration.buildSessionFactory(builder.build());
    }

    public Session openCurrentSession() {
        currentSession = getSessionFactory().openSession();
        return getSessionFactory().openSession();
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

    public void setCurrentSession(Session currentSession) {
        this.currentSession = currentSession;
    }

    public Transaction getCurrentTransaction() {
        return currentTransaction;
    }

    public void setCurrentTransaction(Transaction currentTransaction) {
        this.currentTransaction = currentTransaction;
    }

    @Override
    public void insert(Inbox entity) {
        getCurrentSession().save(entity);
    }

    @Override
    public void update(Inbox entity) {
    }

    @Override
    public Inbox findById(Integer id) {
        return getCurrentSession().get(Inbox.class, id);
    }

}
