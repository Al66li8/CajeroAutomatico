package CajeroBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class CambiarNIP extends JFrame {
    private JTextField nipActualField;
    private JTextField nuevoNIPField;
    private JButton cambiarNIPButton;
    private JButton volverButton;
    private String nipActual;

    public CambiarNIP(String nipActual) {
        this.nipActual = nipActual;
        initComponents();
    }

    private void initComponents() {
        setTitle("Cambiar NIP");
        setSize(300, 200);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel nipActualLabel = new JLabel("NIP Actual:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(nipActualLabel, gbc);

        nipActualField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(nipActualField, gbc);

        JLabel nuevoNIPLabel = new JLabel("Nuevo NIP:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(nuevoNIPLabel, gbc);

        nuevoNIPField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(nuevoNIPField, gbc);

        cambiarNIPButton = new JButton("Cambiar NIP");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(cambiarNIPButton, gbc);

        volverButton = new JButton("Volver");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(volverButton, gbc);

        cambiarNIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarNIP();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void cambiarNIP() {
        String nipIngresado = nipActualField.getText().trim();
        String nuevoNIP = nuevoNIPField.getText().trim();

        if (nipIngresado.equals(nipActual)) {
            if (nuevoNIP.length() > 0) {
                guardarNuevoNIP(nuevoNIP);
                JOptionPane.showMessageDialog(this, "NIP cambiado con éxito.");
                dispose(); // Cerrar la ventana después de cambiar el NIP
            } else {
                JOptionPane.showMessageDialog(this, "El nuevo NIP no puede estar vacío.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "NIP actual incorrecto.");
        }
    }

    private void guardarNuevoNIP(String nuevoNIP) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("nip.txt"))) {
            writer.write(nuevoNIP);
        } catch (IOException e) {
            System.err.println("Error al guardar el nuevo NIP: " + e.getMessage());
        }
    }
}
