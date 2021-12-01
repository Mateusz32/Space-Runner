package view;

import buttons.SpaceRunnerButton;
import lable.EnterNamePlayerLabel;
import lable.SmallInfoLabel;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import javafx.scene.media.AudioClip;
import textField.NameField;

import java.util.Date;
import java.util.Random;

public class GameViewManager {

    private AnchorPane gamePane;
    private Scene gameScene;
    private Stage gameStage;

    private final static int GAME_WIDTH = 600;
    private final static int GAME_HEIGHT = 700;

    private Stage menuStage;
    private ImageView ship;

    private boolean isLeftKeyPressed;
    private boolean isRightKeyPressed;
    private boolean isSpaceKeyPressed;
    private int angle;
    private AnimationTimer gameTimer;

    private GridPane gridPane1;
    private GridPane gridPane2;
    private final static String BACKGROUND_IMAGE = "file:src/main/resources/blue.png";

    private final static String METEOR_BROWN_IMAGE = "file:src/main/resources/meteor_brown.png";
    private final static String METEOR_GREY_IMAGE = "file:src/main/resources/meteor_grey.png";

    private ImageView[] brownMeteors;
    private ImageView[] greyMeteors;
    private Random randomPositionGenerator;

    private ImageView star;
    private SmallInfoLabel pointsLabel;
    private ImageView[] playerLifes;
    private int playerLife;
    private int points;
    private final static String GOLD_STAR_IMAGE = "file:src/main/resources/star_gold.png";

    private final static int STAR_RADIUS = 12;
    private final static int SHIP_RADIUS = 27;
    private final static int METEOR_RADIUS = 20;
    private final static int LASER_RADIUS = 12;

    private final static String LASER = "file:src/main/resources/laserBlue01.png";
    private ImageView[] laser;
    private int counter;
    private Player player;
    private NameField namePlayer;

    private Date startGame = new Date();

    private int speedOfMeteors = 7;

    public GameViewManager() {
        initializeStage();
        createKeyListeners();
        randomPositionGenerator = new Random();
    }

