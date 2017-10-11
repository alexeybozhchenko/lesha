package cs5.bozhchenko;
import java.util.Calendar;
import java.util.Date;
/**
 * Created by motorcrue on 26.09.2017.
 */



public class User {

    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;

    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }



    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return getLastName() + ", " + getFirstName();
    }

    public int getAge() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int currentYear = calendar.get(Calendar.YEAR);
        calendar.setTime(getDateOfBirth());
        int year = calendar.get(Calendar.YEAR);
        return currentYear - year;}

}


