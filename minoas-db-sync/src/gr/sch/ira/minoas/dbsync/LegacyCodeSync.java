package gr.sch.ira.minoas.dbsync;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LegacyCodeSync {

    
    public static Connection getConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://agaph.dide.ira.net:1433;"
                + "databaseName=minoas;user=slavikos;password=slavikos;";
        return DriverManager.getConnection(connectionUrl);
    }

    public static List<EmployeeSignature> fetchAllEmployees(Connection dbConnection) throws Exception {

        PreparedStatement st = null;
        List<EmployeeSignature> returnValue = new ArrayList<EmployeeSignature>(100);
        try {
            st = dbConnection
                    .prepareStatement("SELECT b.KVD, b.ONOMA, b.EPIUETO, b.PATERAS, b.EIDKLAD, b.OKVD FROM mkdb..basiko b");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                EmployeeSignature e = new EmployeeSignature();
                e.setLegacyCode(rs.getString("KVD"));
                String name = String.valueOf(rs.getString("ONOMA")).trim();
                String surname = String.valueOf(rs.getString("EPIUETO")).trim();
                String fatherName = String.valueOf(rs.getString("PATERAS")).trim();
                String specializationID = String.valueOf(rs.getString("EIDKLAD")).trim();
                String schoolID = String.valueOf(rs.getString("OKVD")).trim();
                String signature = String.format("%s-%s-%s-%s-%s", surname, name, fatherName, specializationID, schoolID);
                e.setSignature(signature);
                returnValue.add(e);
            }
            return returnValue;
        } catch (Exception ex) {
            if (st != null) {
                st.close();
            }
            throw new RuntimeException(ex);
        }

    }
    
    public static List<EmployeeSignature> findCandidatesForMinoasEmployee(EmployeeSignature minoasEmployee, List<EmployeeSignature> legacyEmployees) {
        List<EmployeeSignature> candidates = new ArrayList<EmployeeSignature>();
        String tmpStr = null;
        EmployeeSignature tmple = null;
        int bestMatch = Integer.MAX_VALUE;
        for(EmployeeSignature le : legacyEmployees) {
            int stringDistance = LevenshteinDistance.computeLevenshteinDistance(le.getSignature(), minoasEmployee.getSignature());
            if(stringDistance<30) {
                if(stringDistance<bestMatch) {
                    tmpStr = String.format("distance = %d -> '%s' and '%s'", stringDistance, le.getSignature(), minoasEmployee.getSignature());
                    tmple = le;
                    bestMatch=stringDistance;
                }
            }
        }
        if(tmple!=null) {
            tmple.setMinoasEmployeeId(minoasEmployee.getMinoasEmployeeId());
            candidates.add(tmple);
            System.err.println(tmpStr);
        }
        return candidates;
    }

    public static List<EmployeeSignature> fetchAllRegularMinoasEmployeesWithNoLegacyCode(Connection dbConnection)
            throws Exception {
        PreparedStatement st = null;
        List<EmployeeSignature> returnValue = new ArrayList<EmployeeSignature>(100);
        try {
            st = dbConnection
                    .prepareStatement("SELECT e.ID, e.FIRST_NAME, e.LAST_NAME, e.FATHER_NAME, s.SPECIALIZATION_ID, em.SCHOOL_ID FROM minoas..EMPLOYEE AS e INNER JOIN SPECIALIZATION s ON s.SPECIALIZATION_ID=e.LAST_SPECIALIZATION_ID INNER JOIN EMPLOYMENT em ON e.CURRENT_EMPLOYMENT_ID=em.ID WHERE e.LEGACY_CODE IS NULL AND e.EMPLOYEE_TYPE='REGULAR'");
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                EmployeeSignature e = new EmployeeSignature();
                e.setMinoasEmployeeId(new Integer(rs.getInt("ID")));
                String name = String.valueOf(rs.getString("FIRST_NAME")).trim();
                String surname = String.valueOf(rs.getString("LAST_NAME")).trim();
                String fatherName = String.valueOf(rs.getString("FATHER_NAME")).trim();
                String specializationID = String.valueOf(rs.getString("SPECIALIZATION_ID")).trim();
                String schoolID = String.valueOf(rs.getString("SCHOOL_ID")).trim();
                String signature = String.format("%s-%s-%s-%s-%s", surname, name, fatherName, specializationID, schoolID);
                e.setSignature(signature);
                returnValue.add(e);
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
            System.out.println("fetching all regular employees from minoas without a legacy code");
            List<EmployeeSignature> minoasEmployees = fetchAllRegularMinoasEmployeesWithNoLegacyCode(dbConnection);
            System.out.println(String.format("fetched totally %d employee(s).", minoasEmployees.size()));
            System.out.println("fetching all regular employees from legacy db");
            List<EmployeeSignature> employees = fetchAllEmployees(dbConnection);
            System.out.println(String.format("fetched totally %d employee(s) from legacy db.", employees.size()));
            int foundCandidates = 0;
            for(EmployeeSignature e : minoasEmployees) {
//                System.out.println("*******************************************************");
  //              System.out.println(String.format("Candidates for '%s' minoas emloyee: ", e.getSignature()));
 //               System.out.println("*******************************************************");
                List<EmployeeSignature> candidates = findCandidatesForMinoasEmployee(e, employees);
                int i = 1;
                if(candidates.size() > 0) {
                    for(EmployeeSignature c : candidates) {
   //                     System.out.println(String.format("***** #%d : found candidate '%s'", i, c.getSignature()));
     //                   System.out.println(String.format("SELECT b.KVD, b.ONOMA, b.EPIUETO, b.PATERAS, b.EIDKLAD, b.OKVD FROM mkdb..basiko b WHERE b.KVD = '%s'", c.getLegacyCode()));
         //               System.out.println(String.format("SELECT e.ID, e.FIRST_NAME, e.LAST_NAME, e.FATHER_NAME, s.SPECIALIZATION_ID, em.SCHOOL_ID FROM minoas..EMPLOYEE AS e INNER JOIN minoas..SPECIALIZATION s ON s.SPECIALIZATION_ID=e.LAST_SPECIALIZATION_ID INNER JOIN minoas..EMPLOYMENT em ON e.CURRENT_EMPLOYMENT_ID=em.ID WHERE e.ID=%s", c.getMinoasEmployeeId().toString()));
       //                 System.out.println("//======== UPDATE SQL ========");
                        System.out.println(String.format("// UPDATE minoas..EMPLOYEE SET LEGACY_CODE='%s' WHERE ID=%s", c.getLegacyCode(), c.getMinoasEmployeeId().toString())); 
                        
                    }
                    foundCandidates++;
                }
               // System.out.println("======================================================");
            }
            System.out.println("");
            System.out.println("");
            System.out.println(String.format("We have manage to find totally %d candidates out of %d employee(s)", foundCandidates, minoasEmployees.size()));
        } catch (Exception ex) {
            System.err.println(ex);

        } finally {
            if(dbConnection!=null)
                try {
                    dbConnection.close();
                } catch(Exception ex) {
                    
                }
        }
    }

}
