package yamanov.database;

import yamanov.database.DAO.InboxDao;
import yamanov.database.entities.Inbox;

public class InboxService {
    private static InboxDao inboxDao;

    public InboxService() {
        inboxDao = new InboxDao();
    }

    public void insert(Inbox entity) {
        inboxDao.openCurrentSessionWithTransaction();
        inboxDao.insert(entity);
        inboxDao.closeCurrentSessionWithTransaction();
    }

    public void update(Inbox entity) {
        inboxDao.openCurrentSessionWithTransaction();
        inboxDao.update(entity);
        inboxDao.closeCurrentSessionWithTransaction();
    }
}
