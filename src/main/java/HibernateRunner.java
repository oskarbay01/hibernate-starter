import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.oskarbay.entity.Role;
import com.oskarbay.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.Configuration;

import javax.persistence.PersistenceException;
import java.sql.SQLException;
import java.time.LocalDate;

public class HibernateRunner {
    public static void main(String[] args)  throws PersistenceException {
        Configuration configuration = new Configuration();
//        configuration.setPhysicalNamingStrategy( new CamelCaseToUnderscoresNamingStrategy());
//        configuration.addAnnotatedClass(User.class);
        configuration.registerTypeOverride(new JsonBinaryType());
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("ivan3@gmail.com")
                    .firstname("Ivan")
                    .lastname("Ivanov")
                    .info("""
                            {
                                "name": "Ivan",
                                "id": 274
                            }
                            """)
                    .birthDate(LocalDate.of(2000, 1, 19))
                    .role(Role.ADMIN)
                    .age(20)
                    .build();

            session.delete(user);

            session.getTransaction().commit();
        }
    }
}
