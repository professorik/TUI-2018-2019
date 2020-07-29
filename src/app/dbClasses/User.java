package app.dbClasses;

public class User {
    private String name;
    private String sername;
    private String userName;
    private String password;
    private String typeOfUser;
    private String gender;

    public User(String name, String sername, String userName, String password, String typeOfUser, String gender) {
        this.name = name;
        this.sername = sername;
        this.userName = userName;
        this.password = password;
        this.typeOfUser = typeOfUser;
        this.gender = gender;
    }

    public User(String name, String sername, String userName, String typeOfUser, String gender) {
        this.name = name;
        this.sername = sername;
        this.userName = userName;
        this.typeOfUser = typeOfUser;
        this.gender = gender;
    }

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSername() {
        return sername;
    }

    public void setSername(String sername) {
        this.sername = sername;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTypeOfUser() {
        return typeOfUser;
    }

    public void setTypeOfUser(String typeOfUser) {
        this.typeOfUser = typeOfUser;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
