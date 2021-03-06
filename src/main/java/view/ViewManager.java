package view;

import buttons.SpaceRunnerButton;
import lable.InfoLable;
import lable.RankingLabel;
import lable.TextInfoButtonLabel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;

import java.util.ArrayList;
import java.util.List;

public class ViewManager {

    private static final int HIGHT = 768;
    private static final int WIDTH = 1024;
    private AnchorPane mainPane;
    private Scene mainScene;
    private Stage mainStage;

    private final static int MENU_BUTTON_START_X = 100;
    private final static int MENU_BUTTON_START_Y = 150;

    private SpaceRunnerSubScene creditsSubScene;
    private SpaceRunnerSubScene helpSubScene;
    private SpaceRunnerSubScene scoresSubScene;
    private SpaceRunnerSubScene shipChoserSubScene;

    private SpaceRunnerSubScene sceneToHide;

    private List<SpaceRunnerButton> menuButtons;

    private List<ShipPicker> shipList;
    private SHIP choosenShip;

    private TextInfoButtonLabel textInfoButtonLabel;
    private String text;

    public ViewManager() {
        menuButtons = new ArrayList<>();
        mainPane = new AnchorPane();
        mainScene = new Scene(mainPane, WIDTH, HIGHT);
        mainStage = new Stage();
        mainStage.setScene(mainScene);
        createSubScene();
        createButtons();
        createBackground();
        createLogo();
    }

    private void showSubScene(SpaceRunnerSubScene subScene) {
        if (sceneToHide != null) {
            sceneToHide.moveSubScene();
        }
        subScene.moveSubScene();
        sceneToHide = subScene;
    }

    private void createSubScene() {
        creditsSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(creditsSubScene);

        helpSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(helpSubScene);

        createScoreSubScene();
        createShipChooserSubScene();
    }

    private void createShipChooserSubScene() {
        shipChoserSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(shipChoserSubScene);

        InfoLable choosenShipLable = new InfoLable("CHOOSEN YOUR SHIP");
        choosenShipLable.setLayoutX(110);
        choosenShipLable.setLayoutY(25);
        shipChoserSubScene.getPane().getChildren().add(choosenShipLable);
        shipChoserSubScene.getPane().getChildren().add(createShipToChoosen());
        shipChoserSubScene.getPane().getChildren().add(createButtonToStart());
    }

    private void createScoreSubScene() {
        scoresSubScene = new SpaceRunnerSubScene();
        mainPane.getChildren().add(scoresSubScene);
    }

    private HBox createShipToChoosen() {
        HBox box = new HBox();
        box.setSpacing(20);
        shipList = new ArrayList<>();
        for (SHIP ship : SHIP.values()) {
            ShipPicker shipToPicker = new ShipPicker(ship);
            shipList.add(shipToPicker);

            box.getChildren().add(shipToPicker);
            shipToPicker.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    for (ShipPicker ship : shipList) {
                        ship.setIsCircleChoosen(false);
                    }
                    shipToPicker.setIsCircleChoosen(true);
                    choosenShip = shipToPicker.getShip();
                }
            });
        }
        box.setLayoutX(300 - (118 * 2));
        box.setLayoutY(100);
        return box;
    }

    private SpaceRunnerButton createButtonToStart() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("START");
        startButton.setLayoutX(350);
        startButton.setLayoutY(300);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (choosenShip != null) {
                    GameViewManager gameManager = new GameViewManager();
                    gameManager.createNewGame(mainStage, choosenShip);
                }
            }
        });

        return startButton;
    }

    public Stage getMainStage() {
        return mainStage;
    }

    private void addMenuButton(SpaceRunnerButton button) {
        button.setLayoutX(MENU_BUTTON_START_X);
        button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size() * 100);
        menuButtons.add(button);
        mainPane.getChildren().add(button);
    }

    private void createButtons() {
        createStartButton();
        createScoresButton();
        createHelpButton();
        createCreditsButton();
        createExitButton();
    }

    private void createStartButton() {
        SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
        addMenuButton(startButton);

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                showSubScene(shipChoserSubScene);
            }
        });
    }

    private void createScoresButton() {
        SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
        addMenuButton(scoresButton);

        scoresButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scoresSubScene.getPane().getChildren().clear();
                InfoLable rankingTop5PlayersLabel = new InfoLable("RANKING TOP 5 PLAYERS");
                rankingTop5PlayersLabel.setLayoutX(110);
                rankingTop5PlayersLabel.setLayoutY(25);
                scoresSubScene.getPane().getChildren().add(rankingTop5PlayersLabel);
                scoresSubScene.getPane().getChildren().add(displayRanking());
                showSubScene(scoresSubScene);
            }
        });
    }

    private void createHelpButton() {
        SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
        addMenuButton(helpButton);

        helpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                text = "Control:\n" +
                        "\n" +
                        "Arrow right - move ship to the right\n" +
                        "\n" +
                        "Arrow left - move ship to the left\n" +
                        "\n" +
                        "Space - laser shooting\n" +
                        "\n" +
                        "Star - get star one point\n" +
                        "\n" +
                        "Meteor - shoot meteor get one point\n" +
                        "\n" +
                        "\n" +
                        "Orginal game by Javacraving - youtube\n" +
                        "Game modified by Mateusz Rudkiewicz";

                textInfoButtonLabel = new TextInfoButtonLabel(text);
                helpSubScene.getPane().getChildren().add(textInfoButtonLabel);
                showSubScene(helpSubScene);
            }
        });
    }

    private void createCreditsButton() {
        SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
        addMenuButton(creditsButton);

        creditsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                text = "You are a space delivery man. Your \n" +
                        "\n" +
                        "mission is to carry pachangers through\n" +
                        "\n" +
                        "asteroids. It's a dangerous mission.\n" +
                        "\n" +
                        "Be aware of asteroids. Collect stars\n" +
                        "\n" +
                        "and shoot meteors to get points.\n" +
                        "\n" +
                        "Time by time the game become harder.\n" +
                        "\n" +
                        "Good luck spacer runner.";

                textInfoButtonLabel = new TextInfoButtonLabel(text);
                creditsSubScene.getPane().getChildren().add(textInfoButtonLabel);
                showSubScene(creditsSubScene);
            }
        });

    }

    private void createExitButton() {
        SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
        addMenuButton(exitButton);

        exitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainStage.close();
            }
        });
    }

    private void createBackground() {
        Image backgroundImage = new Image("file:src/main/resources/blue.png", 256, 256, false, true);
        BackgroundImage backgound = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
        mainPane.setBackground(new Background(backgound));
    }

    private void createLogo() {
        ImageView logo = new ImageView("file:src/main/resources/space_runner.png");
        logo.setLayoutX(400);
        logo.setLayoutY(50);

        logo.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(new DropShadow());
            }
        });

        logo.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                logo.setEffect(null);
            }
        });

        mainPane.getChildren().add(logo);
    }

    private VBox displayRanking() {
        VBox box = new VBox();
        box.setSpacing(5);
        RankigOfPlayers ranking = new RankigOfPlayers();
        List<Player> listOfFiveBestPlayer = ranking.getFiveBestPlayer();
        RankingLabel label;

        for (int i = 0; i < listOfFiveBestPlayer.size(); i++) {
            label = new RankingLabel(i + 1 + ". " + listOfFiveBestPlayer.get(i).getName() + "   " + listOfFiveBestPlayer.get(i).getPoints());
            box.getChildren().add(label);
        }

        box.setLayoutX(120);
        box.setLayoutY(100);
        return box;
    }
}
