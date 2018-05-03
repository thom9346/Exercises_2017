package DataLayer;


import javax.naming.CommunicationException;
import java.sql.*;

/**
 * Created by Thomas on 06-05-2017.
 */
public class BackupDatabase {


    //Local connection
    private Connection localconn;
    //Local values for local database
    private static String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String LocalDB_URL = "jdbc:mysql://localhost:3006/costumerregistry";
    private static String Local_DB_USER = "FullAccess";
    private static String Local_DB_PASS = "test123";

    public void backUp () {

        //Creating connection to local hosted database first
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            localconn = DriverManager.getConnection(LocalDB_URL, Local_DB_USER, Local_DB_PASS);
            String MySQL = ("SELECT * FROM costumer");
            ResultSet rs_customer = localconn.createStatement().executeQuery(MySQL);
            String MySQL1 = ("SELECT * FROM faktura");
            ResultSet rs_faktura = localconn.createStatement().executeQuery(MySQL1);
            String MySQL2 = ("SELECT * FROM debitor");
            ResultSet rs_debitor = localconn.createStatement().executeQuery(MySQL2);

            //Creation of cloud connection
            Connection onlineconn;
            Class.forName(JDBC_DRIVER).newInstance();

            String cloud_DB_URL = "jdbc:mysql://yearproject.ceumid9pylis.eu-west-2.rds.amazonaws.com:3306/costumerregistry";
            String cloud_DB_USER = "root";
            String cloud_DB_PASS = "root1234";
            onlineconn = DriverManager.getConnection(cloud_DB_URL, cloud_DB_USER, cloud_DB_PASS);

            String create_table = null;
            ResultSet getTables = null;
            DatabaseMetaData dbm = onlineconn.getMetaData();
            String Sql = null;


                getTables = dbm.getTables(null, "customerregistry", "costumer", null);
                System.out.println("I got to here");
                if (getTables.next()) {
                    System.out.println("jumped after getTables.next");

                    //Dropping of tables, individually for now
                    Sql = ("DROP TABLE faktura");
                    onlineconn.createStatement().execute(Sql);
                    System.out.println("I'm dropping table faktura");
                    Sql = ("DROP TABLE costumer");
                    onlineconn.createStatement().execute(Sql);
                    Sql = ("DROP TABLE debitor");
                    onlineconn.createStatement().execute(Sql);
                }

                System.out.println("I made it past");

                //Creation of table
                create_table = "CREATE TABLE costumerregistry.debitor(iddebitor VARCHAR (40) NOT NULL, PRIMARY KEY (iddebitor));";
                onlineconn.createStatement().execute(create_table);

                create_table = "CREATE TABLE costumerregistry.costumer(idCostumer VARCHAR (40) NOT NULL, Customer_name VARCHAR (40) NOT NULL, " +
                    "Costumer_adress VARCHAR (40) NOT NULL, iddebitor VARCHAR (40) NOT NULL, PRIMARY KEY (idCostumer), FOREIGN KEY (iddebitor) " +
                        "REFERENCES debitor(iddebitor));";
                onlineconn.createStatement().execute(create_table);

                create_table = "CREATE TABLE costumerregistry.faktura(fakturaNr VARCHAR (40) NOT NULL, total_beløb VARCHAR (40) NOT NULL, " +
                        "faktura_dato VARCHAR (40) NOT NULL, idCostumer VARCHAR (40) NOT NULL, PRIMARY KEY (fakturaNr), FOREIGN KEY (idCostumer) " +
                        "REFERENCES costumer(idCostumer));";
                onlineconn.createStatement().execute(create_table);
                System.out.println(create_table);

                    while (rs_debitor.next() && rs_customer.next() && rs_faktura.next()) {
                        Sql = ("INSERT INTO debitor VALUE ('" + rs_debitor.getString("iddebitor") + "');");
                        onlineconn.createStatement().execute(Sql);

                        //SQL insertion into costumer
                        Sql = ("INSERT INTO costumer VALUES ('" + rs_customer.getString("idCostumer") + "', '" + rs_customer.getString("Customer_name") + "'," +
                                "'" + rs_customer.getString("Costumer_adress") + "', '" + rs_customer.getString("iddebitor") + "');");
                        onlineconn.createStatement().execute(Sql);

                        //Insertion of values into faktura. THIS IS WHERE IT GOES WRONG
                        Sql = ("INSERT INTO faktura VALUES ('" + rs_faktura.getString("fakturaNr") + "', '" + rs_faktura.getString("total_beløb") + "', " +
                                "'" + rs_faktura.getString("faktura_dato") + "', '" + rs_faktura.getString("idCostumer") + "');");
                        onlineconn.createStatement().execute(Sql);
                        System.out.println("SQL 3");
                        System.out.println("I'm inserting data");

                    }


            System.out.println("I'm done");


            onlineconn.close();
            rs_customer.close();
            rs_debitor.close();
            rs_faktura.close();
            getTables.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Something went wrong");
            System.out.println(e);

        } catch (SQLException e) {
            System.out.println(e);
        } catch (IllegalAccessException e) {
            System.out.println(e + "AHHHHH");
        } catch (InstantiationException e) {
            System.out.println(e);
        }


    }




}
