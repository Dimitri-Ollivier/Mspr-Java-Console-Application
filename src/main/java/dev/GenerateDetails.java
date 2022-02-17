package dev;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class GenerateDetails {
    public GenerateDetails(String fileName) throws Exception {
        // TODO scan du répertoire en cherchant en paramètre le nom du fichier

        // Récuppération des données parsées pour notre utilisateur
        ArrayList<String> info = ParseTextFile("src/main/resources/"+ fileName +".txt");

        String name = info.get(0);
        String surname = info.get(1);
        String identCard = (surname.charAt(0)+name).toLowerCase();

        StringBuilder htmlContent = new StringBuilder();

        for (int i = 5; i < info.size() ; i++){
            htmlContent.append("<li>").append(info.get(i)).append("</li>");
        }

        htmlContent = new StringBuilder("<ul>" + htmlContent + "</ul>");

        File htmlFile = new File("src/main/resources/templateDesc.html");
        Scanner htmlScanner = new Scanner(htmlFile);
        htmlScanner.useDelimiter(System.getProperty("line.separator"));

        StringBuilder docHtml = new StringBuilder();

        while (htmlScanner.hasNext()) {
            docHtml.append(htmlScanner.next());
        }

        htmlScanner.close();

        docHtml = new StringBuilder(docHtml.toString().replace("[[NAME]]", name));
        docHtml = new StringBuilder(docHtml.toString().replace("[[SURNAME]]", surname));
        docHtml = new StringBuilder(docHtml.toString().replace("[[IDENT_CARD]]", identCard + ".jpg"));
        docHtml = new StringBuilder(docHtml.toString().replace("[[MATERIAL]]", htmlContent.toString()));

        File fileToSend = new File(identCard+".html");

        BufferedWriter bw = new BufferedWriter(new FileWriter(fileToSend));
        bw.write(docHtml.toString());
        bw.close();
    }

    public ArrayList<String> ParseTextFile(String fileName) throws Exception {
        ArrayList<String> infos = new ArrayList<>();
        File textFile;
        Scanner textScanner = null;

        try {
            // Instanciation du fichier
            textFile = new File(fileName);

            // Lecture du contenu du fichier
            textScanner = new Scanner(textFile);

            // Lecture de chaque ligne du fichier
            while (textScanner.hasNextLine()) {
                // Ajout de la ligne courante à la liste
                infos.add(textScanner.nextLine());
            }
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {
            if (textScanner != null) {
                textScanner.close();
            }
        }

        return infos;
    }
}
