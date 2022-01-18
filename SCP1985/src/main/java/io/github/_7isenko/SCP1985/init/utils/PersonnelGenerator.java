package io.github._7isenko.SCP1985.init.utils;

import com.github.javafaker.Faker;
import io.github._7isenko.SCP1985.jpa.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.jpa.object_types.Classification;
import io.github._7isenko.SCP1985.jpa.object_types.ClearanceLevel;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author 7isenko
 */
public class PersonnelGenerator {

    private final Faker faker = new Faker();
    private final Classification[] items = new Classification[]{new Classification("A", 0.5D),
            new Classification("B", 1), new Classification("C", 1.5D),
            new Classification("D", 10), new Classification("E", 3)};

    public ArrayList<PersonnelEntity> generatePersonnel(int amount) {
        double totalWeight = 0.0;
        for (Classification it : items) {
            totalWeight += it.getWeight();
        }

        ArrayList<PersonnelEntity> personnels = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int idx = 0;
            for (double r = Math.random() * totalWeight; idx < items.length - 1; ++idx) {
                r -= items[idx].getWeight();
                if (r <= 0.0) break;
            }
            Classification cl = items[idx];
            int level = 0;
            if (cl.name.equals("A")) level = ThreadLocalRandom.current().nextInt(4, 6);
            if (cl.name.equals("B")) level = ThreadLocalRandom.current().nextInt(3, 6);
            if (cl.name.equals("C")) level = ThreadLocalRandom.current().nextInt(2, 5);
            if (cl.name.equals("D")) level = 1;

            personnels.add(new PersonnelEntity(faker.name().firstName(), faker.name().lastName(), ClearanceLevel.values()[level], io.github._7isenko.SCP1985.jpa.object_types.Classification.valueOf(cl.name)));
        }
        return personnels;
    }


    private static class Classification {
        private final String name;
        private final double weight;

        public Classification(String name, double weight) {
            this.name = name;
            this.weight = weight;
        }

        public double getWeight() {
            return weight;
        }

        public String getName() {
            return name;
        }
    }
}
