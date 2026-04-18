--?? 1. SCRIPT ORACLE 19c
--? Tabla de logs
CREATE TABLE TAREA_LOG (
    ID NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    MENSAJE VARCHAR2(200),
    FECHA_EJECUCION TIMESTAMP DEFAULT SYSTIMESTAMP
);

COMMIT;

--?? Procedimiento que crea y ejecuta el JOB
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

COMMIT;