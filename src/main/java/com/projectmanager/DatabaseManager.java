package com.projectmanager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DatabaseManager {
    private static final Logger logger = Logger.getLogger(DatabaseManager.class.getName());
    private static final String DB_URL = "jdbc:h2:./projectmanager;DB_CLOSE_ON_EXIT=FALSE";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "";
    
    private static DatabaseManager instance;
    private Connection connection;
    
    private DatabaseManager() {
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    private void initializeDatabase() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            createTables();
            logger.info("Database connection established and tables created/verified");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to initialize database", e);
            throw new RuntimeException("Database initialization failed", e);
        }
    }
    
    private void createTables() throws SQLException {
        // Create users table
        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(50) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        
        // Create projects table
        String createProjectsTable = """
            CREATE TABLE IF NOT EXISTS projects (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                description TEXT,
                due_date DATE,
                budget VARCHAR(50),
                created_by INT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (created_by) REFERENCES users(id)
            )
            """;
        
        // Create tasks table
        String createTasksTable = """
            CREATE TABLE IF NOT EXISTS tasks (
                id INT AUTO_INCREMENT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                description TEXT,
                due_date DATE,
                priority VARCHAR(20) DEFAULT 'Moyenne',
                assignee INT,
                status VARCHAR(50) DEFAULT 'Non commencé',
                project_id INT,
                created_by INT,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                FOREIGN KEY (assignee) REFERENCES users(id),
                FOREIGN KEY (project_id) REFERENCES projects(id),
                FOREIGN KEY (created_by) REFERENCES users(id)
            )
            """;
        
        // Create messages table
        String createMessagesTable = """
            CREATE TABLE IF NOT EXISTS messages (
                id INT AUTO_INCREMENT PRIMARY KEY,
                sender INT NOT NULL,
                receiver INT NOT NULL,
                content TEXT NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                is_read BOOLEAN DEFAULT FALSE,
                FOREIGN KEY (sender) REFERENCES users(id),
                FOREIGN KEY (receiver) REFERENCES users(id)
            )
            """;
        
        // Create notifications table
        String createNotificationsTable = """
            CREATE TABLE IF NOT EXISTS notifications (
                id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                message TEXT NOT NULL,
                timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                is_read BOOLEAN DEFAULT FALSE,
                FOREIGN KEY (user_id) REFERENCES users(id)
            )
            """;
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createUsersTable);
            stmt.execute(createProjectsTable);
            stmt.execute(createTasksTable);
            stmt.execute(createMessagesTable);
            stmt.execute(createNotificationsTable);
        }
    }
    
    // User operations
    public boolean createUser(String username, String password, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            int rowsAffected = stmt.executeUpdate();
            logger.info("User created: " + username + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create user: " + username, e);
            return false;
        }
    }
    
    public User getUser(String username, String password) {
        String sql = "SELECT id, username, password, role FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get user: " + username, e);
        }
        return null;
    }
    
    public User getUserById(int id) {
        String sql = "SELECT id, username, password, role FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get user by id: " + id, e);
        }
        return null;
    }
    
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, password, role FROM users";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get all users", e);
        }
        return users;
    }
    
    // Project operations
    public boolean createProject(String name, String description, String dueDate, String budget, int createdBy) {
        String sql = "INSERT INTO projects (name, description, due_date, budget, created_by) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDate(3, dueDate != null ? Date.valueOf(dueDate) : null);
            stmt.setString(4, budget);
            stmt.setInt(5, createdBy);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Project created: " + name + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create project: " + name, e);
            return false;
        }
    }
    
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, p.due_date, p.budget, p.created_by, u.username as created_by_name FROM projects p LEFT JOIN users u ON p.created_by = u.id";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                projects.add(new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null,
                    rs.getString("budget"),
                    rs.getInt("created_by"),
                    rs.getString("created_by_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get all projects", e);
        }
        return projects;
    }
    
    public List<Project> getUserProjects(int userId) {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.description, p.due_date, p.budget, p.created_by, u.username as created_by_name FROM projects p LEFT JOIN users u ON p.created_by = u.id WHERE p.created_by = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                projects.add(new Project(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null,
                    rs.getString("budget"),
                    rs.getInt("created_by"),
                    rs.getString("created_by_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get user projects for user: " + userId, e);
        }
        return projects;
    }
    
    // Task operations
    public boolean createTask(String name, String description, String dueDate, String priority, int assignee, int projectId, int createdBy) {
        String sql = "INSERT INTO tasks (name, description, due_date, priority, assignee, project_id, created_by) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setDate(3, dueDate != null ? Date.valueOf(dueDate) : null);
            stmt.setString(4, priority);
            stmt.setInt(5, assignee);
            stmt.setInt(6, projectId);
            stmt.setInt(7, createdBy);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Task created: " + name + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create task: " + name, e);
            return false;
        }
    }
    
    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        String sql = """
            SELECT t.id, t.name, t.description, t.due_date, t.priority, t.status, t.assignee, t.project_id, t.created_by,
                   u1.username as assignee_name, u2.username as created_by_name, p.name as project_name
            FROM tasks t 
            LEFT JOIN users u1 ON t.assignee = u1.id 
            LEFT JOIN users u2 ON t.created_by = u2.id 
            LEFT JOIN projects p ON t.project_id = p.id
            """;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                tasks.add(new Task(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null,
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getInt("assignee"),
                    rs.getInt("project_id"),
                    rs.getInt("created_by"),
                    rs.getString("assignee_name"),
                    rs.getString("created_by_name"),
                    rs.getString("project_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get all tasks", e);
        }
        return tasks;
    }
    
    public List<Task> getUserTasks(int userId) {
        List<Task> tasks = new ArrayList<>();
        String sql = """
            SELECT t.id, t.name, t.description, t.due_date, t.priority, t.status, t.assignee, t.project_id, t.created_by,
                   u1.username as assignee_name, u2.username as created_by_name, p.name as project_name
            FROM tasks t 
            LEFT JOIN users u1 ON t.assignee = u1.id 
            LEFT JOIN users u2 ON t.created_by = u2.id 
            LEFT JOIN projects p ON t.project_id = p.id
            WHERE t.assignee = ?
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                tasks.add(new Task(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getDate("due_date") != null ? rs.getDate("due_date").toLocalDate() : null,
                    rs.getString("priority"),
                    rs.getString("status"),
                    rs.getInt("assignee"),
                    rs.getInt("project_id"),
                    rs.getInt("created_by"),
                    rs.getString("assignee_name"),
                    rs.getString("created_by_name"),
                    rs.getString("project_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get user tasks for user: " + userId, e);
        }
        return tasks;
    }
    
    public boolean updateTaskStatus(int taskId, String status) {
        String sql = "UPDATE tasks SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setInt(2, taskId);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Task status updated: " + taskId + " to " + status + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to update task status for task: " + taskId, e);
            return false;
        }
    }
    
    // Message operations
    public boolean createMessage(int sender, int receiver, String content) {
        String sql = "INSERT INTO messages (sender, receiver, content) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, sender);
            stmt.setInt(2, receiver);
            stmt.setString(3, content);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Message created from " + sender + " to " + receiver + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create message from " + sender + " to " + receiver, e);
            return false;
        }
    }
    
    public List<Message> getMessagesBetweenUsers(int user1, int user2) {
        List<Message> messages = new ArrayList<>();
        String sql = """
            SELECT m.id, m.sender, m.receiver, m.content, m.timestamp, m.is_read,
                   u1.username as sender_name, u2.username as receiver_name
            FROM messages m
            LEFT JOIN users u1 ON m.sender = u1.id
            LEFT JOIN users u2 ON m.receiver = u2.id
            WHERE (m.sender = ? AND m.receiver = ?) OR (m.sender = ? AND m.receiver = ?)
            ORDER BY m.timestamp ASC
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, user1);
            stmt.setInt(2, user2);
            stmt.setInt(3, user2);
            stmt.setInt(4, user1);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("id"),
                    rs.getInt("sender"),
                    rs.getInt("receiver"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getBoolean("is_read"),
                    rs.getString("sender_name"),
                    rs.getString("receiver_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get messages between users " + user1 + " and " + user2, e);
        }
        return messages;
    }
    
    public List<Message> getUserMessages(int userId) {
        List<Message> messages = new ArrayList<>();
        String sql = """
            SELECT m.id, m.sender, m.receiver, m.content, m.timestamp, m.is_read,
                   u1.username as sender_name, u2.username as receiver_name
            FROM messages m
            LEFT JOIN users u1 ON m.sender = u1.id
            LEFT JOIN users u2 ON m.receiver = u2.id
            WHERE m.sender = ? OR m.receiver = ?
            ORDER BY m.timestamp DESC
            """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("id"),
                    rs.getInt("sender"),
                    rs.getInt("receiver"),
                    rs.getString("content"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getBoolean("is_read"),
                    rs.getString("sender_name"),
                    rs.getString("receiver_name")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get user messages for user: " + userId, e);
        }
        return messages;
    }
    
    // Notification operations
    public boolean createNotification(int userId, String message) {
        String sql = "INSERT INTO notifications (user_id, message) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Notification created for user " + userId + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to create notification for user: " + userId, e);
            return false;
        }
    }
    
    public List<Notification> getUserNotifications(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT id, user_id, message, timestamp, is_read FROM notifications WHERE user_id = ? ORDER BY timestamp DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getTimestamp("timestamp").toLocalDateTime(),
                    rs.getBoolean("is_read")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to get notifications for user: " + userId, e);
        }
        return notifications;
    }
    
    public boolean markNotificationAsRead(int notificationId) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Notification marked as read: " + notificationId + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to mark notification as read: " + notificationId, e);
            return false;
        }
    }
    
    public boolean markAllNotificationsAsRead(int userId) {
        String sql = "UPDATE notifications SET is_read = TRUE WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            logger.info("All notifications marked as read for user: " + userId + ", rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to mark all notifications as read for user: " + userId, e);
            return false;
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Database connection closed");
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Failed to close database connection", e);
            }
        }
    }
}
