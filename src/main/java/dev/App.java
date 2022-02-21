package dev;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class App {
    private static final String username = "dimitri.ollivier14@gmail.com";
    private static final String password = "$2y$10$YG7vxD5obRyndy2mAUus..gDCskawtO/4YfxlqZeMKBiCnzWysRVW";
    protected static final String gitPath = "https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Backup-Directory/main/";
    protected static List<User> users = new ArrayList<>();

    public static void main(String[] args) {
        try {
            Sftp.Connect();

            String staffPath = gitPath + "staff.txt";
            String listPath = gitPath + "liste.txt";

            List<String> userNames = GetGitFiles(staffPath);

            if (userNames != null && !userNames.isEmpty()) {
                for (String UserName : userNames ) {
                    User user = generateUser(UserName);

                    if (user != null) {
                        users.add(user);
                    } else {
                        System.out.println("L'utilisateur " + UserName + " n'a pas été généré, le contenu du fichier " + UserName + ".txt n'est pas valide.");
                    }
                }
            }

            if (!users.isEmpty()) {
                for (User user : users ) {
                    // Génération de la page html de chaque utilisateur
                    String htmlContent = HtmlPage.GenerateHtmlPage(user);

                    File userHtmlFile = File.createTempFile("user", ".html");

                    FileWriter fw = new FileWriter(userHtmlFile.getAbsoluteFile());
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(htmlContent);
                    bw.close();

                    if (Sftp.IsConnected()) {
                        Sftp.Upload(userHtmlFile.getAbsolutePath(), user.getSurname().toLowerCase().charAt(0) + user.getName().toLowerCase() + ".html");
                    }

                    if (!user.getPhoto().isEmpty()) {
                        File userPhotoFile = File.createTempFile("photo", ".jpg");

                        URL url = new URL(user.getPhoto());
                        InputStream is = url.openStream();
                        OutputStream os = new FileOutputStream(userPhotoFile);

                        byte[] b = new byte[2048];
                        int length;

                        while ((length = is.read(b)) != -1) {
                            os.write(b, 0, length);
                        }

                        is.close();
                        os.close();

                        Sftp.Upload(userPhotoFile.getAbsolutePath(), user.getSurname().toLowerCase().charAt(0) + user.getName().toLowerCase() + ".jpg");
                    }
                }

                String indexHtmlContent = HtmlPage.GenerateIndex(users);

                File indexHtmlFile = File.createTempFile("index", ".html");

                FileWriter fw = new FileWriter(indexHtmlFile.getAbsoluteFile());
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(indexHtmlContent);
                bw.close();

                if (Sftp.IsConnected()) {
                    Sftp.Upload(indexHtmlFile.getAbsolutePath(), "index.html");
                }
            }

            Sftp.Disconnect();
        } catch (Exception ex) {
            System.out.println("Traitement terminé avec une erreur : " + ex.getMessage());
            System.exit(1);
        }

        System.out.println("\n Traitement terminé avec succès.");
        System.exit(0);
    }

    protected static List<String> GetGitFiles(String path) throws IOException {
        java.net.URL url;
        List<String> dataRaw = new ArrayList<>();

        try {
            url = new java.net.URL(path);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();

            uc.setRequestProperty("X-Requested-With", "Curl");
            String userPass = username + ":" + password;
            String basicAuth = "Basic " + new String(Base64.getEncoder().encode(userPass.getBytes()));//needs Base64 encoder, apache.commons.codec
            uc.setRequestProperty("Authorization", basicAuth);

            int responseCode = uc.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    dataRaw.add(line);
                }

                return dataRaw;
            } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(url + " est inaccessible, le fichier n'existe pas.");
            }

            return null;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

        private static User generateUser(String username) throws IOException {
        // Récupération des informations de l'utilisateur passé en paramètre
        List<String> userData = GetGitFiles(gitPath + username + ".txt");

        if (userData != null && userData.size() >= 4) {
            String name = userData.get(0);
            String surname = userData.get(1);
            String job = userData.get(2);
            String password = userData.get(3);

            // Création d'une nouvelle instance de User
            User user = new User(name, surname);

            user.setJob(job);
            user.setPassword(password);

            // Récupération du matériel de l'utilisateur
            List<String> materials = new ArrayList<>();

            if (userData.size() >= 5) {
                for (int i = 5; i <= userData.size() - 1; i++) {
                    materials.add(userData.get(i));
                }

                if (materials.size() > 0) {
                    user.setMaterials(materials);
                }
            }

            user.setPhoto(gitPath + username + ".jpg");

            return user;
        }

        return null;
    }
}