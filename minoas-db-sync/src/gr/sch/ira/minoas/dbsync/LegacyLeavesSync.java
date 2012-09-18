package gr.sch.ira.minoas.dbsync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
            st = dbConnection
                    .prepareStatement("SELECT b.APO, b.EVS, b.ARHMER, b.NOTE, b.CODE, b.KVD FROM mkdb..bohu b ORDER BY b.KVD,b.APO");
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
            if (st != null) {
                st.close();
            }
            throw new RuntimeException(ex);
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
            st = dbConnection
                    .prepareStatement("SELECT e.ID, e.LEGACY_CODE FROM EMPLOYEE e WHERE e.LEGACY_CODE IS NOT NULL");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Map employee = new HashMap();
                returnValue.put(rs.getString(MINOAS_EMPLOYEE_LEGACY_CODE).trim(), rs.getInt(MINOAS_EMPLOYEE_ID));
            }
            return returnValue;
        } catch (Exception ex) {
            if (st != null) {
                st.close();
            }
            throw new RuntimeException(ex);
        }
    }

    public static Map<String, Integer> getLeaveMapping(Connection dbConnection) throws Exception {

        PreparedStatement st = null;
        Map<String, Integer> returnValue = new HashMap<String, Integer>();
        try {
            st = dbConnection.prepareStatement("SELECT t.LEGACY_CODE, t.ID FROM minoas..EMPLOYEE_LEAVE_TYPE t");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                returnValue.put(rs.getString("LEGACY_CODE"), Integer.valueOf(rs.getInt("ID")));
            }
            return returnValue;
        } catch (Exception ex) {
            if (st != null) {
                st.close();
            }
            throw new RuntimeException(ex);
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
                    .prepareStatement("INSERT INTO minoas..EMPLOYEE_LEAVES(VERSION, IS_ACTIVE, COMMENT, DUE_TO, ESTABLISHED, EFFECTIVE_DAYS_COUNT, EMPLOYEE_LEAVE_TYPE_ID, EMPLOYEE_ID, GENERATES_CDRS) VALUES(0, 0, ?, ?, ?, ?, ?, ?, 1)");
            int count = 0;
            dbConnection.setAutoCommit(true);
            for (Map legacyLeave : legacyLeaves) {
                if (legacyLeaveMapping.containsKey(legacyLeave.get(LEGACY_LEAVE_CODE))) {
                    Integer minoasLeaveCode = legacyLeaveMapping.get(legacyLeave.get(LEGACY_LEAVE_CODE));
                    Integer minoasEmployeeCode = (Integer) employeeMapping.get(legacyLeave
                            .get(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE));
                    if (minoasEmployeeCode != null) {
                        insertStatement.setString(1, (String) legacyLeave.get(LEGACY_LEAVE_NOTE));
                        insertStatement.setDate(2,
                                new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_TO)).getTime()));
                        insertStatement.setDate(3,
                                new java.sql.Date(((Date) legacyLeave.get(LEGACY_LEAVE_FROM)).getTime()));
                        insertStatement.setInt(4, ((Integer) legacyLeave.get(LEGACY_LEAVE_DURATION)).intValue());
                        insertStatement.setInt(5, minoasLeaveCode);
                        insertStatement.setInt(6, minoasEmployeeCode);
                        if(insertStatement.execute())
                            count++;
                    } else {
                        System.out.println(String.format("could not map legacy employee '%s' to the corresponding minoas employee", legacyLeave
                            .get(LEGACY_LEAVE_EMPLOYEE_LEGACY_CODE)));
                    }
                } else {
                    /* no mapping */
                    continue;
                }
            }
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
