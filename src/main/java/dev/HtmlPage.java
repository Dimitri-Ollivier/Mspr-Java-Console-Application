package dev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HtmlPage {
    public static String GenerateHtmlPage(User user) throws IOException {
        File templateHtmlPage = new File(App.gitPath + "templateUserDesc.html");
        Scanner htmlScanner = new Scanner(templateHtmlPage);
        htmlScanner.useDelimiter(System.getProperty("line.separator"));

        StringBuilder htmlPage = new StringBuilder();

        while (htmlScanner.hasNext()) {
            htmlPage.append(htmlScanner.next());
        }

        htmlScanner.close();

        htmlPage = new StringBuilder(htmlPage.toString().replace("[[NAME]]", user.getName()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[SURNAME]]", user.getSurname()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[JOB]]", user.getJob()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[IDENT_CARD]]", user.getPhoto()));
        htmlPage = new StringBuilder(htmlPage.toString().replace("[[MATERIAL]]", user.getMaterials().toString()));

        return htmlPage.toString();
    }

    public static String GenerateIndex(List<String> staff) throws IOException {

        List<String> names = new ArrayList<>();
        for (String nom : staff) {
            names.add(nom.substring(1, 2).toUpperCase() + nom.substring(2));
        }
        java.util.Collections.sort(names);

        List<String> urls = new ArrayList<>();
        for (String nom : names) {
            for (String url : staff) {
                if (nom.equals(url.substring(1, 2).toUpperCase() + url.substring(2))) {
                    urls.add(url);
                }
            }
        }

        String li = "<ul>";
        int index = 0;
        for (String name : names) {
            li = li + "<li><a href=\"" + urls.get(index) + ".html\">" + name + "</a></li>";
            index++;
        }
        li = li + "</ul>";

        File doc2 = new File(App.gitPath + "templateIndex.html");
        Scanner sc2 = new Scanner(doc2);
        sc2.useDelimiter(System.getProperty("line.separator"));

        StringBuilder docHtml = new StringBuilder();

        while (sc2.hasNext()) {
            docHtml.append(sc2.next());
        }
        sc2.close();

        docHtml = new StringBuilder(docHtml.toString().replace("[[liste]]", li));

        return docHtml.toString();
    }
}
