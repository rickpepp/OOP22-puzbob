package it.unibo.puzbob.view;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
    private final String FXML_FOLDER = "view" + FILE_SEPARATOR + "board.fxml";
    private static final String APP_NAME = "Puzbob";

    private final String ICON16 = "view" + FILE_SEPARATOR + "icon16.png";
    private final String ICON32 = "view" + FILE_SEPARATOR + "icon32.png";
    private final String ICON64 = "view" + FILE_SEPARATOR + "icon64.png";

    private static Output output;

    /**
     * Start of the JavaFX application
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        // Get screen Dimension
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();

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

        // Icons of the application
        Image icon16 = new Image(ClassLoader.getSystemResource(ICON16).toString());
        Image icon32 = new Image(ClassLoader.getSystemResource(ICON32).toString());
        Image icon64 = new Image(ClassLoader.getSystemResource(ICON64).toString());
        primaryStage.getIcons().addAll(icon16, icon32, icon64);
        
        // Property of stage
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void run(final String[] args) {
        launch(args);
    }

    /**
     * Public static getter for the Output
     * @return an Output class
     */
    public static Output getController() {
        return output;
    }

    /**
     * Entry point class.
     */
    public static final class Main {
        private Main() {
            // the constructor will never be called directly.
        }

        /**
         * Program's entry point.
         * @param args args for the starting app
         */
        public static void main(final String... args) {
            View.run(args);
        }
    }
    
}
