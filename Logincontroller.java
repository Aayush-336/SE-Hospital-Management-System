import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Logincontroller {
    private final ArrayList<String> usernames = new ArrayList<>();
    private final ArrayList<String> passwords = new ArrayList<>();
    @FXML
    Button cancelbutton;
    @FXML
    Label messegelabel;
    @FXML
    TextField usernametextfield;
    @FXML
    PasswordField enterpasswordfield;

    Scene currentScene;
    ResultSet queryResult;

    public void initialize() throws SQLException {
        Database_connection connectNow = new Database_connection();
        queryResult = connectNow.getStatement().executeQuery("select * from user_pass");
        int i = 0;
        while (queryResult.next()) {
            usernames.add(queryResult.getString(1));
            passwords.add(queryResult.getString(2));

        }
        connectNow.closeConnection();
    }

    public void setCurrentScene(Scene scene) {
        currentScene = scene;
    }

    public void signinbuttonOnAction() throws SQLException, IOException {

        if (usernametextfield.getText().isEmpty() && enterpasswordfield.getText().isEmpty()) {
            messegelabel.setText("Please Enter Username and Password");
        } else {
            validatesignin();
        }
    }


    public void cancelbuttonOnAction() {
        Stage stage = (Stage) cancelbutton.getScene().getWindow();
        stage.close();
    }

    public void validatesignin() throws IOException {
        boolean flag = false;
        String username = usernametextfield.getText();
        String password = enterpasswordfield.getText();
        if (usernames.contains(username) & passwords.contains(password)) {
            messegelabel.setText("Congratulations...Login Successful.");
            flag = true;
        }
        if (flag)
            openLibrary();
        else
            messegelabel.setText("Username or Password Invalid");
    }

    public void openLibrary() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("OpenMainScreen.fxml"));
        currentScene.setRoot(root.load());
        MainScreenController mainScreenController = root.getController();
        mainScreenController.setCurrentScene(currentScene);
        mainScreenController.setSizeToScene();
    }

    public void changePasswordPage() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("ForgetPassword.fxml"));
        Scene scene = new Scene(root.load());
        ((Stage)currentScene.getWindow()).setScene(scene);
        ForgetPassword forgetPassword = root.getController();
        forgetPassword.setParentScene(currentScene);
    }

    public void forgetPasswordPage() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("changePassword.fxml"));
        Scene parentScene = new Scene(root.load());
        ((Stage)currentScene.getWindow()).setScene(parentScene);
        ChangePassword changePassword = root.getController();
        changePassword.setParentScene(currentScene);

    }
}