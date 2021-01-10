package admindashboard;

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
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class InstitutionScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private Label city;
    @FXML
    private Label address;
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

    private void setData() {
        name.setCellValueFactory(new PropertyValueFactory<Specialist, String>("name"));
        surname.setCellValueFactory(new PropertyValueFactory<Specialist, String>("surname"));
        specialization.setCellValueFactory(new PropertyValueFactory<Specialist, String>("specialization"));
        office.setCellValueFactory(new PropertyValueFactory<Specialist, Integer>("office"));
        price.setCellValueFactory(new PropertyValueFactory<Specialist, Integer>("price"));

        ObservableList<Specialist> list = FXCollections.observableArrayList();
        try {
            con = DBManagment.connect();
            String sql = "SELECT * FROM placowka p, dyrektor d where p.id_placowka = d.id_placowka and d.id_dyrektor = "+ LoginScreenController.acc+";";
            pst = con.prepareStatement(sql);
            res = pst.executeQuery();

            if (res.next()) {
                city.setText(res.getString("mejscowosc"));
                StringBuilder tmp = new StringBuilder();
                tmp.append(res.getString("ulica")).append(", ").append(res.getString("nr_budynku"));
                address.setText(tmp.toString());
            }
            pst.close();
            res.close();

            con = DBManagment.connect();
            String sql1 = "SELECT * FROM informacjaspecjalista inf, dyrektor d WHERE inf.id_placowka = d.id_placowka and d.id_dyrektor = " + LoginScreenController.acc +";";
            pst = con.prepareStatement(sql1);
            res = pst.executeQuery();
            while (res.next()) {
                list.add(new Specialist(res.getString("imie"), res.getString("nazwisko"), res.getString("specjalizacja"), Integer.parseInt(res.getString("id_gabinet")), Integer.parseInt(res.getString("cena_wizyty"))));
            }
            con.close();
            pst.close();
            res.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        specialistTable.setItems(list);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
