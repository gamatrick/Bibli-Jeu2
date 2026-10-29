package fr.vinci.biblijeu2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// Base H2 en mémoire : le test ne crée pas de fichier data/ .
@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:testdb")
class BibliJeu2ApplicationTests {

    @Test
    void contextLoads() {
    }

}