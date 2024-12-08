package ex1;

public class Main {
    public static void main(String[] args) {
        CompteBancaire compte = new CompteBancaire(1000); 
        SanjiEtNamiJob job = new SanjiEtNamiJob(compte, 200); 

        
        Thread sanji = new Thread(job, "Sanji");
        Thread nami = new Thread(job, "Nami");

        sanji.start();
        nami.start();
    }
}

