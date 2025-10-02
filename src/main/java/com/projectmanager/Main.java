package com.projectmanager;

import javafx.application.Application;

/**
 * Point d'entrée principal de l'application Project Manager.
 * Cette classe démarre l'application JavaFX.
 */
public class Main {
    
    /**
     * Méthode principale qui lance l'application.
     * @param args Arguments de ligne de commande
     */
    public static void main(String[] args) {
        // Démarrer l'application JavaFX
        Application.launch(ProjectManagerApp.class, args);
    }
}
