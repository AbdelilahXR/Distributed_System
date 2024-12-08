package ex2;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;


class TransfertSynchronise implements Runnable {
    private final BanqueSynchronisee banque;
    private final int compte;
    private final double montantMax;
    
    public TransfertSynchronise(BanqueSynchronisee banque, int compte, double montantMax) {
        this.banque = banque;
        this.compte = compte;
        this.montantMax = montantMax;
    }
    
    @Override
    public void run() {
        while (true) {
            int vers = (int) (Math.random() * banque.size());
            double montant = Math.random() * montantMax;
            banque.transferer(compte, vers, montant);
        }
    }
}