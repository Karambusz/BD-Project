package patientdashboard;

import SQLManagment.DBManagment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.LoginScreenController;
import users.Patient;

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

    @FXML
    private TableView<Patient> historyTable;
    @FXML
    private TableColumn<Patient, String> sName;
    @FXML
    private TableColumn<Patient, String> sSurname;
    @FXML
    private TableColumn<Patient, String> date;
    @FXML
    private TableColumn<Patient, String> diseases;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     * @see Exception
     */
    private void setData() {
        sName.setCellValueFactory(new PropertyValueFactory<>("specialistName"));
        sSurname.setCellValueFactory(new PropertyValueFactory<>("specialistSurname"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        diseases.setCellValueFactory(new PropertyValueFactory<>("diseases"));

        ObservableList<Patient> list = FXCollections.observableArrayList();
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

            res.close();
            pst.close();
            String sql1 = "select c.s_imie, c.s_nazwisko, c.data, c.nazwa  from chorobypacjenta c where id_historia="+PatientDashboardController.idhistoria+";";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            while(res.next()) {
                System.out.println(res.getString("s_imie") + res.getString("s_nazwisko") + res.getString("data") + res.getString("nazwa"));
                list.add(new Patient(res.getString("s_imie"), res.getString("s_nazwisko"), res.getString("data"), res.getString("nazwa")));
            }
            res.close();
            pst.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        historyTable.setItems(list);
    }

    /**
     * Jest to główna metoda wykorzystująca metodę setData()
     * @param location [URL]
     * @param resources [ResourceBundle]
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
