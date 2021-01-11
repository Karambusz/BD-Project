package patientdashboard;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class SpecialistScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private ComboBox<String> specialisationBox;
    @FXML
    private ComboBox<String> daysBox;

    @FXML
    private TableView<Specialist> specialistTable;
    @FXML
    private TableColumn<Specialist, String> name;
    @FXML
    private TableColumn<Specialist, String> surname;
    @FXML
    private TableColumn<Specialist, String> city;
    @FXML
    private TableColumn<Specialist, String> street;
    @FXML
    private TableColumn<Specialist, Integer> number;
    @FXML
    private TableColumn<Specialist, Integer> office;
    @FXML
    private TableColumn<Specialist, Integer> price;
    @FXML
    private DatePicker dob;

    private void setData() {
        ObservableList<String> specializeList = FXCollections.observableArrayList("Psychoterapeuta", "Diagnostyk", "Neurolog", "Dermatolog", "Laryngolog");
        try {
           con = DBManagment.connect();
           String sql = "select * from dzien_pracy";
           pst = con.prepareStatement(sql);
           res = pst.executeQuery();
           while (res.next()) {
               daysBox.getItems().add(res.getString("dzien"));
           }
           con.close();
           pst.close();
           res.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        specialisationBox.getItems().addAll(specializeList);
    }

    @FXML
    private void searchSpecialist(MouseEvent event) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        city.setCellValueFactory(new PropertyValueFactory<>("city"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        office.setCellValueFactory(new PropertyValueFactory<>("office"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        ObservableList<Specialist> list = FXCollections.observableArrayList();

        try {
            if(specialisationBox.getSelectionModel().getSelectedItem() == null || daysBox.getSelectionModel().getSelectedItem() == null) {
                throw new Exception();
            }
            String specialization = specialisationBox.getSelectionModel().getSelectedItem();
            String day = daysBox.getSelectionModel().getSelectedItem();
            con = DBManagment.connect();
            String sql = "select * from informacjaSpecjalista inf join dnipracyspecjalista dps on inf.id_specjalista = dps.id_specjalista where dps.dzien='" +day+"' and inf.specjalizacja='"+specialization+"';";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            while (res.next()) {
                System.out.println("Imie = " + res.getString("imie"));
                list.add(new Specialist(res.getString("imie"), res.getString("nazwisko"), res.getString("mejscowosc"), res.getString("ulica"), res.getInt("nr_budynku"), Integer.parseInt(res.getString("id_gabinet")), Integer.parseInt(res.getString("cena_wizyty"))));
            }
            con.close();
            pst.close();
            res.close();
        }catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }
        specialistTable.setItems(list);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
        setData();
    }
}
