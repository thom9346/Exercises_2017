package DataLayer;

import LogicLayer.CustomerInformation;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thomas on 06-05-2017.
 */
public class ReadFromDatabase extends ConnectToDatabase {

    public void selectAllData(ObservableList<CustomerInformation> dataList) {

        try {

            Connection conn = connect();

            String mySQL = ("SELECT F.fakturaNr, F.faktura_dato, C.idCostumer, D.iddebitor, C.Customer_name, C.Costumer_adress, F.total_beløb " +
                    "FROM costumer C, faktura F, debitor D WHERE C.idCostumer = F.idCostumer AND C.iddebitor = D.iddebitor");
            System.out.println(mySQL);
            ResultSet rs = conn.createStatement().executeQuery(mySQL);
            while (rs.next()){
                dataList.add(new CustomerInformation(rs.getString("fakturaNr"),rs.getString("faktura_dato"),
                        rs.getString("idCostumer"), rs.getString("iddebitor"), rs.getString("Customer_name"),
                        rs.getString("Costumer_adress"), rs.getString("total_beløb")));
            }
         rs.close();

        } catch (SQLException e){
            System.out.println(e + "SQL Broke");
        }


    } //end of selectAllData

    public void importData(ObservableList<CustomerInformation> data, TableView<CustomerInformation> tableView){

        selectAllData(data);

        tableView.setItems(data);


    }
    public void findDateBetweenTwoDates(String date1, String date2, ObservableList<CustomerInformation> data, TableView<CustomerInformation> tableView){
        try {


            Connection conn = connect();
            ResultSet rs = conn.createStatement().executeQuery("SELECT F.fakturaNr, F.faktura_dato, C.idCostumer, D.iddebitor, C.Customer_name, C.Costumer_adress, F.total_beløb " +
                    "FROM costumer C, faktura F, debitor D WHERE C.idCostumer = F.idCostumer AND C.iddebitor = D.iddebitor AND faktura_dato BETWEEN CAST('"+date1+"' AS DATE) AND CAST('"+date2+"' AS DATE)");



            while(rs.next()) {

                    data.add(new CustomerInformation(rs.getString("fakturaNr"), rs.getString("faktura_dato"),
                            rs.getString("idCostumer"), rs.getString("iddebitor"), rs.getString("Customer_name"),
                            rs.getString("Costumer_adress"), rs.getString("total_beløb")));

            }

            rs.close();
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.err.println("Error: " + ex);
        }


    }


}

