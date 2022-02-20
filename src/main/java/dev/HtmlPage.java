package dev;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HtmlPage {
    public static String GenerateHtmlPage(User user) throws IOException {
        String templateHtml = App.gitPath + "templateUserPage.html";
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

        String templateHtml = App.gitPath + "templateIndexPage.html";
        List<String> htmlTemplateIndex = App.GetGitFiles(templateHtml);
        StringBuilder htmlIndex = new StringBuilder();

        if (htmlTemplateIndex != null) {
            for (String line: htmlTemplateIndex) {
                htmlIndex.append(line);
            }
        }

        htmlIndex = new StringBuilder(htmlIndex.toString().replace("[[liste]]", li));

        return htmlIndex.toString();
    }
}
