package application;

import application.com.cn.orm.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManagerFactory;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HibernateApplicationTests {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    private Session session;
    private Transaction tr;

    @Before
    public void before() {
        session = entityManagerFactory.unwrap(SessionFactory.class).openSession();
        tr = null != session.getTransaction() && session.getTransaction().isActive() ? session.getTransaction()
                : session.beginTransaction();
    }

    @After
    public void after() {
        tr.commit();
        if (null != session) {
            session.close();
        }
    }

    @Test
    public void testSave(){
        User user = new User("zhangsan",10,"aa@qq.com");
        session.save(user);
    }

    @Test
    public void testLoad(){
        User user = session.load(User.class,1);
        user.setName("Java");
        System.out.println(user);

        User user2 = session.load(User.class, 1);
        System.out.println(user2);
    }

}
