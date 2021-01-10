package login;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import changescreen.ChangeScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    public static Stage myStage = null;
    public static int acc;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ChangeScreen screen = new ChangeScreen();
    @FXML
    private Pane mainArea;
    @FXML
    private TextField numberField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;
    @FXML
    private RadioButton patientBtn;
    @FXML
    private RadioButton specialistBtn;
    @FXML
    private RadioButton managerBtn;


    public boolean checkPatientLoginData(String number, String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujPacjenta(?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,number);
            pst.setString(2,login);
            pst.setString(3,pass);
            pst.execute();

            con.close();
            pst.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        }
        return false;
    }

    public boolean checkSpecialitLoginData(String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujSpecjalista(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,login);
            pst.setString(2,pass);
            pst.execute();

            con.close();
            pst.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        }
        return false;
    }

    public boolean checkManagerLoginData(String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujDyrektora(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,login);
            pst.setString(2,pass);
            pst.execute();

            con.close();
            pst.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        }
        return false;
    }

    public void loginAccount(MouseEvent event) throws IOException{
        String number = numberField.getText();
        String login = loginField.getText();
        String pass = passField.getText();
        if(patientBtn.isSelected()) {
            if (checkPatientLoginData(number, login, pass)) {
//                ((Node)event.getSource()).getScene().getWindow().hide();
//                Parent root = FXMLLoader.load(getClass().getResource("/patientdashboard/PatientDashboard.fxml"));
//                Scene scene = new Scene(root);
//                Stage stage = new Stage();
//                stage.setTitle("Obsługa przychodni specjalistycznej");
//                stage.setScene(scene);
//                stage.setResizable(false);
//                stage.show();
//                this.myStage = stage;
                try {
                    con = DBManagment.connect();
                    String sql = "select * from pacjent where nr_telefonu=?;";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, numberField.getText());
                    res = pst.executeQuery();

                    if (res.next()) {
                        acc = res.getInt("id_pacjent");
                        System.out.println(acc);
                    }
                    con.close();
                    pst.close();
                    res.close();
                    screen.loadScreen("/patientdashboard/PatientDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (specialistBtn.isSelected()) {
            if (checkSpecialitLoginData(login, pass)) {
                try {
                    con = DBManagment.connect();
                    String sql = "select * from dane_specjalista where login=? and haslo=?;";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, loginField.getText());
                    pst.setString(2, passField.getText());
                    res = pst.executeQuery();

                    if (res.next()) {
                        acc = res.getInt("id_specjalista");
                        System.out.println(acc);
                    }
                    con.close();
                    pst.close();
                    res.close();
                    screen.loadScreen("/specialistdashboard/SpecialistDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (managerBtn.isSelected()) {
            if (checkManagerLoginData(login, pass)) {
                try {
                    con = DBManagment.connect();
                    String sql = "select * from dane_dyrektor where login=? and haslo=?;";
                    pst = con.prepareStatement(sql);
                    pst.setString(1, loginField.getText());
                    pst.setString(2, passField.getText());
                    res = pst.executeQuery();

                    if (res.next()) {
                        acc = res.getInt("id_dyrektor");
                        System.out.println(acc);
                    }
                    con.close();
                    pst.close();
                    res.close();
                    screen.loadScreen("/admindashboard/AdminDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    @FXML
    private void createAccount(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/createaccount/CreateAccount.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
