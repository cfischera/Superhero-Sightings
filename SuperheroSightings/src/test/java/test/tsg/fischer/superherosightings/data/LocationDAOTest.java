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
public class LocationDAOTest {
    @Autowired
    LocationDAO locationDAO;

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    SuperpowerDAO superpowerDAO;

    public LocationDAOTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Location> locations = locationDAO.getAllLocations();
        for (Location location : locations) {
            locationDAO.deleteLocationById(location.getId());
        }

        List<Sighting> sightings = sightingDAO.getAllSightings();
        for (Sighting sighting : sightings) {
            sightingDAO.deleteSightingById(sighting.getId());
        }

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
    public void testAddGetLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Location fromDao = locationDAO.getLocationById(location.getId());
        Assert.assertEquals(location, fromDao);
    }

    @Test
    public void testGetAllLocations() {
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
        location2.setLatitude(12.123456);
        location2.setLongitude(123.123456);
        location2 = locationDAO.addLocation(location2);

        List<Location> locations = locationDAO.getAllLocations();

        Assert.assertEquals(2, locations.size());
        Assert.assertTrue(locations.contains(location1));
        Assert.assertTrue(locations.contains(location2));
    }

    @Test
    public void testUpdateLocation() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Location fromDao = locationDAO.getLocationById(location.getId());
        Assert.assertEquals(location, fromDao);

        location.setName("New Test Location");
        location.setDescription("New Test Description");
        location.setAddress("New Test Address");
        location.setLatitude(-12.123456);
        location.setLongitude(-123.123456);
        locationDAO.updateLocation(location);

        Assert.assertNotEquals(location, fromDao);

        fromDao = locationDAO.getLocationById(location.getId());

        Assert.assertEquals(location, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman = new Superhuman();
        superhuman.setName("Test Superhuman");
        superhuman.setDescription("Test Description");
        superhuman.setSuperpower(superpower);
        superhuman = superhumanDAO.addSuperhuman(superhuman);

        Sighting sighting = new Sighting();
        sighting.setDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        sighting.setSuperhuman(superhuman);
        sighting.setLocation(location);
        sighting = sightingDAO.addSighting(sighting);

        Location fromDao = locationDAO.getLocationById(location.getId());
        Assert.assertEquals(location, fromDao);

        locationDAO.deleteLocationById(location.getId());

        fromDao = locationDAO.getLocationById(location.getId());
        sighting = sightingDAO.getSightingById(sighting.getId());

        Assert.assertNull(fromDao);
        Assert.assertNull(sighting);
    }
}
