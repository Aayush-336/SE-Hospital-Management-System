import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {
    @FXML
    Pane changePane;

    private Scene currentScene;

    public Scene getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(Scene currentScene) {
        this.currentScene = currentScene;
    }

    public void onPatientClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("patientFile.fxml"));
        ChangePane editPane = new ChangePane(changePane);
        editPane.setOnly(loader.load());
        PatientController patientController = loader.getController();
        patientController.setChangePane(editPane);
    }

    public void setSizeToScene()
    {
        currentScene.getWindow().sizeToScene();
    }


    public void onHomeButtonClick() throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("OpenMainScreen.fxml"));
        currentScene.setRoot(root.load());
        MainScreenController mainScreenController = root.getController();
        mainScreenController.setCurrentScene(getCurrentScene());
    }

    public void onDoctorClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("doctorFile.fxml"));
        ChangePane editPane = new ChangePane(changePane);
        editPane.setOnly(loader.load());
        DoctorController doctorController = loader.getController();
        doctorController.setChangePane(editPane);
    }

}

class ChangePane extends Pane {
    Pane changePane;

    ChangePane(Pane changePane) {
        this.changePane = changePane;
    }

    public void setOnly(Node node) {
        changePane.getChildren().clear();
        changePane.getChildren().add(node);
    }
}
