import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;

public class Interface extends JFrame {
    private static final Color BG = new Color(255, 240, 245);
    private static final Color PN = new Color(255, 228, 236);
    private static final Color BT = new Color(248, 187, 208);
    private static final Color BH = new Color(244, 143, 177);
    private static final Color TT = new Color(194, 24, 91);
    private static final Color TX = new Color(51, 51, 51);
    private JLabel st;

    public Interface() {
        setTitle("MUGI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel root = new JPanel(new BorderLayout(12, 12));
        root.setBackground(BG);
        root.setBorder(new EmptyBorder(12, 12, 12, 12));
        setContentPane(root);
    }

}