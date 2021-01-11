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
import javafx.scene.text.Text;
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HospitalScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;

    @FXML
    private ComboBox<String> hospitalsList;

    @FXML
    private TableView<Specialist> specialistTable;
    @FXML
    private TableColumn<Specialist, String> name;
    @FXML
    private TableColumn<Specialist, String> surname;
    @FXML
    private TableColumn<Specialist, String> specialization;
    @FXML
    private TableColumn<Specialist, Integer> office;
    @FXML
    private TableColumn<Specialist, Integer> price;
    @FXML
    private TableColumn<Specialist, String> days;
    @FXML
    private DatePicker dob;


    private void setData() {
        try {
            con = DBManagment.connect();
            String sql = "SELECT * FROM placowka";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();
            while (res.next()) {
                String tmp = res.getString("mejscowosc") + "," + res.getString("ulica")+","+res.getInt("nr_budynku");
                hospitalsList.getItems().add(tmp);
            }
            con.close();
            pst.close();
            res.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void searchSpecialist(MouseEvent event) {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        office.setCellValueFactory(new PropertyValueFactory<>("office"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        days.setCellValueFactory(new PropertyValueFactory<>("days"));

        ObservableList<Specialist> list = FXCollections.observableArrayList();
        try {
            if (hospitalsList.getSelectionModel().getSelectedItem() == null) {
                throw new Exception();
            }
            String [] tmp = hospitalsList.getSelectionModel().getSelectedItem().split(",");
            con = DBManagment.connect();
            String sql = "select * from informacjaSpecjalista where mejscowosc=? and ulica=? and nr_budynku=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, tmp[0]);
            pst.setString(2, tmp[1]);
            pst.setInt(3, Integer.parseInt(tmp[2]));
            res = pst.executeQuery();
            while (res.next()) {
                String sql1 = "select * from dnipracyspecjalista dps where dps.id_specjalista=?;";
                pst = con.prepareStatement(sql1);
                pst.setInt(1, res.getInt("id_specjalista"));
                res1 = pst.executeQuery();
                StringBuilder d = new StringBuilder();
                while (res1.next()) {
                    d.append(res1.getString("dzien")).append(" ");
                }
                list.add(new Specialist(res.getString("imie"), res.getString("nazwisko"), res.getString("specjalizacja"), Integer.parseInt(res.getString("id_gabinet")), Integer.parseInt(res.getString("cena_wizyty")), d.toString()));
                pst.close();
                res1.close();
            }
            con.close();
            pst.close();
            res.close();
            days.setCellFactory(param -> {
                TableCell<Specialist, String> cell = new TableCell<>();
                Text text = new Text();
                cell.setGraphic(text);
                cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
                text.textProperty().bind(cell.itemProperty());
                text.wrappingWidthProperty().bind(days.widthProperty());
                return cell;
            });
        }catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }
        specialistTable.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
        dob.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
