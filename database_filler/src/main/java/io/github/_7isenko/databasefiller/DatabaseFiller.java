package io.github._7isenko.databasefiller;

import io.github._7isenko.databasefiller.database.DBRepository;
import io.github._7isenko.databasefiller.database.entities.Location;
import io.github._7isenko.databasefiller.database.entities.SCPInstance;
import io.github._7isenko.databasefiller.misc.CollectionsHelper;
import io.github._7isenko.databasefiller.scp.SCPReceiver;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 7isenko
 */
public class DatabaseFiller {

    private static final int DEFAULT_FOUNDATIONS_AMOUNT = 5;
    private static final int DEFAULT_SCP_AMOUNT = 5;

    /**
     * @param args - принимает один целочисленный аргумент - количество SCP-объектов для добавления в бд.
     */
    public static void main(@Nullable String[] args) {

        Locale.setDefault(new Locale("en", "US"));

        if (args.length != 0) {
            if (args[0].equals("help")) {
                // TODO: print full help
                System.out.println("List of arguments:");
                System.out.println("1 - amount of foundations to add, default - 5; number 1 will always exist"); // TODO: always exist
                System.out.println("2 - amount of scp to add, default - 100; number 1985 will always exist");
                return;
            }
        }

        int foundationsAmount = parseArg(args, 0, DEFAULT_FOUNDATIONS_AMOUNT, "количество фондов");
        int scpAmount = parseArg(args, 1, DEFAULT_SCP_AMOUNT, "количество SCP");

        DBRepository dbRepository = new DBRepository();

        // Adding foundations' locations
        Location baseFoundationLocation = new Location(37.928141, -119.026927);
        dbRepository.addLocation(baseFoundationLocation); // base foundation location
        List<Location> locationList = getRandomLocations(foundationsAmount);
        dbRepository.addLocationList(locationList);

        // Adding foundations
        dbRepository.addFoundation(baseFoundationLocation);
        for (Location location : locationList) {
            dbRepository.addFoundation(location);
        }

        // Adding SCPs
        SCPReceiver scpReceiver = new SCPReceiver(4000, null);
        dbRepository.addSCP(scpReceiver.receiveScp1985());
        List<SCPInstance> scpInstanceList = scpReceiver.getSCPList(scpAmount);
        dbRepository.addScpList(scpInstanceList);
        System.out.printf("%d случайных экземпляров SCP было добавлено в таблицу%n", scpInstanceList.size());


        dbRepository.close();
    }

    private static int parseArg(String[] args, int index, int defaultValue, String name) {
        if (args.length >= index + 1) {
            try {
                return Integer.parseInt(args[index]);
            } catch (Exception e) {
                System.out.printf("Невозможно распарсить %s, используется стандартное значение %d%n", name, defaultValue);
                return defaultValue;
            }
        }
        return defaultValue;
    }

    private static ArrayList<Location> getRandomLocations(int amount) {
        ArrayList<Double> latitudes = CollectionsHelper.getRandomList(amount, -90D, 90D);
        ArrayList<Double> longitudes = CollectionsHelper.getRandomList(amount, -180D, 180D);

        ArrayList<Location> locations = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            locations.add(new Location(latitudes.get(i), longitudes.get(i)));
        }

        return locations;
    }
}