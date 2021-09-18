import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class DischargePatient {
    @FXML
    AnchorPane ancPane;
    @FXML
    TextField tf1;
    @FXML
    GridPane gridPane;
    @FXML
    Text updateText;
    @FXML
    Text nameText;
    @FXML
    DatePicker datePicker;

    Patient patient;
    ArrayList<Pane> panes = new ArrayList<>();

    public void searchRecord() throws SQLException {
        String patient_id = tf1.getText();
        if (patient_id.equals("")) return;
        String name = patient.getRequiredRecordFromId(patient_id, new ArrayList<>(Arrays.asList("Name"))).get(0);
        nameText.setText("Patient Name: " + name);
        ArrayList<ArrayList<String>> results = patient.dischargePatientBill(patient_id, new ArrayList<>(Arrays.asList("`Service Name`", "Bill")));
        resetGridpane();
        setGridPane(results);
    }

    public void setGridPane(ArrayList<ArrayList<String>> results) {
        int i, j, amount = 0;
        for (i = 0; i < results.size(); i++) {
            ArrayList<String> eachArray = results.get(i);
            gridPane.addRow(i + 1);

            for (j = 0; j < eachArray.size(); j++) {
                setAddPane(eachArray.get(j), i + 1, j);
                if (j == 1) amount += Integer.parseInt(eachArray.get(j));//To Sum up the amount
            }
        }
        gridPane.addRow(i + 1);
        setAddPane("Total Amount:", i + 1, 0).setFill(Color.RED);
        setAddPane(Integer.toString(amount), i + 1, 1).setFill(Color.RED);
    }

    public Text setAddPane(String text, int row, int col) {
        Pane record = new Pane();
        Text value = new Text(text);
        value.setFont(Font.font("verdana", FontWeight.MEDIUM, 18));
        value.setLayoutY(20);
        record.getChildren().add(value);
        gridPane.add(record, col, row);
        panes.add(record);
        return value;
    }

    public void onSubmitClicked() throws SQLException {
        String patient_id = tf1.getText();
        LocalDate date = datePicker.getValue();
        if (patient_id.equals("") | date == null) return;
        int no_of_rows_affected = patient.dischargePatient(patient_id, date.toString());
        patient.deleteBill(patient_id);
        if (no_of_rows_affected == 0) {
            updateText.setText("Patient Already Discharged");
        } else {
            updateText.setText("Patient Discharged Successfully");
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
