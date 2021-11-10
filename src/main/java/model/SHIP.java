package model;

public enum SHIP {
    BLUE("file:src/main/resources/blue_ship.png", "file:src/main/resources/blue_life.png"),
    GREEN("file:src/main/resources/green_ship.png", "file:src/main/resources/green_life.png"),
    ORANGE("file:src/main/resources/orange_ship.png", "file:src/main/resources/orange_life.png"),
    RED("file:src/main/resources/red_ship.png", "file:src/main/resources/red_life.png");

    private String urlShip;
    private String urlLife;

    SHIP(String urlShip, String urlLife) {
        this.urlShip = urlShip;
        this.urlLife = urlLife;
    }

    public String getUrl() {
        return this.urlShip;
    }

    public String getURLLife() {
        return this.urlLife;
    }

}
