package com.projectmanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ProjectManagerAppTest {

    private DatabaseManager dbManager;
    private Connection testConnection;

    @BeforeEach
    public void setUp() throws SQLException {
        // Initialize the database manager
        dbManager = DatabaseManager.getInstance();
        
        // Get connection for testing
        testConnection = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
    }

    @Test
    public void testDatabaseInitialization() {
        assertNotNull(dbManager, "DatabaseManager should be initialized");
        assertNotNull(testConnection, "Test connection should be established");
    }

    @Test
    public void testUserCreation() {
        boolean result = dbManager.createUser("testuser", "testpass", "MEMBRE_D_EQUIPE");
        assertTrue(result, "User should be created successfully");
    }

    @Test
    public void testUserAuthentication() {
        // Create a test user
        boolean createResult = dbManager.createUser("testuser", "testpass", "MEMBRE_D_EQUIPE");
        assertTrue(createResult, "User should be created successfully");
        
        // Try to authenticate
        User user = dbManager.getUser("testuser", "testpass");
        assertNotNull(user, "User should be found");
        assertEquals("testuser", user.getUsername(), "Username should match");
        assertEquals("MEMBRE_D_EQUIPE", user.getRole(), "Role should match");
    }

    @Test
    public void testProjectCreation() {
        // Create a test user first
        boolean userCreated = dbManager.createUser("testuser", "testpass", "GESTIONNAIRE_DE_PROJET");
        assertTrue(userCreated, "User should be created successfully");
        
        User user = dbManager.getUser("testuser", "testpass");
        assertNotNull(user, "User should be found");
        
        // Create a test project
        boolean projectCreated = dbManager.createProject("Test Project", "Test Description", "2025-12-31", "10000", user.getId());
        assertTrue(projectCreated, "Project should be created successfully");
    }

    @Test
    public void testTaskCreation() {
        // Create a test user
        boolean userCreated = dbManager.createUser("testuser", "testpass", "GESTIONNAIRE_DE_PROJET");
        assertTrue(userCreated, "User should be created successfully");
        
        User user = dbManager.getUser("testuser", "testpass");
        assertNotNull(user, "User should be found");
        
        // Create a test task
        boolean taskCreated = dbManager.createTask("Test Task", "Test Description", "2025-12-31", "Haute", user.getId(), 0, user.getId());
        assertTrue(taskCreated, "Task should be created successfully");
    }

    @Test
    public void testGetAllUsers() {
        // Create test users
        dbManager.createUser("user1", "pass1", "MEMBRE_D_EQUIPE");
        dbManager.createUser("user2", "pass2", "GESTIONNAIRE_DE_PROJET");
        
        // Get all users
        List<User> users = dbManager.getAllUsers();
        assertNotNull(users, "Users list should not be null");
        assertTrue(users.size() >= 2, "Should have at least 2 users");
    }

    @Test
    public void testGetAllProjects() {
        // Create a test user and project
        dbManager.createUser("testuser", "testpass", "GESTIONNAIRE_DE_PROJET");
        User user = dbManager.getUser("testuser", "testpass");
        
        dbManager.createProject("Project 1", "Description 1", "2025-12-31", "1000", user.getId());
        dbManager.createProject("Project 2", "Description 2", "2026-01-15", "2000", user.getId());
        
        // Get all projects
        List<Project> projects = dbManager.getAllProjects();
        assertNotNull(projects, "Projects list should not be null");
        assertTrue(projects.size() >= 2, "Should have at least 2 projects");
    }

    @AfterEach
    public void tearDown() throws SQLException {
        if (testConnection != null && !testConnection.isClosed()) {
            testConnection.close();
        }
        if (dbManager != null) {
            dbManager.close();
        }
    }
}
