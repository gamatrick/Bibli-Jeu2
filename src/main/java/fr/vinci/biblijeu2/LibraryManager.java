package fr.vinci.biblijeu2;

import org.springframework.stereotype.Service;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class LibraryManager {
    private static final String FILE_PATH = "library.txt";
    private List<Game> myLibrary = new ArrayList<>();

    public LibraryManager() {
        loadLibrary();
    }

    public void addGame(Game game) {
        if (myLibrary.stream().noneMatch(g -> g.getId() == game.getId())) {
            myLibrary.add(game);
            saveLibrary();
        }
    }

    public void removeGame(int gameId) {
        myLibrary.removeIf(g -> g.getId() == gameId);
        saveLibrary();
    }

    public List<Game> getLibrary() {
        return myLibrary;
    }

    private void saveLibrary() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Game game : myLibrary) {
                writer.println(game.getId() + ";" + game.getName() + ";" + game.getReleaseDate() + ";" + game.getRating());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadLibrary() {
        myLibrary.clear();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length >= 4) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String releaseDate = parts[2].equals("null") ? null : parts[2];
                    double rating = Double.parseDouble(parts[3]);

                    Game game = new Game(id, name, releaseDate, rating);
                    myLibrary.add(game);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}