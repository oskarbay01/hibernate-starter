import com.oskarbay.entity.PersonalInfo;
import com.oskarbay.entity.User;
import com.oskarbay.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.PersistenceException;

@Slf4j
public class HibernateRunner {
    public static void main(String[] args) throws PersistenceException {

        var user = User.builder()
                .username("serik@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .firstname("serik")
                        .lastname("serikov")
                        .build())
                .build();
        log.info("User entity is it transient state,object: {}",user);


        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            try (Session session1 = sessionFactory.openSession()) {
                var transaction = session1.beginTransaction();
                log.trace("Transaction is created, {}",transaction);

                session1.saveOrUpdate(user);

                log.trace("User is in presistent state: {},session:{}",user,session1);

                session1.getTransaction().commit();
            }
            try (Session session2 = sessionFactory.openSession()) {
                session2.beginTransaction();
                // session2.delete(user);
                // session2.refresh(user);
                var merge = session2.merge(user);

                session2.getTransaction().commit();
            }

        }
    }
}
