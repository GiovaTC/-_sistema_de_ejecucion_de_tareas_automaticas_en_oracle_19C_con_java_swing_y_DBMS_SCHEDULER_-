# -_sistema_de_ejecucion_de_tareas_automaticas_en_oracle_19C_con_java_swing_y_DBMS_SCHEDULER_- :.

<img width="1024" height="1024" alt="image" src="https://github.com/user-attachments/assets/9c886ab3-203d-4b7a-8aeb-63411ddd0137" />    

<img width="2544" height="1078" alt="image" src="https://github.com/user-attachments/assets/f820cb14-2aba-483b-97d3-41b0d6747d9f" />    

<img width="2553" height="1079" alt="image" src="https://github.com/user-attachments/assets/01bd8a99-b4cc-4669-a50b-b8b22660ba34" />    

```
🧩 Arquitectura Propuesta
📌 Capas
GUI (Swing)
Servicio (Lógica de negocio)
DAO (JDBC)
Oracle (DBMS_SCHEDULER) .

🔄 Flujo
Usuario presiona botón "Ejecutar tarea"
Java invoca un procedimiento en Oracle
Oracle crea y ejecuta un JOB inmediatamente
El JOB ejecuta una tarea (ej: insertar registro en tabla log).

🗄️ 1. SCRIPT ORACLE 19c
📋 Tabla de logs
CREATE TABLE TAREA_LOG (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    MENSAJE VARCHAR2(200),
    FECHA_EJECUCION TIMESTAMP DEFAULT SYSTIMESTAMP
);

⚙️ Procedimiento que crea y ejecuta el JOB
CREATE OR REPLACE PROCEDURE EJECUTAR_TAREA_AUTOMATICA IS
BEGIN
    DBMS_SCHEDULER.CREATE_JOB (
        job_name   => 'JOB_TAREA_' || TO_CHAR(SYSTIMESTAMP, 'YYYYMMDDHH24MISS'),
        job_type   => 'PLSQL_BLOCK',
        job_action => '
            BEGIN
                INSERT INTO TAREA_LOG (MENSAJE)
                VALUES (''Tarea ejecutada desde Java'');
                COMMIT;
            END;',
        start_date => SYSTIMESTAMP,
        enabled    => TRUE
    );
END;
/

✔ Esto crea y ejecuta el job en el momento exacto.

☕ 2. CONEXIÓN JDBC (DAO)
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.CallableStatement;

public class OracleDAO {

    private final String URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private final String USER = "system";
    private final String PASS = "12345";

    public void ejecutarTarea() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            CallableStatement stmt = conn.prepareCall("{ call EJECUTAR_TAREA_AUTOMATICA }");
            stmt.execute();

            System.out.println("Tarea ejecutada correctamente en Oracle");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

🧠 3. CAPA SERVICIO
package service;

import dao.OracleDAO;

public class TareaService {

    private OracleDAO dao = new OracleDAO();

    public void ejecutar() {
        dao.ejecutarTarea();
    }
}

🖥️ 4. INTERFAZ GRÁFICA (Swing)
package view;

import service.TareaService;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JButton btnEjecutar;
    private JTextArea areaLog;
    private TareaService service;

    public MainFrame() {
        service = new TareaService();

        setTitle("Ejecutor de Tareas Oracle 19c");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnEjecutar = new JButton("Ejecutar Tarea");
        areaLog = new JTextArea();
        areaLog.setEditable(false);

        btnEjecutar.addActionListener(e -> ejecutarTarea());

        setLayout(new BorderLayout());
        add(btnEjecutar, BorderLayout.NORTH);
        add(new JScrollPane(areaLog), BorderLayout.CENTER);
    }

    private void ejecutarTarea() {
        service.ejecutar();
        areaLog.append("✔ Tarea enviada a Oracle\n");
    }
}

🚀 5. CLASE PRINCIPAL
import view.MainFrame;

public class Main {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

🎯 Resultado Esperado

Cuando el usuario presiona el botón:

✔ Se crea un JOB en Oracle
✔ El JOB se ejecuta inmediatamente
✔ Se inserta un registro en TAREA_LOG.

🔎 Verificación en BD
SELECT * 
FROM TAREA_LOG 
ORDER BY FECHA_EJECUCION DESC;

🔧 Mejora Opcional (Nivel Profesional)

Puedes extender la solución con:

Consultar USER_SCHEDULER_JOBS
Mostrar estado del JOB en la GUI
Ejecutar jobs con parámetros
Programar tareas futuras (tipo cron).

⚠️ Consideración Técnica Importante:
Si solo necesitas ejecución inmediata, no es obligatorio usar DBMS_SCHEDULER:

INSERT INTO TAREA_LOG (MENSAJE) VALUES ('Ejecución directa');
✔ Cuándo SÍ usar DBMS_SCHEDULER
Necesitas auditoría
Requieres jobs recurrentes
Quieres desacoplar la ejecución
Manejas tareas asincrónicas o diferidas :. . / .
