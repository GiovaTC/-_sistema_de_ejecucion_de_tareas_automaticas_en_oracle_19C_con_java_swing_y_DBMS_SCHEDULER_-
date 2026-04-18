package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;

public class OracleDAO {

    private final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private final String USER = "system";
    private final String PASS = "Tapiero123";

    public void ejecutarTarea() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            CallableStatement stmt = conn.prepareCall("{ call EJECUTAR_TAREA_AUTOMATICA}");
            stmt.execute();

            System.out.println("TAREA EJECUTADA CORRECTAMENTE EN ORACLE 19 C .");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
