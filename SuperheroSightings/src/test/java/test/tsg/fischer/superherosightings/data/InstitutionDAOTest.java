package test.tsg.fischer.superherosightings.data;

import com.tsg.fischer.superherosightings.TestAppConfiguration;
import com.tsg.fischer.superherosightings.data.*;
import com.tsg.fischer.superherosightings.model.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestAppConfiguration.class)
public class InstitutionDAOTest {
    @Autowired
    SuperpowerDAO superpowerDAO;

    @Autowired
    SuperhumanDAO superhumanDAO;

    @Autowired
    SightingDAO sightingDAO;

    @Autowired
    LocationDAO locationDAO;

    @Autowired
    InstitutionDAO institutionDAO;

    public InstitutionDAOTest() {
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

        List<Institution> institutions = institutionDAO.getAllInstitutions();
        for (Institution institution : institutions) {
            institutionDAO.deleteInstitutionById(institution.getId());
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
    public void testAddGetInstitution() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman1 = new Superhuman();
        superhuman1.setName("Test Superhuman");
        superhuman1.setDescription("Test Description");
        superhuman1.setSuperpower(superpower);
        superhuman1 = superhumanDAO.addSuperhuman(superhuman1);

        Superhuman superhuman2 = new Superhuman();
        superhuman2.setName("Test Superhuman");
        superhuman2.setDescription("Test Description");
        superhuman2.setSuperpower(superpower);
        superhuman2 = superhumanDAO.addSuperhuman(superhuman2);

        List<Superhuman> superhumans = new ArrayList<>();
        superhumans.add(superhuman1);
        superhumans.add(superhuman2);

        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setDescription("Test Description");
        institution.setContactInfo("Test Contact Info");
        institution.setLocation(location);
        institution.setSuperhumans(superhumans);
        institution = institutionDAO.addInstitution(institution);

        Institution fromDao = institutionDAO.getInstitutionById(institution.getId());
        Assert.assertEquals(institution, fromDao);
    }

    @Test
    public void testGetAllInstitutions() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman1 = new Superhuman();
        superhuman1.setName("Test Superhuman");
        superhuman1.setDescription("Test Description");
        superhuman1.setSuperpower(superpower);
        superhuman1 = superhumanDAO.addSuperhuman(superhuman1);

        Superhuman superhuman2 = new Superhuman();
        superhuman2.setName("Test Superhuman");
        superhuman2.setDescription("Test Description");
        superhuman2.setSuperpower(superpower);
        superhuman2 = superhumanDAO.addSuperhuman(superhuman2);

        List<Superhuman> superhumans = new ArrayList<>();
        superhumans.add(superhuman1);
        superhumans.add(superhuman2);

        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Institution institution1 = new Institution();
        institution1.setName("Test Institution");
        institution1.setDescription("Test Description");
        institution1.setContactInfo("Test Contact Info");
        institution1.setLocation(location);
        institution1.setSuperhumans(superhumans);
        institution1 = institutionDAO.addInstitution(institution1);

        Institution institution2 = new Institution();
        institution2.setName("Test Institution");
        institution2.setDescription("Test Description");
        institution2.setContactInfo("Test Contact Info");
        institution2.setLocation(location);
        institution2.setSuperhumans(superhumans);
        institution2 = institutionDAO.addInstitution(institution2);

        List<Institution> institutions = institutionDAO.getAllInstitutions();

        Assert.assertEquals(2, institutions.size());
        Assert.assertTrue(institutions.contains(institution1));
        Assert.assertTrue(institutions.contains(institution2));
    }

    @Test
    public void testUpdateInstitution() {
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

        List<Superhuman> superhumans1 = new ArrayList<>();
        superhumans1.add(superhuman1);
        superhumans1.add(superhuman2);

        Superhuman superhuman3 = new Superhuman();
        superhuman3.setName("Test Superhuman");
        superhuman3.setDescription("Test Description");
        superhuman3.setSuperpower(superpower1);
        superhuman3 = superhumanDAO.addSuperhuman(superhuman3);

        Superhuman superhuman4 = new Superhuman();
        superhuman4.setName("Test Superhuman");
        superhuman4.setDescription("Test Description");
        superhuman4.setSuperpower(superpower2);
        superhuman4 = superhumanDAO.addSuperhuman(superhuman4);

        List<Superhuman> superhumans2 = new ArrayList<>();
        superhumans2.add(superhuman3);
        superhumans2.add(superhuman4);

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

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setDescription("Test Description");
        institution.setContactInfo("Test Contact Info");
        institution.setLocation(location1);
        institution.setSuperhumans(superhumans1);
        institution = institutionDAO.addInstitution(institution);

        Institution fromDao = institutionDAO.getInstitutionById(institution.getId());
        Assert.assertEquals(institution, fromDao);

        institution.setName("New Test Institution");
        institution.setDescription("New Test Description");
        institution.setContactInfo("New Test Contact Info");
        institution.setLocation(location2);
        institution.setSuperhumans(superhumans2);
        institutionDAO.updateInstitution(institution);

        Assert.assertNotEquals(institution, fromDao);

        fromDao = institutionDAO.getInstitutionById(institution.getId());

        Assert.assertEquals(institution, fromDao);
    }

    @Test
    public void testDeleteInstitutionById() {
        Superpower superpower = new Superpower();
        superpower.setName("Test Superpower");
        superpower = superpowerDAO.addSuperpower(superpower);

        Superhuman superhuman1 = new Superhuman();
        superhuman1.setName("Test Superhuman");
        superhuman1.setDescription("Test Description");
        superhuman1.setSuperpower(superpower);
        superhuman1 = superhumanDAO.addSuperhuman(superhuman1);

        Superhuman superhuman2 = new Superhuman();
        superhuman2.setName("Test Superhuman");
        superhuman2.setDescription("Test Description");
        superhuman2.setSuperpower(superpower);
        superhuman2 = superhumanDAO.addSuperhuman(superhuman2);

        List<Superhuman> superhumans = new ArrayList<>();
        superhumans.add(superhuman1);
        superhumans.add(superhuman2);

        Location location = new Location();
        location.setName("Test Location");
        location.setDescription("Test Description");
        location.setAddress("Test Address");
        location.setLatitude(12.123456);
        location.setLongitude(123.123456);
        location = locationDAO.addLocation(location);

        Institution institution = new Institution();
        institution.setName("Test Institution");
        institution.setDescription("Test Description");
        institution.setContactInfo("Test Contact Info");
        institution.setLocation(location);
        institution.setSuperhumans(superhumans);
        institution = institutionDAO.addInstitution(institution);

        Institution fromDao = institutionDAO.getInstitutionById(institution.getId());
        Assert.assertEquals(institution, fromDao);

        institutionDAO.deleteInstitutionById(institution.getId());

        fromDao = institutionDAO.getInstitutionById(institution.getId());

        Assert.assertNull(fromDao);
    }
}
