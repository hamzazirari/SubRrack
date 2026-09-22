package dao;

import entity.Paiement;
import entity.StatutPaiement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PaiementDAO {

    private List<Paiement> paiements = new ArrayList<>();

    public Paiement create(Paiement paiement) {
        paiements.add(paiement);
        return paiement;
    }

    public Optional<Paiement> findById(String idPaiement) {
        return paiements.stream()
                .filter(p -> p.getIdPaiement().equals(idPaiement))
                .findFirst();
    }

    public List<Paiement> findByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .collect(Collectors.toList());
    }

    public List<Paiement> findAll() {
        return new ArrayList<>(paiements);
    }

    public boolean update(Paiement paiementModifie) {
        for (int i = 0; i < paiements.size(); i++) {
            if (paiements.get(i).getIdPaiement().equals(paiementModifie.getIdPaiement())) {
                paiements.set(i, paiementModifie);
                return true;
            }
        }
        return false;
    }

    public boolean delete(String idPaiement) {
        return paiements.removeIf(p -> p.getIdPaiement().equals(idPaiement));
    }

    public List<Paiement> findUnpaidByAbonnement(String idAbonnement) {
        return paiements.stream()
                .filter(p -> p.getIdAbonnement().equals(idAbonnement))
                .filter(p -> p.getStatut() == StatutPaiement.NON_PAYE
                        || p.getStatut() == StatutPaiement.EN_RETARD)
                .collect(Collectors.toList());
    }

    public List<Paiement> findLastPayments() {
        return paiements.stream()
                .sorted(Comparator.comparing(Paiement::getDateEcheance).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}