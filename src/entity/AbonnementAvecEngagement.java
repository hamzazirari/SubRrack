package entity;

import java.time.LocalDate;

public class AbonnementAvecEngagement extends Abonnement {

    private int dureeEngagementMois;

    public AbonnementAvecEngagement(String nomService, double montantMensuel,
                                    LocalDate dateDebut, LocalDate dateFin,
                                    int dureeEngagementMois) {
        super(nomService, montantMensuel, dateDebut, dateFin);
        this.dureeEngagementMois = dureeEngagementMois;
    }

    // Getter et Setter
    public int getDureeEngagementMois() {
        return dureeEngagementMois;
    }

    public void setDureeEngagementMois(int dureeEngagementMois) {
        this.dureeEngagementMois = dureeEngagementMois;
    }

    @Override
    public String getTypeAbonnement() {
        return "AVEC_ENGAGEMENT";
    }

    @Override
    public String toString() {
        return "Abonnement : " + getNomService() +
                " (ID: " + getId() + ")" +
                " | Montant : " + getMontantMensuel() + " DH/mois" +
                " | Du " + getDateDebut() + " au " + getDateFin() +
                " | Statut : " + getStatut() +
                " | Engagement : " + dureeEngagementMois + " mois";
    }
}