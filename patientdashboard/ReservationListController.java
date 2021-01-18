package patientdashboard;

import SQLManagment.DBManagment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import login.LoginScreenController;
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class ReservationListController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;

    @FXML
    private TableView<Specialist> reservationTable;
    @FXML
    private TableColumn<Specialist, String> name;
    @FXML
    private TableColumn<Specialist, String> surname;
    @FXML
    private TableColumn<Specialist, String> specialization;
    @FXML
    private TableColumn<Specialist, String> street;
    @FXML
    private TableColumn<Specialist, Integer> office;
    @FXML
    private TableColumn<Specialist, Integer> price;
    @FXML
    private TableColumn<Specialist, String> date;

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     * @see Exception
     */
    private void setData() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        specialization.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        street.setCellValueFactory(new PropertyValueFactory<>("street"));
        office.setCellValueFactory(new PropertyValueFactory<>("office"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        date.setCellValueFactory(new PropertyValueFactory<>("days"));

        ObservableList<Specialist> list = FXCollections.observableArrayList();
        try{
            con = DBManagment.connect();
            String sql = "select * from informacjaSpecjalista join pacjent_specjalista using(id_specjalista) where id_pacjent = ?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, LoginScreenController.acc);
            res = pst.executeQuery();
            while(res.next()) {
                list.add(new Specialist(res.getString("imie"), res.getString("nazwisko"), res.getString("specjalizacja"), (res.getString("ulica") + " " + res.getInt("nr_budynku")), Integer.parseInt(res.getString("id_gabinet")), Integer.parseInt(res.getString("cena_wizyty")), res.getString("data")));
            }
            res.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        reservationTable.setItems(list);
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
