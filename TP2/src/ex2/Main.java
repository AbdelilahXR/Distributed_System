package ex2;

public class Main {
    public static void main(String[] args) {
        System.out.println("Version non Synchronisée");
        Banque banque = new Banque(100, 1000);
        
        System.out.println("Solde Total Initial: " + banque.soldeTotal() + " DH");
        
        for (int compte = 0; compte < 100; compte++) {
            Runnable r = new Transfert(banque, compte, 1000);
            new Thread(r).start();
        }
        
        while (true) {
            try {
                Thread.sleep(2000);
                System.out.println("Solde total actuel: " + banque.soldeTotal() + " DH");
            } catch (InterruptedException e) {
                break;
            }
        }
    }
}