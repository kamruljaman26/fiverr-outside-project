package com.ais.recruit.aisr;

import com.ais.recruit.aisr.dao.StaffCsvDao;
import com.ais.recruit.aisr.model.Staff;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StaffCsvDaoTest {

    private static StaffCsvDao staffDao = new StaffCsvDao();
    private static final String testEmail = "test.staff@example.com";
    private static Staff testStaff = new Staff("Test Staff", "Test Address", "000-111-2222",
            testEmail, "teststaff", "staffpass", 1, Level.MID_LEVEL_MANAGER, Branch.SYDNEY);

    @BeforeAll
    static void setup() {
        // Load initial data or clear previous test data
        staffDao.load();
    }

    @Test
    @Order(1)
    void testAddStaff() {
        staffDao.addOrUpdate(testStaff);
        staffDao.save();
        Staff retrieved = staffDao.getById(testEmail);
        assertNotNull(retrieved, "Staff should be added and retrieved successfully.");
    }

    @Test
    @Order(2)
    void testGetStaff() {
        Staff retrieved = staffDao.getById(testEmail);
        assertNotNull(retrieved, "Staff should be retrieved successfully.");
        assertEquals(testStaff.getEmail(), retrieved.getEmail(), "Email should match.");
    }

    @Test
    @Order(3)
    void testUpdateStaff() {
        Staff updatedStaff = new Staff("Updated Staff", "Updated Address", "333-444-5555",
                testEmail, "updatedstaff", "updatedpass", 2, Level.SENIOR_MANAGER, Branch.MELBOURNE);
        staffDao.addOrUpdate(updatedStaff);
        staffDao.save();
        Staff retrieved = staffDao.getById(testEmail);
        assertEquals("Updated Staff", retrieved.getFullName(), "Staff name should be updated.");
        assertEquals(Level.SENIOR_MANAGER, retrieved.getLevel(), "Staff level should be updated.");
        assertEquals(Branch.MELBOURNE, retrieved.getBranch(), "Staff branch should be updated.");
    }

    @Test
    @Order(4)
    void testDeleteStaff() {
        staffDao.remove(testStaff);
        staffDao.save();
        Staff retrieved = staffDao.getById(testEmail);
        assertNull(retrieved, "Staff should be deleted successfully.");
    }

    @Test
    @Order(5)
    void testGetAllStaff() {
        List<Staff> staffList = staffDao.getAll();
        assertTrue(staffList.isEmpty(), "Staff list should be empty after deletion.");
    }
}
