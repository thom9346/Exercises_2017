package LogicLayer;

import javafx.fxml.FXMLLoader;
import javafx.fxml.LoadException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by Thomas on 11-05-2017.
 */
public class OpenNewWindow {


     void newWindow(String windowName, String windowTitle)
    {

        try {

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/UILayer/" + windowName));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();

            stage.setTitle(windowTitle);
            stage.setScene(new Scene(root1));
            stage.show();


        }catch (IOException e) {
            System.out.println("Cant load window"  + e);
        }
    }
}
