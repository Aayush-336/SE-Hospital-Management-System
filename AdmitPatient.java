import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class AdmitPatient {

    @FXML
    TextField patientIdField;
    @FXML
    TextField nameField;
    @FXML
    TextField diseaseField;
    @FXML
    DatePicker admitDatePicker;
    @FXML
    TextField docIdField;
    @FXML
    TextField docNameField;
    @FXML
    Text updateText;

    Patient patient;

    public void onSubmitButton() throws SQLException {
        String patientId = patientIdField.getText();
        String disease = diseaseField.getText();
        LocalDate admitDate = admitDatePicker.getValue();
        String docId = docIdField.getText();
        ArrayList<String> values = new ArrayList<>(Arrays.asList(patientId, disease, admitDate.toString(), docId));
        if (!isAnyEmpty(values)) {
            int no_of_rows_affected = patient.admitPatient(patientId, disease, admitDate.toString(), docId);
            if (no_of_rows_affected == 0) {
                updateText.setText("Error");
            } else {
                updateText.setText("Patient Admitted Successfully");
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

    public void onDocSearchClicked() throws SQLException {
        String docId = docIdField.getText();
        Doctor doctor = new Doctor();
        ArrayList<String> result = doctor.getRequiredRecordFromId(docId, new ArrayList<>(Arrays.asList("Name")));
        docNameField.setText(result.get(0));
    }


    public void setPatient(Patient patient) {
        this.patient = patient;
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

}