    private void createKeyListeners() {
        gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = true;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = true;
                } else if (event.getCode() == KeyCode.SPACE) {
                    isSpaceKeyPressed = true;
                }
            }
        });

        gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode() == KeyCode.LEFT) {
                    isLeftKeyPressed = false;
                } else if (event.getCode() == KeyCode.RIGHT) {
                    isRightKeyPressed = false;
                } else if (event.getCode() == KeyCode.SPACE) {
                    isSpaceKeyPressed = false;
                }
                ;
            }
        });
    }

    private void initializeStage() {
        gamePane = new AnchorPane();
        gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
        gameStage = new Stage();
        gameStage.setScene(gameScene);
    }

    public void createNewGame(Stage menuStage, SHIP choosenShip) {
        this.menuStage = menuStage;
        this.menuStage.hide();
        createBackground();
        createShip(choosenShip);
        createGameElements(choosenShip);
        createGameLoop();
        gameStage.show();
    }

    private void createGameElements(SHIP choosenShip) {
        playerLife = 2;
        star = new ImageView(GOLD_STAR_IMAGE);
        setNewElementsPosition(star);
        gamePane.getChildren().add(star);
        pointsLabel = new SmallInfoLabel("POINTS: 00");
        pointsLabel.setLayoutX(460);
        pointsLabel.setLayoutY(20);
        gamePane.getChildren().add(pointsLabel);
        playerLifes = new ImageView[3];

        for (int i = 0; i < playerLifes.length; i++) {
            playerLifes[i] = new ImageView(choosenShip.getURLLife());
            playerLifes[i].setLayoutX(455 + (i * 50));
            playerLifes[i].setLayoutY(80);
            gamePane.getChildren().add(playerLifes[i]);
        }

        brownMeteors = new ImageView[3];
        for (int i = 0; i < brownMeteors.length; i++) {
            brownMeteors[i] = new ImageView(METEOR_BROWN_IMAGE);
            setNewElementsPosition(brownMeteors[i]);
            gamePane.getChildren().add(brownMeteors[i]);
        }

        greyMeteors = new ImageView[3];
        for (int i = 0; i < greyMeteors.length; i++) {
            greyMeteors[i] = new ImageView(METEOR_GREY_IMAGE);
            setNewElementsPosition(greyMeteors[i]);
            gamePane.getChildren().add(greyMeteors[i]);
        }

        laser = new ImageView[10];
        for (int i = 0; i < laser.length; i++) {
            laser[i] = new ImageView(LASER);
            setNewElementsPosition(laser[i]);
            setNewElementsPosition(laser[i]);
            gamePane.getChildren().add(laser[i]);
        }
    }

    private void moveGameElements() {
        star.setLayoutY(star.getLayoutY() + 5);

        for (int i = 0; i < brownMeteors.length; i++) {
            brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + increaseSpeedOfMeteors());
            brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
        }

        for (int i = 0; i < greyMeteors.length; i++) {
            greyMeteors[i].setLayoutY(greyMeteors[i].getLayoutY() + increaseSpeedOfMeteors());
            greyMeteors[i].setRotate(greyMeteors[i].getRotate() + 4);
        }


        for (int i = 0; i < laser.length; i++) {
            laser[i].setLayoutY(laser[i].getLayoutY() - 15);
        }
    }

    private void checkElementsAreBehindTheShipAndRelocate() {
        if (star.getLayoutY() > 1200) {
            setNewElementsPosition(star);
        }

        for (int i = 0; i < brownMeteors.length; i++) {
            if (brownMeteors[i].getLayoutY() > 900) {
                setNewElementsPosition(brownMeteors[i]);
            }
        }

        for (int i = 0; i < greyMeteors.length; i++) {
            if (greyMeteors[i].getLayoutY() > 900) {
                setNewElementsPosition(greyMeteors[i]);
            }
        }
        for (int i = 0; i < laser.length; i++) {
            if (laser[i].getLayoutY() < -900) {
                laser[i].setLayoutY(ship.getLayoutY() + 100);
                laser[i].setLayoutX(ship.getLayoutX() + 1200);
            }
        }
    }

    private void setNewElementsPosition(ImageView image) {
        image.setLayoutX(randomPositionGenerator.nextInt(370));
        image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
    }

    private void createShip(SHIP choosenShip) {
        ship = new ImageView(choosenShip.getUrl());
        ship.setLayoutX(GAME_WIDTH / 2);
        ship.setLayoutY(GAME_HEIGHT - 90);
        gamePane.getChildren().add(ship);
    }

    private void createGameLoop() {
        gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                moveBackground();
                moveGameElements();
                checkElementsAreBehindTheShipAndRelocate();
                checkIfElementsCollide();
                shootLaser();
                moveShip();
            }
        };
        gameTimer.start();
    }

    private void moveShip() {
        if (isLeftKeyPressed && !isRightKeyPressed) {
            if (angle > -30) {
                angle -= 5;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() > -20) {
                ship.setLayoutX(ship.getLayoutX() - 3);
            }
        }

        if (isRightKeyPressed && !isLeftKeyPressed) {
            if (angle < 30) {
                angle += 5;
            }
            ship.setRotate(angle);
            if (ship.getLayoutX() < 522) {
                ship.setLayoutX(ship.getLayoutX() + 3);
            }
        }

        if (!isLeftKeyPressed && !isRightKeyPressed) {
            if (angle < 0) {
                angle += 5;
            } else if (angle > 0) {
                angle -= 5;
            }
            ship.setRotate(angle);
        }

        if (isLeftKeyPressed && isRightKeyPressed)
            if (angle < 0) {
                angle += 5;
            } else if (angle > 0) {
                angle -= 5;
            }
        ship.setRotate(angle);
        {
        }

    }

    private void createBackground() {
        gridPane1 = new GridPane();
        gridPane2 = new GridPane();

        for (int i = 0; i < 12; i++) {
            ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
            ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
            GridPane.setConstraints(backgroundImage1, i % 3, i / 3);
            GridPane.setConstraints(backgroundImage2, i % 3, i / 3);
            gridPane1.getChildren().add(backgroundImage1);
            gridPane2.getChildren().add(backgroundImage2);
        }

        gridPane2.setLayoutY(-1024);

        gamePane.getChildren().addAll(gridPane1, gridPane2);
    }

    private void moveBackground() {
        gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
        gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);

        if (gridPane1.getLayoutY() >= 1024) {
            gridPane1.setLayoutY(-1024);
        }
        if (gridPane2.getLayoutY() >= 1024) {
            gridPane2.setLayoutY(-1024);
        }
    }

    private void checkIfElementsCollide() {
        if (SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX() + 49, star.getLayoutX() + 15, ship.getLayoutY() + 37, star.getLayoutY() + 15)) {
            setNewElementsPosition(star);
            createSound(SOUNDS.SHIP_COLLIDED_WITH_STAR_SOUND);
            addPoints();
        }

        for (int i = 0; i < brownMeteors.length; i++) {
            if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX() + 49, brownMeteors[i].getLayoutX() + 20, ship.getLayoutY() + 37, brownMeteors[i].getLayoutY() + 20)) {
                removeLife();
                setNewElementsPosition(brownMeteors[i]);
                createSound(SOUNDS.METEOR_COLLIDE_WITH_SHIP_SOUND);
            }
        }

        for (int i = 0; i < greyMeteors.length; i++) {
            if (METEOR_RADIUS + SHIP_RADIUS > calculateDistance(ship.getLayoutX() + 49, greyMeteors[i].getLayoutX() + 20, ship.getLayoutY() + 37, greyMeteors[i].getLayoutY() + 20)) {
                removeLife();
                setNewElementsPosition(greyMeteors[i]);
                createSound(SOUNDS.METEOR_COLLIDE_WITH_SHIP_SOUND);
            }
        }

        for (int i = 0; i < laser.length; i++) {
            int counter = 0;
            while (counter < brownMeteors.length) {
                if (METEOR_RADIUS + LASER_RADIUS > calculateDistance(laser[i].getLayoutX() + 5, brownMeteors[counter].getLayoutX() + 20,
                        laser[i].getLayoutY() + 5, brownMeteors[counter].getLayoutY() + 20)) {
                    setNewElementsPosition(brownMeteors[counter]);
                    setNewElementsPosition(laser[i]);
                    addPoints();
                }
                counter++;
            }
        }

        for (int i = 0; i < laser.length; i++) {
            int counter = 0;
            while (counter < greyMeteors.length) {
                if (METEOR_RADIUS + LASER_RADIUS > calculateDistance(laser[i].getLayoutX() + 5, greyMeteors[counter].getLayoutX() + 20,
                        laser[i].getLayoutY() + 5, greyMeteors[counter].getLayoutY() + 20)) {
                    setNewElementsPosition(greyMeteors[counter]);
                    setNewElementsPosition(laser[i]);
                    addPoints();
                }
                counter++;
            }
        }
    }

    private void removeLife() {
        gamePane.getChildren().remove(playerLifes[playerLife]);
        playerLife--;
        if (playerLife < 0) {
            gameTimer.stop();
            createEnterNamePlayerLable();
            createNamePlayerTextFiled();
            createButtonBack();
        }
    }

    private double calculateDistance(double x1, double x2, double y1, double y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    private void createSound(SOUNDS sound) {
        AudioClip sounds = new AudioClip(sound.getSound());
        sounds.play();
    }

    private void shootLaser() {
        Date time = new Date();
        double beforeClickSpace = time.getTime();

        if (isSpaceKeyPressed) {
            createSound(SOUNDS.SHOOT_LASER_SOUND);
            double afterClickSpace = time.getTime();
            isSpaceKeyPressed = false;

            if (afterClickSpace - beforeClickSpace < 0.01) {
                laser[counter].setLayoutY(ship.getLayoutY() - 40);
                laser[counter].setLayoutX(ship.getLayoutX() + 45);
                counter++;
                if (counter >= laser.length) {
                    counter = 0;
                }
            }
        }
    }

    private void addPoints() {
        points++;
        String textToSet = "POINTS : ";
        if (points < 10) {
            textToSet = textToSet + "0";
        }
        pointsLabel.setText(textToSet + points);
    }

    private void createNamePlayerTextFiled() {
        namePlayer = new NameField();
        namePlayer.setCursor(Cursor.TEXT);
        namePlayer.setLayoutX(130);
        namePlayer.setLayoutY(300);
        gamePane.getChildren().add(namePlayer);
    }

    private SpaceRunnerButton createButtonBack() {
        SpaceRunnerButton backButton = new SpaceRunnerButton("BACK");
        backButton.setLayoutX(200);
        backButton.setLayoutY(500);
        gamePane.getChildren().add(backButton);

        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!namePlayer.getText().isBlank() && namePlayer.getText().length() <= 5) {
                    createNewPlayerAndSaveToTheListOfRanking();
                    gameStage.close();
                    menuStage.show();
                } else {
                    Label info = new Label("Set correctly Your name (max 5 characters)");
                    info.setLayoutX(180);
                    info.setLayoutY(350);
                    gamePane.getChildren().add(info);
                }
            }
        });

        return backButton;
    }

    private void createEnterNamePlayerLable() {
        EnterNamePlayerLabel namePlayer = new EnterNamePlayerLabel("Enter your name: ");
        namePlayer.setLayoutX(85);
        namePlayer.setLayoutY(100);
        gamePane.getChildren().add(namePlayer);
    }

    private void createNewPlayerAndSaveToTheListOfRanking() {
        player = new Player();
        player.setName(namePlayer.getText());
        player.setPoints(points);
        RankigOfPlayers ranking = new RankigOfPlayers();
        ranking.savePlayerIntoRanking(player);
    }

    private int increaseSpeedOfMeteors() {
        Date currentTime = new Date();

        if (currentTime.getTime() - startGame.getTime() > 10000) {
            speedOfMeteors = speedOfMeteors + 2;
            startGame = currentTime;
        }
        return speedOfMeteors;
    }

}
