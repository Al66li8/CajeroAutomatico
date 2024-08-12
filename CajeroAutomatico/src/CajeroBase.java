package CajeroBase;

public class CajeroBase {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CajeroGUI().setVisible(true);
            }
        });
    }
}

