package it.unibo.puzbob.view;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class View extends Application {

    public static final double WINDOW_PROPORTION = 1.5;
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private final String FXML_FOLDER = "view" + FILE_SEPARATOR + "board.fxml";
    private static final String APP_NAME = "Puzzle Bobble";

    private static Output output;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        FXMLLoader loader = new FXMLLoader(ClassLoader.getSystemResource(FXML_FOLDER));
        AnchorPane rootPane = loader.load();
        final Scene scene = new Scene(rootPane, screenSize.getWidth() / WINDOW_PROPORTION, 
            screenSize.getHeight() / WINDOW_PROPORTION);
        FXMLController fxmlController = loader.getController();
        output = fxmlController.getOutput();
        
        
        primaryStage.setTitle(APP_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void run(final String[] args) {
        launch(args);
    }

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
         * @param args
         */

        public static void main(final String... args) {
            View.run(args);
        }
    }
    
}
