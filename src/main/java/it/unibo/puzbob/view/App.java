package it.unibo.puzbob.view;

import java.awt.Dimension;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        AnchorPane rootPane = FXMLLoader.load(ClassLoader.getSystemResource("view/board.fxml"));
        final Scene scene = new Scene(rootPane, screenSize.getWidth() / 1.5, screenSize.getHeight() / 1.5);
        
        primaryStage.setTitle("Puzzle Bobble");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static void run(final String[] args) {
        launch(args);
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
            App.run(args);
        }
    }
    
}
