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
        root.add(top(), BorderLayout.NORTH);
    }

    private JPanel top(){
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(PN);
        JLabel t =new JLabel("  MUGI File Cipher  ", SwingConstants.CENTER);
        t.setFont(new Font("Serif", Font.BOLD, 30));
        t.setForeground(TT);
        JLabel s = new JLabel("Шифрування та розшифрування файлів з використанням потокового шифру MUGI", SwingConstants.CENTER);
        s.setFont(new Font("SansSerif", Font.PLAIN, 18));
        s.setForeground(TX);
        JTextArea a = new JTextArea("Як користуватися програмою:\nОберіть потрібний режим роботи, вкажіть вхідний файл, файл для збереження результату,\nфайл ключа та файл ініціалізаційного вектора (IV), а потім натисніть кнопку виконання.\nПісля успішного завершення програма повідомить про результат.");
        a.setEditable(false);
        a.setFocusable(false);
        a.setOpaque(false);
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setFont(new Font("SansSerif", Font.PLAIN, 15));
        a.setForeground(TX);
        a.setBorder(new EmptyBorder(6, 10, 6, 10));
        p.add(t,BorderLayout.NORTH);
        p.add(s,BorderLayout.CENTER);
        p.add(a,BorderLayout.SOUTH);
        return p;
    }

}