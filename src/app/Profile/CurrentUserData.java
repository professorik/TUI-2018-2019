package app.Profile;

import app.dbClasses.Const;
import app.dbClasses.DatabaseHandler;
import app.dbClasses.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CurrentUserData {

    private static String FIRSTNAME = "Vlad";
    private static String LASTNAME = "Vlad";
    private static String LOGIN = "Vlad";
    private static String OLDLOGIN = "Vlad";
    private static String PASSWORDLAST = "Vlad";
    private static String TYPEUSER = "Vlad";
    // 0 - administrator , 1 - user , 2 - driver, 3 - cooker
    public static int type = 1;
    //0 - login, 1 - password aren't equal , 2 - never have password! , 3 - no errors
    public static int error = 3;
    private static boolean GENDER;


    //0 - famale , 1 - male
    public static void setUserData(String firstname , String lastname ,String login , boolean gender){
        if (isNotFound(login)) {
            FIRSTNAME = firstname;
            LASTNAME = lastname;
            GENDER = gender;
            LOGIN = login;
            ChangeDataBase(FIRSTNAME , LASTNAME , LOGIN , GENDER , PASSWORDLAST, OLDLOGIN);
        }
    }

    public static void setUserPassword(String firstname , String lastname ,String login , boolean gender, String password1, String password2 , String password3){
        if (PASSWORDLAST.equals(password1)){
            if (password2.equals(password3)){
                FIRSTNAME = firstname;
                LASTNAME = lastname;
                GENDER = gender;
                LOGIN = login;
                PASSWORDLAST = password2;
                ChangeDataBase(FIRSTNAME , LASTNAME , LOGIN , GENDER , PASSWORDLAST, OLDLOGIN);
            }else{
               error = 1;
                // System.out.println("Error. Can't Change your password, because your passwords are not equal!");
                //Error of equals passwords
            }
        }else{
            error = 2;
            //System.out.println("Error. Can't Change your password, because you did never have this password!");
            //Error of old password
        }
    }


    public static String getFIRSTNAME() {
        return FIRSTNAME;
    }

    public static String getLASTNAME() {
        return LASTNAME;
    }

    public static String getLOGIN() {
        return LOGIN;
    }

    public static String getPASSWORDLAST() {
        return PASSWORDLAST;
    }

    public static boolean isGENDER() {
        return GENDER;
    }

    private static boolean isNotFound(String loginText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        ResultSet result = dbHandler.getUserForChange(user);
        int counter = 0;
        try {
            while (result.next()) {
                counter++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (counter >= 1) {
            error = 0;
            //System.out.println("Error. Can't Change Data Profile, because this login is registered!");
            return false;
        }
            return true;
    }

    public static void ChangeDataBase(String name, String sername, String login, Boolean gender, String password, String oldLogin){
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(oldLogin);
        ResultSet result = dbHandler.getUserForChange(user);
        try {
            while (result.next()) {
                String genderSTR;
                if (gender){
                    genderSTR = "Мужской";
                }else{
                    genderSTR = "Женский";
                }
                Statement statement = dbHandler.getDbConnection().createStatement();
                String command = "DELETE FROM " + Const.USER_TABLE + " WHERE userName = '" + oldLogin + "'";
                statement.executeUpdate(command);
                user = new User(name, sername, login, password, TYPEUSER, genderSTR);
                dbHandler.signUpUser(user);
                OLDLOGIN = login;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static void setUser(String name, String sername, String login, String gender, String password, String typeOfUser){
        FIRSTNAME = name;
        LASTNAME = sername;
        GENDER = gender.equals("Мужской");
        LOGIN = login;
        PASSWORDLAST = password;
        OLDLOGIN = LOGIN;
        TYPEUSER = typeOfUser;
    }
}
