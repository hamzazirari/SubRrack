package service;

import dao.PaiementDAO;
import entity.Paiement;
import entity.StatutPaiement;

import exception.PaiementNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementService {

    private PaiementDAO paiementDAO;

    public PaiementService(PaiementDAO paiementDAO) {
        this.paiementDAO = paiementDAO;
    }

    // Enregistrer qu'un paiement a ete paye
    public void enregistrerPaiement(String idPaiement, LocalDate datePaiement) throws PaiementNotFoundException {
        Optional<Paiement> paiementOpt = paiementDAO.findById(idPaiement);

        if (paiementOpt.isPresent()) {
            Paiement paiement = paiementOpt.get();
            paiement.setDatePaiement(datePaiement);
            paiement.setStatut(StatutPaiement.PAYE);
            paiementDAO.update(paiement);
        } else {
            throw new PaiementNotFoundException("Aucun paiement trouve avec l'id : " + idPaiement);
        }
    }

    // Modifier un paiement
    public void modifierPaiement(String idPaiement, String nouveauType) throws PaiementNotFoundException {
        Optional<Paiement> paiementOpt = paiementDAO.findById(idPaiement);

        if (paiementOpt.isPresent()) {
            Paiement paiement = paiementOpt.get();
            paiement.setTypePaiement(nouveauType);
            paiementDAO.update(paiement);
        } else {
            throw new PaiementNotFoundException("Aucun paiement trouve avec l'id : " + idPaiement);
        }
    }

    // Supprimer un paiement
    public void supprimerPaiement(String idPaiement) throws PaiementNotFoundException {
        boolean supprime = paiementDAO.delete(idPaiement);
        if (!supprime) {
            throw new PaiementNotFoundException("Aucun paiement trouve avec l'id : " + idPaiement);
        }
    }

    // Detecter  paiement en retard dateEcheance depassee et pas encore paye
    public List<Paiement> detecterImpayes() {
        LocalDate aujourdHui = LocalDate.now();

        return paiementDAO.findAll().stream()
                .filter(p -> p.getStatut() == StatutPaiement.NON_PAYE)
                .filter(p -> p.getDateEcheance().isBefore(aujourdHui))
                .collect(Collectors.toList());
    }

    // Calculer le montant total impaye pour un abonnement precis
    public double calculerMontantImpaye(String idAbonnement, double montantMensuel) {
        long nombreImpayes = paiementDAO.findUnpaidByAbonnement(idAbonnement).size();
        return nombreImpayes * montantMensuel;
    }

    // Calculer la somme totale payee pour un abonnement
    public double calculerSommePayee(String idAbonnement, double montantMensuel) {
        long nombrePayes = paiementDAO.findByAbonnement(idAbonnement).stream()
                .filter(p -> p.getStatut() == StatutPaiement.PAYE)
                .count();
        return nombrePayes * montantMensuel;
    }

    // Afficher les 5 derniers paiements
    public List<Paiement> derniersPaiements() {
        return paiementDAO.findLastPayments();
    }

    // Rapport mensuel : regrouper les paiements par annee-mois
    public Map<String, List<Paiement>> rapportMensuel() {
        return paiementDAO.findAll().stream()
                .collect(Collectors.groupingBy(
                        p -> p.getDateEcheance().getYear() + "-" + p.getDateEcheance().getMonthValue()
                ));
    }

    // Rapport annuel : regrouper les paiements par annee
    public Map<Integer, List<Paiement>> rapportAnnuel() {
        return paiementDAO.findAll().stream()
                .collect(Collectors.groupingBy(p -> p.getDateEcheance().getYear()));
    }

    // Rapport des impayes : regrouper les impayes par abonnement
    public Map<String, List<Paiement>> rapportImpayes() {
        return detecterImpayes().stream()
                .collect(Collectors.groupingBy(Paiement::getIdAbonnement));
    }
}