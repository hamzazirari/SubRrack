package entity;

import java.time.LocalDate;

public class AbonnementSansEngagement extends Abonnement {

    public AbonnementSansEngagement(String nomService, double montantMensuel,
                                    LocalDate dateDebut, LocalDate dateFin) {
        super(nomService, montantMensuel, dateDebut, dateFin);
    }

    @Override
    public String getTypeAbonnement() {
        return "SANS_ENGAGEMENT";
    }

    @Override
    public String toString() {
        return "Abonnement : " + getNomService() +
                " (ID: " + getId() + ")" +
                " | Montant : " + getMontantMensuel() + " DH/mois" +
                " | Du " + getDateDebut() + " au " + getDateFin() +
                " | Statut : " + getStatut() +
                " | Sans engagement";
    }
}