import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchPatient {
    boolean headerSet = false;
    @FXML
    AnchorPane ancPane;
    @FXML
    TextField tf1;
    @FXML
    Text text;
    @FXML
    GridPane gridPane;
    Patient patient;
    ArrayList<ArrayList<String>> results = new ArrayList<>();
    ArrayList<Pane> panes = new ArrayList<>();


    public void setHeaders() {
        ArrayList<String> headers = patient.getHeaders();
        for (int i = 0; i < headers.size(); i++) {
            gridPane.addColumn(i);
            Pane record = new Pane();
            Text value = new Text(headers.get(i));
            value.setFont(Font.font("Calibri", FontWeight.BOLD, 20));
            value.setFill(Color.RED);
            value.setLayoutY(20);
            record.getChildren().add(value);
            gridPane.add(record, i, 0);
        }
    }

    public void search() throws SQLException {
        String givenName = tf1.getText();
        if (!headerSet) {
            setHeaders();
            headerSet = true;
        }
        results.clear();

        results = patient.searchRecord(givenName, "Name", new ArrayList<>(Arrays.asList("`Patient Id`", "name", "Email", "`Contact No`", "Gender", "Age", "`Blood Group`")));
        resetGridpane();
        for (int i = 0; i < results.size(); i++) {
            ArrayList<String> eachArray = results.get(i);
            gridPane.addRow(i + 1);

            for (int j = 0; j < eachArray.size(); j++) {
                Pane record = new Pane();
                Text value = new Text(eachArray.get(j));
                value.setFont(Font.font("Calibri", FontWeight.MEDIUM, 18));
                value.setLayoutY(20);
                record.getChildren().add(value);
                gridPane.add(record, j, i + 1);
                panes.add(record);
            }
        }
    }

    public void resetGridpane() {
        for (Pane pane :
                panes) {
            gridPane.getChildren().remove(pane);
        }
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
