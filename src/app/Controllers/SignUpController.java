package app.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import app.animations.Shake;

import app.dbClasses.DatabaseHandler;
import app.dbClasses.User;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    //Error Texts
    @FXML
    private Label textAboutLoginError;

    @FXML
    private Label textAboutPasswordError;

    @FXML
    private Label textAboutAbsoluteError;

    //Fields
    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordRepeatField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    //Buttons
    @FXML
    private Button signInButton;

    @FXML
    private Button cancelButton;

    //Dropdowns
    @FXML
    private MenuButton typeOfUser;

    @FXML
    private MenuItem MenuItemClient;

    @FXML
    private MenuItem MenuItemCook;

    @FXML
    private MenuItem MenuItemAdministrator;

    @FXML
    private MenuItem MenuItemDriver;

    @FXML
    private MenuButton typeOfGender;

    @FXML
    private MenuItem MenuItemMale;

    @FXML
    private MenuItem MenuItemFemale;

    @FXML
    private MenuItem MenuItemOther;

    private boolean[] userType = {true, false, false, false};
    private boolean[] genderType = {false, false, false};

    @FXML
    void initialize() {
        //Setting Error Messages Not To Be Visible
        textAboutAbsoluteError.setVisible(false);
        textAboutPasswordError.setVisible(false);
        textAboutLoginError.setVisible(false);

        //This Is For Role Selection
        MenuItemClient.setOnAction(event -> {
            typeOfUser.setText(MenuItemClient.getText());

            userType[0] = true;
            userType[1] = false;
            userType[2] = false;
            userType[3] = false;
        });
        MenuItemCook.setOnAction(event -> {
            typeOfUser.setText(MenuItemCook.getText());

            userType[0] = false;
            userType[1] = true;
            userType[2] = false;
            userType[3] = false;
        });
        MenuItemAdministrator.setOnAction(event -> {
            typeOfUser.setText(MenuItemAdministrator.getText());

            userType[0] = false;
            userType[1] = false;
            userType[2] = true;
            userType[3] = false;
        });
        MenuItemDriver.setOnAction(event -> {
            typeOfUser.setText(MenuItemDriver.getText());

            userType[0] = false;
            userType[1] = false;
            userType[2] = false;
            userType[3] = true;
        });

        //This One Is For Gender Selection
        MenuItemMale.setOnAction(event -> {
            typeOfGender.setText(MenuItemMale.getText());

            genderType[0] = true;
            genderType[1] = false;
            genderType[2] = false;
        });

        MenuItemFemale.setOnAction(event -> {
            typeOfGender.setText(MenuItemFemale.getText());

            genderType[0] = false;
            genderType[1] = true;
            genderType[2] = false;
        });

        MenuItemOther.setOnAction(event -> {
            typeOfGender.setText(MenuItemOther.getText());

            genderType[0] = false;
            genderType[1] = false;
            genderType[2] = true;
        });

        //Creating New User In DataBase
        signInButton.setOnAction(event -> {
            signUpNewUser();
        });

        cancelButton.setOnAction(event -> {
            openNewScene("/app/UI/FXMLs/Login.fxml");
        });
    }

    private void signUpNewUser() {
        DatabaseHandler dbHandler = new DatabaseHandler();

        String name = firstNameField.getText();
        String surname = lastNameField.getText();

        String userName = loginField.getText();
        String password = passwordField.getText();
        String typeOfUser = null;

        //Setting Up Type Of Account
        if (userType[0]) typeOfUser = MenuItemClient.getText();
        else if (userType[1]) typeOfUser = MenuItemCook.getText();
        else if (userType[2]) typeOfUser = MenuItemAdministrator.getText();
        else if (userType[3]) typeOfUser = MenuItemDriver.getText();

        //Setting Up User's Gender
        String gender = null;
        if (genderType[0]) gender = "Male";
        else if (genderType[1]) gender = "Female";
        else if (genderType[2]) gender = "Other";

        if (passwordRepeatField.getText().equals(passwordField.getText()) && isLoginDoesNotExist(userName)
                && !(firstNameField.getText().equals("") || lastNameField.getText().equals("")
                || loginField.getText().equals("") || passwordField.getText().equals(""))) {
            User user = new User(name, surname, userName, password, typeOfUser, gender);
            dbHandler.signUpUser(user);
            openNewScene("/app/UI/FXMLs/UserInterface1.fxml");
        } else if (firstNameField.getText().equals("") || lastNameField.getText().equals("")
                || loginField.getText().equals("") || passwordField.getText().equals("")){

            playErrorSignUp(true,true,true,true,true);
            textAboutAbsoluteError.setVisible(true);
        } else if (isLoginDoesNotExist(userName)){
            //Password Error Text Is Going To Be Visible NOW
            textAboutAbsoluteError.setVisible(false);
            textAboutPasswordError.setVisible(true);
            textAboutLoginError.setVisible(false);
            playErrorSignUp(false,false,false,true,true);
        } else {
            //Login Error Text Is Going to Become Visible Now
            textAboutAbsoluteError.setVisible(false);
            textAboutLoginError.setVisible(true);
            textAboutPasswordError.setVisible(false);
            playErrorSignUp(false,false,true,false,false);
        }
    }

    private void openNewScene(String window) {
        signInButton.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }

    private boolean isLoginDoesNotExist(String loginText) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        ResultSet result = dbHandler.getUserForSignUp(user);

        int counter = 0;
        try {
            while (result.next()) {
                counter++;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (counter == 0) return true;
        else return false;
    }

    private void playErrorSignUp(boolean name,boolean surname,boolean login,boolean password,boolean password2){
        if (name){
            firstNameField.setStyle("-fx-border-color:#ff0000");
            Shake userLoginAnim = new Shake(firstNameField);
            userLoginAnim.playAnim();
        } else {
            firstNameField.setStyle("-fx-border-color:#c0c0c0");
            firstNameField.setStyle("-fx-border-radius:5");
        }
        if (surname){
            lastNameField.setStyle("-fx-border-color:#ff0000");
            Shake userLoginAnim = new Shake(lastNameField);
            userLoginAnim.playAnim();
        }else{
            lastNameField.setStyle("-fx-border-color:#c0c0c0");
            lastNameField.setStyle("-fx-border-radius:5");
        }
        if (login){
            loginField.setStyle("-fx-border-color:#ff0000");
            Shake userLoginAnim = new Shake(loginField);
            userLoginAnim.playAnim();
        }else{
            loginField.setStyle("-fx-border-color:#c0c0c0");
            loginField.setStyle("-fx-border-radius:5");
        }
        if (password){
            passwordField.setStyle("-fx-border-color:#ff0000");
            Shake userLoginAnim = new Shake(passwordField);
            userLoginAnim.playAnim();
        }else{
            passwordField.setStyle("-fx-border-color:#c0c0c0");
            passwordField.setStyle("-fx-border-radius:5");
        }
        if (password2){
            passwordRepeatField.setStyle("-fx-border-color:#ff0000");
            Shake userLoginAnim = new Shake(passwordRepeatField);
            userLoginAnim.playAnim();
        }else{
            passwordRepeatField.setStyle("-fx-border-color:#c0c0c0");
            passwordRepeatField.setStyle("-fx-border-radius:5");
        }
    }
}