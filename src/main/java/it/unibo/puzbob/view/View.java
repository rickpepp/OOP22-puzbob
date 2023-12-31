package it.unibo.puzbob.view;

import java.awt.Dimension;

import it.unibo.puzbob.controller.GameState;
import it.unibo.puzbob.controller.SaveState;
import it.unibo.puzbob.controller.SaveStateImpl;
import it.unibo.puzbob.controller.commands.MoveLeft;
import it.unibo.puzbob.controller.commands.MoveRight;
import it.unibo.puzbob.controller.commands.Shot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * This is the main Class. This work in JavaFX
 */
public class View extends Application {

    /**
     * Default constructor
     */
    public View() {}

    /**
     * Proportion between the screen risolution and the window
     */
    public static final double WINDOW_PROPORTION = 1.5;
    /**
     * File separator to improve multi-system programming
     */
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private final String FXML_FOLDER = "board.fxml";
    private final String FXML_FILE_START = "StartingMenu.fxml";
    private static final String APP_NAME = "Puzbob";

    private final String ICON16 = "icon16.png";
    private final String ICON32 = "icon32.png";
    private final String ICON64 = "icon64.png";

    private static Output output;
    private SaveState saveState;

    /**
     * Start of the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Icons of the application
        Image icon16 = new Image(ClassLoader.getSystemResource(ICON16).toString());
        Image icon32 = new Image(ClassLoader.getSystemResource(ICON32).toString());
        Image icon64 = new Image(ClassLoader.getSystemResource(ICON64).toString());
        primaryStage.getIcons().addAll(icon16, icon32, icon64);

        // Get screen Dimension
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

        // The Starting Menu
        FXMLLoader loaderStart = new FXMLLoader(ClassLoader.getSystemResource(FXML_FILE_START));
        AnchorPane rootPaneStart = loaderStart.load();
        final Scene sceneStart = new Scene(rootPaneStart, screenSize.getWidth() / WINDOW_PROPORTION, 
            screenSize.getHeight() / WINDOW_PROPORTION);

        // The Starting Menu controller
        FXMLControllerStart fxmlControllerStart = loaderStart.getController();

        fxmlControllerStart.scale();
        fxmlControllerStart.startPosition();

        // Property of stage
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(sceneStart);
        primaryStage.show();

        // Load the fxml file
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML_FOLDER));
        AnchorPane rootPane = loader.load();
        final Scene scene = new Scene(rootPane, screenSize.getWidth() / WINDOW_PROPORTION, 
            screenSize.getHeight() / WINDOW_PROPORTION);

        // Get Controller
        FXMLController fxmlController = loader.getController();
        output = fxmlController.getOutput();

        // The window is not resizable
        primaryStage.setResizable(false);

        // New SaveState
        this.saveState = new SaveStateImpl();

        // Check if there is a saved state
        if (this.saveState.thereIsState()) {
            fxmlControllerStart.getLoadButton().setDisable(false);
        }

        // Set the buttons events
        fxmlControllerStart.getNewButton().setOnAction(event -> {
            game(primaryStage, scene, 1 , 0);
        });

        fxmlControllerStart.getLoadButton().setOnAction(event -> {
            game(primaryStage, scene, saveState.getLevel() , saveState.getScore());
        });
 
    }

    private void game(Stage primaryStage, Scene scene, int level, int score) {
        // New GameState
        GameState gs = new GameState(getController(), this.saveState, score, level);

        // Put it in a new Thread
        Thread thread = new Thread(() -> {
            gs.startNewLevel();
        });

        // Close it when close the view
        thread.setDaemon(true);

        // Start the thread
        thread.start();

        // Set the scene in the stage
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set the input
        scene.setOnKeyPressed(event ->{
            KeyCode key = event.getCode();
            switch(key){
                case LEFT: 
                    gs.getGameLoop().notifyInput(new MoveLeft());
                    break;
                case  RIGHT:
                    gs.getGameLoop().notifyInput(new MoveRight());
                    break;
                case  SPACE:
                    gs.getGameLoop().notifyInput(new Shot());
                    break;
                default:
                    break;
            }
        });
    }

    /**
     * This run the javaFX application
     * @param args the args to pass at the game
     */
    public static void run(final String[] args) {
        launch(args);
    }

    /**
     * Public static getter for the Output
     * @return an Output class
     */
    public static Output getController() {
        return output;
    }
    
}
