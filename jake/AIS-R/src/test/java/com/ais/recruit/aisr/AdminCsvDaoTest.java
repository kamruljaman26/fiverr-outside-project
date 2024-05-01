package com.ais.recruit.aisr;

import com.ais.recruit.aisr.dao.AdminCsvDao;
import com.ais.recruit.aisr.model.Admin;
import com.ais.recruit.aisr.model.enums.Position;
import org.junit.jupiter.api.*;
import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminCsvDaoTest {

    private static AdminCsvDao adminDao = new AdminCsvDao();
    private static final String testEmail = "test.admin@example.com";
    private static Admin testAdmin = new Admin("Test Admin", "Test Address", "000-000-0000",
            testEmail, "testadmin", "testpass", 101, Position.FULL_TIME);

    @BeforeAll
    static void setup() {
        adminDao.load(); // Assuming this cleans the previous test data or starts from a known state
    }

    @Test
    @Order(1)
    void testAddAdmin() {
        adminDao.addOrUpdate(testAdmin);
        adminDao.save();
        Admin retrieved = adminDao.getById(testEmail);
        assertNotNull(retrieved, "Admin should be added and retrieved successfully.");
    }

    @Test
    @Order(2)
    void testGetAdmin() {
        Admin retrieved = adminDao.getById(testEmail);
        assertNotNull(retrieved, "Admin should be retrieved successfully.");
        assertEquals(testAdmin.getEmail(), retrieved.getEmail(), "Email should match.");
    }

    @Test
    @Order(3)
    void testUpdateAdmin() {
        Admin updatedAdmin = new Admin("Updated Admin", "Updated Address", "111-111-1111",
                testEmail, "updatedadmin", "updatedpass", 102, Position.PART_TIME);
        adminDao.addOrUpdate(updatedAdmin);
        adminDao.save();
        Admin retrieved = adminDao.getById(testEmail);
        assertEquals("Updated Admin", retrieved.getFullName(), "Admin name should be updated.");
        assertEquals(Position.PART_TIME, retrieved.getPosition(), "Admin position should be updated.");
    }

    @Test
    @Order(4)
    void testDeleteAdmin() {
        adminDao.remove(testAdmin);
        adminDao.save();
        Admin retrieved = adminDao.getById(testEmail);
        assertNull(retrieved, "Admin should be deleted successfully.");
    }

    @Test
    @Order(5)
    void testGetAllAdmins() {
        List<Admin> admins = adminDao.getAll();
        assertTrue(admins.isEmpty(), "Admin list should be empty after deletion.");
    }
}
