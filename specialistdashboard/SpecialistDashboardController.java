package specialistdashboard;

import changescreen.ChangeScreen;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SpecialistDashboardController implements Initializable {

    ChangeScreen screen = new ChangeScreen();

    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
