package io.github._7isenko.databasefiller;

import com.sun.istack.internal.Nullable;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class DatabaseFiller {

    private static final int MAX_SCP_ID = 4000;
    private static final Set<Integer> SCP_IDS = new HashSet<>();

    /**
     * @param args - принимает один целочисленный аргумент - количество SCP-объектов для добавления в бд.
     */
    public static void main(String[] args) {
        int amountOfDocuments = 10;
        if (args.length != 0) {
            try {
                amountOfDocuments = Integer.parseInt(args[0]);
            } catch (Exception e) {
                System.out.println("Unpassable number");
            }
        }

        for (int i = 1; i <= amountOfDocuments; i++) {
            int scpId = getRandomSCPNumber();
            Document document;
            try {
                document = receiveDocumentByScpId(scpId);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Internet problems");
                return;
            }
            if (document == null) {
                i--;
                continue;
            }

            String name = getNameFromDocument(document);
            String description = getDescriptionFromDocument(document);

            if (name == null || description == null) {
                System.out.printf("Страница объекта №%d - SCP-%03d имеет нестандартную структуру. " +
                        "Она будет пропущена.%n%n", i, scpId);
                continue;
            }

            System.out.printf("Объект %d:%n", i);
            System.out.printf("SCP-%03d - %s%n", scpId, name);
            System.out.printf("%s%n%n", description);
        }
    }

    private static int getRandomSCPNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomId;
        do {
            randomId = random.nextInt(1, MAX_SCP_ID);
        } while (SCP_IDS.contains(randomId));
        SCP_IDS.add(randomId);
        return randomId;
    }

    @Nullable
    private static String getNameFromDocument(Document document) {
        Element nameElement = document.getElementById("page-title");
        if (nameElement == null) return null;
        return nameElement.text().split(" ", 3)[2];
    }

    @Nullable
    private static String getDescriptionFromDocument(Document document) {
        for (Element element : document.select("#page-content p")) {
            Elements strong = element.getElementsByTag("strong");
            if (strong.size() == 0) continue;
            if (strong.get(0).text().startsWith("Описание")) {
                return element.text().substring(10);
            }
        }
        return null;
    }


    @Nullable
    private static Document receiveDocumentByScpId(int scpId) throws IOException {
        try {
            String link = String.format("https://scp-ru.wikidot.com/scp-%03d", scpId);
            return Jsoup.connect(link).get();
        } catch (HttpStatusException e) {
            return null;
        }
    }
}
