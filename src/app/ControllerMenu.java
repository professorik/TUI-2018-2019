package app;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import java.net.URL;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import app.dbClasses.DatabaseHandler;
import app.dbClasses.Dish;
import app.dbClasses.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ControllerMenu {


    private ObservableList<Dish> usersData = FXCollections.observableArrayList();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Dish> TableView;

    @FXML
    private TableColumn<Dish, String> NameColumn;

    @FXML
    private TableColumn<Dish, Blob> PhotoColumn;

    @FXML
    private ImageView ImageMenu;

    @FXML
    private TextArea Text;

    @FXML
    private Button Button_Buy;

    @FXML
    private TextField PriceField;

    @FXML
    void initialize() throws SQLException {
        Text.setEditable(false);
        PriceField.setEditable(false);
        DatabaseHandler dbHandler = new DatabaseHandler();
        ResultSet resultSet = dbHandler.getDish();
        while (resultSet.next()){
            System.out.println("find!");
            Text.setText(resultSet.getString("Description"));
            PriceField.setText(String.valueOf(resultSet.getInt("DishPrice")));
          //  Image.setImage((javafx.scene.image.Image) resultSet.getBlob("Photo"));
            System.out.println(resultSet.getString("DishName"));
            usersData.add(new Dish(resultSet.getString("DishName") , resultSet.getBlob("Photo")));
        }
        NameColumn.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));
        PhotoColumn.setCellValueFactory(new PropertyValueFactory<Dish, Blob>("Photo"));
        TableView.setItems(usersData);
    }
}
