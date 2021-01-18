package specialistdashboard;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import login.LoginScreenController;
import users.Patient;
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VisitList implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private TableView<Patient> visitTable;
    @FXML
    private TableColumn<Specialist, String> name;
    @FXML
    private TableColumn<Specialist, String> surname;
    @FXML
    private TableColumn<Specialist, Integer> age;
    @FXML
    private TableColumn<Specialist, String> phoneNumber;
    @FXML
    private TableColumn<Specialist, String> date;
    @FXML
    private TableColumn<Specialist, String> diseases;


    @FXML
    private ComboBox<String> diseasesList;


    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     * @see Exception
     */
    private void setData() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        diseases.setCellValueFactory(new PropertyValueFactory<>("diseases"));

        ObservableList<Patient> list = FXCollections.observableArrayList();

        try {
            con = DBManagment.connect();
            String sql = "select * from informacjapacjenta where id_specjalista=?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            res = pst.executeQuery();
            while(res.next()) {
                StringBuilder disease = new StringBuilder(" ");
                String sql1 = "select * from chorobypacjenta where id_pacjent=?;";
                pst = con.prepareStatement(sql1);
                pst.setInt(1, res.getInt("id_pacjent"));
                res1 = pst.executeQuery();
                while (res1.next()) {
                    disease.append(res1.getString("nazwa")).append(" ");
                }
                list.add(new Patient(res.getString("imie"), res.getString("nazwisko"), res.getInt("wiek"), res.getString("nr_telefonu"), res.getString("data"), disease.toString()));
                res1.close();
                pst.close();
            }
            res.close();
            pst.close();
            con.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        visitTable.setItems(list);
    }

    /**
     * Metoda, uzupełniająca listę chorób
     * @see Exception
     */
    private void setDiseases() {
        try {
            con = DBManagment.connect();
            String sql = "select * from choroba order by nazwa;";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                diseasesList.getItems().add(res.getString("nazwa"));
            }
            res.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metoda pozwalająca na dodanie wpisu do historii choroby i uzupełnienie tabeli po dodaniu
     * @param event [MouseEvent]
     * @see SQLException
     * @see Exception
     */
    @FXML
    private void addNote(MouseEvent event) {
        try{
            if(diseasesList.getSelectionModel().getSelectedItem() == null) {
                throw new Exception();
            }
            int idHisory = 0;
            int idDisease = 0;
            ObservableList<Patient> picked;
            picked = visitTable.getSelectionModel().getSelectedItems();

            String pName = picked.get(0).getName();
            String pSurname = picked.get(0).getSurname();
            int pAge = picked.get(0).getAge();
            String pDate = picked.get(0).getDate();
            String pDiseases = picked.get(0).getDiseases();

            con = DBManagment.connect();
            String sql = "SELECT * from informacjapacjenta where imie = ? and nazwisko = ? and wiek = ? and data = ?;";
            pst = con.prepareStatement(sql);
            pst.setString(1, pName);
            pst.setString(2, pSurname);
            pst.setInt(3,pAge);
            pst.setString(4, pDate);
            res = pst.executeQuery();
            if (res.next()) {
                idHisory = res.getInt("id_historia");
            }
            res.close();
            pst.close();

            System.out.println("Historia = " + idHisory + " choroba = " + idDisease + " " + SpecialistDashboardController.specialistName + " " + SpecialistDashboardController.specialistSurname);
            String sql1 = "select * from choroba where nazwa = ?";
            pst = con.prepareStatement(sql1);
            pst.setString(1, diseasesList.getSelectionModel().getSelectedItem());
            res = pst.executeQuery();
            if(res.next()) {
                idDisease = res.getInt("id_choroba");
            }
            res.close();
            pst.close();
            System.out.println("Historia = " + idHisory + " choroba = " + idDisease + " " + SpecialistDashboardController.specialistName + " " + SpecialistDashboardController.specialistSurname);
            String sql2 = "insert into  choroba_historia (id_historia, id_choroba, s_imie, s_nazwisko, data) values (?, ? , ? , ?, ?);";
            pst = con.prepareStatement(sql2);
            pst.setInt(1, idHisory);
            pst.setInt(2, idDisease);
            pst.setString(3, SpecialistDashboardController.specialistName);
            pst.setString(4, SpecialistDashboardController.specialistSurname);
            pst.setString(5, pDate);
            pst.executeUpdate();

            pst.close();
            con.close();
            setData();
            AlertBox.infoAlert("Wpis.", "Wpis został dodany", "Wpis został dodany");
        }catch (SQLException e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }catch (Exception e) {
            System.out.println(e.getMessage());
            AlertBox.errorAlert("Nie udało sie dodać wpisu.", "Brak wybranej choroby lub pacjenta. Wybierz chorobę oraz pacjenta aby dodać wpis.");
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
    }


    /**
     * Jest to główna metoda wykorzystująca metodę setData() oraz setDiseases()
     * @param location [URL]
     * @param resources [ResourceBundle]
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDiseases();
        setData();
    }
}
