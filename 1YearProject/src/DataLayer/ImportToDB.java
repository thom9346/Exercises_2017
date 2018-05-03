package DataLayer;

import LogicLayer.CustomerInformation;
import javafx.beans.binding.When;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.format.CellTextFormatter;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.StringJoiner;

/**
 * Created by jakob on 26-05-2017.
 */
public class ImportToDB extends ConnectToDatabase {

    //Hardcoded for now due to time constraints, if anyone sees this then all you have to do is make it variable
    //I know its lazy but the deadline for delivering this assignement is soon

    File filetoRead = new File("C:/Users/Thomas/Desktop/importDatabase.xlt");
    DataFormatter df = new DataFormatter();
    public void importExcel () {
        try {
        FileInputStream excelfile = new FileInputStream(filetoRead);
        Workbook workbook = new HSSFWorkbook(excelfile);
        Sheet sheet = workbook.getSheetAt(0);
        Row row;
            for (int i = 0; i < sheet.getLastRowNum() ; i++) {
                row = (Row) sheet.getRow(i);

                String fakturaNr = df.formatCellValue(row.getCell(0));
                String date = df.formatCellValue(row.getCell(1));
                String kundeNr = df.formatCellValue(row.getCell(2));
                String iddebitor = df.formatCellValue(row.getCell(3));
                String name = df.formatCellValue(row.getCell(4));
                String adress = df.formatCellValue(row.getCell(5));
                String payment = df.formatCellValue(row.getCell(6));


                CustomerInformation cI = new CustomerInformation(fakturaNr, date, kundeNr, iddebitor, name, adress, payment);
                WriteToDatabase writeToDatabase = new WriteToDatabase();
                writeToDatabase.writeCustomer(cI);

            }



    } catch (FileNotFoundException e){
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (SQLException e) {
            System.out.println("This almost never happens " + e);
        }
    }

    //Sources for this function:
    //https://www.mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/
    //Used for initial testing purposes
    //https://stackoverflow.com/questions/30125465/cannot-get-a-text-value-from-a-numeric-cell-poi/30125586
    //Used for auctual structure and general guideline for how to import the data to a database, the answer made by Harshli and Manish were used in creation of this function
    //
    //The reason    why I have chosen to copy these creators
    //is due to the tight deadline and not having worked before with the Maven repository found here
    //https://mvnrepository.com/artifact/org.apache.poi/poi/3.9
}
