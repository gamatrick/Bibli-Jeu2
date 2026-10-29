package fr.vinci.biblijeu2.controllers;

import fr.vinci.biblijeu2.services.BibliothequeService;
import fr.vinci.biblijeu2.services.RawgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

@Controller
public class HomeController {

    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    private final RawgService rawgService;
    private final BibliothequeService bibliothequeService;

    public HomeController(RawgService rawgService, BibliothequeService bibliothequeService) {
        this.rawgService = rawgService;
        this.bibliothequeService = bibliothequeService;
    }

    // url accueil : http://localhost:8080/
    // url recherche : http://localhost:8080/?nom=zelda
    @GetMapping("/")
    public String accueil(@RequestParam(value = "nom", required = false) String nom, Model model) {
        model.addAttribute("nom", nom);

        if (nom != null && !nom.isBlank()) {
            try {
                model.addAttribute("resultats", rawgService.rechercher(nom.trim()));
                model.addAttribute("dejaAjoutes", bibliothequeService.getRawgIdsEnregistres());
            } catch (IllegalStateException e) {
                // Clé API absente.
                model.addAttribute("erreur", e.getMessage());
            } catch (RestClientException e) {
                // Sécurité : le message de l'exception contient l'URL appelée, donc la clé API visible.
                // Ne l'affichez jamais : message générique pour l'utilisateur !!!!
                // Et seulement le type d'erreur dans les logs.
                log.warn("Échec de l'appel RAWG : {}", e.getClass().getSimpleName());
                model.addAttribute("erreur", "Impossible de contacter RAWG pour le moment, réessaie plus tard.");
            }
        }
        return "index"; // templates/index.html
    }
}