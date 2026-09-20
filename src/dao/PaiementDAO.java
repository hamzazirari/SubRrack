package dao;

import entity.Paiement;
import entity.StatutPaiement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementDAO {

    // Notre "base de donnees" en memoire pour les paiements
    private List<Paiement> paiements = new ArrayList<>();

    // Ajouter un nouveau paiement
    public Paiement create(Paiement paiement) {
        paiements.add(paiement);
        return paiement;
    }

    // Chercher un paiement par son id
    public Optional<Paiement> findById(String idPaiement) {
        return paiements.stream()
                .filter(p -> p.getIdPaiement().equals(idPaiement))
                .findFirst();
    }

    // Trouver tous les paiements lies a un abonnement precis
    public List<Paiement> findByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .collect(Collectors.toList());
    }

    // Retourner tous les paiements
    public List<Paiement> findAll() {
        return new ArrayList<>(paiements);
    }

    // Mettre a jour un paiement existant
    public boolean update(Paiement paiementModifie) {
        for (int i = 0; i < paiements.size(); i++) {
            if (paiements.get(i).getIdPaiement().equals(paiementModifie.getIdPaiement())) {
                paiements.set(i, paiementModifie);
                return true;
            }
        }
        return false;
    }

    // Supprimer un paiement par son id
    public boolean delete(String idPaiement) {
        return paiements.removeIf(p -> p.getIdPaiement().equals(idPaiement));
    }

    // Trouver les paiements non payes d'un abonnement precis
    public List<Paiement> findUnpaidByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .filter(p -> p.getStatut() == StatutPaiement.NON_PAYE
                        || p.getStatut() == StatutPaiement.EN_RETARD)
                .collect(Collectors.toList());
    }

    // Trouver les 5 derniers paiements (les plus recents en date d'echeance)
    public List<Paiement> findLastPayments() {
        return paiements.stream()
                .sorted(Comparator.comparing(Paiement::getDateEcheance).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}