package specialistdashboard;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import login.LoginScreenController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangePINController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;

    @FXML
    private TextField oldPin;
    @FXML
    private TextField newPin;
    @FXML
    private TextField retypePin;

    SpecialistDashboardController dc = new SpecialistDashboardController();

    /**
     * Metoda, która pozwala zmienić hasło
     *      @see Exception
     */
    public void changePass(MouseEvent event) {
        try {
            con = DBManagment.connect();
            String sql = "select * from dane_specjalista where id_specjalista=? and haslo=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            pst.setString(2, oldPin.getText());
            res = pst.executeQuery();
            if (res.next()) {
                if(newPin.getText().equals(retypePin.getText())) {
                    String sql1 = "UPDATE dane_specjalista SET haslo='" + newPin.getText() + "'WHERE id_specjalista='" + LoginScreenController.acc + "';";
                    pst = con.prepareStatement(sql1);
                    pst.executeUpdate();

                    AlertBox.infoAlert("Zmiana hasła", "Hasło zostało pomyślnie zmienione","Twoje hasło zostało zmienione, teraz musisz zalogować się ponownie przy użyciu nowego hasła");
                    pst.close();
                    oldPin.setText("");
                    newPin.setText("");
                    retypePin.setText("");
                    dc.logout(event);
                } else {
                    AlertBox.errorAlert("Bład", "Hasło nie zostało zmienione, upewnij się, że nowe hasło zostało poprawnie wpisane.");
                    oldPin.setText("");
                    newPin.setText("");
                    retypePin.setText("");
                }
            } else {
                AlertBox.errorAlert("Bład", "Hasło nie zostało zmienione, sprawdź dane i sprobuj ponownie.");
            }
            res.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
