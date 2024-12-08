package ex1;

public class CompteBancaire {
    private double solde;

    public CompteBancaire(double soldeInitial) {
        this.solde = soldeInitial;
    }

   
    public synchronized boolean demandeRetrait(double somme) {
        if (solde < somme) {
            return false; 
        } else {
            System.out.println(Thread.currentThread().getName() + " est sur le point de retirer.");
            return true;
        }
    }

    
    public synchronized void retirer(double somme) {
        if (solde >= somme) {
            solde -= somme;
            System.out.println(Thread.currentThread().getName() + " a complété le retrait.");
        }
    }

    public double getSolde() {
        return solde;
    }
}
