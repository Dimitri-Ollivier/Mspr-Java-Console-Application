package dev;

public class App {

    public static void main(String[] args) {
        try {
            new GenerateIndex();
            new GenerateDetails("cberthier");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        System.out.println("Traitement terminé avec succès.");
    }
}