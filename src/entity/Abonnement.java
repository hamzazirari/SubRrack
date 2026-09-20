package entity;

import java.time.LocalDate;
import java.util.UUID;


public abstract class Abonnement {

    private String id;
    private String nomService;
    private double montantMensuel;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private StatutAbonnement statut;

    public Abonnement(String nomService, double montantMensuel, LocalDate dateDebut, LocalDate dateFin) {
        this.id = UUID.randomUUID().toString(); // génération automatique de l'id
        this.nomService = nomService;
        this.montantMensuel = montantMensuel;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = StatutAbonnement.ACTIVE; // statut par défaut à la création
    }

    public String getId() {
        return id;
    }

    public String getNomService() {
        return nomService;
    }

    public void setNomService(String nomService) {
        this.nomService = nomService;
    }

    public double getMontantMensuel() {
        return montantMensuel;
    }

    public void setMontantMensuel(double montantMensuel) {
        this.montantMensuel = montantMensuel;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public StatutAbonnement getStatut() {
        return statut;
    }

    public void setStatut(StatutAbonnement statut) {
        this.statut = statut;
    }

    public abstract String getTypeAbonnement();

    @Override
    public String toString() {
        return "Abonnement{" +
                "id='" + id + '\'' +
                ", nomService='" + nomService + '\'' +
                ", montantMensuel=" + montantMensuel +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", statut=" + statut +
                '}';
    }
}