package com.projectmanager;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Import standalone classes
import com.projectmanager.User;
import com.projectmanager.Project;
import com.projectmanager.Task;
import com.projectmanager.Message;
import com.projectmanager.Notification;
import com.projectmanager.DatabaseManager;

public class ProjectManagerApp extends Application {

    private boolean isLoggedIn = false;
    private String userRole = "";
    private User currentUser;
    private DatabaseManager dbManager;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        dbManager = DatabaseManager.getInstance();
        primaryStage.setTitle("Gestionnaire de Projets");
        showLoginScreen(primaryStage);
    }

    private void showLoginScreen(Stage stage) {
        VBox loginLayout = new VBox(15);
        loginLayout.setAlignment(Pos.CENTER);
        loginLayout.setPadding(new Insets(30));
        loginLayout.setSpacing(15);

        Label titleLabel = new Label("Connexion au Gestionnaire de Projets");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Username field with improved styling
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(300);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        // Password field with improved styling
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        // Login button with improved styling
        Button loginButton = new Button("Connexion");
        loginButton.setPrefWidth(300);
        loginButton.setPrefHeight(45);
        loginButton.setStyle("""
            -fx-background-color: #2196F3; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        // Sign up button with improved styling
        Button signUpButton = new Button("Inscription");
        signUpButton.setPrefWidth(300);
        signUpButton.setPrefHeight(45);
        signUpButton.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Veuillez remplir tous les champs !");
                return;
            }

            User user = dbManager.getUser(username, password);
            if (user != null) {
                currentUser = user;
                userRole = user.getRole();
                isLoggedIn = true;
                showDashboard(stage);
            } else {
                messageLabel.setText("Identifiants invalides !");
            }
        });

        signUpButton.setOnAction(e -> showSignUpScreen(stage));

        loginLayout.getChildren().addAll(titleLabel, usernameField, passwordField, loginButton, signUpButton, messageLabel);

        Scene scene = new Scene(loginLayout, 500, 450);
        stage.setScene(scene);
        stage.show();
    }

    private void showSignUpScreen(Stage stage) {
        VBox signUpLayout = new VBox(15);
        signUpLayout.setAlignment(Pos.CENTER);
        signUpLayout.setPadding(new Insets(30));
        signUpLayout.setSpacing(15);

        Label titleLabel = new Label("Inscription");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Username field with improved styling
        TextField usernameField = new TextField();
        usernameField.setPromptText("Nom d'utilisateur");
        usernameField.setMaxWidth(300);
        usernameField.setPrefHeight(40);
        usernameField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        // Password field with improved styling
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        passwordField.setMaxWidth(300);
        passwordField.setPrefHeight(40);
        passwordField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        // Role selection with improved styling
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("GESTIONNAIRE_DE_PROJET", "MEMBRE_D_EQUIPE");
        roleComboBox.setPromptText("Sélectionnez un rôle");
        roleComboBox.setMaxWidth(300);
        roleComboBox.setPrefHeight(40);
        roleComboBox.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        // Sign up button with improved styling
        Button signUpButton = new Button("S'inscrire");
        signUpButton.setPrefWidth(300);
        signUpButton.setPrefHeight(45);
        signUpButton.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        // Back button with improved styling
        Button backButton = new Button("Retour à la connexion");
        backButton.setPrefWidth(300);
        backButton.setPrefHeight(45);
        backButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Label messageLabel = new Label("");
        messageLabel.setStyle("-fx-text-fill: #e74c3c; -fx-font-size: 14px; -fx-font-weight: bold;");
        messageLabel.setWrapText(true);
        messageLabel.setMaxWidth(300);

        signUpButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();
            String role = roleComboBox.getValue();

            if (username.isEmpty() || password.isEmpty() || role == null) {
                messageLabel.setText("Veuillez remplir tous les champs !");
                return;
            }

            // Vérifie si le nom d'utilisateur existe déjà
            if (dbManager.getUser(username, password) != null) {
                messageLabel.setText("Le nom d'utilisateur existe déjà !");
                return;
            }

            // Crée le nouvel utilisateur dans la base de données
            boolean success = dbManager.createUser(username, password, role);
            if (success) {
                messageLabel.setText("Inscription réussie ! Vous pouvez maintenant vous connecter.");
                messageLabel.setStyle("-fx-text-fill: #4CAF50; -fx-font-size: 14px; -fx-font-weight: bold;");
                // Clear fields after successful signup
                usernameField.clear();
                passwordField.clear();
                roleComboBox.getSelectionModel().clearSelection();
            } else {
                messageLabel.setText("Échec de l'inscription. Veuillez réessayer.");
            }
        });

        backButton.setOnAction(e -> showLoginScreen(stage));

        signUpLayout.getChildren().addAll(titleLabel, usernameField, passwordField, roleComboBox, signUpButton, backButton, messageLabel);

        Scene scene = new Scene(signUpLayout, 500, 500);
        stage.setScene(scene);
    }

    private void showDashboard(Stage stage) {
        VBox dashboardLayout = new VBox(15);
        dashboardLayout.setAlignment(Pos.TOP_CENTER);
        dashboardLayout.setPadding(new Insets(30));
        dashboardLayout.setSpacing(15);

        Label welcomeLabel = new Label("Bienvenue " + currentUser.getUsername() + " (" + userRole + ")");
        welcomeLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Create styled buttons
        Button createProjectButton = new Button("Créer un nouveau projet");
        createProjectButton.setPrefWidth(300);
        createProjectButton.setPrefHeight(50);
        createProjectButton.setStyle("""
            -fx-background-color: #9C27B0; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button createTaskButton = new Button("Créer une nouvelle tâche");
        createTaskButton.setPrefWidth(300);
        createTaskButton.setPrefHeight(50);
        createTaskButton.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button tasksButton = new Button("Voir les tâches");
        tasksButton.setPrefWidth(300);
        tasksButton.setPrefHeight(50);
        tasksButton.setStyle("""
            -fx-background-color: #2196F3; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button viewProjectsButton = new Button("Voir les projets");
        viewProjectsButton.setPrefWidth(300);
        viewProjectsButton.setPrefHeight(50);
        viewProjectsButton.setStyle("""
            -fx-background-color: #2196F3; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button messagesButton = new Button("Messages");
        messagesButton.setPrefWidth(300);
        messagesButton.setPrefHeight(50);
        messagesButton.setStyle("""
            -fx-background-color: #FF9800; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button notificationsButton = new Button("Notifications");
        notificationsButton.setPrefWidth(300);
        notificationsButton.setPrefHeight(50);
        notificationsButton.setStyle("""
            -fx-background-color: #FF5722; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        Button logoutButton = new Button("Déconnexion");
        logoutButton.setPrefWidth(300);
        logoutButton.setPrefHeight(50);
        logoutButton.setStyle("""
            -fx-background-color: #f44336; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        // Add project/task creation buttons only for project managers
        if (userRole.equals("GESTIONNAIRE_DE_PROJET")) {
            dashboardLayout.getChildren().addAll(welcomeLabel, createProjectButton, createTaskButton);
            createProjectButton.setOnAction(e -> showCreateProjectDialog());
            createTaskButton.setOnAction(e -> showCreateTaskDialog());
        } else {
            dashboardLayout.getChildren().add(welcomeLabel);
        }

        // Add all other buttons
        dashboardLayout.getChildren().addAll(tasksButton, viewProjectsButton, messagesButton, notificationsButton, logoutButton);

        tasksButton.setOnAction(e -> showTasks());
        viewProjectsButton.setOnAction(e -> showProjects());
        messagesButton.setOnAction(e -> showMessages());
        notificationsButton.setOnAction(e -> showNotifications());
        logoutButton.setOnAction(e -> {
            isLoggedIn = false;
            currentUser = null;
            userRole = "";
            showLoginScreen(stage);
        });

        Scene scene = new Scene(dashboardLayout, 600, 600);
        stage.setScene(scene);
    }

    private void showCreateTaskDialog() {
        Stage createTaskStage = new Stage();
        createTaskStage.setTitle("Créer une nouvelle tâche");

        VBox createTaskLayout = new VBox(15);
        createTaskLayout.setAlignment(Pos.CENTER);
        createTaskLayout.setPadding(new Insets(30));
        createTaskLayout.setSpacing(15);

        TextField taskNameField = new TextField();
        taskNameField.setPromptText("Nom de la tâche");
        taskNameField.setMaxWidth(300);
        taskNameField.setPrefHeight(40);
        taskNameField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        TextArea taskDescriptionField = new TextArea();
        taskDescriptionField.setPromptText("Description de la tâche");
        taskDescriptionField.setMaxWidth(300);
        taskDescriptionField.setPrefHeight(80);
        taskDescriptionField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Date d'échéance");
        dueDatePicker.setMaxWidth(300);
        dueDatePicker.setPrefHeight(40);
        dueDatePicker.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        ComboBox<String> priorityComboBox = new ComboBox<>();
        priorityComboBox.getItems().addAll("Haute", "Moyenne", "Basse");
        priorityComboBox.setPromptText("Sélectionnez la priorité");
        priorityComboBox.setMaxWidth(300);
        priorityComboBox.setPrefHeight(40);
        priorityComboBox.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        ComboBox<String> assigneeComboBox = new ComboBox<>();
        List<User> allUsers = dbManager.getAllUsers();
        for (User user : allUsers) {
            if (user.getRole().equals("MEMBRE_D_EQUIPE")) {
                assigneeComboBox.getItems().add(user.getUsername());
            }
        }
        assigneeComboBox.setPromptText("Sélectionnez l'assigné");
        assigneeComboBox.setMaxWidth(300);
        assigneeComboBox.setPrefHeight(40);
        assigneeComboBox.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        Button createButton = new Button("Créer la tâche");
        createButton.setPrefWidth(300);
        createButton.setPrefHeight(45);
        createButton.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        createButton.setOnAction(e -> {
            String taskName = taskNameField.getText().trim();
            String taskDescription = taskDescriptionField.getText().trim();
            LocalDate dueDate = dueDatePicker.getValue();
            String priority = priorityComboBox.getValue();
            String assigneeUsername = assigneeComboBox.getValue();

            if (taskName.isEmpty() || taskDescription.isEmpty() || dueDate == null || priority == null || assigneeUsername == null) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            // Trouver l'ID de l'utilisateur assigné
            User assigneeUser = null;
            for (User user : allUsers) {
                if (user.getUsername().equals(assigneeUsername)) {
                    assigneeUser = user;
                    break;
                }
            }

            if (assigneeUser == null) {
                showAlert("Erreur", "Utilisateur assigné non trouvé.");
                return;
            }

            // Créer la tâche dans la base de données
            boolean success = dbManager.createTask(taskName, taskDescription, dueDate.toString(), priority, assigneeUser.getId(), 0, currentUser.getId());
            if (success) {
                showAlert("Succès", "Tâche créée avec succès !");
                createTaskStage.close();
                // Notifie l'assigné
                dbManager.createNotification(assigneeUser.getId(), "Vous avez été assigné à une nouvelle tâche : " + taskName);
            } else {
                showAlert("Erreur", "Échec de la création de la tâche.");
            }
        });

        createTaskLayout.getChildren().addAll(taskNameField, taskDescriptionField, dueDatePicker, priorityComboBox, assigneeComboBox, createButton);

        Scene scene = new Scene(createTaskLayout, 400, 450);
        createTaskStage.setScene(scene);
        createTaskStage.show();
    }

    private void showCreateProjectDialog() {
        Stage createProjectStage = new Stage();
        createProjectStage.setTitle("Créer un nouveau projet");

        VBox createProjectLayout = new VBox(15);
        createProjectLayout.setAlignment(Pos.CENTER);
        createProjectLayout.setPadding(new Insets(30));
        createProjectLayout.setSpacing(15);

        TextField projectNameField = new TextField();
        projectNameField.setPromptText("Nom du projet");
        projectNameField.setMaxWidth(300);
        projectNameField.setPrefHeight(40);
        projectNameField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        TextArea projectDescriptionField = new TextArea();
        projectDescriptionField.setPromptText("Description du projet");
        projectDescriptionField.setMaxWidth(300);
        projectDescriptionField.setPrefHeight(80);
        projectDescriptionField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        DatePicker dueDatePicker = new DatePicker();
        dueDatePicker.setPromptText("Date d'échéance");
        dueDatePicker.setMaxWidth(300);
        dueDatePicker.setPrefHeight(40);
        dueDatePicker.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        TextField budgetField = new TextField();
        budgetField.setPromptText("Budget");
        budgetField.setMaxWidth(300);
        budgetField.setPrefHeight(40);
        budgetField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        Button createButton = new Button("Créer le projet");
        createButton.setPrefWidth(300);
        createButton.setPrefHeight(45);
        createButton.setStyle("""
            -fx-background-color: #9C27B0; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        createButton.setOnAction(e -> {
            String projectName = projectNameField.getText().trim();
            String projectDescription = projectDescriptionField.getText().trim();
            LocalDate dueDate = dueDatePicker.getValue();
            String budget = budgetField.getText().trim();

            if (projectName.isEmpty() || projectDescription.isEmpty() || dueDate == null || budget.isEmpty()) {
                showAlert("Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            boolean success = dbManager.createProject(projectName, projectDescription, dueDate.toString(), budget, currentUser.getId());
            if (success) {
                showAlert("Succès", "Projet créé avec succès !");
                createProjectStage.close();
            } else {
                showAlert("Erreur", "Échec de la création du projet.");
            }
        });

        createProjectLayout.getChildren().addAll(projectNameField, projectDescriptionField, dueDatePicker, budgetField, createButton);

        Scene scene = new Scene(createProjectLayout, 400, 400);
        createProjectStage.setScene(scene);
        createProjectStage.show();
    }

    private void showTasks() {
        Stage taskStage = new Stage();
        taskStage.setTitle("Tâches");

        VBox taskLayout = new VBox(15);
        taskLayout.setAlignment(Pos.TOP_CENTER);
        taskLayout.setPadding(new Insets(30));
        taskLayout.setSpacing(15);

        Label titleLabel = new Label("Liste des tâches");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<String> taskList = new ListView<>();
        taskList.setPrefHeight(300);
        taskList.setMaxWidth(500);
        List<Task> userTasks = dbManager.getUserTasks(currentUser.getId());
        for (Task task : userTasks) {
            taskList.getItems().add(task.getName() + " - " + task.getDescription() + " - Échéance : " + task.getDueDate() + " - Priorité : " + task.getPriority() + " - Statut : " + task.getStatus());
        }

        Button closeButton = new Button("Fermer");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(40);
        closeButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        closeButton.setOnAction(e -> taskStage.close());

        taskLayout.getChildren().addAll(titleLabel, taskList, closeButton);

        Scene scene = new Scene(taskLayout, 600, 500);
        taskStage.setScene(scene);
        taskStage.show();
    }

    private void showProjects() {
        Stage projectsStage = new Stage();
        projectsStage.setTitle("Projets");

        VBox projectsLayout = new VBox(15);
        projectsLayout.setAlignment(Pos.TOP_CENTER);
        projectsLayout.setPadding(new Insets(30));
        projectsLayout.setSpacing(15);

        Label titleLabel = new Label("Liste des projets");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<String> projectList = new ListView<>();
        projectList.setPrefHeight(300);
        projectList.setMaxWidth(550);
        List<Project> allProjects = dbManager.getAllProjects();
        for (Project project : allProjects) {
            projectList.getItems().add(project.getName() + " - " + project.getDescription() + " - Échéance : " + project.getDueDate() + " - Budget : " + project.getBudget());
        }

        Button closeButton = new Button("Fermer");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(40);
        closeButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        closeButton.setOnAction(e -> projectsStage.close());

        projectsLayout.getChildren().addAll(titleLabel, projectList, closeButton);

        Scene scene = new Scene(projectsLayout, 650, 500);
        projectsStage.setScene(scene);
        projectsStage.show();
    }

    private void showMessages() {
        Stage messagesStage = new Stage();
        messagesStage.setTitle("Messages");

        VBox messagesLayout = new VBox(15);
        messagesLayout.setAlignment(Pos.TOP_CENTER);
        messagesLayout.setPadding(new Insets(30));
        messagesLayout.setSpacing(15);

        Label titleLabel = new Label("Sélectionnez un utilisateur pour envoyer un message");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Liste des utilisateurs (excluant l'utilisateur actuel)
        ListView<String> userList = new ListView<>();
        userList.setPrefHeight(200);
        userList.setMaxWidth(400);
        List<User> allUsers = dbManager.getAllUsers();
        for (User user : allUsers) {
            if (user.getId() != currentUser.getId()) {
                userList.getItems().add(user.getUsername() + " (" + user.getRole() + ")");
            }
        }

        // Bouton pour commencer à envoyer des messages
        Button startMessagingButton = new Button("Commencer à envoyer des messages");
        startMessagingButton.setPrefWidth(300);
        startMessagingButton.setPrefHeight(45);
        startMessagingButton.setStyle("""
            -fx-background-color: #2196F3; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        startMessagingButton.setOnAction(e -> {
            String selectedUserText = userList.getSelectionModel().getSelectedItem();
            if (selectedUserText != null) {
                // Extract username from "username (role)" format
                String selectedUsername = selectedUserText.split(" \\(")[0];
                // Trouver l'ID de l'utilisateur sélectionné
                User selectedUser = null;
                for (User user : allUsers) {
                    if (user.getUsername().equals(selectedUsername)) {
                        selectedUser = user;
                        break;
                    }
                }
                if (selectedUser != null) {
                    messagesStage.close();
                    openMessagingWindow(selectedUser.getId(), selectedUser.getUsername());
                }
            } else {
                showAlert("Erreur", "Veuillez sélectionner un utilisateur pour envoyer un message.");
            }
        });

        Button closeButton = new Button("Fermer");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(40);
        closeButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        closeButton.setOnAction(e -> messagesStage.close());

        messagesLayout.getChildren().addAll(titleLabel, userList, startMessagingButton, closeButton);

        Scene scene = new Scene(messagesLayout, 500, 400);
        messagesStage.setScene(scene);
        messagesStage.show();
    }

    private void openMessagingWindow(int receiverId, String receiverUsername) {
        Stage messagingStage = new Stage();
        messagingStage.setTitle("Messagerie : " + receiverUsername);

        VBox messagingLayout = new VBox(15);
        messagingLayout.setAlignment(Pos.TOP_CENTER);
        messagingLayout.setPadding(new Insets(30));
        messagingLayout.setSpacing(15);

        Label titleLabel = new Label("Messagerie avec " + receiverUsername);
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // Affiche les messages entre l'utilisateur actuel et l'utilisateur sélectionné
        ListView<String> messageList = new ListView<>();
        messageList.setPrefHeight(250);
        messageList.setMaxWidth(450);
        List<Message> messages = dbManager.getMessagesBetweenUsers(currentUser.getId(), receiverId);
        for (Message message : messages) {
            messageList.getItems().add(message.getSender() + ": " + message.getContent());
        }

        // Champ de texte pour envoyer un nouveau message
        TextField messageField = new TextField();
        messageField.setPromptText("Tapez votre message ici...");
        messageField.setMaxWidth(450);
        messageField.setPrefHeight(40);
        messageField.setStyle("""
            -fx-font-size: 14px; 
            -fx-padding: 10px; 
            -fx-border-color: #bdc3c7; 
            -fx-border-radius: 5; 
            -fx-background-radius: 5;
        """);

        Button sendButton = new Button("Envoyer");
        sendButton.setPrefWidth(200);
        sendButton.setPrefHeight(40);
        sendButton.setStyle("""
            -fx-background-color: #4CAF50; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);

        sendButton.setOnAction(e -> {
            String content = messageField.getText().trim();
            if (!content.isEmpty()) {
                boolean success = dbManager.createMessage(currentUser.getId(), receiverId, content);
                if (success) {
                    messageList.getItems().add(currentUser.getUsername() + ": " + content);
                    messageField.clear();
                    // Notifie le destinataire
                    dbManager.createNotification(receiverId, "Vous avez un nouveau message de " + currentUser.getUsername());
                } else {
                    showAlert("Erreur", "Échec de l'envoi du message.");
                }
            }
        });

        Button closeButton = new Button("Fermer");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(40);
        closeButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        closeButton.setOnAction(e -> messagingStage.close());

        messagingLayout.getChildren().addAll(titleLabel, messageList, messageField, sendButton, closeButton);

        Scene scene = new Scene(messagingLayout, 550, 500);
        messagingStage.setScene(scene);
        messagingStage.show();
    }

    private void showNotifications() {
        Stage notificationsStage = new Stage();
        notificationsStage.setTitle("Notifications");

        VBox notificationsLayout = new VBox(15);
        notificationsLayout.setAlignment(Pos.TOP_CENTER);
        notificationsLayout.setPadding(new Insets(30));
        notificationsLayout.setSpacing(15);

        Label titleLabel = new Label("Notifications");
        titleLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        ListView<String> notificationList = new ListView<>();
        notificationList.setPrefHeight(300);
        notificationList.setMaxWidth(500);
        List<Notification> notifications = dbManager.getUserNotifications(currentUser.getId());
        for (Notification notification : notifications) {
            notificationList.getItems().add(notification.getMessage());
        }

        Button closeButton = new Button("Fermer");
        closeButton.setPrefWidth(200);
        closeButton.setPrefHeight(40);
        closeButton.setStyle("""
            -fx-background-color: #9E9E9E; 
            -fx-text-fill: white; 
            -fx-font-size: 16px; 
            -fx-font-weight: bold; 
            -fx-background-radius: 5;
            -fx-cursor: hand;
        """);
        closeButton.setOnAction(e -> notificationsStage.close());

        notificationsLayout.getChildren().addAll(titleLabel, notificationList, closeButton);

        Scene scene = new Scene(notificationsLayout, 600, 500);
        notificationsStage.setScene(scene);
        notificationsStage.show();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
