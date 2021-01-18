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
import login.LoginScreenController;
import users.Specialist;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class SpecialistScreenController implements Initializable {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet res = null;
    ResultSet res1 = null;
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

    /**
     * Metoda, ustawiająca początkowe dane na ekranie
     * @see Exception
     */
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

    /**
     * Metoda, która generuje listę specjalistów w zalezności od wybranej specjalizacji oraz dnia pracy
     *      @param event [MouseEvent]    -   zdarzenie
     *      @see Exception
     */
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
                dob.setDisable(true);
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
            dob.setDisable(false);
        }catch (Exception e) {
            AlertBox.errorAlert("Bląd", e.getMessage());
        }
        specialistTable.setItems(list);
    }

    /**
     * Metoda, która pozwala zarezerwować wizytę u wybranego specjalisty
     *      @see NullPointerException
     *      @see SQLException
     */
    @FXML
    private void reserve(MouseEvent event) {
        try {
            DateTimeFormatter dayOfWeekFormatter = DateTimeFormatter.ofPattern("EEE", Locale.ENGLISH);
            LocalDate date = LocalDate.of(dob.getValue().getYear(), dob.getValue().getMonth(), dob.getValue().getDayOfMonth());

            int dayOfReservation = PatientDashboardController.convertDay(date.format(dayOfWeekFormatter));
            System.out.println(dayOfReservation);
            ObservableList<Specialist> picked;
            picked = specialistTable.getSelectionModel().getSelectedItems();

            String sName = picked.get(0).getName();
            String sSurname = picked.get(0).getSurname();
            int sOffice = picked.get(0).getOffice();
            int sPrice = picked.get(0).getPrice();


            int sId = 0;

            System.out.println(sName + " " + sSurname + " " + " " + sOffice + " " + sPrice + " " + sId + " " + dob.getValue().toString());
            con = DBManagment.connect();
            String sql = "SELECT * from informacjaSpecjalista where imie=? and nazwisko=? and cena_wizyty=? and id_gabinet=?;";
            pst = con.prepareStatement(sql);
            pst.setString(1, sName);
            pst.setString(2, sSurname);
            pst.setInt(3, sPrice);
            pst.setInt(4, sOffice);
            res = pst.executeQuery();
            if(res.next()) {
                sId = res.getInt("id_specjalista");
            }
            pst.close();
            res.close();

            System.out.println(sName + " " + sSurname + " " + " " + sOffice + " " + sPrice + " " + sId + " " + dob.getValue().toString());

            String sql1 = "select * from plan_specjalisty where id_specjalista=? and id_dzien=?";
            pst = con.prepareStatement(sql1);
            pst.setInt(1, sId);
            pst.setInt(2, dayOfReservation);
            res = pst.executeQuery();
            if (res.next()) {
                int idVisit = 0;
                String sql2 = "insert into wizyta (kod_wizyty) values (?);";
                pst = con.prepareStatement(sql2);
                pst.setString(1, sName+sSurname+dob.getValue().toString());
                int in = pst.executeUpdate();
                if (in > 0) {
                    String sql3 = "select * from wizyta";
                    pst = con.prepareStatement(sql3);
                    res1 = pst.executeQuery();
                    if (res1.next()) {
                        idVisit = res1.getInt("id_wizyta");
                    }
                    res1.close();
                    pst.close();
                }
                pst.close();
                String sql4 = "insert into pacjent_specjalista (id_wizyta, id_specjalista, id_pacjent, data) values (?, ? ,? , ?)";
                pst = con.prepareStatement(sql4);
                pst.setInt(1, idVisit);
                pst.setInt(2, sId);
                pst.setInt(3, LoginScreenController.acc);
                pst.setString(4, dob.getValue().toString());
                pst.executeUpdate();
                System.out.println("TRUUUUE");
                AlertBox.infoAlert("Rezerwacja.", "Termin został zarezerwowany. Cena wizyty - " + sPrice + " zl", "Możesz sprawdzić termin w swoich rezerwacjach.");
                pst.close();
            } else {
                AlertBox.errorAlert("Bląd", "Specjalista nie jest dostępny w tym dniu. Wybierz inny dzień");
                System.out.println("FALSE");
            }
            res.close();
            pst.close();
            con.close();
        } catch (NullPointerException e) {
            AlertBox.errorAlert("Bląd", "Brak wybranej daty lub specjalisty");
        }
        catch (SQLException e) {
            AlertBox.errorAlert("Bląd", "Wybierz specjalizację oraz dzień");
        } finally {
            DBManagment.closeAll(con, res, pst);
        }
    }


    /**
     * Jest to główna metoda wykorzystująca metodę setData() oraz synhronizuje kalendarz z lokalnym czasem
     * @param location [URL]
     * @param resources [ResourceBundle]
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dob.setDisable(true);
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
