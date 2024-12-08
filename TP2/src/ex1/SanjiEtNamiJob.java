package ex1;

public class SanjiEtNamiJob implements Runnable {
    private CompteBancaire compte;
    private double somme;

    public SanjiEtNamiJob(CompteBancaire compte, double somme) {
        this.compte = compte;
        this.somme = somme;
    }

    @Override
    public void run() {
        while (true) {
            if (!compte.demandeRetrait(somme)) {
                System.out.println("Pas assez d'argent pour " + Thread.currentThread().getName());
            } else {
                try {
                    Thread.sleep(500); 
                    System.out.println(Thread.currentThread().getName() + " réveillé.");
                    compte.retirer(somme);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            try {
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
