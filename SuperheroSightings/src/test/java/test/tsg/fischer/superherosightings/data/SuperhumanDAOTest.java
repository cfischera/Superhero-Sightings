package test.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.TestAppConfiguration;
import com.tsg.fischer.superherosightings.data.LocationDAO;
import com.tsg.fischer.superherosightings.data.SightingDAO;
import com.tsg.fischer.superherosightings.data.SuperhumanDAO;
import com.tsg.fischer.superherosightings.data.SuperpowerDAO;
import com.tsg.fischer.superherosightings.model.Location;
import com.tsg.fischer.superherosightings.model.Sighting;
import com.tsg.fischer.superherosightings.model.Superhuman;
import com.tsg.fischer.superherosightings.model.Superpower;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAppConfiguration.class)
public class SuperhumanDAOTest {
    @Autowired
    SuperpowerDAO superpowerDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    LocationDAO locationDAO;

    public SuperhumanDAOTest() {
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

        List<Sighting> sightings = sightingDAO.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDAO.deleteSightingById(sighting.getId());
        }

        List<Location> locations = locationDAO.getAllLocations();
        for (Location location : locations) {
            locationDAO.deleteLocationById(location.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetSuperhuman() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman = new Superhuman();
        superhuman.setName("Test Superhuman");
        superhuman.setDescription("Test Description");
        superhuman.setSuperpower(superpower);
        superhuman = superhumanDAO.addSuperhuman(superhuman);

        Superhuman fromDao = superhumanDAO.getSuperhumanById(superhuman.getId());
        Assert.assertEquals(superhuman, fromDao);
    }

    @Test
    public void testGetAllSuperhumans() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman1 = new Superhuman();
        superhuman1.setName("Test Superhuman 1");
        superhuman1.setDescription("Test Description");
        superhuman1.setSuperpower(superpower);
        superhuman1 = superhumanDAO.addSuperhuman(superhuman1);

        Superhuman superhuman2 = new Superhuman();
        superhuman2.setName("Test Superhuman 2");
        superhuman2.setDescription("Test Description");
        superhuman2.setSuperpower(superpower);
        superhuman2 = superhumanDAO.addSuperhuman(superhuman2);

        List<Superhuman> superhumans = superhumanDAO.getAllSuperhumans();

        Assert.assertEquals(2, superhumans.size());
        Assert.assertTrue(superhumans.contains(superhuman1));
        Assert.assertTrue(superhumans.contains(superhuman2));
    }

    @Test
    public void testUpdateSuperhuman() {
        Superpower superpower1 = new Superpower();
        superpower1.setName("Test Superpower 1");
        superpower1 = superpowerDAO.addSuperpower(superpower1);

        Superpower superpower2 = new Superpower();
        superpower2.setName("Test Superpower 2");
        superpower2 = superpowerDAO.addSuperpower(superpower2);

        Superhuman superhuman = new Superhuman();
        superhuman.setName("Test Superhuman");
        superhuman.setDescription("Test Description");
        superhuman.setSuperpower(superpower1);
        superhuman = superhumanDAO.addSuperhuman(superhuman);

        Superhuman fromDao = superhumanDAO.getSuperhumanById(superhuman.getId());
        Assert.assertEquals(superhuman, fromDao);

        superhuman.setName("New Test Superhuman");
        superhuman.setDescription("New Test Description");
        superhuman.setSuperpower(superpower2);
        superhumanDAO.updateSuperhuman(superhuman);

        Assert.assertNotEquals(superhuman, fromDao);

        fromDao = superhumanDAO.getSuperhumanById(superhuman.getId());

        Assert.assertEquals(superhuman, fromDao);
    }

    @Test
    public void testDeleteSuperhumanById() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman = new Superhuman();
        superhuman.setName("Test Superhuman");
        superhuman.setDescription("Test Description");
        superhuman.setSuperpower(superpower);
        superhuman = superhumanDAO.addSuperhuman(superhuman);

        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting.setSuperhuman(superhuman);
        sighting = sightingDAO.addSighting(sighting);

        Superhuman fromDao = superhumanDAO.getSuperhumanById(superhuman.getId());
        Assert.assertEquals(superhuman, fromDao);

        superhumanDAO.deleteSuperhumanById(superhuman.getId());

        fromDao = superhumanDAO.getSuperhumanById(superhuman.getId());
        sighting = sightingDAO.getSightingById(sighting.getId());

        Assert.assertNull(fromDao);
        Assert.assertNull(sighting);
    }
}
