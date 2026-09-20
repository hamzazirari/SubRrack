package ui;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import entity.*;
import service.AbonnementService;
import service.PaiementService;

import exception.AbonnementNotFoundException;
import exception.PaiementNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Optional;

public class MenuPrincipal {

    private Scanner scanner = new Scanner(System.in);
    private AbonnementDAO abonnementDAO = new AbonnementDAO();
    private PaiementDAO paiementDAO = new PaiementDAO();
    private AbonnementService abonnementService = new AbonnementService(abonnementDAO, paiementDAO);
    private PaiementService paiementService = new PaiementService(paiementDAO);

    public void demarrer() {
        int choix = -1;

        while (choix != 0) {
            afficherMenu();
            try {
                choix = Integer.parseInt(scanner.nextLine());

                switch (choix) {
                    case 1:
                        creerAbonnementAvecEngagement();
                        break;
                    case 2:
                        creerAbonnementSansEngagement();
                        break;
                    case 3:
                        modifierAbonnement();
                        break;
                    case 4:
                        supprimerAbonnement();
                        break;
                    case 5:
                        listerAbonnements();
                        break;
                    case 6:
                        afficherPaiementsAbonnement();
                        break;
                    case 7:
                        enregistrerPaiement();
                        break;
                    case 8:
                        modifierPaiement();
                        break;
                    case 9:
                        supprimerPaiement();
                        break;
                    case 10:
                        afficherImpayes();
                        break;
                    case 11:
                        afficherSommePayee();
                        break;
                    case 12:
                        afficherDerniersPaiements();
                        break;
                    case 13:
                        genererRapports();
                        break;
                    case 0:
                        System.out.println("Au revoir !");
                        break;
                    default:
                        System.out.println("Choix invalide, reessayez.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Erreur : veuillez entrer un nombre valide.");
            } catch (Exception e) {
                System.out.println("Erreur inattendue : " + e.getMessage());
            }

            System.out.println();
        }
    }

    private void afficherMenu() {
        System.out.println("===== GESTION DES ABONNEMENTS =====");
        System.out.println("1. Creer un abonnement AVEC engagement");
        System.out.println("2. Creer un abonnement SANS engagement");
        System.out.println("3. Modifier le montant d'un abonnement");
        System.out.println("4. Supprimer un abonnement");
        System.out.println("5. Lister les abonnements");
        System.out.println("6. Afficher les paiements d'un abonnement");
        System.out.println("7. Enregistrer un paiement");
        System.out.println("8. Modifier un paiement");
        System.out.println("9. Supprimer un paiement");
        System.out.println("10. Afficher les paiements manques (impayes)");
        System.out.println("11. Afficher la somme payee d'un abonnement");
        System.out.println("12. Afficher les 5 derniers paiements");
        System.out.println("13. Generer des rapports financiers");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    // ----- ABONNEMENTS -----

    private void creerAbonnementAvecEngagement() {
        System.out.print("Nom du service : ");
        String nom = scanner.nextLine();

        System.out.print("Montant mensuel : ");
        double montant = Double.parseDouble(scanner.nextLine());

        System.out.print("Date de debut (AAAA-MM-JJ) : ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Duree engagement (en mois) : ");
        int duree = Integer.parseInt(scanner.nextLine());

        AbonnementAvecEngagement abonnement =
                abonnementService.creerAbonnementAvecEngagement(nom, montant, dateDebut, duree);

        System.out.println("Abonnement cree avec succes : " + abonnement);
    }

    private void creerAbonnementSansEngagement() {
        System.out.print("Nom du service : ");
        String nom = scanner.nextLine();

        System.out.print("Montant mensuel : ");
        double montant = Double.parseDouble(scanner.nextLine());

        System.out.print("Date de debut (AAAA-MM-JJ) : ");
        LocalDate dateDebut = LocalDate.parse(scanner.nextLine());

        System.out.print("Date de fin (AAAA-MM-JJ) : ");
        LocalDate dateFin = LocalDate.parse(scanner.nextLine());

        AbonnementSansEngagement abonnement =
                abonnementService.creerAbonnementSansEngagement(nom, montant, dateDebut, dateFin);

        System.out.println("Abonnement cree avec succes : " + abonnement);
    }

    private void modifierAbonnement() {
        System.out.print("ID de l'abonnement : ");
        String id = scanner.nextLine();

        System.out.print("Nouveau montant mensuel : ");
        double montant = Double.parseDouble(scanner.nextLine());

        try {
            abonnementService.modifierMontant(id, montant);
            System.out.println("Abonnement modifie avec succes.");
        } catch (AbonnementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void supprimerAbonnement() {
        System.out.print("ID de l'abonnement a supprimer : ");
        String id = scanner.nextLine();

        try {
            abonnementService.supprimerAbonnement(id);
            System.out.println("Abonnement supprime avec succes.");
        } catch (AbonnementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void listerAbonnements() {
        List<Abonnement> liste = abonnementService.listerTousLesAbonnements();

        if (liste.isEmpty()) {
            System.out.println("Aucun abonnement enregistre.");
        } else {
            for (Abonnement a : liste) {
                System.out.println(a);
            }
        }
    }

    // ----- PAIEMENTS -----

    private void afficherPaiementsAbonnement() {
        System.out.print("ID de l'abonnement : ");
        String id = scanner.nextLine();

        List<Paiement> liste = paiementDAO.findByAbonnement(id);

        if (liste.isEmpty()) {
            System.out.println("Aucun paiement trouve pour cet abonnement.");
        } else {
            for (Paiement p : liste) {
                System.out.println(p);
            }
        }
    }

    private void enregistrerPaiement() {
        System.out.print("ID du paiement : ");
        String id = scanner.nextLine();

        System.out.print("Date de paiement (AAAA-MM-JJ) : ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        try {
            paiementService.enregistrerPaiement(id, date);
            System.out.println("Paiement enregistre avec succes.");
        } catch (PaiementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void modifierPaiement() {
        System.out.print("ID du paiement : ");
        String id = scanner.nextLine();

        System.out.print("Nouveau type de paiement : ");
        String type = scanner.nextLine();

        try {
            paiementService.modifierPaiement(id, type);
            System.out.println("Paiement modifie avec succes.");
        } catch (PaiementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void supprimerPaiement() {
        System.out.print("ID du paiement a supprimer : ");
        String id = scanner.nextLine();

        try {
            paiementService.supprimerPaiement(id);
            System.out.println("Paiement supprime avec succes.");
        } catch (PaiementNotFoundException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private void afficherImpayes() {
        List<Paiement> impayes = paiementService.detecterImpayes();

        if (impayes.isEmpty()) {
            System.out.println("Aucun paiement en retard.");
        } else {
            for (Paiement p : impayes) {
                System.out.println(p);
            }
        }
    }

    private void afficherSommePayee() {
        System.out.print("ID de l'abonnement : ");
        String id = scanner.nextLine();

        Optional<Abonnement> abonnementOpt = abonnementDAO.findById(id);

        if (abonnementOpt.isPresent()) {
            Abonnement abonnement = abonnementOpt.get();
            double somme = paiementService.calculerSommePayee(id, abonnement.getMontantMensuel());
            System.out.println("Somme payee : " + somme);
        } else {
            System.out.println("Abonnement non trouve.");
        }
    }

    private void afficherDerniersPaiements() {
        List<Paiement> derniers = paiementService.derniersPaiements();

        if (derniers.isEmpty()) {
            System.out.println("Aucun paiement enregistre.");
        } else {
            for (Paiement p : derniers) {
                System.out.println(p);
            }
        }
    }

    // ----- RAPPORTS -----

    private void genererRapports() {
        System.out.println("--- Rapport mensuel ---");
        Map<String, List<Paiement>> rapportMensuel = paiementService.rapportMensuel();
        rapportMensuel.forEach((mois, paiements) ->
                System.out.println(mois + " -> " + paiements.size() + " paiement(s)"));

        System.out.println("--- Rapport annuel ---");
        Map<Integer, List<Paiement>> rapportAnnuel = paiementService.rapportAnnuel();
        rapportAnnuel.forEach((annee, paiements) ->
                System.out.println(annee + " -> " + paiements.size() + " paiement(s)"));

        System.out.println("--- Rapport des impayes ---");
        Map<String, List<Paiement>> rapportImpayes = paiementService.rapportImpayes();
        rapportImpayes.forEach((idAbonnement, paiements) ->
                System.out.println("Abonnement " + idAbonnement + " -> " + paiements.size() + " impaye(s)"));
    }
}