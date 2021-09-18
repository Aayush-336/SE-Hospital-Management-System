import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class PatientController {
    Boolean isLoaded = Boolean.FALSE;
    Patient patient;
    ChangePane changePane;


    public void initialize() {
        new Thread(() -> {
            patient = new Patient();
            synchronized (isLoaded) {
                isLoaded.notify();
            }
            isLoaded = Boolean.TRUE;

        }).start();
    }

    public void openAddPatient() throws IOException, InterruptedException {
        checkIsLoaded();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPatient.fxml"));
        changePane.setOnly(loader.load());
        AddPatient controller = loader.getController();
        controller.setPatient(patient);
    }

    public void searchPatient() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("searchPatient.fxml"));
        changePane.setOnly(loader.load());
        SearchPatient controller = loader.getController();
        controller.setPatient(patient);
    }

    public void dischargePatient() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("dischargePatient.fxml"));
        changePane.setOnly(loader.load());
        DischargePatient controller = loader.getController();
        controller.setPatient(patient);
    }

    public void admitPatient() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("admitPatient.fxml"));
        changePane.setOnly(loader.load());
        AdmitPatient controller = loader.getController();
        controller.setPatient(patient);
    }


    public void addService() throws InterruptedException, IOException {
        checkIsLoaded();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("addService.fxml"));
        changePane.setOnly(loader.load());
        AddService controller = loader.getController();
        controller.setPatient(patient);
    }

    public void setChangePane(ChangePane changePane) {
        this.changePane = changePane;
    }

    public void checkIsLoaded() throws InterruptedException {
        if (!isLoaded) {
            synchronized (isLoaded) {
                isLoaded.wait();
            }
        }
    }
}
