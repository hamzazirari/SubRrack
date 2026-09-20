package dao;

import entity.Abonnement;
import entity.AbonnementAvecEngagement;
import entity.AbonnementSansEngagement;
import entity.StatutAbonnement;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AbonnementDAO {

    // Notre "base de donnees" en memoire : une simple liste
    private List<Abonnement> abonnements = new ArrayList<>();

    // Ajouter un nouvel abonnement
    public Abonnement create(Abonnement abonnement) {
        abonnements.add(abonnement);
        return abonnement;
    }

    // Chercher un abonnement par son id
    // Optional car il se peut que l'id n'existe pas
    public Optional<Abonnement> findById(String id) {
        return abonnements.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst();
    }

    // Retourner tous les abonnements
    public List<Abonnement> findAll() {
        return new ArrayList<>(abonnements); // copie pour proteger la liste originale
    }

    // Mettre a jour un abonnement existant
    public boolean update(Abonnement abonnementModifie) {
        for (int i = 0; i < abonnements.size(); i++) {
            if (abonnements.get(i).getId().equals(abonnementModifie.getId())) {
                abonnements.set(i, abonnementModifie); // on remplace l'ancien par le nouveau
                return true;
            }
        }
        return false; // id non trouve
    }

    // Supprimer un abonnement par son id
    public boolean delete(String id) {
        // removeIf renvoie true si un element a ete supprime
        return abonnements.removeIf(a -> a.getId().equals(id));
    }

    // Trouver uniquement les abonnements actifs
    public List<Abonnement> findActiveSubscriptions() {
        return abonnements.stream()
                .filter(a -> a.getStatut() == StatutAbonnement.ACTIVE)
                .collect(Collectors.toList());
    }

    // Trouver les abonnements par type (avec ou sans engagement)
    // On utilise "instanceof" pour verifier le type reel de l'objet
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
            return new ArrayList<>(); // type inconnu -> liste vide
        }
    }
}