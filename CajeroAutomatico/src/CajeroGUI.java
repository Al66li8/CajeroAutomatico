package CajeroBase;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CajeroGUI extends JFrame {

    private JLabel saldoLabel;
    private JTextField montoDepositoField;
    private JTextField montoRetiroField;
    private JTextField nipActualField;
    private JTextField nuevoNIPField;
    private JButton consultarSaldoButton;
    private JButton depositarButton;
    private JButton retirarButton;
    private JButton cambiarNIPButton;
    private JButton salirButton;
    private JPanel menuPanel;
    private JPanel accionesPanel;
    private String nip; // NIP cargado desde el archivo

    public CajeroGUI() {
        nip = cargarNIP(); // Cargar el NIP desde el archivo al iniciar
        if (autenticar()) {
            initMenuComponents();
        } else {
            JOptionPane.showMessageDialog(this, "NIP incorrecto. Saliendo...");
            System.exit(0); // Cerrar la aplicación si el NIP es incorrecto
        }
    }

    private String cargarNIP() {
        try (BufferedReader reader = new BufferedReader(new FileReader("nip.txt"))) {
            return reader.readLine();
        } catch (IOException e) {
            System.err.println("Error al cargar el NIP: " + e.getMessage());
            return "2910"; // NIP predeterminado en caso de error
        }
    }

    private boolean autenticar() {
        String inputNIP = JOptionPane.showInputDialog(this, "Ingrese su NIP:");
        return nip.equals(inputNIP);
    }

    private void initMenuComponents() {
        // Configurar el panel del menú con disposición similar a un cajero automático
        menuPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        consultarSaldoButton = new JButton("Consultar Saldo");
        depositarButton = new JButton("Depositar");
        retirarButton = new JButton("Retirar");
        cambiarNIPButton = new JButton("Cambiar NIP");
        salirButton = new JButton("Salir");

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        menuPanel.add(consultarSaldoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        menuPanel.add(depositarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        menuPanel.add(retirarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        menuPanel.add(cambiarNIPButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        menuPanel.add(salirButton, gbc);

        consultarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarConsultarSaldo();
            }
        });

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarDepositar();
            }
        });

        retirarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarRetirar();
            }
        });

        cambiarNIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarCambiarNIP();
            }
        });

        salirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(menuPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Cajero Automático");
        setSize(400, 400); // Tamaño de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setVisible(true);
    }

    private void mostrarConsultarSaldo() {
        menuPanel.setVisible(false); // Ocultar el menú
        accionesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        saldoLabel = new JLabel("Saldo: " + SuperClaseSaldo.getSaldo());
        gbc.gridx = 0;
        gbc.gridy = 0;
        accionesPanel.add(saldoLabel, gbc);

        JButton volverButton = new JButton("Volver al Menú");
        gbc.gridx = 0;
        gbc.gridy = 1;
        accionesPanel.add(volverButton, gbc);

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });

        add(accionesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void mostrarDepositar() {
        menuPanel.setVisible(false); // Ocultar el menú
        accionesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        montoDepositoField = new JTextField(10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        accionesPanel.add(new JLabel("Monto a depositar:"), gbc);

        gbc.gridx = 1;
        accionesPanel.add(montoDepositoField, gbc);

        depositarButton = new JButton("Depositar");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        accionesPanel.add(depositarButton, gbc);

        JButton volverButton = new JButton("Volver al Menú");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        accionesPanel.add(volverButton, gbc);

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                depositarActionPerformed(e);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });

        add(accionesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void mostrarRetirar() {
        menuPanel.setVisible(false); // Ocultar el menú
        accionesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Seleccione una cantidad a retirar:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        accionesPanel.add(label, gbc);

        JButton retirar50Button = new JButton("$50");
        JButton retirar100Button = new JButton("$100");
        JButton retirar200Button = new JButton("$200");
        JButton retirar500Button = new JButton("$500");
        JButton otroMontoButton = new JButton("Otro Monto");

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        accionesPanel.add(retirar50Button, gbc);

        gbc.gridx = 1;
        accionesPanel.add(retirar100Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        accionesPanel.add(retirar200Button, gbc);

        gbc.gridx = 1;
        accionesPanel.add(retirar500Button, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        accionesPanel.add(otroMontoButton, gbc);

        JButton volverButton = new JButton("Volver al Menú");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        accionesPanel.add(volverButton, gbc);

        retirar50Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRetiro(50);
            }
        });

        retirar100Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRetiro(100);
            }
        });

        retirar200Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRetiro(200);
            }
        });

        retirar500Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                procesarRetiro(500);
            }
        });

        otroMontoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solicitarOtroMonto();
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });

        add(accionesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void solicitarOtroMonto() {
        String montoStr = JOptionPane.showInputDialog(this, "Ingrese el monto a retirar:");
        if (montoStr != null && !montoStr.isEmpty()) {
            try {
                double monto = Double.parseDouble(montoStr);
                procesarRetiro(monto);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Monto inválido.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Monto no ingresado.");
        }
    }

    private void procesarRetiro(double monto) {
        double saldoActual = SuperClaseSaldo.getSaldo();
        if (monto <= saldoActual) {
            SuperClaseSaldo.actualizarSaldo(-monto);
            JOptionPane.showMessageDialog(this, "Retiro de $" + monto + " realizado con éxito.");
            volverAlMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Fondos insuficientes.");
        }
    }

    private void mostrarCambiarNIP() {
        menuPanel.setVisible(false); // Ocultar el menú
        accionesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        accionesPanel.add(new JLabel("NIP Actual:"), gbc);

        nipActualField = new JTextField(10);
        gbc.gridx = 1;
        accionesPanel.add(nipActualField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        accionesPanel.add(new JLabel("Nuevo NIP:"), gbc);

        nuevoNIPField = new JTextField(10);
        gbc.gridx = 1;
        accionesPanel.add(nuevoNIPField, gbc);

        cambiarNIPButton = new JButton("Cambiar NIP");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        accionesPanel.add(cambiarNIPButton, gbc);

        JButton volverButton = new JButton("Volver al Menú");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        accionesPanel.add(volverButton, gbc);

        cambiarNIPButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cambiarNIPActionPerformed(e);
            }
        });

        volverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAlMenu();
            }
        });

        add(accionesPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void cambiarNIPActionPerformed(ActionEvent e) {
        String nipActualIngresado = nipActualField.getText();
        String nuevoNIP = nuevoNIPField.getText();

        if (!nip.equals(nipActualIngresado)) {
            JOptionPane.showMessageDialog(this, "El NIP actual ingresado es incorrecto.");
            return;
        }

        if (nuevoNIP.isEmpty() || nuevoNIP.length() != 4) {
            JOptionPane.showMessageDialog(this, "El nuevo NIP debe tener 4 dígitos.");
            return;
        }

        nip = nuevoNIP;
        guardarNIP();
        JOptionPane.showMessageDialog(this, "NIP cambiado exitosamente.");
        volverAlMenu();
    }

    private void guardarNIP() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("nip.txt"))) {
            writer.write(nip);
        } catch (IOException e) {
            System.err.println("Error al guardar el NIP: " + e.getMessage());
        }
    }

    private void depositarActionPerformed(ActionEvent e) {
        try {
            double monto = Double.parseDouble(montoDepositoField.getText());
            SuperClaseSaldo.actualizarSaldo(monto);
            JOptionPane.showMessageDialog(this, "Depósito de $" + monto + " realizado con éxito.");
            volverAlMenu();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Monto inválido.");
        }
    }

    private void volverAlMenu() {
        remove(accionesPanel);
        menuPanel.setVisible(true);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CajeroGUI();
            }
        });
    }
}
