import javax.swing.*;
import javax.swing.border.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

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
        root.add(tabs(), BorderLayout.CENTER);
        root.add(bottom(), BorderLayout.SOUTH);
    }

    private JPanel top(){
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(PN);
        p.setBorder(border(""));
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

    private JTabbedPane tabs(){
        JTabbedPane t = new JTabbedPane();
        t.setFont(new Font("SansSerif", Font.BOLD, 16));
        t.addTab("Зашифрувати", mode(true));
        t.addTab("Розшифрувати", mode(false));
        return t;
    }

    private JPanel mode(boolean enc) {
        JPanel wrap=new JPanel(new BorderLayout());
        wrap.setBackground(BG);
        wrap.setBorder(new EmptyBorder(8, 0, 0, 0));
        JPanel box=new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
        box.setBackground(PN);
        box.setBorder(border("Параметри"));
        JTextField in = new JTextField();
        JTextField out = new JTextField();
        JTextField key = new JTextField();
        JTextField iv = new JTextField();
        box.add(Box.createVerticalStrut(12));
        box.add(row("Вхідний файл:",in,false));
        box.add(Box.createVerticalStrut(12));
        box.add(row("Вихідний файл:", out, true));
        box.add(Box.createVerticalStrut(12));
        box.add(row("Файл ключа:", key, false));
        box.add(Box.createVerticalStrut(12));
        box.add(row("Файл IV:",iv,false));
        box.add(Box.createVerticalStrut(18));
        JButton b = btn(enc ? "Зашифрувати файл" : "Розшифрувати файл");
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setPreferredSize(new Dimension(260, 44));
        b.setMaximumSize(new Dimension(260, 44));
        b.setBackground(new Color(244, 143, 177));
        b.setForeground(Color.WHITE);
        b.setFont(new Font("SansSerif", Font.BOLD, 15));
        b.setBorder(new LineBorder(new Color(194, 24, 91), 1, true));
        b.addActionListener(e -> {
            st.setText("Статус: Виконується...");
            if (in.getText().isEmpty() ||out.getText().isEmpty() || key.getText().isEmpty() || iv.getText().isEmpty()){
                st.setText("Статус: Заповніть усі поля");
                return;
            }
            try{
                byte[] inputData = Files.readAllBytes(Paths.get(in.getText()));
                byte[] keyData = Files.readAllBytes(Paths.get(key.getText()));
                byte[] ivData =Files.readAllBytes(Paths.get(iv.getText()));
                byte[] res;
                if (enc){
                    res=MugiCipher.encryptData(inputData, keyData, ivData);
                }
                else {
                    res=MugiCipher.decryptData(inputData,keyData, ivData);
                }
                Files.write(Paths.get(out.getText()),res);
                st.setText(enc ? "Статус: Файл успішно зашифровано" : "Статус: Файл успішно розшифровано");
            }
            catch (Exception ex) {
                st.setText("Статус: Помилка");
            }
        });
        box.add(Box.createVerticalStrut(14));
        wrap.add(box,BorderLayout.CENTER);
        return wrap;
    }

    private JPanel row(String txt, JTextField f,boolean save){
        JPanel p = new JPanel();
        p.setBackground(PN);
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setBorder(new EmptyBorder(0, 16, 0, 16));
        JLabel l =new JLabel(txt);
        l.setFont(new Font("SansSerif", Font.PLAIN, 16));
        l.setForeground(TX);
        l.setPreferredSize(new Dimension(170, 34));
        l.setMinimumSize(new Dimension(170, 34));
        l.setMaximumSize(new Dimension(170, 34));
        f.setFont(new Font("SansSerif", Font.PLAIN, 15));
        f.setPreferredSize(new Dimension(520, 34));
        f.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        JButton b = btn("Огляд...");
        b.setPreferredSize(new Dimension(120, 34));
        b.setMinimumSize(new Dimension(120, 34));
        b.setMaximumSize(new Dimension(120, 34));
        b.addActionListener(e -> pick(f, save));
        p.add(l);
        p.add(Box.createHorizontalStrut(12));
        p.add(f);
        p.add(Box.createHorizontalStrut(12));
        p.add(b);
        return p;
    }

    private JPanel bottom() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(PN);
        p.setBorder(border(""));
        st = new JLabel("Статус: Готово до роботи");
        st.setFont(new Font("SansSerif", Font.PLAIN, 16));
        st.setForeground(TX);
        st.setBorder(new EmptyBorder(10,12,10,12));
        p.add(st, BorderLayout.CENTER);
        return p;
    }

    private JButton btn(String txt) {
        JButton b = new JButton(txt);
        b.setFocusPainted(false);
        b.setBackground(BT);
        b.setForeground(TX);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBorder(new EmptyBorder(8, 14, 8, 14));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(BH);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(BT);
            }
        });
        return b;
    }

    private void pick(JTextField f, boolean save) {
        JFileChooser c = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        c.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int r = save ? c.showSaveDialog(this) : c.showOpenDialog(this);
        if (r==JFileChooser.APPROVE_OPTION) {
            File file = c.getSelectedFile();
            f.setText(file.getAbsolutePath());
        }
    }

    private Border border(String txt) {
        Border line = new LineBorder(new Color(233, 180, 200), 1, true);
        Border pad = new EmptyBorder(10, 10, 10, 10);
        Border all =new CompoundBorder(line, pad);
        if (txt == null || txt.isBlank()) {
            return all;
        }
        TitledBorder t = new TitledBorder(all, txt);
        t.setTitleFont(new Font("SansSerif", Font.BOLD, 16));
        t.setTitleColor(TT);
        return t;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception e) {
            }
            new Interface().setVisible(true);
        });
    }



}