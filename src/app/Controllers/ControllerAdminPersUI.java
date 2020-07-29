package app.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import app.dbClasses.DatabaseHandler;
import app.dbClasses.User;
import javafx.stage.Stage;

public class ControllerAdminPersUI {

    private ObservableList<User> usersData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button button;

    @FXML
    private MenuItem ExitButtonPers;

    @FXML
    private MenuItem MenuButtonPers;

    @FXML
    private MenuItem SaveBoxButtonPers;

    @FXML
    private MenuItem StatisticButtonPers;

    @FXML
    private MenuItem MyProfileButtonPers;

    @FXML
    private MenuItem InstructionButtonPers;

    @FXML
    private MenuItem AboutButtonPers;

    @FXML
    private MenuItem PersonalMenu;

    @FXML
    private TableView<User> MainTablePers;

    @FXML
    private TableColumn<User, String> TypeTable;

    @FXML
    private TableColumn<User, String> NameTable;

    @FXML
    private TableColumn<User, String> SernameTable;

    @FXML
    private TableColumn<User, String> LoginTable;

    @FXML
    private TableColumn<User, String> GenderTable;

    @FXML
    private TableColumn<User, String> PasswordTable;

    @FXML
    void initialize() throws SQLException {
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet = dbHandler.getAllPersonalUsers();
            while (resultSet.next()){
                usersData.add(new User(resultSet.getString("name"), resultSet.getString("sername"), resultSet.getString("userName")
                        , resultSet.getString("password") , resultSet.getString("typeOfUser") , resultSet.getString("gender")));
            }
            TypeTable.setCellValueFactory(new PropertyValueFactory<User, String>("typeOfUser"));
            NameTable.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
            SernameTable.setCellValueFactory(new PropertyValueFactory<User, String>("sername"));
            LoginTable.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
            GenderTable.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
            PasswordTable.setCellValueFactory(new PropertyValueFactory<User, String>("password"));
            MainTablePers.setItems(usersData);
            //Menu
        ExitButtonPers.setOnAction(event -> {
            openScene("/app/UI/FXMLs/Login.fxml");
        });
        MenuButtonPers.setOnAction(event -> {

        });
        SaveBoxButtonPers.setOnAction(event -> {

        });
        StatisticButtonPers.setOnAction(event -> {

        });
        MyProfileButtonPers.setOnAction(event -> {
            openScene("/app/UI/FXMLs/Profile.fxml");
        });
        InstructionButtonPers.setOnAction(event -> {

        });
        AboutButtonPers.setOnAction(event -> {

        });
        PersonalMenu.setOnAction(event -> {
            openScene("/app/UI/FXMLs/AdminPers.fxml");

        });
    }

   public void openScene(String window){
        button.getScene().getWindow().hide();
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
}
