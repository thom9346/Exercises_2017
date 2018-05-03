package DataLayer;

import LogicLayer.CustomerInformation;


import java.sql.*;

/**
 * Created by jakob on 16-05-2017.
 */
public class RemoveDataDB extends ConnectToDatabase{



    public void deleteData(String idNumber) throws SQLException {

        Connection conn = connect();
        Statement stmt = null;

        try {


            //3. Execute Query
            stmt = conn.createStatement();

            String mySQL = ("DELETE FROM faktura WHERE fakturaNr = '"+idNumber+"';");

            stmt.execute(mySQL);
            //4. Close everything up again
            stmt.close();
            conn.close();

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
