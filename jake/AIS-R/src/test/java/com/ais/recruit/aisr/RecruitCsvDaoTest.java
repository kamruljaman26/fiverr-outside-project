package com.ais.recruit.aisr;

import com.ais.recruit.aisr.dao.RecruitCsvDao;
import com.ais.recruit.aisr.model.Recruit;
import com.ais.recruit.aisr.model.enums.Branch;
import com.ais.recruit.aisr.model.enums.Level;
import org.junit.jupiter.api.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RecruitCsvDaoTest {

    private static RecruitCsvDao recruitDao = new RecruitCsvDao();
    private static final String testEmail = "test.recruit@example.com";
    private static Recruit testRecruit = new Recruit(
            "Test Recruit", "Test Address", "000-222-3333",
            testEmail, "testrecruit", "recruitpass",
            "Bachelor's in Computer Science", "Software Developer", "Master's in Software Engineering",
            LocalDateTime.of(2023, 5, 25, 14, 0), Level.MID_LEVEL_MANAGER, Branch.MELBOURNE);

    @BeforeAll
    static void setup() {
        recruitDao.load(); // Load or clear previous data
    }

    @Test
    @Order(1)
    void testAddRecruit() {
        recruitDao.addOrUpdate(testRecruit);
        recruitDao.save();
        Recruit retrieved = recruitDao.getById(testEmail);
        assertNotNull(retrieved, "Recruit should be added and retrieved successfully.");
        assertEquals(testRecruit.getEmail(), retrieved.getEmail(), "Email should match.");
    }

    @Test
    @Order(2)
    void testGetRecruit() {
        Recruit retrieved = recruitDao.getById(testEmail);
        assertNotNull(retrieved, "Recruit should be retrieved successfully.");
        assertEquals(testRecruit.getDegree(), retrieved.getDegree(), "Degree should match.");
    }

    @Test
    @Order(3)
    void testUpdateRecruit() {
        Recruit updatedRecruit = new Recruit(
                "Updated Recruit", "Updated Address", "111-222-3333",
                testEmail, "updatedrecruit", "updatedpass",
                "Bachelor's in AI", "AI Developer", "PhD in AI",
                LocalDateTime.of(2023, 10, 5, 10, 30), Level.SENIOR_MANAGER, Branch.SYDNEY);
        recruitDao.addOrUpdate(updatedRecruit);
        recruitDao.save();
        Recruit retrieved = recruitDao.getById(testEmail);
        assertEquals("Updated Recruit", retrieved.getFullName(), "Recruit name should be updated.");
        assertEquals(Level.SENIOR_MANAGER, retrieved.getLevel(), "Recruit level should be updated.");
    }

    @Test
    @Order(4)
    void testDeleteRecruit() {
        recruitDao.remove(testRecruit);
        recruitDao.save();
        Recruit retrieved = recruitDao.getById(testEmail);
        assertNull(retrieved, "Recruit should be deleted successfully.");
    }

    @Test
    @Order(5)
    void testGetAllRecruits() {
        List<Recruit> recruits = recruitDao.getAll();
        assertTrue(recruits.isEmpty(), "Recruit list should be empty after deletion.");
    }
}
