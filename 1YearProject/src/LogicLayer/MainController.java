package LogicLayer;

import DataLayer.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;


/**
 * Created by Thomas on 07-05-2017.
 */
public class MainController extends ReadFromDatabase implements Initializable{

    @FXML
    private TextField invoiceInput, dateInput, customerNumberInput, debitorInput, nameInput, addressInput, priceInput;

    @FXML
    private
    TableView<CustomerInformation> tableView;

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
    private TextField searchField;

    @FXML
    private TableColumn<CustomerInformation, String> colorColumn;


    private PropertyValues propertyValues = new PropertyValues();
    private OpenNewWindow openNewWindow = new OpenNewWindow();




    //ObservableList: A list that allows listeners to track changes when they occur
    //Source: https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html

     final ObservableList<CustomerInformation> data = FXCollections.observableArrayList();
    //method implemented from the initializble interface. This is what happens on start up of the window.

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        dataFromDatabase();
       tableView.setItems(data);

        propertyValues.values(invoiceNumber, date, customer, debitor, name, address, price,colorColumn,data,tableView);
        tableView.setEditable(true);




    }

    public void importFromExcel () {

        ImportToDB importToDB = new ImportToDB();
        importToDB.importExcel();

    }

    //method called on click of the save button
    public void saveData(){

        CustomerInformation entry = new CustomerInformation(invoiceInput.getText(), dateInput.getText(), customerNumberInput.getText(),
                debitorInput.getText(), nameInput.getText(), addressInput.getText(), priceInput.getText());

        //insert data in table
        data.add(entry);
        clearTextFields();
        //Insert data to database
        WriteToDatabase write = new WriteToDatabase();
        try {
            write.writeCustomer(entry);
        }
        catch (SQLException e){
            System.out.println(e + "Sql broke");
        }

    }
    public void deleteData() throws InvocationTargetException {
        RemoveDataDB removeDataDB = new RemoveDataDB();
        ObservableList<CustomerInformation> customerSelected, allCustomers;
        allCustomers = tableView.getItems();
        customerSelected = tableView.getSelectionModel().getSelectedItems();



        int indexofinvoice = tableView.getSelectionModel().getSelectedIndex();

        try {
            removeDataDB.deleteData(customerSelected.get(indexofinvoice).getInvoice_number());
        } catch (SQLException e) {
            System.out.println(e + "Sql broke");
        }
        customerSelected.forEach(allCustomers::remove);
        tableView.refresh();
    }

    private void clearTextFields(){

        invoiceInput.clear();
        dateInput.clear();
        customerNumberInput.clear();
        debitorInput.clear();
        nameInput.clear();
        addressInput.clear();
        priceInput.clear();
    }

    private void dataFromDatabase(){

      importData(data, tableView);


    }


    public void searchForData(){

        //This wraps the ObservableList(data) in a FilteredList.
        FilteredList<CustomerInformation> filteredData = new FilteredList<>(data, e -> true);

        // Set the filter Predicate whenever the filter changes.
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(customerInfo -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            String lowerCaseFilter = newValue.toLowerCase();

            if (customerInfo.getInvoice_number().toLowerCase().contains(lowerCaseFilter)) {
                return true;

            }
            else if (customerInfo.getrCustomerNumber().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            }

            return false; // Does not match.

        }));

        // Wrap the FilteredList in a SortedList.
        SortedList<CustomerInformation> sortedData = new SortedList<>(filteredData);

        //  Bind the SortedList comparator to the TableView comparator.
       // propertyValues.setColors(name);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());

        // Add sorted (and filtered) data to the table.
        tableView.setItems(sortedData);
        tableView.refresh();
    }
    public void openSearchWindow(){
        openNewWindow.newWindow("AdvancedSearching.fxml", "Searching in intervals");
    }


    public void backupToDB () {
        BackupDatabase backup = new BackupDatabase();

        backup.backUp();
    }


}


