package app.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import app.Profile.CurrentUserData;

public class ProfileInterfaceController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LastName;

    @FXML
    private TextField Login;

    @FXML
    private CheckBox Male;

    @FXML
    private CheckBox Female;

    @FXML
    private Button ChangeAll;

    @FXML
    private Button ChangePassword;

    @FXML
    private PasswordField OldPassword;

    @FXML
    private TextField FirstName;

    @FXML
    private PasswordField NewPassword;

    @FXML
    private PasswordField ReturnNewPassword;

    @FXML
    private Label PasswordError;

    @FXML
    private Label LoginError;

    @FXML
    void initialize() {
        FirstName.setText(CurrentUserData.getFIRSTNAME());
        LastName.setText(CurrentUserData.getLASTNAME());
        Login.setText(CurrentUserData.getLOGIN());
        Male.setSelected(CurrentUserData.isGENDER());
        Female.setSelected(!CurrentUserData.isGENDER());
        LoginError.setVisible(false);
        PasswordError.setVisible(false);

        Female.setOnAction(event -> {
            Male.setSelected(false);
        });

        Male.setOnAction(event -> {
            Female.setSelected(false);
        });


        ChangeAll.setOnAction(event -> {
            CurrentUserData.setUserData(FirstName.getText(), LastName.getText() , Login.getText() , Male.isSelected());
            if (CurrentUserData.error == 0){
                LoginError.setText("Such login already exists!");
                LoginError.setVisible(true);
            }
        });
        ChangePassword.setOnAction(event -> {
            CurrentUserData.setUserPassword(FirstName.getText(), LastName.getText() , Login.getText() , Male.isSelected(), OldPassword.getText() , NewPassword.getText() , ReturnNewPassword.getText());
            if (CurrentUserData.error == 1){
                PasswordError.setText("Can't Change your password, because your passwords are not equal!");
                PasswordError.setVisible(true);
            }else if(CurrentUserData.error == 2){
                PasswordError.setText("Can't Change your password, because you did never have this password!");
                PasswordError.setVisible(true);
            }
        });

    }
}
