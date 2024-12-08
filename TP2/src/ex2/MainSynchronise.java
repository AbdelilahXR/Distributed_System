package ex2;

public class MainSynchronise {
    public static void main(String[] args) {
        BanqueSynchronisee banque = new BanqueSynchronisee(100, 1000);
        
        System.out.println("Solde total initial: " + banque.soldeTotal() + " DH");
        
        for (int compte = 0; compte < 100; compte++) {
            Runnable r = new TransfertSynchronise(banque, compte, 1000);
            Thread t = new Thread(r);
            t.setName("Thread-" + compte);
            t.start();
        }
        while (true) {
            try {
                Thread.sleep(2000);
                double soldeTotal = banque.soldeTotal();
                System.out.println("Solde total actuel: " + soldeTotal + " DH");
                
                if (Math.abs(soldeTotal - 100000) > 0.01) {
                    System.out.println("ATTENTION : Le solde total ne correspond plus à 100000 DH !");
                }
                
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}