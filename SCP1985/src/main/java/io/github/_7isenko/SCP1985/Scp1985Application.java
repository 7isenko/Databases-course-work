package io.github._7isenko.SCP1985;

import io.github._7isenko.SCP1985.jpa.entities.ScpObjectEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
@RestController
public class Scp1985Application {

    private final EntityManagerFactory entityManagerFactory;

    public Scp1985Application(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public static void main(String[] args) {
        SpringApplication.run(Scp1985Application.class, args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/scp")
    public String scp(@RequestParam(value = "id", defaultValue = "1985") String name) {
        String description = entityManagerFactory.createEntityManager().find(ScpObjectEntity.class, 1985).getDescription();
        return String.format("Description for %s:<p>%s</p>", name, description);
    }

}
