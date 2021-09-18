import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class AddPatient {
    @FXML
    TextField nameField;
    @FXML
    TextField emailField;
    @FXML
    TextField contactField;
    @FXML
    ComboBox<String> genderComboBox;
    @FXML
    TextField ageField;
    @FXML
    TextField bloodGroupField;
    @FXML
    Text updateText;


    Patient patient;

    public void initialize() {
        genderComboBox.getItems().removeAll(genderComboBox.getItems());
        genderComboBox.getItems().add("M");
        genderComboBox.getItems().add("F");
    }

    public void onSubmitClicked() throws SQLException {
        updateText.setText("");
        int rows_affected = 0;
        String name = nameField.getText();
        String email = emailField.getText();
        String contact_no = contactField.getText();
        String gender = genderComboBox.getValue();
        String age = ageField.getText();
        String bloodGroup = bloodGroupField.getText();
        boolean isAnyEmpty = false;

        ArrayList<String> values = new ArrayList<>();
        values.addAll(Arrays.asList(name, email, contact_no, gender, age, bloodGroup));
        for (String value :
                values) {
            if (isEmpty(value)) {
                isAnyEmpty = true;
                break;
            }
        }


        if (!isAnyEmpty) {
            rows_affected = patient.addPatient(name, email, contact_no, gender, age, bloodGroup);
        } else {
            updateText.setText("Please fill all the fields");
        }
        System.out.println(rows_affected);
        if (rows_affected > 0) {
            updateText.setText("Record Successfully Added");
        } else {
            updateText.setText("Error");
        }
    }

    private boolean isEmpty(String s) {
        return s.equals("");
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
