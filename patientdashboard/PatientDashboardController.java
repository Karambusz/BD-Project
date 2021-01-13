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
    private void information(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("InformationScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @FXML
    private void hospitals(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("HospitalScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    @FXML
    private void specialists(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("SpecialistScreen.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }
    @FXML
    private void reservations(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("ReservationList.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }

    public static int convertDay(String day) {
        int res = 0;
        switch (day) {
            case "Mon":
                res = 1;
                break;
            case "Tue":
                res = 2;
                break;
            case "Wed":
                res = 3;
                break;
            case "Thu":
                res = 4;
                break;
            case "Fri":
                res = 5;
                break;
            case "Sat":
                res = 6;
                break;
            case "Sun":
                res = 7;
                break;
        }
        return res;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
