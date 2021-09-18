import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Doctor {
    private final Database_connection connection;

    Doctor() {
        connection = new Database_connection();
    }

    public int addDoctor(String name, String email, String contact_no, String qualification, String gender, String age, String joinDate) throws SQLException {
        String query = String.format("insert into doctors (Name,Email,`Contact No`,Qualification,gender,age,`Joining Date`) values ('%s','%s','%s','%s','%s',%s,'%s')", name, email, contact_no, qualification, gender, age, joinDate);
        return connection.getStatement().executeUpdate(query);
    }

    private ArrayList<ArrayList<String>> resultSetToArrayWithHeaders(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int no_of_columns = metaData.getColumnCount();
        ArrayList<ArrayList<String>> results = new ArrayList<>();
        results.add(new ArrayList<>());
        for (int i = 0; i < no_of_columns; i++) {
            results.get(0).add(metaData.getColumnName(i + 1));
        }
        int r = 1;
        while (resultSet.next()) {
            results.add(new ArrayList<>());
            for (int i = 0; i < no_of_columns; i++) {
                results.get(r).add(resultSet.getString(i + 1));
            }
            r++;
        }
        return results;
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

    public ArrayList<String> getRequiredRecordFromId(String docId, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from doctors where `Doctor Id`=(%s)", query_requirements, docId);
        ResultSet rs = connection.getStatement().executeQuery(query);
        return resultSetToArray(rs).get(0);
    }

    public ArrayList<ArrayList<String>> searchRecordWithHeaders(String key, String searchBy, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from doctors where %s like '%%%s%%'", query_requirements, searchBy, key);
        ResultSet resultSet = connection.getStatement().executeQuery(query);

        return resultSetToArrayWithHeaders(resultSet);
    }

    public ArrayList<ArrayList<String>> searchRecord(String key, String searchBy, ArrayList<String> requirements) throws SQLException {
        String query_requirements = generateQuery(requirements);
        String query = String.format("select %s from doctors where %s like '%%%s%%'", query_requirements, searchBy, key);
        ResultSet resultSet = connection.getStatement().executeQuery(query);
        return resultSetToArray(resultSet);
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

}
