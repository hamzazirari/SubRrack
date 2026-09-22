# Gestion d'Abonnements — Java 8 / JDBC (Sprint 1)

Application console en Java 8 permettant de centraliser la gestion d'abonnements personnels et professionnels : suivi des échéances, détection des paiements manqués, génération de rapports financiers.

## Contexte

La gestion des abonnements (streaming, assurances, logiciels, services cloud...) devient rapidement complexe à suivre manuellement. Ce projet propose une solution centralisée pour :
- Suivre les échéances de paiement
- Détecter rapidement les paiements manqués
- Générer des rapports financiers synthétiques (mensuels, annuels, impayés)

## Technologies utilisées

- **Java 8**
- Programmation fonctionnelle : Stream API, Lambda, Optional, Collectors
- Persistance en mémoire (collections Java) — pas de base de données pour ce sprint
- Architecture en couches (UI, Service, DAO, Entity)

## Architecture du projet

```
src/
├── Main.java                              → Point d'entrée de l'application
│
├── entity/                                → Objets métier
│   ├── Abonnement.java                    → Classe abstraite (id, nomService, montantMensuel, dateDebut, dateFin, statut)
│   ├── AbonnementAvecEngagement.java      → Hérite de Abonnement (+ dureeEngagementMois)
│   ├── AbonnementSansEngagement.java      → Hérite de Abonnement
│   ├── Paiement.java                      → Échéance liée à un abonnement
│   ├── StatutAbonnement.java              → Enum (ACTIVE, SUSPENDU, RESILIE)
│   └── StatutPaiement.java                → Enum (PAYE, NON_PAYE, EN_RETARD)
│
├── dao/                                   → Accès aux données (persistance en mémoire)
│   ├── AbonnementDAO.java                 → create, findById, findAll, update, delete, findActiveSubscriptions, findByType
│   └── PaiementDAO.java                   → create, findById, findByAbonnement, findAll, update, delete, findUnpaidByAbonnement, findLastPayments
│
├── service/                               → Logique métier
│   ├── AbonnementService.java             → Création, modification, résiliation, génération d'échéances
│   └── PaiementService.java               → Enregistrement, détection des impayés, rapports financiers
│
├── exception/                             → Gestion des erreurs personnalisées
│   ├── AbonnementNotFoundException.java
│   └── PaiementNotFoundException.java
│
└── ui/                                    → Interface console
    └── MenuPrincipal.java                 → Menu de navigation
```

### Flux d'exécution (architecture en couches)

```
UI (MenuPrincipal) → Service (logique métier) → DAO (accès données) → Entity (objets)
```

Chaque couche ne communique qu'avec la couche directement inférieure, ce qui isole les responsabilités et facilite l'évolution du projet (par exemple, un futur passage à une base de données ne toucherait que la couche DAO).

## Fonctionnalités

- Créer un abonnement (avec ou sans engagement)
- Modifier / supprimer un abonnement
- Consulter la liste des abonnements
- Générer automatiquement les échéances de paiement à la création d'un abonnement
- Enregistrer un paiement
- Modifier / supprimer un paiement
- Détecter les paiements manqués (impayés) et calculer le montant total impayé
- Afficher la somme payée pour un abonnement
- Afficher les 5 derniers paiements
- Générer des rapports financiers (mensuel, annuel, impayés) via Stream API et Collectors

## Comment lancer le projet

### Prérequis
- JDK 8 ou supérieur installé
- IntelliJ IDEA (ou tout IDE Java)

### Étapes
1. Cloner le dépôt :
   ```
   git clone <lien-du-depot>
   ```
2. Ouvrir le projet dans IntelliJ IDEA
3. Lancer la classe `Main.java` (clic droit → Run 'Main')
4. Suivre les instructions du menu console

## Exemple d'utilisation

```
===== GESTION DES ABONNEMENTS =====
1. Creer un abonnement AVEC engagement
2. Creer un abonnement SANS engagement
3. Modifier le montant d'un abonnement
4. Supprimer un abonnement
5. Lister les abonnements
6. Afficher les paiements d'un abonnement
7. Enregistrer un paiement
8. Modifier un paiement
9. Supprimer un paiement
10. Afficher les paiements manques (impayes)
11. Afficher la somme payee d'un abonnement
12. Afficher les 5 derniers paiements
13. Generer des rapports financiers
0. Quitter
Votre choix :
```

## Modèle de données

| Entité | Attributs |
|---|---|
| Abonnement | id, nomService, montantMensuel, dateDebut, dateFin, statut, typeAbonnement, dureeEngagementMois (si avec engagement) |
| Paiement | idPaiement, idAbonnement, dateEcheance, datePaiement, typePaiement, statut |

Relation : un `Abonnement` possède plusieurs `Paiement` (relation 1..n).

## Auteur

Projet individuel — Sprint 1 (14/09/2026 — 18/09/2026)
