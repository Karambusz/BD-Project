package patientdashboard;

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

public class PatientDashboardController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    public static int idhistoria;
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainArea;

    public void setData() {
        try {
            con = DBManagment.connect();
            String sql = "select * from pacjent where id_pacjent=?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            idhistoria = LoginScreenController.acc;
            res = pst.executeQuery();

            if(res.next()) {
                StringBuffer tmp = new StringBuffer();
                tmp.append(res.getString("imie")).append(" ").append(res.getString("nazwisko"));
                nameLabel.setText(tmp.toString());
            }
            con.close();
            pst.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }

    @FXML
    private void Information(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @FXML
    private void Hospitals(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("HospitalScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @FXML
    private void Specialists(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("SpecialistScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
