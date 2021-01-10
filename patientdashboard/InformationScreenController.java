package patientdashboard;

import SQLManagment.DBManagment;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import login.LoginScreenController;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class InformationScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private Label name;
    @FXML
    private Label surname;
    @FXML
    private Label age;
    @FXML
    private Label phoneNumber;

    private void setData() {
        try {
            con = DBManagment.connect();
            String sql = "SELECT * from pacjent where id_pacjent="+LoginScreenController.acc+";";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if(res.next()) {
                name.setText(res.getString("imie"));
                surname.setText(res.getString("nazwisko"));
                age.setText(String.valueOf(res.getInt("wiek")));
                phoneNumber.setText(res.getString("nr_telefonu"));
            }
            con.close();
            pst.close();
            res.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
