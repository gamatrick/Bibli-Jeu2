package fr.vinci.biblijeu2;

import fr.vinci.biblijeu2.api.RawgApiService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GameController {

    private final RawgApiService rawgApiService;
    private final LibraryManager libraryManager;

    public GameController(RawgApiService rawgApiService, LibraryManager libraryManager) {
        this.rawgApiService = rawgApiService;
        this.libraryManager = libraryManager;
    }

    @GetMapping("/search")
    public String searchGames(@RequestParam String q) {
        try {
            return rawgApiService.searchGamesByName(q);
        } catch (Exception e) {
            return "Erreur lors de la recherche : " + e.getMessage();
        }
    }

    @GetMapping("/library")
    public List<Game> getLibrary() {
        return libraryManager.getLibrary();
    }
    @PostMapping("/library")
    public String addGameToLibrary(@RequestBody Game game) {
        libraryManager.addGame(game);
        return "Jeu ajouté avec succès : " + game.getName();
    }

    @DeleteMapping("/library/{id}")
    public String removeGameFromLibrary(@PathVariable int id) {
        libraryManager.removeGame(id);
        return "Jeu supprimé avec succès.";
    }
}