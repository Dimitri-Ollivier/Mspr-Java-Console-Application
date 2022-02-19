package dev;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class App {
    private static final String username = "dimitri.ollivier14@gmail.com";
    private static final String password = "Dimi1405";
    private static final String gitPath = "https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Backup-Directory/main/";
    protected static List<User> users;

    public static void main(String[] args) {
        try {
            String staffPath = gitPath + "staff.txt";
            String listPath = gitPath + "liste.txt";

            List<String> UserNames = GetGitFiles(staffPath);
            List<String> Materials = GetGitFiles(listPath);

            if (!UserNames.isEmpty()) {
                for (String UserName : UserNames ) {
                    User user = generateUser(UserName);

                    if (user != null) {
                        users.add(generateUser(UserName));
                    } else {
                        System.out.println("L'utilisateur : " + UserName + " n'a pas été généré, le contenu du fichier " + UserName + ".txt n'est pas valide.");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Traitement terminé avec une erreur : " + ex.getMessage());
            System.exit(1);
        }

        System.out.println("Traitement terminé avec succès.");
        System.exit(0);
    }

    private static List<String> GetGitFiles(String path) throws IOException {
        java.net.URL url;
        List<String> dataRaw = new ArrayList<>();

        try {
            url = new java.net.URL(path);
            java.net.URLConnection uc;
            uc = url.openConnection();

            uc.setRequestProperty("X-Requested-With", "Curl");
            java.util.ArrayList<String> list = new java.util.ArrayList<String>();
            String userpass = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userpass.getBytes()));//needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                dataRaw.add(line);
            }

            return dataRaw;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static User generateUser(String username) throws IOException {
        // Récupération des informations de l'utilisateur passé en paramètre
        List<String> userData = GetGitFiles(gitPath + username + ".txt");

        if (userData.size() >= 4) {
            String name = userData.get(0);
            String surname = userData.get(1);
            String job = userData.get(2);
            String password = userData.get(3);

            // Création d'une nouvelle instance de User
            User user = new User(name, surname, false);

            user.setJob(job);
            user.setPassword(password);

            // Récupération du matériel de l'utilisateur
            List<String> materials = new ArrayList<>();

            if (userData.size() >= 4) {
                for (int i = 4; i <= userData.size(); i++) {
                    materials.add(userData.get(i));
                }

                if (materials.size() > 0) {
                    user.setMaterials(materials);
                }
            }

            // Récupération de l'image de l'utilisateur sur le git de sauvegarde
            URL url = new URL(gitPath + username + ".jpg");
            Image userPicture = ImageIO.read(url);

            user.setPhoto(userPicture);

            return user;
        }

        return null;
    }
}