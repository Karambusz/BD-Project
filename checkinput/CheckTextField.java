package checkinput;

import alerts.AlertBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckTextField {

    public static boolean chechFullnameField(TextField tmpField) {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(tmpField.getText());

        if(m.find() && m.group().equals(tmpField.getText())) {
            return true;
        } else {
            AlertBox.errorAlert("Twoje pole z imieniem lub nazwiskiem jest nieprawidłowe", "Proszę korzystać tylko ze znaków. Sprobuj jeszcze raz!");
            return false;
        }
    }

    public static boolean chechAgeField(TextField tmpField) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(tmpField.getText());

        if(m.find() && m.group().equals(tmpField.getText())) {
            return true;
        } else {
            AlertBox.errorAlert("Twoje pole z wiekiem jest nieprawidłowe", "Proszę wpisać tylko cyfry. Sprobuj jeszcze raz!");
            return false;
        }
    }

    public static boolean chechNumberField(TextField tmpField) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(tmpField.getText());

        if(m.find() && m.group().equals(tmpField.getText()) && tmpField.getText().length() == 11) {
            return true;
        } else {
            AlertBox.errorAlert("Twoje pole z numerem jest nieprawidłowe", "Proszę wpisać tylko cyfry lub sprawdzić długość (wymagana długość - 11). Sprobuj jeszcze raz!");
            return false;
        }
    }


}
