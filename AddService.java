import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddService {
    Patient patient;
    @FXML
    TextField patientIdField;
    @FXML
    TextField nameField;
    @FXML
    TextField serviceField;
    @FXML
    TextField billField;
    @FXML
    Text updateText;


    public void onSubmitButton() throws SQLException {
        String patientId = patientIdField.getText();
        String serviceName = serviceField.getText();
        String bill = billField.getText();
        ArrayList<String> values = new ArrayList<>(Arrays.asList(patientId, serviceName, bill));
        if (!isAnyEmpty(values)) {
            int no_of_rows_affected = patient.addService(patientId, serviceName, bill);
            if (no_of_rows_affected == 0) {
                updateText.setText("Error");
            } else {
                updateText.setText("Service Added Successfully");
            }
        } else {
            updateText.setText("Please fill all the fields");
        }
    }

    public void onPatientSearchClicked() throws SQLException {
        String patientId = patientIdField.getText();
        ArrayList<String> requirements = new ArrayList<>(Arrays.asList("Name"));
        ArrayList<String> output = patient.getRequiredRecordFromId(patientId, requirements);
        nameField.setText(output.get(0));
    }

    public boolean isAnyEmpty(ArrayList<String> values) {
        for (String value :
                values) {
            if (value.equals("")) {
                return true;
            }
        }
        return false;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
