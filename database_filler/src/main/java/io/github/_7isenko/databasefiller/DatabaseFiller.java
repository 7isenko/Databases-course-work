package io.github._7isenko.databasefiller;

import com.sun.istack.internal.Nullable;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class DatabaseFiller {

    private static final int MAX_SCP_ID = 4000;

    public static void main(String[] args) {

        /*
        make args for file
         */


        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < 2; i++) {
            int scpId = random.nextInt(1, MAX_SCP_ID);
            Document doc = receiveDocFromId(scpId);
            if (doc == null) continue;

            // TODO: есть страницы в необычном формате. Их игнорировать.
            Element name = doc.getElementById("page-title");
            System.out.println(name != null ? name.text().split(" ", 3)[2] : null);
        }
    }

    @Nullable
    private static Document receiveDocFromId(int scpId) {
        try {
            String link = String.format("http://scp-ru.wikidot.com/scp-%03d", scpId);
            return Jsoup.connect(link).get();
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404)
                System.out.println(String.format("Объект SCP-%03d не существует", scpId));
            return null;
        } catch (IOException e) {
            System.out.println(String.format("Проблемы с интернет-соединением: %s", e.getMessage()));
            return null;
        }
    }

}
