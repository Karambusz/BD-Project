package login;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import changescreen.ChangeScreen;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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


    /**
     * Funkcja, która sprawdza mozliwość logowania pacjenta do aplikacji
     *      @param number [String]    -   numer pacjenta
     *      @param login [String]    -   login
     *      @param pass [String]    -   hasło
     *      @return  [boolean]
     *      @see SQLException
     */
    public boolean checkPatientLoginData(String number, String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujPacjenta(?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,number);
            pst.setString(2,login);
            pst.setString(3,pass);
            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        }  finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Funkcja, która sprawdza mozliwość logowania specjalisty do aplikacji
     *      @param login [String]    -   login
     *      @param pass [String]    -   hasło
     *      @return  [boolean]
     *      @see SQLException
     */
    public boolean checkSpecialitLoginData(String login, String pass) {
        try {
            con = DBManagment.connect();
            String sql = "Select zalogujSpecjalista(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,login);
            pst.setString(2,pass);
            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Funkcja, która sprawdza mozliwość logowania dyrektora do aplikacji
     *      @param login [String]    -   login
     *      @param pass [String]    -   hasło
     *      @return  [boolean]
     *      @see SQLException
     */
    public boolean checkManagerLoginData(String login, String pass) {
        try {
            con= DBManagment.connect();
            String sql = "Select zalogujDyrektora(?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1,login);
            pst.setString(2,pass);
            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Nie udało sie zalogować!", "Nie udało sie zalogować " + e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    /**
     * Funkcja która loguje uzytkownika w zalezności od wybranej roli
     *      @param event [MouseEvent]    -   zdarzenie
     *      @see Exception
     */
    public void loginAccount(MouseEvent event) throws IOException{
        String number = numberField.getText();
        String login = loginField.getText();
        String pass = passField.getText();
        if(patientBtn.isSelected()) {
            if (checkPatientLoginData(number, login, pass)) {
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
                    res.close();
                    pst.close();
                    con.close();
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
                    res.close();
                    pst.close();
                    con.close();
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
                    res.close();
                    pst.close();
                    con.close();
                    screen.loadScreen("/admindashboard/AdminDashboard.fxml", event, myStage);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }

            }
        }
    }

    /**
     * Metoda, przekierowująca użytkownika do okna Rejestracji konta
     * @param event [MouseEvent]    -   zdarzenie
     * @see IOException
     */
    @FXML
    private void createAccount(MouseEvent event) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/createaccount/CreateAccount.fxml"));
        mainArea.getChildren().removeAll();
        mainArea.getChildren().addAll(fxml);
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
