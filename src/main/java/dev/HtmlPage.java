package dev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HtmlPage {
    public static String GenerateHtmlPage(User user) throws IOException {
        String templateHtml ="https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Console-Application/master/src/main/resources/templateUserPage.html";
        List<String> htmlTemplatePage = App.GetGitFiles(templateHtml);
        StringBuilder htmlPage = new StringBuilder();

        if (htmlTemplatePage != null) {
            for (String line: htmlTemplatePage) {
                htmlPage.append(line);
            }
        }

        htmlPage = new StringBuilder(htmlPage.toString().replace("[[NAME]]", user.getName()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[SURNAME]]", user.getSurname()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[JOB]]", user.getJob()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[IDENT_CARD]]", user.getPhoto()));

        StringBuilder materialsHtmlContent = new StringBuilder();

        for(int i = 0; i <= user.getMaterials().size() - 1; i++) {
            materialsHtmlContent.append("<li><h3 class=\"materiel\">").append(user.getMaterials().get(i)).append("</h3><img class=\"checkIcon\" src=\"https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Backup-Directory/main/box_check_icon.svg\"></li>");
        }

        htmlPage = new StringBuilder(htmlPage.toString().replace("[[MATERIAL]]", materialsHtmlContent));

        return htmlPage.toString();
    }

    public static String GenerateIndex(List<String> staff, List<User> users) throws IOException {
        String templateHtml = "https://raw.githubusercontent.com/Dimitri-Ollivier/Mspr-Java-Console-Application/master/src/main/resources/templateIndexPage.html";
        List<String> htmlTemplateIndex = App.GetGitFiles(templateHtml);
        StringBuilder htmlIndex = new StringBuilder();

        if (htmlTemplateIndex != null) {
            for (String line: htmlTemplateIndex) {
                htmlIndex.append(line);
            }
        }

        StringBuilder usersHtmlContent = new StringBuilder();

        for(int i = 0; i <= users.size() - 1; i++) {
            usersHtmlContent.append("<li><a class=\"button\" href=\"").append(users.get(i).getSurname().charAt(0)).append(users.get(i).getName()).append(".html\">").append(users.get(i).getName()).append(" ").append(users.get(i).getSurname()).append("</a></li>");
        }

        htmlIndex = new StringBuilder(htmlIndex.toString().replace("[[USERS]]", usersHtmlContent));

        return htmlIndex.toString();
    }
}
