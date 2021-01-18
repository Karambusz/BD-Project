package alerts;

import javafx.scene.control.Alert;

public class AlertBox {

    /**
     * Metoda, wyświetlająca komunikat z określonym błędem
     *      @param headerText [String]
     *      @param contentText [String]
     */
    public static void errorAlert(String headerText, String contentText) {

        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Bład");
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }

    /**
     * Metoda, wyświetlająca komunikat z określonym błędem
     *      @param title [String]
     *      @param headerText [String]
     *      @param contentText [String]
     */
    public static void infoAlert(String title,  String headerText, String contentText) {

        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(headerText);
        a.setContentText(contentText);
        a.showAndWait();
    }
}
