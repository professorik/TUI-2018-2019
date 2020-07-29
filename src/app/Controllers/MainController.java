package app.Controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class MainController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    void initialize() {

    }

    public static void transitionBetweenScenes(MenuItem ExitButtonPers, MenuItem MenuButtonPers , MenuItem SaveBoxButtonPers , MenuItem StatisticButtonPers , MenuItem MyProfileButtonPers ,
                                               MenuItem InstructionButtonPers, MenuItem AboutButtonPers){
        Login log = new Login();
        ExitButtonPers.setOnAction(event -> {
            log.openNewScene("/app/UI/FXMLs/Login.fxml");
        });
        MenuButtonPers.setOnAction(event -> {
            log.openNewScene("/app/UI/FXMLs/AdminPers.fxml");
        });
        SaveBoxButtonPers.setOnAction(event -> {

        });
        StatisticButtonPers.setOnAction(event -> {

        });
        MyProfileButtonPers.setOnAction(event -> {
            log.openNewScene("/app/UI/FXMLs/Profile.fxml");
        });
        InstructionButtonPers.setOnAction(event -> {

        });
        AboutButtonPers.setOnAction(event -> {

        });
    }
}
