package createaccount;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import checkinput.CheckTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


import login.Main;


public class CreateAccountController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private TextField ageField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;

    public void backToLogin(MouseEvent event) throws IOException {
        Main.myStage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml")));
    }

    public boolean createPatient(String name, String surname, int age, String number, String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select dodajPacjenta(?,?,?,?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2,surname);
            pst.setInt(3, age);
            pst.setString(4,number);
            pst.setString(5,login);
            pst.setString(6,pass);

            pst.execute();

            con.close();
            pst.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Konto nie utworzone", "Twoje konto nie zostało utworzone! Sprawdź dane i sprobuj jeszcze raz");
        }
        return false;
    }

    public void newAccount(MouseEvent event) throws IOException{
        String name = nameField.getText();
        String surname = surnameField.getText();
        int age = 0;
        try {
            age = Integer.parseInt(ageField.getText());
        } catch (NumberFormatException e) {
            AlertBox.errorAlert("Brak tekstu w polu 'Wiek'!", "Wpisz wiek!");
        }
        String number = numberField.getText();
        String login = loginField.getText();
        String pass = passField.getText();
        System.out.println("Wiek = " + age);
        if (CheckTextField.checkFullnameField(nameField) && CheckTextField.checkFullnameField(surnameField) && CheckTextField.checkAgeField(ageField) && CheckTextField.checkNumberField(numberField) && CheckTextField.checkLogin(loginField) && CheckTextField.checkPass(passField)) {
            if (createPatient(name, surname, age, number, login, pass)) {
                AlertBox.infoAlert("Konto utworzone", "Konto utworzone.", "Twoje konto zostało pomyślnie utworzone. Możesz zalogować się za pomocą numeru telefonu, logina i hasła");
            }
            Main.myStage.getScene().setRoot(FXMLLoader.load(getClass().getResource("/login/LoginScreen.fxml")));
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        numberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                numberField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
