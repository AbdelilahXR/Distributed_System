package ex2;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

class BanqueSynchronisee {
    private final double[] comptes;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition soldeDisponible = lock.newCondition();
    
    public BanqueSynchronisee(int nbComptes, double soldeInitial) {
        comptes = new double[nbComptes];
        for (int i = 0; i < comptes.length; i++) {
            comptes[i] = soldeInitial;
        }
    }
    
    public double soldeTotal() {
        lock.lock();
        try {
            double total = 0;
            for (double solde : comptes) {
                total += solde;
            }
            return total;
        } finally {
            lock.unlock();
        }
    }
    
    public void transferer(int de, int vers, double montant) {
        lock.lock();
        try {
            while (comptes[de] < montant) {
                soldeDisponible.await();
            }
            comptes[de] -= montant;
            comptes[vers] += montant;
            soldeDisponible.signalAll();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
    
    public int size() {
        return comptes.length;
    }
}