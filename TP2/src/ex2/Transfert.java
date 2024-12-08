package ex2;

class Transfert implements Runnable {
    
    private final Banque banque;
    private final int compte;
    private final double montantMax;
    
    public Transfert(Banque banque, int compte, double montantMax) {
        this.banque = banque;
        this.compte = compte;
        this.montantMax = montantMax;
    }
    
    
    public void run() {
        while (true) {
            int vers = (int) (Math.random() * banque.size());
            double montant = Math.random() * montantMax;
            banque.transferer(compte, vers, montant);
        }
    }
}
