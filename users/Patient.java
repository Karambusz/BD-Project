package users;

public class Patient {
    private String name;
    private String surname;
    private String specialistName;
    private String specialistSurname;
    private int age;
    private String phoneNumber;
    private String date;
    private String diseases;

    /**
     * Constructs Patient object
     * @param patientName [String]    -   imię pacjenta
     * @param patientSurname [String]    -   nazwisko pacjenta
     * @param date [String]    -   data wizyty
     * @param diseases [String]    -   choroby pacjenta
     */
    public Patient(String patientName, String patientSurname, String date, String diseases) {
        this.specialistName = patientName;
        this.specialistSurname = patientSurname;
        this.date = date;
        this.diseases = diseases;
    }

    /**
     * Constructs Patient object
     * @param name [String]    -   imię pacjenta
     * @param surname [String]    -   nazwisko pacjenta
     * @param age [int]    -   wiek pacjenta
     * @param phoneNumber [String]    -   numer telefonu pacjenta
     * @param date [String]    -   data wizyty
     * @param diseases [String]    -   choroby pacjenta
     */
    public Patient(String name, String surname, int age, String phoneNumber, String date, String diseases) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.diseases = diseases;
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

    public String getSpecialistName() {
        return specialistName;
    }

    public void setSpecialistName(String specialistName) {
        this.specialistName = specialistName;
    }

    public String getSpecialistSurname() {
        return specialistSurname;
    }

    public void setSpecialistSurname(String specialistSurname) {
        this.specialistSurname = specialistSurname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }
}
