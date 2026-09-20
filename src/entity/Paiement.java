package entity;

import java.time.LocalDate;
import java.util.UUID;

public class Paiement {

    private String idPaiement;
    private String idAbonnement; // référence vers l'abonnement concerné
    private LocalDate dateEcheance;
    private LocalDate datePaiement;
    private String typePaiement; // ex: "Carte bancaire", "Virement", "Prélèvement"...
    private StatutPaiement statut;


    public Paiement(String idAbonnement, LocalDate dateEcheance, String typePaiement) {
        this.idPaiement = UUID.randomUUID().toString(); // génération automatique de l'id
        this.idAbonnement = idAbonnement;
        this.dateEcheance = dateEcheance;
        this.typePaiement = typePaiement;
        this.datePaiement = null; // pas encore payé à la création
        this.statut = StatutPaiement.NON_PAYE; // statut par défaut
    }

    public String getIdPaiement() {
        return idPaiement;
    }

    public String getIdAbonnement() {
        return idAbonnement;
    }

    public void setIdAbonnement(String idAbonnement) {
        this.idAbonnement = idAbonnement;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public LocalDate getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDate datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getTypePaiement() {
        return typePaiement;
    }

    public void setTypePaiement(String typePaiement) {
        this.typePaiement = typePaiement;
    }

    public StatutPaiement getStatut() {
        return statut;
    }

    public void setStatut(StatutPaiement statut) {
        this.statut = statut;
    }

    @Override
    public String toString() {
        return "Paiement (ID: " + idPaiement + ")" +
                " | Abonnement : " + idAbonnement +
                " | Echeance : " + dateEcheance +
                " | Paye le : " + (datePaiement != null ? datePaiement : "non paye") +
                " | Type : " + typePaiement +
                " | Statut : " + statut;
    }
}