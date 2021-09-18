import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDoctor {
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
    Text updateText;
    @FXML
    TextField qualificationField;
    @FXML
    DatePicker datePicker;

    Doctor doctor;

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
        String qualification = qualificationField.getText();
        String gender = genderComboBox.getValue();
        String age = ageField.getText();
        LocalDate date = datePicker.getValue();
        boolean isAnyEmpty;

        ArrayList<String> values = new ArrayList<>(Arrays.asList(name, email, contact_no, gender, age, qualification));
        isAnyEmpty = isEmpty(values);


        if (!isAnyEmpty & date != null) {
            rows_affected = doctor.addDoctor(name, email, contact_no, qualification, gender, age, date.toString());
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

    private boolean isEmpty(ArrayList<String> values) {
        for (String value :
                values) {
            if (value.equals("")) {
                return true;
            }
        }
        return false;
    }


    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
