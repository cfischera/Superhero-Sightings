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
public class SightingDAOTest {
    @Autowired
    SuperpowerDAO superpowerDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    LocationDAO locationDAO;

    public SightingDAOTest() {
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
    public void testAddGetSighting() {
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
        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting.setSuperhuman(superhuman);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        Assert.assertEquals(sighting, fromDao);
    }

    @Test
    public void testGetAllSightings() {
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

        Sighting sighting1 = new Sighting();
        sighting1.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting1.setSuperhuman(superhuman);
        sighting1.setLocation(location);
        sighting1 = sightingDAO.addSighting(sighting1);

        Sighting sighting2 = new Sighting();
        sighting2.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting2.setSuperhuman(superhuman);
        sighting2.setLocation(location);
        sighting2 = sightingDAO.addSighting(sighting2);

        List<Sighting> sightings = sightingDAO.getAllSightings();

        Assert.assertEquals(2, sightings.size());
        Assert.assertTrue(sightings.contains(sighting1));
        Assert.assertTrue(sightings.contains(sighting2));
    }

    @Test
    public void testUpdateSighting() {
        Superpower superpower1 = new Superpower();
        superpower1.setName("Test Superpower");
        superpower1 = superpowerDAO.addSuperpower(superpower1);

        Superpower superpower2 = new Superpower();
        superpower2.setName("Test Superpower");
        superpower2 = superpowerDAO.addSuperpower(superpower2);

        Superhuman superhuman1 = new Superhuman();
        superhuman1.setName("Test Superhuman");
        superhuman1.setDescription("Test Description");
        superhuman1.setSuperpower(superpower1);
        superhuman1 = superhumanDAO.addSuperhuman(superhuman1);

        Superhuman superhuman2 = new Superhuman();
        superhuman2.setName("Test Superhuman");
        superhuman2.setDescription("Test Description");
        superhuman2.setSuperpower(superpower2);
        superhuman2 = superhumanDAO.addSuperhuman(superhuman2);

        Location location1 = new Location();
        location1.setName("Test Location");
        location1.setDescription("Test Description");
        location1.setAddress("Test Address");
        location1.setLatitude(12.123456);
        location1.setLongitude(123.123456);
        location1 = locationDAO.addLocation(location1);

        Location location2 = new Location();
        location2.setName("Test Location");
        location2.setDescription("Test Description");
        location2.setAddress("Test Address");
        location2.setLatitude(-12.123456);
        location2.setLongitude(-123.123456);
        location2 = locationDAO.addLocation(location2);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting.setSuperhuman(superhuman1);
        sighting.setLocation(location1);
        sighting = sightingDAO.addSighting(sighting);

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        Assert.assertEquals(sighting, fromDao);

        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusDays(1L));
        sighting.setSuperhuman(superhuman2);
        sighting.setLocation(location2);
        sightingDAO.updateSighting(sighting);

        Assert.assertNotEquals(sighting, fromDao);

        fromDao = sightingDAO.getSightingById(sighting.getId());

        Assert.assertEquals(sighting, fromDao);
    }

    @Test
    public void testDeleteSightingById() {
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

        Sighting fromDao = sightingDAO.getSightingById(sighting.getId());
        Assert.assertEquals(sighting, fromDao);

        sightingDAO.deleteSightingById(sighting.getId());

        fromDao = sightingDAO.getSightingById(sighting.getId());

        Assert.assertNull(fromDao);
    }
}
