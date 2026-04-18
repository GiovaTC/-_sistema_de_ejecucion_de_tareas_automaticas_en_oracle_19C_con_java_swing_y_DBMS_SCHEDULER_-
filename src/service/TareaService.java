package service;

import dao.OracleDAO;

public class TareaService {

    private OracleDAO dao = new OracleDAO();

    public void ejecutar() {
        dao.ejecutarTarea();
    }
}
