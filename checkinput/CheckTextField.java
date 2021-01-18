package checkinput;

import alerts.AlertBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class CheckTextField {

    /**
     * Funkcja, która sprawdza poprawność wprowadzonego imienia lub nazwiska
     *      @param tmpField [TextField]    -   pole z którego pobieramy tekst
     *      @return  [boolean]
     */
    public static boolean checkFullnameField(TextField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Puste pole!", "Puste pole, wprowadź dane");
            return false;
        } else {
            Pattern p = Pattern.compile("[a-zA-Z]+");
            Matcher m = p.matcher(tmpField.getText());

            if(m.find() && m.group().equals(tmpField.getText())) {
                return true;
            } else {
                AlertBox.errorAlert("Twoje pole z imieniem lub nazwiskiem jest nieprawidłowe", "Proszę korzystać tylko ze znaków. Sprobuj jeszcze raz!");
                return false;
            }
        }

    }

    /**
     * Funkcja, która sprawdza poprawność wprowadzonego wieku
     *      @param tmpField [TextField]    -   pole z którego pobieramy tekst
     *      @return  [boolean]
     */
    public static boolean checkAgeField(TextField tmpField) {
        if(tmpField.getText().equals("")) {
            AlertBox.errorAlert("Puste pole!", "Puste pole, wprowadź dane");
            return false;
        } else {
            Pattern p = Pattern.compile("[0-9]+");
            Matcher m = p.matcher(tmpField.getText());

            if(m.find() && m.group().equals(tmpField.getText())) {
                return true;
            } else {
                AlertBox.errorAlert("Twoje pole z wiekiem jest nieprawidłowe", "Proszę wpisać tylko cyfry. Sprobuj jeszcze raz!");
                return false;
            }
        }
    }

    /**
     * Funkcja sprawdzająca poprawność pól numerycznych
     *      @param tmpField [TextField]    -   pole z którego pobieramy tekst
     *      @return  [boolean]
     */
    public static boolean checkNumberField(TextField tmpField) {
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(tmpField.getText());

        if(m.find() && m.group().equals(tmpField.getText()) && tmpField.getText().length() == 11) {
            return true;
        } else {
            AlertBox.errorAlert("Twoje pole z numerem jest nieprawidłowe", "Proszę wpisać tylko cyfry lub sprawdzić długość (wymagana długość - 11). Sprobuj jeszcze raz!");
            return false;
        }
    }

    /**
     * Funkcja sprawdzająca poprawność loginu
     *      @param tmpField [TextField]    -   pole z którego pobieramy tekst
     *      @return  [boolean]
     */
    public static boolean checkLogin(TextField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Brak tekstu w pole login", "Wpisz swój login");
            return false;
        }
        return true;
    }

    /**
     * Funkcja sprawdzająca poprawność hasła
     *      @param tmpField [PasswordField]    -   pole z którego pobieramy tekst
     *      @return  [boolean]
     */
    public static boolean checkPass(PasswordField tmpField) {
        if (tmpField.getText().equals("")) {
            AlertBox.errorAlert("Brak tekstu w pole hasło", "Wpisz swoje hasło");
            return false;
        }
        return true;
    }


}
