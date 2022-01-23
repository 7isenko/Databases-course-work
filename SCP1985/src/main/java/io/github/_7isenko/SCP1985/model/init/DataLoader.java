package io.github._7isenko.SCP1985.model.init;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


/**
 * @author 7isenko
 */
@Component
public class DataLoader implements ApplicationRunner {

    private final InitialDataManager initialDataManager;


    public DataLoader(InitialDataManager initialDataManager) {
        this.initialDataManager = initialDataManager;
    }

    @Override
    public void run(ApplicationArguments args) {
        initialDataManager.requireInitialData();
        System.out.println("Данные об SCP-1985 сохранены.");
    }

}
