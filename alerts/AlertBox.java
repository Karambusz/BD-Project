package alerts;

import javafx.scene.control.Alert;

public class AlertBox {
    public static void errorAlert(String headerText, String contentText) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Bład");
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }

    public static void infoAlert(String title,  String headerText, String contentText) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }
}
