package users;

public class Specialist {
    private String name;
    private String surname;
    private String specialization;
    private int office;
    private int price;

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
}
