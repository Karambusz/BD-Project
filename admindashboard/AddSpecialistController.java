package admindashboard;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import checkinput.CheckTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddSpecialistController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    private int idSpecialist;

    @FXML
    private TextField nameField;
    @FXML
    private TextField surnameField;
    @FXML
    private ComboBox<String> specialisationBox;
    @FXML
    private TextField priceField;
    @FXML
    private ComboBox<Integer> officeBox;
    @FXML
    private CheckBox monday;
    @FXML
    private CheckBox tuesday;
    @FXML
    private CheckBox wednesday;
    @FXML
    private CheckBox thursday;
    @FXML
    private CheckBox friday;
    @FXML
    private CheckBox saturday ;
    @FXML
    private CheckBox sunday;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;

    private void setData() {
        ObservableList<String> specializeList = FXCollections.observableArrayList("Psychoterapeuta", "Diagnostyk", "Neurolog", "Dermatolog", "Laryngolog");
        officeBox.getItems().clear();
        try {
            con = DBManagment.connect();
            String sql = "select g.id_gabinet from gabinet g EXCEPT select gp.id_gabinet from gabinet_placowka gp where gp.id_placowka ="+AdminDashboardController.idhospital + "  order by 1;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                officeBox.getItems().add(res.getInt("id_gabinet"));
            }

            res.close();
            pst.close();
            con.close();
            specialisationBox.getItems().addAll(specializeList);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private boolean checkAdd(String name, String surname, String spec, int price,  String login, String pass, int officeN, int hospitalN) {
        try {
            con = DBManagment.connect();
            String sql = "Select dodajSpecjalista(?,?,?,?,?,?,?,?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2,surname);
            pst.setString(3, spec);
            pst.setInt(4,price);
            pst.setString(5,login);
            pst.setString(6,pass);
            pst.setInt(7, officeN);
            pst.setInt(8, hospitalN);

            pst.execute();

            pst.close();
            con.close();
            return true;

        } catch (SQLException e) {
            AlertBox.errorAlert("Specjalista nie został dodany do bazy", e.getMessage());
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
        return false;
    }

    @FXML
    private void addSpecialistToBaze(MouseEvent event) {
        try {
            if(!(monday.isSelected() || tuesday.isSelected() || wednesday.isSelected() || thursday.isSelected() || friday.isSelected() || saturday.isSelected() || sunday.isSelected()) || officeBox.getItems().size() == 0) {
                AlertBox.errorAlert("Specjalista nie został dodany do bazy", "Brak dostępnych gabinetów albo nie wybrano żadnych dni pracy.");
            } else {
                if (CheckTextField.checkFullnameField(nameField) && CheckTextField.checkFullnameField(surnameField) && CheckTextField.checkAgeField(priceField) && CheckTextField.checkLogin(loginField) && CheckTextField.checkPass(passwordField)) {
                    String name = nameField.getText();
                    String surname = surnameField.getText();
                    String spec = specialisationBox.getSelectionModel().getSelectedItem();
                    int price = Integer.parseInt(priceField.getText());
                    String login = loginField.getText();
                    String pass = passwordField.getText();
                    int officeN = officeBox.getSelectionModel().getSelectedItem();
                    int hospitalN = AdminDashboardController.idhospital;
                    if (checkAdd(name, surname,spec , price, login,pass, officeN, hospitalN)) {
                        con = DBManagment.connect();
                        String takeId = "SELECT max(id_specjalista) FROM specjalista;";
                        pst = con.prepareStatement(takeId);
                        res = pst.executeQuery();
                        if(res.next()) {
                            idSpecialist = res.getInt("max");
                        }
                        res.close();
                        pst.close();
                        if(monday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ? );";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 1);
                            pst.executeUpdate();
                            System.out.println("monday");
                            pst.close();
                        }
                        if(tuesday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ? );";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 2);
                            pst.executeUpdate();
                            System.out.println("tuesday");
                            pst.close();
                        }
                        if(wednesday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ?);";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 3);
                            pst.executeUpdate();
                            System.out.println("wednesday");
                            pst.close();
                        }
                        if(thursday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ? );";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 4);
                            pst.executeUpdate();
                            System.out.println("thursday");
                            pst.close();
                        }
                        if(friday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ? );";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 5);
                            pst.executeUpdate();
                            System.out.println("friday");
                            pst.close();
                        }
                        if(saturday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ?);";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 6);
                            pst.executeUpdate();
                            System.out.println("saturday");
                            pst.close();
                        }
                        if(sunday.isSelected()) {
                            String sql = "INSERT INTO plan_specjalisty (id_specjalista, id_dzien) values (?, ?);";
                            pst = con.prepareStatement(sql);
                            pst.setInt(1, idSpecialist);
                            pst.setInt(2, 7);
                            pst.executeUpdate();
                            System.out.println("sunday");
                            pst.close();
                        }
                        pst.close();
                        con.close();
                        AlertBox.infoAlert("Specjalista dodany." ,"Specjalista został dodany do bazy.", "Specjalista został dodany.");
                        setData();
                    }

                }
                nameField.clear();
                surnameField.clear();
                specialisationBox.getSelectionModel().clearSelection();
                priceField.clear();
                loginField.clear();
                passwordField.clear();
                officeBox.getSelectionModel().clearSelection();
            }

        } catch (Exception e) {
            AlertBox.errorAlert("Specjalista nie został dodany do bazy", "Wybierz specjalizację oraz numer gabinetu.");
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
