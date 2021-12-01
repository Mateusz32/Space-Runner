package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RankigOfPlayers {

    private File file = new File("ranking.txt");
    private List<Player> listOfPlayer;

    public RankigOfPlayers() {
        this.listOfPlayer = new ArrayList<>();
    }

    public void createFileOfPlayers() {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void savePlayerIntoRanking(Player player) {
        createFileOfPlayers();
        String name = player.getName();
        String points = String.valueOf(player.getPoints());
        try {
            PrintWriter save = new PrintWriter(new FileWriter(file, true));
            save.println(name + "|" + points);
            save.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Player> getFiveBestPlayer() {
        String line = null;
        String name;
        int points;

        try {
            FileReader reader = new FileReader(file);
            BufferedReader readerOfPlayer = new BufferedReader(reader);
            try {
                line = readerOfPlayer.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (line != null) {

                try {
                    name = line.substring(0, line.indexOf("|"));
                    points = Integer.parseInt(line.substring(line.indexOf("|") + 1, line.length()));
                    Player player = new Player();
                    player.setName(name);
                    player.setPoints(points);
                    listOfPlayer.add(player);
                    line = readerOfPlayer.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Player> listOfFiveBestPlayers = listOfPlayer.stream()
                .sorted(Comparator.comparingInt(Player::getPoints)
                        .reversed())
                .limit(5)
                .collect(Collectors.toList());
        return listOfFiveBestPlayers;
    }
}

