package service;

import dao.AbonnementDAO;
import dao.PaiementDAO;
import entity.*;

import exception.AbonnementNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class AbonnementService {

    private AbonnementDAO abonnementDAO;
    private PaiementDAO paiementDAO;

    public AbonnementService(AbonnementDAO abonnementDAO, PaiementDAO paiementDAO) {
        this.abonnementDAO = abonnementDAO;
        this.paiementDAO = paiementDAO;
    }

    // Creer un abonnement AVEC engagement
    public AbonnementAvecEngagement creerAbonnementAvecEngagement(
            String nomService, double montantMensuel, LocalDate dateDebut, int dureeEngagementMois) {

        LocalDate dateFin = dateDebut.plusMonths(dureeEngagementMois);

        AbonnementAvecEngagement abonnement = new AbonnementAvecEngagement(
                nomService, montantMensuel, dateDebut, dateFin, dureeEngagementMois
        );

        abonnementDAO.create(abonnement);
        genererEcheances(abonnement);
        return abonnement;
    }

    // Creer un abonnement SANS engagement
    public AbonnementSansEngagement creerAbonnementSansEngagement(
            String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin) {

        AbonnementSansEngagement abonnement = new AbonnementSansEngagement(
                nomService, montantMensuel, dateDebut, dateFin
        );

        abonnementDAO.create(abonnement);
        genererEcheances(abonnement);
        return abonnement;
    }

    // Genere un paiement (echeance) pour chaque mois entre dateDebut et dateFin
    private void genererEcheances(Abonnement abonnement) {
        LocalDate dateCourante = abonnement.getDateDebut();

        while (!dateCourante.isAfter(abonnement.getDateFin())) {
            Paiement paiement = new Paiement(
                    abonnement.getId(),
                    dateCourante,
                    "Mensuel"
            );
            paiementDAO.create(paiement);
            dateCourante = dateCourante.plusMonths(1);
        }
    }

    // Modifier le montant mensuel d'un abonnement
    public void modifierMontant(String id, double nouveauMontant) throws AbonnementNotFoundException {
        Optional<Abonnement> abonnementOpt = abonnementDAO.findById(id);

        if (abonnementOpt.isPresent()) {
            Abonnement abonnement = abonnementOpt.get();
            abonnement.setMontantMensuel(nouveauMontant);
            abonnementDAO.update(abonnement);
        } else {
            throw new AbonnementNotFoundException("Aucun abonnement trouve avec l'id : " + id);
        }
    }

    // Resilier un abonnement
    public void resilierAbonnement(String id) throws AbonnementNotFoundException {
        Optional<Abonnement> abonnementOpt = abonnementDAO.findById(id);

        if (abonnementOpt.isPresent()) {
            Abonnement abonnement = abonnementOpt.get();
            abonnement.setStatut(StatutAbonnement.RESILIE);
            abonnementDAO.update(abonnement);
        } else {
            throw new AbonnementNotFoundException("Aucun abonnement trouve avec l'id : " + id);
        }
    }

    // Supprimer un abonnement
    public void supprimerAbonnement(String id) throws AbonnementNotFoundException {
        boolean supprime = abonnementDAO.delete(id);
        if (!supprime) {
            throw new AbonnementNotFoundException("Aucun abonnement trouve avec l'id : " + id);
        }
    }

    // Lister tous les abonnements
    public List<Abonnement> listerTousLesAbonnements() {
        return abonnementDAO.findAll();
    }

    // Lister uniquement les abonnements actifs
    public List<Abonnement> listerAbonnementsActifs() {
        return abonnementDAO.findActiveSubscriptions();
    }
}