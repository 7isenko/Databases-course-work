package io.github._7isenko.SCP1985.model.utils;

import com.github.javafaker.Faker;
import io.github._7isenko.SCP1985.model.entities.PersonnelEntity;
import io.github._7isenko.SCP1985.model.object_types.Classification;
import io.github._7isenko.SCP1985.model.object_types.ClearanceLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author 7isenko
 */
public class PersonnelHelper {

    public static List<PersonnelEntity> getAllowedPersonnel(List<PersonnelEntity> personnelEntities) {
        return personnelEntities.stream().filter(personnelEntity ->
                        (personnelEntity.getClearanceLevel() == ClearanceLevel.THREE || personnelEntity.getClearanceLevel() == ClearanceLevel.FOUR ||
                                personnelEntity.getClearanceLevel() == ClearanceLevel.FIVE) && personnelEntity.getClassification() == Classification.C)
                .collect(Collectors.toList());
    }

    public static ArrayList<PersonnelEntity> generatePersonnel(int amount) {
        Faker faker = new Faker();
        double totalWeight = 0.0;
        for (ClassificationWithWeight it : items) {
            totalWeight += it.getWeight();
        }

        ArrayList<PersonnelEntity> personnels = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            int idx = 0;
            for (double r = Math.random() * totalWeight; idx < items.length - 1; ++idx) {
                r -= items[idx].getWeight();
                if (r <= 0.0) break;
            }
            ClassificationWithWeight cl = items[idx];
            int level = 0;
            if (cl.name.equals("A")) level = ThreadLocalRandom.current().nextInt(5, 6);
            if (cl.name.equals("B")) level = ThreadLocalRandom.current().nextInt(4, 6);
            if (cl.name.equals("C")) level = ThreadLocalRandom.current().nextInt(3, 5);
            if (cl.name.equals("D")) level = 1;

            personnels.add(new PersonnelEntity(faker.name().firstName(), faker.name().lastName(), ClearanceLevel.values()[level], Classification.valueOf(cl.name)));
        }
        return personnels;
    }


    private static class ClassificationWithWeight {
        private final String name;
        private final double weight;

        public ClassificationWithWeight(String name, double weight) {
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

    private static final ClassificationWithWeight[] items = new ClassificationWithWeight[]{new ClassificationWithWeight("A", 1),
            new ClassificationWithWeight("B", 2), new ClassificationWithWeight("C", 3),
            new ClassificationWithWeight("D", 6), new ClassificationWithWeight("E", 3)};

}
