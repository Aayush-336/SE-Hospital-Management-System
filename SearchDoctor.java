import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class SearchDoctor {
    boolean headerSet = false;
    Doctor doctor;
    @FXML
    TextField tf1;
    @FXML
    Text text;
    @FXML
    GridPane gridPane;

    ArrayList<ArrayList<String>> results = new ArrayList<>();
    ArrayList<Pane> panes = new ArrayList<>();

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setHeaders(ArrayList<String> headers) {
        for (int i = 0; i < headers.size(); i++) {
            gridPane.addColumn(i);
            setGridPane(headers.get(i), 0, i, false).setFill(Color.RED);
        }
    }

    public void search() throws SQLException {
        String givenName = tf1.getText();
        results.clear();
        results = doctor.searchRecordWithHeaders(givenName, "Name", new ArrayList<>(Arrays.asList("`Doctor Id`", "Name", "Email", "`Contact No`", "Qualification", "Gender", "Age", "`Joining Date`")));
        setHeaders(results.get(0));
        results.remove(0);
        resetGridpane();
        for (int i = 0; i < results.size(); i++) {
            ArrayList<String> eachArray = results.get(i);
            gridPane.addRow(i + 1);

            for (int j = 0; j < eachArray.size(); j++) {
                setGridPane(eachArray.get(j), i + 1, j, true).getStyleClass().add("notApply");
            }
        }
    }

    Text setGridPane(String text, int row, int col, boolean addInPane) {
        Pane record = new Pane();
        Text value = new Text(text);
        value.setFont(Font.font("Calibri", FontWeight.MEDIUM, 18));
        value.setLayoutY(20);
        record.getChildren().add(value);
        gridPane.add(record, col, row);
        if (addInPane) panes.add(record);
        return value;
    }

    public void resetGridpane() {
        for (Pane pane :
                panes) {
            gridPane.getChildren().remove(pane);
        }
    }

}
