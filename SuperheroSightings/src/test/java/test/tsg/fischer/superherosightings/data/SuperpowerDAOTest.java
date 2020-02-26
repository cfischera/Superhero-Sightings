package test.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.TestAppConfiguration;
import com.tsg.fischer.superherosightings.data.SuperhumanDAO;
import com.tsg.fischer.superherosightings.data.SuperpowerDAO;
import com.tsg.fischer.superherosightings.model.Superhuman;
import com.tsg.fischer.superherosightings.model.Superpower;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAppConfiguration.class)
public class SuperpowerDAOTest {
    @Autowired
    SuperpowerDAO superpowerDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    public SuperpowerDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();
        for (Superpower superpower : superpowers) {
            superpowerDAO.deleteSuperpowerById(superpower.getId());
        }

        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();
        for (Superhuman superhuman : superhumans) {
            superhumanDAO.deleteSuperhumanById(superhuman.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superpower fromDao = superpowerDAO.getSuperpowerById(superpower.getId());
        Assert.assertEquals(superpower, fromDao);
    }

    @Test
    public void testGetAllSuperpowers() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower 1");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superpower superpower2 = new Superpower();
        superpower2.setName("Test Superpower 2");
        superpower2 = superpowerDAO.addSuperpower(superpower2);

        List<Superpower> superpowers = superpowerDAO.getAllSuperpowers();

        Assert.assertEquals(2, superpowers.size());
        Assert.assertTrue(superpowers.contains(superpower));
        Assert.assertTrue(superpowers.contains(superpower2));
    }

    @Test
    public void testUpdateSuperpower() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superpower fromDao = superpowerDAO.getSuperpowerById(superpower.getId());
        Assert.assertEquals(superpower, fromDao);

        superpower.setName("New Test Superpower");
        superpowerDAO.updateSuperpower(superpower);

        Assert.assertNotEquals(superpower, fromDao);

        fromDao = superpowerDAO.getSuperpowerById(superpower.getId());

        Assert.assertEquals(superpower, fromDao);
    }

    @Test
    public void testDeleteSuperpowerById() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman = new Superhuman();
        superhuman.setName("Test Superhuman");
        superhuman.setDescription("A temporary Superhuman Object to test with creating and deleting a Superpower");
        superhuman.setSuperpower(superpower);
        superhuman = superhumanDAO.addSuperhuman(superhuman);

        Superpower fromDao = superpowerDAO.getSuperpowerById(superpower.getId());
        Assert.assertEquals(superpower, fromDao);

        superpowerDAO.deleteSuperpowerById(superpower.getId());

        fromDao = superpowerDAO.getSuperpowerById(superpower.getId());
        superhuman = superhumanDAO.getSuperhumanById(superhuman.getId());

        Assert.assertNull(fromDao);
        Assert.assertNull(superhuman);
    }
}
