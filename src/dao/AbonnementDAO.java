package dao;

import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;
import entity.StatutAbonnement;

import java.util.*;
import java.util.stream.Collectors;

public class AbonnementDAO {

    private List<Abonnement> abonnements = new ArrayList<>();

    public Abonnement create(Abonnement abonnement) {
        abonnements.add(abonnement);
        return abonnement;
    }

    public Optional<Abonnement> findById(String id) {
        return abonnements.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    public List<Abonnement> findAll() {
        return new ArrayList<>(abonnements); // copie pour proteger la liste originale
    }

    public boolean update(Abonnement abonnementModifie) {
        for (int i = 0; i < abonnements.size(); i++) {
            if (abonnements.get(i).getId().equals(abonnementModifie.getId())) {
                abonnements.set(i, abonnementModifie); // on remplace l'ancien par le nouveau
                return true;
            }
        }
        return false;
    }

    public boolean delete(String id) {
        // removeIf renvoie true si un element a ete supprime
        return abonnements.removeIf(a -> a.getId().equals(id));
    }

    public List<Abonnement> findActiveSubscriptions() {
        return abonnements.stream()
                .filter(a -> a.getStatut() == StatutAbonnement.ACTIVE)
                .collect(Collectors.toList());
    }

    public List<Abonnement> findByType(String type) {
        if (type.equalsIgnoreCase("AVEC_ENGAGEMENT")) {
            return abonnements.stream()
                    .filter(a -> a instanceof AbonnementAvecEngagement)
                    .collect(Collectors.toList());
        } else if (type.equalsIgnoreCase("SANS_ENGAGEMENT")) {
            return abonnements.stream()
                    .filter(a -> a instanceof AbonnementSansEngagement)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}