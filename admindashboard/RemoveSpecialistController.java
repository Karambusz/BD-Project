package admindashboard;

import SQLManagment.DBManagment;
import alerts.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class RemoveSpecialistController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    @FXML
    private ListView<String> specialistList;

    private void setData() {
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            con = DBManagment.connect();
            String sql = "SELECT * from informacjaSpecjalista where id_placowka=?;";
            pst = con.prepareStatement(sql);
            pst.setInt(1, AdminDashboardController.idhospital);
            res = pst.executeQuery();
            while (res.next()) {
                list.add(res.getString("imie") + " " + res.getString("nazwisko") + " " + res.getString("specjalizacja") + " gabinet: " + res.getString("id_gabinet"));
            }
            res.close();
            pst.close();
            con.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
        specialistList.setItems(list);
    }

    @FXML
    private void removeSpecialist(MouseEvent event) {
        try {
            if(specialistList.getSelectionModel().getSelectedItem() == null) {
                System.out.println(specialistList.getSelectionModel().getSelectedItem());
                throw new Exception();
            }
            String tmp[] = specialistList.getSelectionModel().getSelectedItem().split(" ");
            for (String a: tmp) {
                System.out.println(a);
            }
            con = DBManagment.connect();
            String sql = "SELECT * from usunSpecjalista(?, ? ,?, ? , ?);";
            pst = con.prepareStatement(sql);
            pst.setString(1, tmp[0]);
            pst.setString(2, tmp[1]);
            if(tmp.length == 6) {
                pst.setString(3, tmp[2]+" "+tmp[3]);
                pst.setInt(4, Integer.parseInt(tmp[5]));
            } else {
                pst.setString(3, tmp[2]);
                pst.setInt(4, Integer.parseInt(tmp[4]));
            }
            pst.setInt(5, AdminDashboardController.idhospital);
            res = pst.executeQuery();
            if(res.next()) {
                setData();
                AlertBox.infoAlert("Usunięcie specjalisty", "Specjalista został usunięty z bazy danych", "Specjalista został usunięty z bazy danych");
            }
            res.close();
            pst.close();
            con.close();
        }catch (SQLException e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            AlertBox.errorAlert("Bląd", "Nie wybrano zadnego specjalisty.");
        } finally {
            DBManagment.closeAll(con, res, pst);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setData();
    }
}
