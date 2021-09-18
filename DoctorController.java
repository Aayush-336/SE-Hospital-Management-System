import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class DoctorController {
    ChangePane changePane;
    Doctor doctor;
    Boolean isLoaded = Boolean.FALSE;

    public void initialize() {
        new Thread(() -> {
            doctor = new Doctor();
            synchronized (isLoaded) {
                isLoaded.notify();
            }
            isLoaded = Boolean.TRUE;

        }).start();
    }

    public void setChangePane(ChangePane changePane) {
        this.changePane = changePane;
    }

    public void openAddDoctor() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addDoctor.fxml"));
        changePane.setOnly(loader.load());
        AddDoctor controller = loader.getController();
        controller.setDoctor(doctor);
    }

    public void searchDoctor() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchDoctor.fxml"));
        changePane.setOnly(loader.load());
        SearchDoctor controller = loader.getController();
        controller.setDoctor(doctor);
    }

    public void checkIsLoaded() throws InterruptedException {
        if (!isLoaded) {
            synchronized (isLoaded) {
                isLoaded.wait();
            }
        }
    }

}
