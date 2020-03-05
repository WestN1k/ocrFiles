package yamanov.database;

import yamanov.database.DAO.InboxDao;
import yamanov.database.entities.Inbox;

import java.util.List;

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

    public boolean bulkInsert(List<Inbox> inboxList) {

        try {
            for (Inbox inbox : inboxList) {
                if (notValid(inbox)) {
                    return false;
                } else {
                    insert(inbox);
                }
            }
            return true;
        } catch (Exception e){
            e.printStackTrace();
            inboxDao.closeCurrentSessionWithTransaction();
            return false;
        }
    }

    private boolean notValid(Inbox inbox) {
        return inbox.customer() == null
                || inbox.getDocDate() == null
                || inbox.getDocNumber() == null;
//                || inbox.getcSprOrganization() == 0;
    }
}
