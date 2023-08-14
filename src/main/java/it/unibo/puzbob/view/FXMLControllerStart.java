package it.unibo.puzbob.view;

import java.awt.Dimension;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

/**
 * This is the fxml controller for the starting menu.
 */
public class FXMLControllerStart {

    // Stage dimension
    private double screenHeight;
    private double screenWidth;

    // Button dimension
    private final double BUTTON_WIDTH_PROPORTION = 0.15;
    private final double BUTTON_HEIGHT_PROPORTION = 0.1;

    // Button position
    private final double BUTTON_NEW_LAYOUT_X_PROPORTION = 0.125;
    private final double BUTTON_LOAD_LAYOUT_X_PROPORTION = 0.725;
    private final double BUTTON_LAYOUT_Y_PROPORTION = 0.8;

    // Texts position
    private final double TITLE_LAYOUT_Y_PROPORTION = 0.1;
    private final double DESCRIPTION_LAYOUT_Y_PROPORTION = 0.25;
    private final double KEYS_DESCRIPTION_LAYOUT_Y_PROPORTION = 0.5;
    
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

    /**
     * This is done when the fxml file is loaded
     */
    @FXML
    public void initialize() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        this.screenWidth = screenSize.getWidth() / View.WINDOW_PROPORTION;
        this.screenHeight = screenSize.getHeight() / View.WINDOW_PROPORTION;
    }

    /**
     * This is the method that scale the button dimension compared to the screen dimension
     */
    @FXML
    public void scale() {
        newGameButton.setPrefHeight(screenHeight * BUTTON_HEIGHT_PROPORTION);
        loadGameButton.setPrefHeight(screenHeight * BUTTON_HEIGHT_PROPORTION);
        newGameButton.setPrefWidth(screenWidth * BUTTON_WIDTH_PROPORTION);
        loadGameButton.setPrefWidth(screenWidth * BUTTON_WIDTH_PROPORTION);
    }

    /**
     * This is the method that set the elements position compared to the screen dimension
     */
    @FXML
    public void startPosition() {
        newGameButton.setLayoutX(screenWidth * BUTTON_NEW_LAYOUT_X_PROPORTION);
        loadGameButton.setLayoutX(screenWidth * BUTTON_LOAD_LAYOUT_X_PROPORTION);
        newGameButton.setLayoutY(screenHeight * BUTTON_LAYOUT_Y_PROPORTION);
        loadGameButton.setLayoutY(screenHeight * BUTTON_LAYOUT_Y_PROPORTION);

        title.setLayoutX((screenWidth - title.getBoundsInLocal().getWidth()) / 2);
        title.setLayoutY((screenHeight * TITLE_LAYOUT_Y_PROPORTION) 
            + title.getBoundsInLocal().getHeight());

        description.setLayoutX((screenWidth - description.getBoundsInLocal().getWidth()) / 2);
        description.setLayoutY((screenHeight * DESCRIPTION_LAYOUT_Y_PROPORTION) 
            + description.getBoundsInLocal().getHeight());

        keysDescription.setLayoutX((screenWidth - keysDescription.getBoundsInLocal().getWidth()) / 2);
        keysDescription.setLayoutY((screenHeight * KEYS_DESCRIPTION_LAYOUT_Y_PROPORTION) 
            + description.getBoundsInLocal().getHeight());
    }

    /**
     * This is a getter for the newButton (to apply the event handling)
     * @return a JavaFX Button type
     */
    @FXML
    public Button getNewButton() {
        return newGameButton;
    }

    /**
     * This is a getter for a loadButton (to apply the event handling)
     * @return a JavaFX Button type
     */
    @FXML
    public Button getLoadButton() {
        return loadGameButton;
    }

}
