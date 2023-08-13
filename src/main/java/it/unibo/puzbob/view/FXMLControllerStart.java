package it.unibo.puzbob.view;

import java.awt.Dimension;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class FXMLControllerStart {

    private double screenHeight;
    private double screenWidth;
    
    @FXML
    AnchorPane pane;

    @FXML
    Button newGameButton;

    @FXML
    Button loadGameButton;

    @FXML
    Text title;

    @FXML
    Text description;

    @FXML
    Text keysDescription;

    @FXML
    public void initialize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth() / View.WINDOW_PROPORTION;
        this.screenHeight = screenSize.getHeight() / View.WINDOW_PROPORTION;
    }

    @FXML
    public void scale() {
        newGameButton.setPrefHeight(screenHeight * 0.1);
        loadGameButton.setPrefHeight(screenHeight * 0.1);
        newGameButton.setPrefWidth(screenWidth * 0.15);
        loadGameButton.setPrefWidth(screenWidth * 0.15);
    }

    @FXML
    public void startPosition() {
        newGameButton.setLayoutX(screenWidth * 0.125);
        loadGameButton.setLayoutX(screenWidth * 0.725);
        newGameButton.setLayoutY(screenHeight * 0.8);
        loadGameButton.setLayoutY(screenHeight * 0.8);

        title.setLayoutX((screenWidth - title.getBoundsInLocal().getWidth()) / 2);
        title.setLayoutY((screenHeight * 0.1) + title.getBoundsInLocal().getHeight());

        description.setLayoutX((screenWidth - description.getBoundsInLocal().getWidth()) / 2);
        description.setLayoutY((screenHeight * 0.25) + description.getBoundsInLocal().getHeight());

        keysDescription.setLayoutX((screenWidth - keysDescription.getBoundsInLocal().getWidth()) / 2);
        keysDescription.setLayoutY((screenHeight * 0.5) + description.getBoundsInLocal().getHeight());
    }

    @FXML
    public Button getNewButton() {
        return newGameButton;
    }

    @FXML
    public Button getLoadButton() {
        return loadGameButton;
    }

}
