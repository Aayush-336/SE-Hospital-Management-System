import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ForgetPassword {
    public PasswordField confirmpasswordfield;
    public PasswordField newpasswordfield;
    public TextField petfield;
    public TextField usernametextfield;
    public Label messegelabel;
    Database_connection connection;
    Scene parentScene;

    public void setParentScene(Scene parentScene) {
        this.parentScene = parentScene;
    }

    public void initialize() {
        connection = new Database_connection();
    }

    public void onResetClick() throws SQLException {
        String username = usernametextfield.getText();
        String pet = petfield.getText();
        String password = newpasswordfield.getText();
        String confirmPassword = confirmpasswordfield.getText();
        if (username.isEmpty() | pet.isEmpty() | password.isEmpty() | confirmPassword.isEmpty()) {
            messegelabel.setText("Please fill all fields");
            return;
        }
        if (password.equals(confirmPassword)) {
            String query = String.format("update table user_pass set password='%s' where username='%s' and petname='%s'",password, username, pet);
            int no_of_rows_affected = connection.getStatement().executeUpdate(query);
            if (no_of_rows_affected == 0) {
                messegelabel.setText("Username or Pet name Wrong");
                return;
            }
            messegelabel.setText("Password Reset Successfully");
        }
        else {
            messegelabel.setText("Password do not match");
        }
    }

    public void onBackButton(ActionEvent event)
    {
        Stage currentStage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        currentStage.setScene(parentScene);
    }


}
