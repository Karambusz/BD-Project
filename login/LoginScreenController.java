package login;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import checkinput.CheckTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    @FXML
    private Pane mainArea;
    @FXML
    private TextField numberField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passField;


    public boolean checkLoginData(String number, String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujPacjenta(?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,number);
            pst.setString(2,login);
            pst.setString(3,pass);
            pst.execute();
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
        if (checkLoginData(number, login, pass)) {
            AlertBox.infoAlert("Ura", "Konto utworzone.", "Twoje konto zostało pomyślnie utworzone. Możesz zalogować się za pomocą numeru telefonu, logina i hasła");
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
