# Project Manager - Application de Gestion de Projets

[![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) [![JavaFX](https://img.shields.io/badge/JavaFX-17-2196F3?style=for-the-badge&logo=oracle&logoColor=white)](https://openjfx.io/) [![H2 Database](https://img.shields.io/badge/H2-Database-CC6600?style=for-the-badge&logo=h2&logoColor=white)](https://www.h2database.com/) [![Maven](https://img.shields.io/badge/Maven-3.8+-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/) [![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)

Une application de gestion de projets complète avec interface graphique JavaFX, base de données H2 intégrée, et fonctionnalités de collaboration en temps réel.

---

## 📚 Historique du Projet

### 🎓 Origine Académique
Ce projet a débuté comme un projet beta académique durant ma 3ème année d'études en génie logiciel. La version initiale était une application basique conçue pour répondre aux exigences du cours de programmation orientée objet en Java.

### 🚀 Évolution et Améliorations
Après la validation académique, j'ai entrepris une refonte complète de l'application pour en faire une solution professionnelle et robuste. Les améliorations majeures incluent :

*   **Architecture renforcée :** Structure de projet professionnelle avec séparation claire des responsabilités
*   **Interface utilisateur moderne :** Design JavaFX amélioré avec composants stylés et responsive
*   **Base de données persistante :** Migration de stockage en mémoire vers H2 Database intégrée
*   **Sécurité renforcée :** Système d'authentification amélioré avec gestion des rôles
*   **Tests complets :** Suite de tests unitaires avec JUnit 5
*   **Documentation professionnelle :** README.md complet avec badges, instructions et exemples
*   **Gestion des dépendances :** Configuration Maven complète avec toutes les dépendances nécessaires

### 🎉 Version Actuelle (1.0.0)
La version actuelle représente une application de gestion de projets complète et prête pour la production avec toutes les fonctionnalités essentielles :

---

## ✨ Fonctionnalités

Cette application fournit une expérience complète de gestion de projets avec sécurité d'entreprise et design UX moderne :

### 🔐 Authentification & Sécurité
*   Connexion sécurisée avec nom d'utilisateur/mot de passe
*   Inscription d'utilisateurs avec rôles différents (Gestionnaire de Projet / Membre d'Équipe)
*   Stockage sécurisé des mots de passe dans la base de données H2

### 👥 Gestion des Utilisateurs
*   **Rôles multiples :** Gestionnaires de projets et membres d'équipe avec permissions différentes
*   **Profils utilisateurs :** Gestion complète des informations utilisateur
*   **Collaboration :** Attribution de tâches et projets aux membres d'équipe appropriés

### 📁 Gestion des Projets
*   **Tableau de bord des projets :** Vue d'ensemble des projets avec dates d'échéance et budgets
*   **Création de projets :** Interface intuitive pour créer de nouveaux projets
*   **Détails des projets :** Informations complètes sur chaque projet
*   **Suivi budgétaire :** Gestion et suivi des budgets de projets

### 📋 Gestion des Tâches
*   **Création de tâches :** Interface détaillée pour créer et assigner des tâches
*   **Priorisation :** Système de priorités (Haute, Moyenne, Basse)
*   **Suivi du statut :** États des tâches (Non commencé, En cours, Terminé)
*   **Dates d'échéance :** Gestion des délais avec calendrier intégré
*   **Attribution :** Assignation de tâches aux membres d'équipe

### 💬 Messagerie & Notifications
*   **Messagerie privée :** Communication directe entre utilisateurs
*   **Notifications en temps réel :** Alertes pour les nouvelles tâches et messages
*   **Historique des messages :** Suivi complet des communications
*   **Interface conviviale :** Fenêtres de messagerie intuitives

### 🎨 Interface Utilisateur
*   **Design moderne :** Interface élégante avec thème cohérent
*   **Navigation fluide :** Transition entre écrans avec menu principal
*   **Responsive :** Interface adaptée à différentes tailles d'écran
*   **Feedback visuel :** Indicateurs clairs pour les actions utilisateur

---

## 🛠️ Stack Technologique

*   **Langage Principal :** Java 17+
*   **Framework IHM :** JavaFX pour l'interface graphique desktop
*   **Gestion des Dépendances :** Apache Maven
*   **Base de Données :** H2 Database (intégrée, pas de serveur externe requis)
*   **Gestion des Versions :** Git & GitHub
*   **Outils de Développement :** IDE Java (IntelliJ IDEA, Eclipse, ou VS Code recommandé)

---

## 📋 Prérequis

Avant de commencer, assurez-vous d'avoir les éléments suivants installés :

*   **Java Development Kit (JDK) 17** ou version ultérieure
*   **Apache Maven 3.8+**
*   **Git** pour le contrôle de version
*   **IDE Java** (optionnel mais recommandé : IntelliJ IDEA, Eclipse, ou VS Code)

---

## 🚀 Installation et Démarrage

### 1. Cloner le Dépôt

```bash
git clone https://github.com/Mizuch1/project-manager.git
cd project-manager
```

### 2. Compiler le Projet

```bash
mvn clean compile
```

### 3. Exécuter l'Application

```bash
mvn javafx:run
```

### 4. Première Connexion

L'application créera automatiquement la base de données H2 lors du premier démarrage. Vous pouvez utiliser les identifiants par défaut pour tester :

*   **Administrateur :** `admin` / `admin` (rôle : Gestionnaire de Projet)
*   **Utilisateur :** `user` / `user` (rôle : Membre d'Équipe)

*Remarque : Ces identifiants par défaut sont uniquement à des fins de test. Dans une utilisation normale, créez vos propres comptes via l'interface d'inscription.*

---

## 📸 Preview
<p align="center">
  <img src="https://turquoise-demetris-15.tiiny.site/project_manager_preview.svg" alt="FlousBank Preview" width="400">
</p>

## 🤝 Contribution

Les contributions, problèmes et demandes de fonctionnalités sont les bienvenus ! N'hésitez pas à consulter la [page des issues](https://github.com/Mizuch1/project-hub-javafx/issues).

### Comment contribuer :
1. Forkez le dépôt
2. Créez votre branche de fonctionnalité (`git checkout -b feature/FonctionnaliteIncroyable`)
3. Commitez vos modifications (`git commit -m 'Ajout d'une fonctionnalité incroyable'`)
4. Poussez vers la branche (`git push origin feature/FonctionnaliteIncroyable`)
5. Ouvrez une Pull Request

---

## 📄 Licence

Ce projet est sous licence MIT - voir le fichier [LICENSE](LICENSE) pour plus de détails.

---

## 📊 Architecture & Diagrammes

Pour des diagrammes d'architecture et une documentation de conception détaillée, consultez le dossier **Diagrams** dans le dépôt.

---

## 📞 Let's Connect!

<div align="center">

[![Email](https://img.shields.io/badge/Email-mohamed.jbilou.it@gmail.com-D14836?style=for-the-badge&logo=gmail&logoColor=white)](mailto:mohamed.jbilou.it@gmail.com)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/mohamedjbilou/)
[![Portfolio](https://img.shields.io/badge/Portfolio-000000?style=for-the-badge&logo=vercel&logoColor=white)](https://my-portfolio-iota-ten-95.vercel.app/)
[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/Mizuch1)

</div>

---

```java
// copyright_info.java

class ProjectInfo {
    private String projectName = "Project Manager";
    private String author = "Mohamed Jbilou (Mizuchi)";
    private int year = 2025;
    
    public String displayInfo() {
        return "© " + year + " " + author + ". Tous droits réservés.";
    }
    
    public String signature() {
        return "Construit avec passion par " + author;
    }
}

// Utilisation
public class Main {
    public static void main(String[] args) {
        ProjectInfo project = new ProjectInfo();
        System.out.println(project.displayInfo());
        System.out.println(project.signature());
        System.out.println("Que vos projets soient bien gérés et votre code sans bogues ! 🚀");
    }
}
```

---

## 👤 Auteur

_Réalisé avec ☕ et 💻 par Mizuchi_
