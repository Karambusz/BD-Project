package specialistdashboard;

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

public class SpecialistDashboardController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    @FXML
    private Label nameLabel;
    @FXML
    private Pane mainArea;
    public static String specialistName;
    public static String specialistSurname;

    private void setData() {
        try {
            con = DBManagment.connect();
            String sql = "select * from specjalista where id_specjalista=?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            res = pst.executeQuery();

            if(res.next()) {
                StringBuffer tmp = new StringBuffer();
                tmp.append(res.getString("imie")).append(" ").append(res.getString("nazwisko"));
                nameLabel.setText(tmp.toString());
                specialistName = res.getString("imie");
                specialistSurname = res.getString("nazwisko");
            }
            con.close();
            pst.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void VisitList(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("VisitList.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
        System.out.println("Click");
    }

    public void logout(MouseEvent event) throws IOException {
        screen.logout(event);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
