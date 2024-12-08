package ex2;


class Banque {
    private final double[] comptes;
    
    public Banque(int nbComptes, double soldeInitial) {
        comptes = new double[nbComptes];
        for (int i = 0; i < comptes.length; i++) {
            comptes[i] = soldeInitial;
        }
    }
    
    public double soldeTotal() {
        double total = 0;
        for (double solde : comptes) {
            total += solde;
        }
        return total;
    }
    
    public void transferer(int de, int vers, double montant) {
        if (comptes[de] < montant) return;
        comptes[de] -= montant;
        comptes[vers] += montant;
    }
    
    public int size() {
        return comptes.length;
    }
}
