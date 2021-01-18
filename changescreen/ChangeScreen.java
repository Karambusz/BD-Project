package changescreen;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ChangeScreen {

    /**
     * Metoda, przekierowująca użytkownika do okna, ktore jest określone w zmiennej resource
     * @param resource [String] - scieżka do okna
     * @param event [MouseEvent]    -   zdarzenie
     * @param myStage [Stage]
     * @see IOException
     */
    public void loadScreen(String resource, MouseEvent event, Stage myStage) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource(resource));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Obsługa przychodni specjalistycznej");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        myStage = stage;
    }


    /**
     * Metoda, przekierowująca użytkownika do okna logowania
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    public void logout(MouseEvent event) throws IOException {
        System.out.println("Click");
        ((Node)event.getSource()).getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle("Obsługa przychodni specjalistycznej");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
