package emt.emtau.repository;

import emt.emtau.model.User;
import emt.emtau.model.enumerations.Role;
import emt.emtau.model.projections.UserProjection;
import emt.emtau.repository.jpa.UserRepository;
import org.apache.catalina.realm.UserDatabaseRealm;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindAll() {
        List<User> userList = this.userRepository.findAll();
        Assert.assertEquals(2L, userList.size());
    }

    @Test
    public void testFetch() {
        List<User> userList = this.userRepository.fetchAll();
        Assert.assertEquals(2L, userList.size());
    }

    @Test
    public void testLoad() {
        List<User> userList = this.userRepository.loadAll();
        Assert.assertEquals(2L, userList.size());
    }

    @Test
    public void testProjectUsernameAndNameAndSurname(){
        UserProjection userProjection = this.userRepository.findByRole(Role.ROLE_USER);
        Assert.assertEquals("Username1", userProjection.getUsername());
        Assert.assertEquals("Username1", userProjection.getName());
        Assert.assertEquals("Username1", userProjection.getSurname());
    }
}
