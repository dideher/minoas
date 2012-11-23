package gr.sch.ira.minoas.dbsync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LegacyLeavesSync {

    public static final String LEGACY_LEAVE_FROM = "APO";

    public static final String LEGACY_LEAVE_TO = "EVS";

    public static final String LEGACY_LEAVE_DURATION = "ARHMER";

    public static final String LEGACY_LEAVE_CODE = "CODE";

    public static final String LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE = "KVD";

    public static final String LEGACY_LEAVE_NOTE = "NOTE";

    public static String MINOAS_EMPLOYEE_ID = "ID";

    public static String MINOAS_EMPLOYEE_LEGACY_CODE = "LEGACY_CODE";

    public static List<Map> fetchAllLegacyLeaves(Connection dbConnection) throws Exception {
        PreparedStatement st = null;
        List<Map> returnValue = new ArrayList<Map>(10000);
        try {
            st = dbConnection.prepareStatement(
                    "SELECT b.APO, b.EVS, b.ARHMER, b.NOTE, b.CODE, b.KVD FROM mkdb..bohu b ORDER BY b.KVD,b.APO",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Map legacyLeave = new HashMap();
                legacyLeave.put(LEGACY_LEAVE_FROM, rs.getDate(LEGACY_LEAVE_FROM));
                legacyLeave.put(LEGACY_LEAVE_TO, rs.getDate(LEGACY_LEAVE_TO));
                legacyLeave.put(LEGACY_LEAVE_DURATION, rs.getInt(LEGACY_LEAVE_DURATION));
                legacyLeave.put(LEGACY_LEAVE_CODE, rs.getString(LEGACY_LEAVE_CODE).trim());
                legacyLeave.put(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE, rs.getString(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE)
                        .trim());

                String legacyLeaveNote = rs.getString(LEGACY_LEAVE_NOTE);
                if (legacyLeaveNote != null)
                    legacyLeave.put(LEGACY_LEAVE_NOTE, legacyLeaveNote.trim());
                else
                    legacyLeave.put(LEGACY_LEAVE_NOTE, "");
                returnValue.add(legacyLeave);
            }
            return returnValue;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            if (st != null)
                st.close();
        }

    }

    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://agaph.dide.ira.net:1433;"
                + "databaseName=minoas;user=slavikos;password=slavikos;";
        return DriverManager.getConnection(connectionUrl);
    }

    public static Map getEmpoyeeMapping(Connection dbConnection) throws Exception {
        PreparedStatement st = null;
        Map returnValue = new HashMap();
        try {
            st = dbConnection.prepareStatement(
                    "SELECT e.ID, e.LEGACY_CODE FROM EMPLOYEE e WHERE e.LEGACY_CODE IS NOT NULL",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Map employee = new HashMap();
                returnValue.put(rs.getString(MINOAS_EMPLOYEE_LEGACY_CODE).trim(), rs.getInt(MINOAS_EMPLOYEE_ID));
            }
            return returnValue;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            st.close();
        }
    }

    public static Map<String, Integer> getLeaveMapping(Connection dbConnection) throws Exception {

        PreparedStatement st = null;
        Map<String, Integer> returnValue = new HashMap<String, Integer>();
        try {
            st = dbConnection.prepareStatement("SELECT t.LEGACY_CODE, t.ID FROM minoas..EMPLOYEE_LEAVE_TYPE t",
                    ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                returnValue.put(rs.getString("LEGACY_CODE"), Integer.valueOf(rs.getInt("ID")));
            }
            return returnValue;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        } finally {
            st.close();
        }
    }

    public static void main(String[] args) {

        Connection dbConnection = null;

        System.out.println("Started !");
        try {
            dbConnection = getConnection();
            // fetch and create the legacy leave code to new minoas leave codes
            System.out.println("building legacy leave mapping.");
            Map<String, Integer> legacyLeaveMapping = getLeaveMapping(dbConnection);
            System.out.println(String.format("found %d totally leave mapping(s)", legacyLeaveMapping.size()));

            /* fetch all legacy leaves */
            List<Map> legacyLeaves = fetchAllLegacyLeaves(dbConnection);
            System.out.println(String.format("found %d totally legacy leaves (plus expirience)", legacyLeaves.size()));

            /* featch minoas and legacy employee mapping */
            Map employeeMapping = getEmpoyeeMapping(dbConnection);
            System.out.println(String.format("found %d totally minoas employee mapping", employeeMapping.size()));

            PreparedStatement insertStatement = dbConnection
                    .prepareStatement("INSERT INTO minoas..EMPLOYEE_LEAVES(VERSION, IS_ACTIVE, COMMENT, DUE_TO, ESTABLISHED, EFFECTIVE_DAYS_COUNT, EMPLOYEE_LEAVE_TYPE_ID, EMPLOYEE_ID, GENERATES_CDRS, IMPORTED_ON) VALUES(0, 0, ?, ?, ?, ?, ?, ?, 1, ?)");
            PreparedStatement st = dbConnection
                    .prepareStatement("UPDATE minoas..EMPLOYEE_LEAVES SET EMPLOYEE_LEAVE_TYPE_ID=? WHERE EMPLOYEE_ID=? AND ESTABLISHED=? AND DUE_TO = ? AND EFFECTIVE_DAYS_COUNT=? AND INSERTED_BY_ID IS NULL AND INSERTED_ON IS NULL");

            int count = 0;
            int updated = 0;
            dbConnection.setAutoCommit(true);
            dbConnection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
            Timestamp importedOnDate = new Timestamp(System.currentTimeMillis());
            
            for (Map legacyLeave : legacyLeaves) {
                try {
                if (legacyLeaveMapping.containsKey(legacyLeave.get(LEGACY_LEAVE_CODE))) {
                    Integer minoasLeaveCode = legacyLeaveMapping.get(legacyLeave.get(LEGACY_LEAVE_CODE));
                    Integer minoasEmployeeCode = (Integer) employeeMapping.get(legacyLeave
                            .get(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE));
                    if (minoasEmployeeCode != null) {

                        st.setInt(1, minoasLeaveCode);
                        st.setInt(2, minoasEmployeeCode);
                        st.setDate(3, new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_FROM)).getTime()));
                        st.setDate(4, new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_TO)).getTime()));
                        st.setInt(5, ((Integer) legacyLeave.get(LEGACY_LEAVE_DURATION)).intValue());
                        int _updated = st.executeUpdate();
                        if (_updated > 0) {
                            updated++;
                        } else {

                            insertStatement.setString(1, (String) legacyLeave.get(LEGACY_LEAVE_NOTE));
                            insertStatement.setDate(2,
                                    new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_TO)).getTime()));
                            insertStatement.setDate(3,
                                    new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_FROM)).getTime()));
                            insertStatement.setInt(4, ((Integer) legacyLeave.get(LEGACY_LEAVE_DURATION)).intValue());
                            insertStatement.setInt(5, minoasLeaveCode);
                            insertStatement.setInt(6, minoasEmployeeCode);
                            insertStatement.setTimestamp(7, importedOnDate);
                            if (insertStatement.execute())
                                count++;
                        }
                        dbConnection.clearWarnings();
                    } else {
                        System.out.println(String.format(
                                "could not map legacy employee '%s' to the corresponding minoas employee",
                                legacyLeave.get(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE)));
                    }
                } else {
                    /* no mapping */
                    continue;
                }
            } catch(Exception ex) {
                System.err.println(ex);
            }
        }
            
            System.out.println(String.format("Totally updated %d leaves", updated));
            System.out.println(String.format("Totally created %d leaves", count));
            System.exit(1);

        } catch (Exception ex) {
            System.err.println(ex);

        } finally {
            if (dbConnection != null)
                try {
                    dbConnection.close();
                } catch (Exception ex) {

                }
        }
    }
}
