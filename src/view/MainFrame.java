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

        setTitle("Ejecutor de Tareas Oracle 19C");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnEjecutar = new JButton("EJECUTAR Tarea");
        areaLog = new JTextArea();
        areaLog.setEditable(false);

        btnEjecutar.addActionListener(e -> ejecutarTarea());

        setLayout(new BorderLayout());
        add(btnEjecutar, BorderLayout.NORTH);
        add(new JScrollPane(areaLog), BorderLayout.CENTER);
    }

    private void ejecutarTarea() {
        service.ejecutar();
        areaLog.append("✔ TAREA enviada a Oracle\n");   
    }
}