package app.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import app.Profile.CurrentUserData;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import app.animations.Shake;

import app.dbClasses.DatabaseHandler;
import app.dbClasses.User;

public class Login {

    //FXMLs
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField login_field;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button loginButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button signInButton;

    @FXML
    private Label LoginAndPasswordErrorText;

    @FXML
    void initialize () {
        LoginAndPasswordErrorText.setVisible(false);

        loginButton.setOnAction(event -> {
                String loginText = login_field.getText().trim();
                String loginPassword = password_field.getText().trim();

                if (!loginText.equals("") && !loginPassword.equals("")){
                    try {
                        loginUser(loginText, loginPassword);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }else{
                    playError(0);
                }
        });

        signInButton.setOnAction(event -> {
           openNewScene("/app/UI/FXMLs/SignUp.fxml");
        });

    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int counter = 0;
        try {
            while (result.next()) {
                counter++;
                CurrentUserData.setUser(result.getString("name"), result.getString("sername") , result.getString("userName"),
                        result.getString("gender") , result.getString("password") , result.getString("typeOfUser"));
                switch (result.getString("typeOfUser")){
                    case "Администратор":
                        CurrentUserData.type = 0;
                        break;
                    case "Клиент":
                        CurrentUserData.type = 1;
                        break;
                    case "Водитель":
                        CurrentUserData.type = 2;
                        break;
                    case "Повар":
                        CurrentUserData.type = 3;
                        break;
                }
                openNewScene("/app/UI/FXMLs/AdminPers.fxml");
               // openNewScene("/app/UI/FXMLs/UserInterface1.fxml");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        if (counter < 1){
            user = new User();
            user.setUserName(loginText);
            result = dbHandler.getUserForChange(user);
            if (result.next()){
                playError(2);
            }else {
                playError(1);
            }
        }
    }

    public void openNewScene(String window){
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

    private void playError(int a){
        switch (a){
            case 0:
                LoginAndPasswordErrorText.setText("Login or password is empty!");
                break;
            case 1:
                LoginAndPasswordErrorText.setText("Wrong login!");
                break;
            case 2:
                LoginAndPasswordErrorText.setText("Wrong password!");
                break;
        }
        LoginAndPasswordErrorText.setVisible(true);
        login_field.setStyle("-fx-border-color:#ff0000");
        password_field.setStyle("-fx-border-color:#ff0000");
        Shake userLoginAnim = new Shake(login_field);
        Shake userPasswordAnim = new Shake(password_field);
        userLoginAnim.playAnim();
        userPasswordAnim.playAnim();
    }
}
