import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Patient {
    private final Database_connection connection;
    ArrayList<String> headers = new ArrayList<>();

    Patient() {
        connection = new Database_connection();
        ResultSet resultSet;
        try {
            resultSet = connection.getStatement().executeQuery("select * from patients where 1=2");
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int no_of_columns = resultSetMetaData.getColumnCount();
            for (int i = 0; i < no_of_columns; i++) {
                headers.add(resultSetMetaData.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public int addPatient(String name, String email, String contact_no, String gender, String age, String bloodGroup) throws SQLException {
        String query = String.format("insert into patients (Name,Email,`Contact No`,gender,age,`blood group`) values ('%s','%s','%s','%s',%s,'%s')", name, email, contact_no, gender, age, bloodGroup);
        return connection.getStatement().executeUpdate(query);

    }

    public int dischargePatient(String patient_id, String date) throws SQLException {
        String query = String.format("update Admit set `Discharge Date` = '%s' where `Patient Id`=%s and `Discharge Date` = NULL", date, patient_id);
        return connection.getStatement().executeUpdate(query);
    }

    public int deleteBill(String patient_id) throws SQLException {
        String query2 = String.format("delete from Service where `Patient Id`=%s",patient_id);
        return connection.getStatement().executeUpdate(query2);
    }

    public ArrayList<ArrayList<String>> dischargePatientBill(String patient_id, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from Service where `Patient Id`=(%s)", query_requirements, patient_id);
        ResultSet rs = connection.getStatement().executeQuery(query);
        return resultSetToArray(rs);
    }

    public int admitPatient(String patient_id, String disease, String date, String docId) throws SQLException {
        String query = String.format("insert into Admit(`Patient Id`,Disease,`Admit Date`,`Doctor Id`) values(%s,'%s','%s',%s)", patient_id, disease, date, docId);
        return connection.getStatement().executeUpdate(query);
    }

    public int addService(String patient_id, String serviceName, String bill) throws SQLException {
        String query = String.format("insert into Service values(%s,'%s',%s)", patient_id, serviceName, bill);
        return connection.getStatement().executeUpdate(query);
    }

    private String generateQuery(ArrayList<String> requirementsArray) {
        StringBuilder requirements = new StringBuilder();
        for (String word :
                requirementsArray) {
            requirements.append(word).append(",");
        }
        requirements.deleteCharAt(requirements.length() - 1);
        return new String(requirements);
    }

    public String getBill(int patient_id) throws SQLException {
        int bill = -1;

        String query = String.format("select bill_amount from patient_bill where `Patient Id`=%s", patient_id);
        ResultSet resultSet = connection.getStatement().executeQuery(query);
        if (resultSet.next()) {
            bill = resultSet.getInt(1);
        }
        if (bill == -1) {
            return "None";
        }
        return Integer.toString(bill);
    }

    public ArrayList<String> getRecordFromId(int patient_id) throws SQLException {
        String query = String.format("select * from patients where `Patient Id`=(%s)", patient_id);
        ResultSet rs = connection.getStatement().executeQuery(query);
        return resultSetToArray(rs).get(0);
    }

    public ArrayList<String> getRequiredRecordFromId(String patient_id, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from patients where `Patient Id`=(%s)", query_requirements, patient_id);
        ResultSet rs = connection.getStatement().executeQuery(query);
        return resultSetToArray(rs).get(0);
    }

    public ArrayList<String> getRecordFromId(int patient_id, int no_of_columns) throws SQLException {
        String query = String.format("select * from patients where `Patient Id`=(%s)", patient_id);
        ResultSet rs = connection.getStatement().executeQuery(query);
        return resultSetToArray(rs).get(0);
    }

    private ArrayList<ArrayList<String>> resultSetToArray(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int no_of_columns = metaData.getColumnCount();
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        int r = 0;
        while (resultSet.next()) {
            results.add(new ArrayList<>());
            for (int i = 0; i < no_of_columns; i++) {
                results.get(r).add(resultSet.getString(i + 1));
            }
            r++;
        }
        return results;
    }

    public ArrayList<ArrayList<String>> searchRecord(String key, String searchBy, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from patients where %s like '%%%s%%'", query_requirements, searchBy, key);
        ResultSet resultSet = connection.getStatement().executeQuery(query);
        return resultSetToArray(resultSet);
    }

}
