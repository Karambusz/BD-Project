package admindashboard;

import SQLManagment.DBManagment;
import changescreen.ChangeScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import login.LoginScreenController;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class AdminDashboardController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    public static int idhospital;
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainArea;


    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     * @see Exception
     */
    public void setData() {
        try {
            con = DBManagment.connect();
            String sql = "select * from dyrektor where id_dyrektor=?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            res = pst.executeQuery();

            if(res.next()) {
                nameLabel.setText(res.getString("imie") + " " + res.getString("nazwisko"));
                idhospital = Integer.parseInt(res.getString("id_placowka"));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda, która wylogowuje użytkownika
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }


    /**
     * Metoda, przekierowująca użytkownika do okna "Dane o placówce"
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    @FXML
    private void information(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("InstitutionScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Dodaj Specjalistę"
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    @FXML
    private void addSpecialist(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("AddSpecialist.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Metoda, przekierowująca użytkownika do okna "Usuń Specjalistę"
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    @FXML
    private void removeSpecialist(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("RemoveSpecialist.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData()
     * @param location [URL]
     * @param resources [ResourceBundle]
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
