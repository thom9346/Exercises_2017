package LogicLayer;

import DataLayer.ReadFromDatabase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.ResourceBundle;

/**
 * Created by Thomas on 19-05-2017.
 */
public class AdvancedSearchController extends ReadFromDatabase implements Initializable {
    @FXML
    private DatePicker date1Input;
    @FXML
    private DatePicker date2Input;

    @FXML
    private TableColumn<CustomerInformation, String> invoiceNumber;
    @FXML
    private TableColumn<CustomerInformation, String> date;
    @FXML
    private TableColumn<CustomerInformation, String> customer;
    @FXML
    private TableColumn<CustomerInformation, String> debitor;
    @FXML
    private TableColumn<CustomerInformation, String> name;
    @FXML
    private TableColumn<CustomerInformation, String> address;
    @FXML
    private TableColumn<CustomerInformation, String> price;
    @FXML
    private Label priceLabel;
    @FXML
    private TableColumn<CustomerInformation, String> colorColumn;

    @FXML
    private
    TableView<CustomerInformation> tableView;

    private PropertyValues propertyValues = new PropertyValues();

    private final ObservableList<CustomerInformation> data = FXCollections.observableArrayList();

    public void searchBetweenDates() {

        tableView.getItems().clear();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");



        String date1 = date1Input.getValue().format(dateTimeFormatter);
        String date2 = date2Input.getValue().format(dateTimeFormatter);


        findDateBetweenTwoDates(date1, date2, data, tableView);


            //refreshing makes it so the colours dont stick to empty fields.
        tableView.refresh();
        double totalPrice = 0;

        for (int i = 0; i < data.size(); i++) {
            totalPrice = totalPrice + Double.parseDouble(data.get(i).getPrice());
        }

        priceLabel.setText("Omsætning for valgt periode: " + totalPrice + " kr");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataFromDatabase();
        tableView.setItems(data);
        propertyValues.values(invoiceNumber, date, customer, debitor, name, address, price,colorColumn,data,tableView);

    }
        private void dataFromDatabase () {

        importData(data, tableView);

        }


    }

