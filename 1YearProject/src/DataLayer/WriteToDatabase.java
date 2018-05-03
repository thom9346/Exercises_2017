package DataLayer;


import LogicLayer.CustomerInformation;

import java.sql.*;

/**
 * Created by Thomas on 06-05-2017.
 */


public class WriteToDatabase extends ConnectToDatabase {
            // 1. Function that takes the costumerinformation object and writes the values that is kept within the object into the database.
    public void writeCustomer(CustomerInformation customerInformation) throws SQLException {
        //Here we can see the usage of our connecttodatabase object in action, including the function.
        Connection conn = connect();
        Statement stmt = null;

        try {


            //3. Execute Query
            stmt = conn.createStatement();
            //We both use DML and DDL in certain queries to modify and get data, in this case we chose where the data needs to be sent in the database. We, after choosing
            //the table and columns, modify the data that is entered into the column via our costuemrinformation object


            //This checks wether or not the costumer number exists
            String checkifCustomerExsists = ("SELECT idCostumer FROM costumer WHERE idCostumer = '"+customerInformation.getrCustomerNumber()+"';");
            String checkifDebitorExists = ("SELECT iddebitor FROM debitor WHERE iddebitor = '"+customerInformation.getDebitor()+"';");

            ResultSet RS = conn.createStatement().executeQuery(checkifCustomerExsists);
            ResultSet RS2 = conn.createStatement().executeQuery(checkifDebitorExists);

//if debitor is the same as another column AND customer is
            if (RS.next() && RS2.next()) {

                System.out.println("You got into the first if");


                String sql2 = ("INSERT INTO faktura VALUES ('"+customerInformation.getInvoice_number()+"', '"+customerInformation.getPrice()+"', '"+customerInformation.getDate()+"'," +
                        "'"+RS.getString("idCostumer")+"')");


                stmt.execute(sql2);
                RS.close();
                RS2.close();
            } else if (RS.next()) {

                System.out.println("You got into the second if");

                String sql1 = ("INSERT INTO costumer (iddebitor) VALUE '"+customerInformation.getDebitor()+"'" +
                        "WHERE idCostumer ='"+RS.getString("idCostumer")+"'");
                String sql2 = ("INSERT INTO faktura (fakturaNr, total_beløb, faktura_dato, idCostumer) " +
                        "VALUES ('"+customerInformation.getInvoice_number()+"', '"+ customerInformation.getPrice()+"'," +
                        "'"+customerInformation.getDate()+"', '"+RS.getString("idCostumer")+"')");
                String sql3 = ("INSERT  INTO debitor (idebitor) VALUE '"+customerInformation.getDebitor()+"'");

                stmt.execute(sql1);
                stmt.execute(sql3);
                stmt.execute(sql2);

                RS.close();
            }else if (RS2.next()) {

                System.out.println("You got into the third if");

                String sql1 = ("INSERT INTO costumer (idCostumer, Customer_name, Costumer_adress, iddebitor)" +
                        "VALUES ('"+customerInformation.getrCustomerNumber()+"', '"+customerInformation.getName()+"', '"+customerInformation.getAddress()+"'" +
                        ", '"+RS2.getString("iddebitor")+"')");
                String sql2 = ("INSERT INTO faktura(fakturaNr, total_beløb, faktura_dato, idCostumer) VALUES " +
                        "('"+customerInformation.getInvoice_number()+"', '"+customerInformation.getPrice()+"', " +
                        "'"+customerInformation.getDate()+"', '"+customerInformation.getrCustomerNumber()+"')");

                stmt.execute(sql1);
                stmt.execute(sql2);

                RS2.close();

            } else  {

                System.out.println("Nothing happened");

                String sql1 = "INSERT INTO costumer (idCostumer, Customer_name, Costumer_adress, iddebitor) " +
                        "VALUES ('" + customerInformation.getrCustomerNumber() + "', '" + customerInformation.getName()
                        + "', '" + customerInformation.getAddress() + "', " +
                        "'" + customerInformation.getDebitor() + "');";
                String sql2 = "INSERT INTO faktura VALUES('" + customerInformation.getInvoice_number()
                        + "', '" + customerInformation.getPrice() + "', '" + customerInformation.getDate() + "', " +
                        "'" + customerInformation.getrCustomerNumber() + "');";
                String sql3 = "INSERT INTO debitor (iddebitor) VALUE ('" + customerInformation.getDebitor() + "');";
                stmt.execute(sql3);
                stmt.execute(sql1);
                stmt.execute(sql2);

            }

            stmt.close();
            conn.close();

         //Try, final and catch block to make sure that any exceptions are handled proberely on the user end
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try


    }
}
