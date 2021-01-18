package users;

public class Specialist {
    private String name;
    private String surname;
    private String specialization;
    private int office;
    private int price;
    private  String days;
    private String city;
    private String street;
    private int number;

    /**
     * Constructs Specialist object
     * @param name [String]    -   imię specjalisty
     * @param surname [String]    -   nazwisko specjalisty
     * @param specialization [String]    -   specjalizacja specjalisty
     * @param street [String]    -   ulica na której znajduje się placówka
     * @param office [int]    -   numer gabinetu
     * @param price [int]    -   cena wizyty
     * @param days [String]    -   dni pracy
     */
    public Specialist(String name, String surname, String specialization,  String street, int office, int price, String days) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.street = street;
        this.office = office;
        this.price = price;
        this.days = days;
    }

    /**
     * Constructs Specialist object
     * @param name [String]    -   imię specjalisty
     * @param surname [String]    -   nazwisko specjalisty
     * @param city [String]    -   mejscowość
     * @param street [String]    -   ulica na której znajduje się placówka
     * @param number [int]    -   numer budynku
     * @param office [int]    -   numer gabinetu
     * @param price [int]    -   cena wizyty
     *
     */
    public Specialist(String name, String surname, String city, String street, int number, int office, int price) {
        this.name = name;
        this.surname = surname;
        this.city = city;
        this.street = street;
        this.number = number;
        this.office = office;
        this.price = price;

    }

    /**
     * Constructs Specialist object
     * @param name [String]    -   imię specjalisty
     * @param surname [String]    -   nazwisko specjalisty
     * @param specialization [String]    -   specjalizacja specjalisty
     * @param office [int]    -   numer gabinetu
     * @param price [int]    -   cena wizyty
     * @param days [String]    -   dni pracy
     */
    public Specialist(String name, String surname, String specialization, int office, int price, String days) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.office = office;
        this.price = price;
        this.days = days;
    }


    /**
     * Constructs Specialist object
     * @param name [String]    -   imię specjalisty
     * @param surname [String]    -   nazwisko specjalisty
     * @param specialization [String]    -   specjalizacja specjalisty
     * @param office [int]    -   numer gabinetu
     * @param price [int]    -   cena wizyty
     */
    public Specialist(String name, String surname, String specialization, int office, int price) {
        this.name = name;
        this.surname = surname;
        this.specialization = specialization;
        this.office = office;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getOffice() {
        return office;
    }

    public void setOffice(int office) {
        this.office = office;
    }

    public String getDays() {
        return days;
    }
    public void setDays() {
        this.days = days;
    }
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
