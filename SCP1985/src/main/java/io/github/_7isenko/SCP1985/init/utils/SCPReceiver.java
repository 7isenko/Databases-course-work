package io.github._7isenko.SCP1985.init.utils;

import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import io.github._7isenko.SCP1985.jpa.object_types.ObjectCLass;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class SCPReceiver {

    private final int maxScpId; // = 4000;
    private final Set<Integer> knownScpIds; // = new HashSet<>();

    public SCPReceiver(int maxScpId, @Nullable Set<Integer> knownScpIds) {
        this.maxScpId = maxScpId;
        this.knownScpIds = Objects.requireNonNullElseGet(knownScpIds, HashSet::new);
    }

    public ArrayList<ScpObjectEntity> getSCPList(int amount) {

        ArrayList<ScpObjectEntity> scpInstances = new ArrayList<>();

        for (int i = 1; i <= amount; i++) {
            int scpId = getRandomSCPNumber();
            Document document;
            try {
                document = receiveDocumentByScpId(scpId);
            } catch (IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Проблемы с соединением к Интернету");
                break;
            }
            if (document == null) {
                i--;
                continue;
            }


            String name = getNameFromDocument(document);
            String description = getDescriptionFromDocument(document);
            String clazz = getClassFromDocument(document);

            if (name == null || description == null || clazz == null || !ObjectCLass.hasValue(clazz)) {
                System.out.printf("Страница объекта №%d - SCP-%03d имеет нестандартную структуру. " +
                        "Он будет пропущен.%n%n", i, scpId);
                continue;
            }
            scpInstances.add(new ScpObjectEntity(scpId, name, description, ObjectCLass.valueOf(clazz)));

            System.out.printf("Объект %d:%n", i);
            System.out.printf("SCP-%03d - %s, Класс: %s%n", scpId, name, clazz);
            System.out.printf("%s%n%n", description);
        }

        return scpInstances;
    }



    private int getRandomSCPNumber() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        int randomId;
        do {
            randomId = random.nextInt(1, maxScpId);
        } while (knownScpIds.contains(randomId));
        knownScpIds.add(randomId);
        return randomId;
    }

    @Nullable
    private String getNameFromDocument(Document document) {
        Element nameElement = document.getElementById("page-title");
        if (nameElement == null) return null;

        String[] split = nameElement.text().split(" ", 3);
        if (split.length == 3) return split[2];
        else return null;
    }

    @Nullable
    private String getDescriptionFromDocument(Document document) {
        for (Element element : document.select("#page-content p")) {
            Elements strong = element.getElementsByTag("strong");
            if (strong.size() == 0) continue;
            if (strong.get(0).text().startsWith("Описание")) {
                try {
                    return element.text().substring(9).trim();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Nullable
    private String getClassFromDocument(Document document) {
        for (Element element : document.select("#page-content p")) {
            Elements strong = element.getElementsByTag("strong");
            if (strong.size() == 0) continue;
            if (strong.get(0).text().startsWith("Класс объекта")) {
                try {
                    String clazz = element.select("a").text().trim();
                    if (!clazz.equals("")) {
                        return clazz;
                    } else {
                        return element.text().trim();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Nullable
    private Document receiveDocumentByScpId(int scpId) throws IOException {
        try {
            String link = String.format("https://scp-ru.wikidot.com/scp-%03d", scpId);
            return Jsoup.connect(link).get();
        } catch (HttpStatusException e) {
            return null;
        }
    }
}
