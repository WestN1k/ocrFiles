package yamanov.database;

import org.hibernate.Session;
import yamanov.database.DAO.InboxDao;
import yamanov.database.DAO.SprOrganizationDao;
import yamanov.database.entities.SprOrganization;

import java.util.List;

public class SprOrganizationService {
    private static SprOrganizationDao sprOrganizationDao;

    public SprOrganizationService() {
        sprOrganizationDao = new SprOrganizationDao();
    }

    public List<SprOrganization> getOrganizationsByLike(String likeTemplate){
        return sprOrganizationDao.getOrganizationsByLike(likeTemplate);
    }
}
