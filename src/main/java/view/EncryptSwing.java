package view;

import controller.*;
import model.*;

import javax.imageio.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.plaf.basic.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.*;
import java.io.*;
import java.net.URL;

public class EncryptSwing {
    private final Symmetric symmetricModel;
    private final Hash hashModel;
    private final Asymmetric asymmetricModel;
    private final Vigenere vigenereModel;
    private final HillCipher hillModel;
    private JPanel panelText;
    public JTextField outputKey;
    private JTextArea textArea;
    private JTextArea textArea1;
    public JTextField outputHash;
    private JTextField sourceFile;
    public JTextField outputKeyFile;
    private JTextField sourceFileInFile;
    private JTextField destFileInFile;
    private JComboBox<String> dropdown;
    private JButton buttonRun;
    public JFrame frame;
    private JButton buttonRunFile;
    private JComboBox<String> dropdownFile;
    private JComboBox<String> dropdown1;
    private JTextField outputKeyPublic;
    private JLabel publicKey;
    private JLabel imageCopykeyPublic;
    private ImageIcon iconCopyInputText;
    private ImageIcon iconCopyOutputText;
    private ImageIcon iconCopyPublicKey;
    private ImageIcon iconCopyKey;
    private JLabel imageCopyKeyInFile;
    private ImageIcon iconCopyKeyInFile;
    private JTextField outputPublicKeyFile;
    private JLabel imageCopyPubliKeyInFile;
    private ImageIcon iconCopyPublicKeyInFile;
    private JLabel creKey;
    private JLabel publicKeyFile;
    private JLabel creKeyFile;

    ActionListener acHash = new HashControl(this);
    ActionListener acText = new TextControl(this);
    ActionListener acFile = new FileControl(this);
    private JPanel paneMain;
    private JLabel imageCopy;
    private ImageIcon iconCopy;
    private ImageIcon iconLoad;
    private JLabel imageLoadFile;
    private JLabel imageLoad;


    public EncryptSwing() {
        this.symmetricModel = new Symmetric();
        this.hashModel = new Hash();
        this.asymmetricModel = new Asymmetric();
        this.vigenereModel = new Vigenere();
        this.hillModel = new HillCipher();
    }

    public void createAndShowGUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Tạo cửa sổ
        frame = new JFrame("Security System");
        frame.setSize(900, 600);

        // Set icon cho JFrame
        Image iconSwing = new ImageIcon(EncryptSwing.class.getResource("/image/lock.png").getPath()).getImage();
        ImageIcon imageIcon = new ImageIcon(iconSwing.getScaledInstance(700, 700, Image.SCALE_SMOOTH));
        frame.setIconImage(imageIcon.getImage());

        // Header
        JLabel headerLabel = new JLabel("SECURITY SYSTEM", JLabel.CENTER);
        headerLabel.setFont(new Font("Serif", Font.BOLD, 26));
        headerLabel.setOpaque(true);
        headerLabel.setBackground(Color.decode("#B1DAF2"));
        headerLabel.setPreferredSize(new Dimension(900, 70)); // Set kích thước cho JLabel

        JLabel headerLabel1 = new JLabel();
        headerLabel1.setOpaque(true);
        headerLabel1.setBackground(Color.decode("#1B6B9A"));
        headerLabel1.setBounds(0, 70, 900, 20);

        JLabel headerLabel2 = new JLabel();
        headerLabel2.setOpaque(true);
        headerLabel2.setBackground(Color.decode("#B1DAF2"));
        headerLabel2.setBounds(0, 90, 900, 13);

        JLabel headerLabel3 = new JLabel();
        headerLabel3.setOpaque(true);
        headerLabel3.setBackground(Color.decode("#1B6B9A"));
        headerLabel3.setBounds(0, 103, 900, 7);

        JLabel imageLabel = new JLabel();
        ImageIcon icon = createResizedImageIcon("image/logo.png", 115, 115);
        imageLabel.setIcon(icon);
        imageLabel.setBounds(40, -10, 115, 115); // Đặt kích thước và vị trí cho hình ảnh


        JLabel imageLabel1 = new JLabel();
        ImageIcon icon1 = createResizedImageIcon("image/lock.png", 50, 50);
        imageLabel1.setIcon(icon1);
        imageLabel1.setBounds(75, 25, 50, 50); // Đặt kích thước và vị trí cho hình ảnh

        frame.add(imageLabel1);
        frame.add(imageLabel);
        frame.add(headerLabel, BorderLayout.PAGE_START);
        frame.add(headerLabel1);
        frame.add(headerLabel2);
        frame.add(headerLabel3);


        // Body
        JPanel body = new JPanel(null);
        body.setPreferredSize(new Dimension(900, 650)); // Set kích thước cho JPanel
        body.setBackground(Color.white);

        JLabel bodyLine = new JLabel();
        bodyLine.setOpaque(true);
        bodyLine.setBackground(Color.decode("#1B6B9A"));
        bodyLine.setBounds(200, 40, 7, 500);
        body.add(bodyLine);

        //main
        paneMain = new JPanel(null);
        paneMain.setBackground(Color.white);
        paneMain.setBounds(200, 40, 700, 500);

        JLabel imageMain = new JLabel();
        iconCopyKey = createResizedImageIcon("image/main.png", 700, 500);
        imageMain.setIcon(iconCopyKey);
        imageMain.setBounds(0, 0, 700, 500);
        paneMain.add(imageMain);
        body.add(paneMain);

        // content Text
        panelText = new JPanel(null);
        panelText.setBackground(Color.white);
        panelText.setBounds(200, 40, 700, 500);

        // Menu algorithm
        JLabel selectAlgorithm = new JLabel("Algorithm:");
        selectAlgorithm.setBounds(20, 15, 100, 20);
        selectAlgorithm.setFont(new Font("Serif", Font.BOLD, 15));
        panelText.add(selectAlgorithm);

        String[] items = {"Choose", "DES", "3-DES", "Blowfish", "Twofish", "IDEA", "AES-128", "AES-192", "AES-256", "CAST6-128", "CAST6-256", "Serpent-128", "Serpent-256", "Camellia-128", "Camellia-256", "RC4-128", "RC5-128", "RC5-256", "RC6-128", "RC6-256", "RSA", "Vigenère", "Hill-2x2"};
        dropdown = new JComboBox<>(items) {
            @Override
            public void updateUI() {
                setUI(new BasicComboBoxUI() {
                    @Override
                    protected JButton createArrowButton() {
                        JButton button = new JButton("▼") {
                            @Override
                            public void updateUI() {
                                setUI(new BasicButtonUI() {
                                    @Override
                                    public void installUI(JComponent c) {
                                        super.installUI(c);
                                        AbstractButton button = (AbstractButton) c;
                                        button.setContentAreaFilled(false);
                                        button.setOpaque(true);
                                        button.setBorderPainted(false);
                                        button.addMouseListener(new MouseAdapter() {
                                            public void mouseEntered(MouseEvent e) {
                                                button.setForeground(Color.WHITE);
                                            }

                                            public void mouseExited(MouseEvent e) {
                                                button.setForeground(Color.BLACK);
                                            }
                                        });
                                    }

                                    @Override
                                    public void paint(Graphics g, JComponent c) {
                                        AbstractButton b = (AbstractButton) c;
                                        ButtonModel model = b.getModel();
                                        if (model.isPressed()) {
                                            g.setColor(Color.decode("#1B6B9A"));
                                        } else {
                                            g.setColor(Color.decode("#7AB8DD"));
                                        }
                                        super.paint(g, c);
                                    }
                                });
                            }
                        };
                        button.setBackground(Color.decode("#7AB8DD"));
                        button.setBorder(BorderFactory.createEmptyBorder());
                        return button;
                    }

                    @Override
                    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                        g.setColor(Color.WHITE);
                        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    }
                });
            }
        };

        dropdown.setBounds(100, 15, 100, 20);
        dropdown.setBackground(Color.decode("#7AB8DD"));
        dropdown.setForeground(Color.BLACK);
        dropdown.setFont(new Font("Serif", Font.PLAIN, 13));
        dropdown.setFocusable(false);

        dropdown.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String selected = (String) comboBox.getSelectedItem();
            if ("RSA".equalsIgnoreCase(selected)) {
                publicKey.setVisible(true);
                outputKeyPublic.setVisible(true);
                imageCopykeyPublic.setVisible(true);
                creKey.setText("Private key:");
                outputKey.setBounds(340, 15, 250, 20);
            } else {
                publicKey.setVisible(false);
                outputKeyPublic.setVisible(false);
                imageCopykeyPublic.setVisible(false);
                creKey.setText("Key:");
                outputKey.setBounds(300, 15, 290, 20);
            }
        });
        dropdown.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    label.setBackground(Color.decode("#7AB8DD"));
                } else {
                    label.setBackground(Color.WHITE);
                }
                return label;
            }
        });
        panelText.add(dropdown);

        //key text
        creKey = new JLabel("Key:");
        creKey.setBounds(250, 15, 100, 25);
        creKey.setFont(new Font("Serif", Font.BOLD, 15));
        panelText.add(creKey);

        outputKey = new JTextField();
        outputKey.setBounds(310, 15, 250, 20);
        outputKey.setFont(new Font("Serif", Font.PLAIN, 13));
        outputKey.setBackground(Color.WHITE);
        panelText.add(outputKey);

        //button copy key
        JLabel imageCopykey = new JLabel();
        iconCopyKey = createResizedImageIcon("image/icon-copy.png", 20, 20);
        imageCopykey.setIcon(iconCopyKey);
        imageCopykey.setBounds(600, 16, 20, 20);
        imageCopykey.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = outputKey.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyKey = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopykey.setBounds(600, 16, 16, 16);
                imageCopykey.setIcon(iconCopyKey);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyKey = createResizedImageIcon("image/icon-copy.png", 20, 20);
                    imageCopykey.setBounds(600, 16, 20, 20);
                    imageCopykey.setIcon(iconCopyKey);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelText.add(imageCopykey);

        // button load key in text
        imageLoad = new JLabel();
        iconLoad = createResizedImageIcon("image/icon-reload.png", 16, 16);
        imageLoad.setIcon(iconLoad);
        imageLoad.setBounds(630, 16, 16, 16);
        panelText.add(imageLoad);
        imageLoad.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runCreateKey();
            }
        });


        // Input
        JLabel textBox1 = new JLabel("Input:");
        textBox1.setBounds(20, 25, 100, 50);
        textBox1.setFont(new Font("Serif", Font.BOLD, 15));
        panelText.add(textBox1);

        JPanel panelTextArea1 = new JPanel();
        panelTextArea1.setBackground(Color.decode("#C5DEFF")); // Màu nền của JPanel
        panelTextArea1.setBounds(20, 65, 650, 137);

        textArea = new JTextArea(7, 80);
        textArea.setBackground(Color.decode("#EAF7FF")); // Màu nền của JTextArea
        textArea.setLineWrap(true); // Cho phép tự động xuống dòng
        textArea.setWrapStyleWord(true); // Cho phép xuống dòng theo từng từ

        Border border = BorderFactory.createEmptyBorder();
        textArea.setBorder(border);

        JScrollPane scrollPane = new JScrollPane(textArea); // Tạo thanh cuộn dọc cho JTextArea
        scrollPane.setBorder(border);
        panelTextArea1.add(scrollPane); // Thêm scrollPane vào panel

        //button copy input text
        JLabel imageCopyinText = new JLabel();
        iconCopyInputText = createResizedImageIcon("image/icon-copy.png", 20, 20);
        imageCopyinText.setIcon(iconCopyInputText);
        imageCopyinText.setBounds(645, 42, 20, 20);
        imageCopyinText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = textArea.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyInputText = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopyinText.setBounds(645, 42, 16, 16);
                imageCopyinText.setIcon(iconCopyInputText);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyInputText = createResizedImageIcon("image/icon-copy.png", 20, 20);
                    imageCopyinText.setBounds(645, 42, 20, 20);
                    imageCopyinText.setIcon(iconCopyInputText);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelText.add(imageCopyinText);

        // Box text 2
        JLabel textBox2 = new JLabel("Output:");
        textBox2.setBounds(20, 225, 100, 50);
        textBox2.setFont(new Font("Serif", Font.BOLD, 15));
        panelText.add(textBox2);

        // Button Run
        buttonRun = new JButton("RUN") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonRun.setBounds(20, 210, 110, 30);
        buttonRun.setFont(new Font("Serif", Font.BOLD, 15));
        buttonRun.setForeground(Color.BLACK);

        buttonRun.addActionListener(acText);

        buttonRun.addActionListener(acHash);
        panelText.add(buttonRun);

        // Button delete
        JButton buttonDelete = new JButton("Delete") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonDelete.setBounds(140, 210, 90, 30);
        buttonDelete.setFont(new Font("Serif", Font.BOLD, 15));
        buttonDelete.setForeground(Color.BLACK);
        buttonDelete.addActionListener(e -> textArea.setText(""));
        panelText.add(buttonDelete);

        // Output
        JPanel panelTextArea2 = new JPanel();
        panelTextArea2.setBackground(Color.decode("#C5DEFF")); // Màu nền của JPanel
        panelTextArea2.setBounds(20, 265, 650, 137);

        textArea1 = new JTextArea(7, 80);
        textArea1.setBackground(Color.decode("#EAF7FF")); // Màu nền của JTextArea
        textArea1.setLineWrap(true); // Cho phép tự động xuống dòng
        textArea1.setWrapStyleWord(true); // Cho phép xuống dòng theo từng từ
        textArea1.setEditable(false);

        Border border1 = BorderFactory.createEmptyBorder();
        textArea1.setBorder(border1);

        JScrollPane scrollPane1 = new JScrollPane(textArea1); // Tạo thanh cuộn dọc cho JTextArea
        scrollPane1.setBorder(border1);
        panelTextArea2.add(scrollPane1); // Thêm scrollPane vào panel

        //button copy output text
        JLabel imageCopyinText1 = new JLabel();
        iconCopyOutputText = createResizedImageIcon("image/icon-copy.png", 20, 20);
        imageCopyinText1.setIcon(iconCopyOutputText);
        imageCopyinText1.setBounds(645, 242, 20, 20);
        imageCopyinText1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = textArea1.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyOutputText = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopyinText1.setBounds(645, 242, 16, 16);
                imageCopyinText1.setIcon(iconCopyOutputText);

                // Set a timer to revert back to copy icon after 1 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyOutputText = createResizedImageIcon("image/icon-copy.png", 20, 20);
                    imageCopyinText1.setBounds(645, 242, 20, 20);
                    imageCopyinText1.setIcon(iconCopyOutputText);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelText.add(imageCopyinText1);

        //public key
        publicKey = new JLabel("Public key:");
        publicKey.setBounds(20, 415, 100, 25);
        publicKey.setFont(new Font("Serif", Font.BOLD, 15));
        panelText.add(publicKey);

        outputKeyPublic = new JTextField();
        outputKeyPublic.setBounds(100, 415, 530, 20);
        outputKeyPublic.setFont(new Font("Serif", Font.PLAIN, 13));
        outputKeyPublic.setBackground(Color.WHITE);
        panelText.add(outputKeyPublic);

        //button copy public key
        imageCopykeyPublic = new JLabel();
        iconCopyPublicKey = createResizedImageIcon("image/icon-copy.png", 20, 20);
        imageCopykeyPublic.setIcon(iconCopyPublicKey);
        imageCopykeyPublic.setBounds(645, 415, 20, 20);
        imageCopykeyPublic.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = outputKeyPublic.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyPublicKey = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopykeyPublic.setBounds(645, 415, 16, 16);
                imageCopykeyPublic.setIcon(iconCopyPublicKey);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyPublicKey = createResizedImageIcon("image/icon-copy.png", 20, 20);
                    imageCopykeyPublic.setBounds(645, 415, 20, 20);
                    imageCopykeyPublic.setIcon(iconCopyPublicKey);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelText.add(imageCopykeyPublic);


        // content hash
        JPanel panelHash = new JPanel(null);
        panelHash.setBackground(Color.white);
        panelHash.setBounds(200, 40, 700, 500);

        JLabel imageFinger = new JLabel();
        ImageIcon iconFinger = createResizedImageIcon("image/finger.png", 500, 500);
        imageFinger.setIcon(iconFinger);
        imageFinger.setBounds(200, 0, 500, 500); // Đặt kích thước và vị trí cho hình ảnh

        JLabel selectAlgorithmHash = new JLabel("Algorithm:");
        selectAlgorithmHash.setBounds(30, 35, 100, 20);
        selectAlgorithmHash.setFont(new Font("Serif", Font.BOLD, 15));
        panelHash.add(selectAlgorithmHash);

        String[] items1 = {"Choose", "md2", "md4", "md5", "SHA-1", "SHA-224", "SHA-256", "SHA-384", "SHA-512"};
        dropdown1 = new JComboBox<>(items1) {
            @Override
            public void updateUI() {
                setUI(new BasicComboBoxUI() {
                    @Override
                    protected JButton createArrowButton() {
                        JButton button = new JButton("▼") {
                            @Override
                            public void updateUI() {
                                setUI(new BasicButtonUI() {
                                    @Override
                                    public void installUI(JComponent c) {
                                        super.installUI(c);
                                        AbstractButton button = (AbstractButton) c;
                                        button.setContentAreaFilled(false);
                                        button.setOpaque(true);
                                        button.setBorderPainted(false);
                                        button.addMouseListener(new MouseAdapter() {
                                            public void mouseEntered(MouseEvent e) {
                                                button.setForeground(Color.WHITE);
                                            }

                                            public void mouseExited(MouseEvent e) {
                                                button.setForeground(Color.BLACK);
                                            }
                                        });
                                    }

                                    @Override
                                    public void paint(Graphics g, JComponent c) {
                                        AbstractButton b = (AbstractButton) c;
                                        ButtonModel model = b.getModel();
                                        if (model.isPressed()) {
                                            g.setColor(Color.decode("#1B6B9A"));
                                        } else {
                                            g.setColor(Color.decode("#7AB8DD"));
                                        }
                                        super.paint(g, c);
                                    }
                                });
                            }
                        };
                        button.setBackground(Color.decode("#7AB8DD"));
                        button.setBorder(BorderFactory.createEmptyBorder());
                        return button;
                    }

                    @Override
                    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                        g.setColor(Color.WHITE);
                        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    }
                });
            }
        };
        dropdown1.setBounds(115, 35, 100, 20);
        dropdown1.setBackground(Color.decode("#7AB8DD"));
        dropdown1.setForeground(Color.BLACK);
        dropdown1.setFont(new Font("Serif", Font.PLAIN, 13));
        dropdown1.setFocusable(false);
        dropdown1.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    label.setBackground(Color.decode("#7AB8DD"));
                } else {
                    label.setBackground(Color.WHITE);
                }
                return label;
            }
        });
        panelHash.add(dropdown1);

        // create hash
        JLabel creHash = new JLabel("Hash:");
        creHash.setBounds(30, 80, 100, 25);
        creHash.setFont(new Font("Serif", Font.BOLD, 15));
        panelHash.add(creHash);

        outputHash = new JTextField();
        outputHash.setBounds(110, 80, 450, 25);
        outputHash.setFont(new Font("Serif", Font.PLAIN, 15));
        outputHash.setBackground(Color.WHITE);
        outputHash.setEditable(false);
        panelHash.add(outputHash);

        //icon copy
        imageCopy = new JLabel();
        iconCopy = createResizedImageIcon("image/icon-copy.png", 25, 25);
        imageCopy.setIcon(iconCopy);
        imageCopy.setBounds(570, 80, 25, 25); // Đặt kích thước và vị trí cho hình ảnh
        imageCopy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = outputHash.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopy = createResizedImageIcon("image/icon-check.png", 20, 20);
                imageCopy.setBounds(572, 80, 20, 20);
                imageCopy.setIcon(iconCopy);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopy = createResizedImageIcon("image/icon-copy.png", 25, 25);
                    imageCopy.setBounds(570, 80, 25, 25);
                    imageCopy.setIcon(iconCopy);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelHash.add(imageCopy);

        // choose file
        JLabel chooseFile = new JLabel("Source file:");
        chooseFile.setBounds(30, 125, 100, 25);
        chooseFile.setFont(new Font("Serif", Font.BOLD, 15));
        panelHash.add(chooseFile);

        sourceFile = new JTextField();
        sourceFile.setBounds(110, 125, 450, 25);
        sourceFile.setFont(new Font("Serif", Font.PLAIN, 15));
        sourceFile.setBackground(Color.WHITE);
        panelHash.add(sourceFile);

        //icon choose file
        JLabel imageFile = new JLabel();
        ImageIcon iconFile = createResizedImageIcon("image/icon-file.png", 25, 25);
        imageFile.setIcon(iconFile);
        imageFile.setBounds(570, 125, 25, 25); // Đặt kích thước và vị trí cho hình ảnh
        imageFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create and show JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(panelHash); // 'panelFile' is where set JFileChooser

                // Check choose file
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedPath = selectedFile.getAbsolutePath();
                    sourceFile.setText(selectedPath);
                }
            }
        });
        panelHash.add(imageFile);

        // Button run
        JButton buttonRunHash = new JButton("Run") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonRunHash.setBounds(30, 185, 120, 25);
        buttonRunHash.setFont(new Font("Serif", Font.BOLD, 15));
        buttonRunHash.setForeground(Color.BLACK);

        buttonRunHash.addActionListener(acHash);

        panelHash.add(buttonRunHash);

        //button delete in hash
        JButton buttonDeleteHash = new JButton("Delete All") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonDeleteHash.setBounds(160, 185, 115, 25);
        buttonDeleteHash.setFont(new Font("Serif", Font.BOLD, 15));
        buttonDeleteHash.setForeground(Color.BLACK);
        buttonDeleteHash.addActionListener(e -> {
            sourceFile.setText("");
            outputHash.setText("");
        });
        panelHash.add(buttonDeleteHash);


        //Content encript and decript file
        JPanel panelFile = new JPanel(null);
        panelFile.setBackground(Color.white);
        panelFile.setBounds(200, 40, 700, 500);

        JLabel selectAlgorithmFile = new JLabel("Algorithm:");
        selectAlgorithmFile.setBounds(25, 60, 100, 20);
        selectAlgorithmFile.setFont(new Font("Serif", Font.BOLD, 15));
        panelFile.add(selectAlgorithmFile);

        String[] itemsFile = {"Choose", "DES", "3-DES", "Blowfish", "Twofish", "IDEA", "AES-128", "AES-192", "AES-256", "CAST6-128", "CAST6-256", "Serpent-128", "Serpent-256", "Camellia-128", "Camellia-256", "RC4-128", "RC5-128", "RC5-256", "RC6-128", "RC6-256", "RSA"};
        dropdownFile = new JComboBox<>(itemsFile) {
            @Override
            public void updateUI() {
                setUI(new BasicComboBoxUI() {
                    @Override
                    protected JButton createArrowButton() {
                        JButton button = new JButton("▼") {
                            @Override
                            public void updateUI() {
                                setUI(new BasicButtonUI() {
                                    @Override
                                    public void installUI(JComponent c) {
                                        super.installUI(c);
                                        AbstractButton button = (AbstractButton) c;
                                        button.setContentAreaFilled(false);
                                        button.setOpaque(true);
                                        button.setBorderPainted(false);
                                        button.addMouseListener(new MouseAdapter() {
                                            public void mouseEntered(MouseEvent e) {
                                                button.setForeground(Color.WHITE);
                                            }

                                            public void mouseExited(MouseEvent e) {
                                                button.setForeground(Color.BLACK);
                                            }
                                        });
                                    }

                                    @Override
                                    public void paint(Graphics g, JComponent c) {
                                        AbstractButton b = (AbstractButton) c;
                                        ButtonModel model = b.getModel();
                                        if (model.isPressed()) {
                                            g.setColor(Color.decode("#1B6B9A"));
                                        } else {
                                            g.setColor(Color.decode("#7AB8DD"));
                                        }
                                        super.paint(g, c);
                                    }
                                });
                            }
                        };
                        button.setBackground(Color.decode("#7AB8DD"));
                        button.setBorder(BorderFactory.createEmptyBorder());
                        return button;
                    }

                    @Override
                    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                        g.setColor(Color.WHITE);
                        g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                    }
                });
            }
        };

        dropdownFile.setBounds(110, 58, 100, 20);
        dropdownFile.setBackground(Color.decode("#7AB8DD"));
        dropdownFile.setForeground(Color.BLACK);
        dropdownFile.setFont(new Font("Serif", Font.PLAIN, 13));
        dropdownFile.setFocusable(false);
        dropdownFile.addActionListener(e -> {
            JComboBox comboBox = (JComboBox) e.getSource();
            String selected = (String) comboBox.getSelectedItem();
            if ("RSA".equalsIgnoreCase(selected)) {
                publicKeyFile.setVisible(true);
                outputPublicKeyFile.setVisible(true);
                imageCopyPubliKeyInFile.setVisible(true);
                creKeyFile.setText("Private key:");
            } else {
                publicKeyFile.setVisible(false);
                outputPublicKeyFile.setVisible(false);
                imageCopyPubliKeyInFile.setVisible(false);
                creKeyFile.setText("Key:");
            }
        });

        dropdownFile.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    label.setBackground(Color.decode("#7AB8DD"));
                } else {
                    label.setBackground(Color.WHITE);
                }
                return label;
            }
        });
        panelFile.add(dropdownFile);


        // public key in file
        publicKeyFile = new JLabel("Public key:");
        publicKeyFile.setBounds(25, 275, 100, 25);
        publicKeyFile.setFont(new Font("Serif", Font.BOLD, 15));
        panelFile.add(publicKeyFile);

        outputPublicKeyFile = new JTextField();
        outputPublicKeyFile.setBounds(110, 275, 450, 25);
        outputPublicKeyFile.setFont(new Font("Serif", Font.PLAIN, 15));
        outputPublicKeyFile.setBackground(Color.WHITE);
        panelFile.add(outputPublicKeyFile);

        //Button copy key in file
        imageCopyPubliKeyInFile = new JLabel();
        iconCopyPublicKeyInFile = createResizedImageIcon("image/icon-copy.png", 25, 25);
        imageCopyPubliKeyInFile.setIcon(iconCopyPublicKeyInFile);
        imageCopyPubliKeyInFile.setBounds(570, 277, 25, 25);
        imageCopyPubliKeyInFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = outputPublicKeyFile.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyPublicKeyInFile = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopyPubliKeyInFile.setBounds(573, 283, 16, 16);
                imageCopyPubliKeyInFile.setIcon(iconCopyPublicKeyInFile);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyPublicKeyInFile = createResizedImageIcon("image/icon-copy.png", 25, 25);
                    imageCopyPubliKeyInFile.setBounds(570, 287, 25, 25);
                    imageCopyPubliKeyInFile.setIcon(iconCopyPublicKeyInFile);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelFile.add(imageCopyPubliKeyInFile);

        //button load key in file
        imageLoadFile = new JLabel();
        imageLoadFile.setIcon(iconLoad);
        imageLoadFile.setBounds(600, 100, 16, 16);
        panelText.add(imageLoad);
        imageLoadFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                runCreateKeyFile();
            }
        });
        panelFile.add(imageLoadFile);

        // Create key
        creKeyFile = new JLabel("Key:");
        creKeyFile.setBounds(25, 95, 100, 25);
        creKeyFile.setFont(new Font("Serif", Font.BOLD, 15));
        panelFile.add(creKeyFile);

        outputKeyFile = new JTextField();
        outputKeyFile.setBounds(110, 95, 450, 25);
        outputKeyFile.setFont(new Font("Serif", Font.PLAIN, 15));
        outputKeyFile.setBackground(Color.WHITE);
        panelFile.add(outputKeyFile);

        //Button copy key in file
        imageCopyKeyInFile = new JLabel();
        iconCopyKeyInFile = createResizedImageIcon("image/icon-copy.png", 25, 25);
        imageCopyKeyInFile.setIcon(iconCopyKeyInFile);
        imageCopyKeyInFile.setBounds(570, 97, 25, 25);
        imageCopyKeyInFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Copy text to clipboard
                String text = outputKeyFile.getText();
                StringSelection stringSelection = new StringSelection(text);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringSelection, null);

                // Change icon to check mark
                iconCopyKeyInFile = createResizedImageIcon("image/icon-check.png", 16, 16);
                imageCopyKeyInFile.setBounds(573, 103, 16, 16);
                imageCopyKeyInFile.setIcon(iconCopyKeyInFile);

                // Set a timer to revert back to copy icon after 3 seconds
                Timer timer = new Timer(1000, evt -> {
                    // Change icon back to copy icon
                    iconCopyKeyInFile = createResizedImageIcon("image/icon-copy.png", 25, 25);
                    imageCopyKeyInFile.setBounds(570, 97, 25, 25);
                    imageCopyKeyInFile.setIcon(iconCopyKeyInFile);
                });
                timer.setRepeats(false); // Only execute once
                timer.start(); // Start the timer
            }
        });
        panelFile.add(imageCopyKeyInFile);

        // choose source file
        JLabel chooseFileInFile1 = new JLabel("Source file:");
        chooseFileInFile1.setBounds(25, 140, 100, 25);
        chooseFileInFile1.setFont(new Font("Serif", Font.BOLD, 15));
        panelFile.add(chooseFileInFile1);

        sourceFileInFile = new JTextField();
        sourceFileInFile.setBounds(110, 140, 450, 25);
        sourceFileInFile.setFont(new Font("Serif", Font.PLAIN, 14));
        sourceFileInFile.setBackground(Color.WHITE);
        panelFile.add(sourceFileInFile);

        //icon choose file
        JLabel imageSourceInFile = new JLabel();
        ImageIcon iconFileInFile = createResizedImageIcon("image/icon-file.png", 25, 25);
        imageSourceInFile.setIcon(iconFileInFile);
        imageSourceInFile.setBounds(570, 140, 25, 25);
        imageSourceInFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create and show JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(panelFile); // 'panelFile' is where set JFileChooser

                // Check choose file
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedPath = selectedFile.getAbsolutePath();
                    sourceFileInFile.setText(selectedPath);
                }
            }
        });
        panelFile.add(imageSourceInFile);

        // choose dest file
        JLabel chooseFileInFile2 = new JLabel("Dest file:");
        chooseFileInFile2.setBounds(25, 190, 100, 25);
        chooseFileInFile2.setFont(new Font("Serif", Font.BOLD, 15));
        panelFile.add(chooseFileInFile2);

        destFileInFile = new JTextField();
        destFileInFile.setBounds(110, 190, 450, 25);
        destFileInFile.setFont(new Font("Serif", Font.PLAIN, 15));
        destFileInFile.setBackground(Color.WHITE);
        panelFile.add(destFileInFile);

        //icon choose file
        JLabel imageDestFile = new JLabel();
        ImageIcon iconFileInFile1 = createResizedImageIcon("image/icon-file.png", 25, 25);
        imageDestFile.setIcon(iconFileInFile1);
        imageDestFile.setBounds(570, 190, 25, 25); // Đặt kích thước và vị trí cho hình ảnh
        imageDestFile.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Create and show JFileChooser
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(panelFile); // 'panelFile' is where set JFileChooser

                // Check choose file
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String selectedPath = selectedFile.getAbsolutePath();
                    destFileInFile.setText(selectedPath);
                }
            }
        });

        panelFile.add(imageDestFile);

        //Button run in file
        buttonRunFile = new JButton("RUN") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonRunFile.setBounds(450, 230, 120, 30);
        buttonRunFile.setFont(new Font("Serif", Font.BOLD, 15));
        buttonRunFile.setForeground(Color.BLACK);

        buttonRunFile.addActionListener(acFile);

        panelFile.add(buttonRunFile);

        //Button delete all
        JButton buttonDeleteAll = new JButton("Delete All") {
            @Override
            public void updateUI() {
                setUI(new BasicButtonUI() {
                    @Override
                    public void installUI(JComponent c) {
                        super.installUI(c);
                        AbstractButton button = (AbstractButton) c;
                        button.setContentAreaFilled(false);
                        button.setOpaque(true);
                        button.setBorderPainted(false);
                        button.addMouseListener(new MouseAdapter() {
                            public void mouseEntered(MouseEvent e) {
                                button.setForeground(Color.WHITE);
                            }

                            public void mouseExited(MouseEvent e) {
                                button.setForeground(Color.BLACK);
                            }
                        });
                    }

                    @Override
                    public void paint(Graphics g, JComponent c) {
                        AbstractButton b = (AbstractButton) c;
                        ButtonModel model = b.getModel();
                        if (model.isPressed()) {
                            g.setColor(Color.decode("#68C46C"));
                        } else {
                            g.setColor(Color.decode("#7AB8DD"));
                        }
                        g.fillRoundRect(0, 0, c.getWidth(), c.getHeight(), 15, 15);
                        super.paint(g, c);
                    }
                });
            }
        };
        buttonDeleteAll.setBounds(310, 230, 120, 30);
        buttonDeleteAll.setFont(new Font("Serif", Font.BOLD, 15));
        buttonDeleteAll.setForeground(Color.BLACK);
        buttonDeleteAll.addActionListener(e -> {
            destFileInFile.setText("");
            sourceFileInFile.setText("");
            outputKeyFile.setText("");
            outputPublicKeyFile.setText("");
        });
        panelFile.add(buttonDeleteAll);


        // Menu function
        JPanel menuButton = new JPanel(null);
        menuButton.setBounds(0, 40, 200, 700);
        menuButton.setBackground(Color.white);

        menuButton.add(createPanel("icon-text-lock.png", "Text Encryption", 0, 60, 200, 40, e -> {
            outputKey.setText("");
            panelText.setVisible(true);
            panelHash.setVisible(false);
            panelFile.setVisible(false);
            textArea.setText("");
            textArea1.setText("");
            buttonRun.setText("ENCRYPT");
            dropdown.setSelectedIndex(0);
            dropdown.setVisible(true);
            creKey.setVisible(true);
            imageCopykey.setVisible(true);
            outputKey.setVisible(true);
            publicKey.setVisible(false);
            outputKeyPublic.setVisible(false);
            imageCopykeyPublic.setVisible(false);
            panelText.add(publicKey);
            panelText.add(outputKeyPublic);
            panelText.add(imageCopykeyPublic);
            paneMain.setVisible(false);
            imageLoad.setVisible(true);
        }));

        menuButton.add(createPanel("icon-text-unlock.png", "Text Decryption", 0, 110, 200, 40, e -> {
            outputKey.setText("");
            panelText.setVisible(true);
            panelHash.setVisible(false);
            panelFile.setVisible(false);
            textArea1.setText("");
            textArea.setText("");
            buttonRun.setText("DECRYPT");
            dropdown.setSelectedIndex(0);
            dropdown.setVisible(true);
            creKey.setVisible(true);
            imageCopykey.setVisible(true);
            outputKey.setVisible(true);
            publicKey.setVisible(false);
            outputKeyPublic.setVisible(false);
            imageCopykeyPublic.setVisible(false);
            panelText.remove(publicKey);
            panelText.remove(outputKeyPublic);
            panelText.remove(imageCopykeyPublic);
            paneMain.setVisible(false);
            imageLoad.setVisible(false);
        }));

        menuButton.add(createPanel("icon_file-lock.png", "File Encryption", 0, 160, 200, 40, e -> {
            panelText.setVisible(false);
            panelHash.setVisible(false);
            panelFile.setVisible(true);
            outputKeyFile.setText("");
            sourceFileInFile.setText("");
            destFileInFile.setText("");
            buttonRunFile.setText("ENCRYPT");
            dropdownFile.setSelectedIndex(0);
            panelFile.add(publicKeyFile);
            panelFile.add(outputPublicKeyFile);
            panelFile.add(imageCopyPubliKeyInFile);
            paneMain.setVisible(false);
            imageLoadFile.setVisible(true);
        }));

        menuButton.add(createPanel("icon-file-unlock.png", "File Decryption", 0, 210, 200, 40, e -> {
            panelText.setVisible(false);
            panelHash.setVisible(false);
            panelFile.setVisible(true);
            outputKeyFile.setText("");
            sourceFileInFile.setText("");
            destFileInFile.setText("");
            buttonRunFile.setText("DECRYPT");
            dropdownFile.setSelectedIndex(0);
            panelFile.remove(publicKeyFile);
            panelFile.remove(outputPublicKeyFile);
            panelFile.remove(imageCopyPubliKeyInFile);
            paneMain.setVisible(false);
            imageLoadFile.setVisible(false);
        }));

        menuButton.add(createPanel("icon-sign-up.png", "Check File", 0, 10, 200, 40, e -> {
            panelText.setVisible(false);
            panelHash.setVisible(true);
            panelFile.setVisible(false);
            buttonRunHash.setText("Check File");
            outputHash.setText("");
            sourceFile.setText("");
            dropdown1.setBounds(115, 35, 100, 20);
            dropdown1.setSelectedIndex(0);
            panelHash.add(dropdown1);
            outputHash.setEditable(true);
            outputHash.setFocusable(true);
            sourceFile.setEditable(true);
            paneMain.setVisible(false);
        }));

        menuButton.add(createPanel("CkeyIcon.png", "Hash File", 0, 260, 200, 40, e -> {
            panelText.setVisible(false);
            panelHash.setVisible(true);
            panelFile.setVisible(false);
            outputHash.setText("");
            sourceFile.setText("");
            buttonRunHash.setText("Create Hash");
            dropdown1.setBounds(115, 35, 100, 20);
            panelHash.add(dropdown1);
            dropdown1.setSelectedIndex(0);
            outputHash.setEditable(false);
            outputHash.setFocusable(false);
            sourceFile.setEditable(true);
            paneMain.setVisible(false);
        }));

        menuButton.add(createPanel("icon-sign-up.png", "Hash Text", 0, 310, 200, 40, e -> {
            panelText.setVisible(true);
            panelHash.setVisible(false);
            panelFile.setVisible(false);
            dropdown.setVisible(false);
            dropdown1.setVisible(true);
            dropdown1.setBounds(100, 15, 100, 20);
            panelText.add(dropdown1);
            dropdown1.setSelectedIndex(0);
            buttonRun.setText("RUN");
            creKey.setVisible(false);
            imageCopykey.setVisible(false);
            outputKey.setVisible(false);
            publicKey.setVisible(false);
            outputKeyPublic.setVisible(false);
            imageCopykeyPublic.setVisible(false);
            paneMain.setVisible(false);
            imageLoad.setVisible(false);
        }));

        menuButton.add(createPanel("icon-guide.png", "Guide", 0, 360, 200, 40, e -> {
            panelText.setVisible(false);
            panelHash.setVisible(false);
            panelFile.setVisible(false);
            paneMain.setVisible(false);
        }));

        menuButton.add(createPanel("ExitIcon.png", "Exit", 0, 410, 200, 40, e -> System.exit(0)));


        panelFile.setVisible(false);
        body.add(panelFile);
        panelHash.add(imageFinger);
        panelHash.setVisible(false);
        panelText.setVisible(false);
        body.add(panelHash);
        panelText.add(panelTextArea2);
        panelText.add(panelTextArea1);
        body.add(menuButton);
        body.add(panelText);
        frame.add(body);

        //Hiển thị cửa sổ
        frame.setResizable(false);
        frame.setVisible(true);

        //Đặt ở giữa màn hình
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    private ImageIcon createResizedImageIcon(String path, int width, int height) {
        try {
            // Sử dụng ClassLoader để đọc tài nguyên từ thư mục resources
            URL imageURL = EncryptSwing.class.getClassLoader().getResource(path);

            if (imageURL != null) {
                Image image = ImageIO.read(imageURL);

                // Resize hình ảnh
                Image resizedImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

                return new ImageIcon(resizedImage);
            } else {
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JPanel createPanel(String imagePath, String labelText, int x, int y, int w, int h, ActionListener actionListener) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel iconLabel = new JLabel();
        ImageIcon icon = createResizedImageIcon("image/" + imagePath, 30, 30);
        iconLabel.setIcon(icon);
        iconLabel.setBorder(new EmptyBorder(0, 10, 0, 10)); // Add left padding of 10px for the icon
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel textLabel = new JLabel(labelText);
        textLabel.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(textLabel, BorderLayout.CENTER);
        panel.setBounds(x, y, w, h);

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(new ActionEvent(panel, ActionEvent.ACTION_PERFORMED, null));
            }

            @Override
            public void mousePressed(MouseEvent e) {
                panel.setBackground(Color.decode("#1B6B9A")); // Đổi màu nền sang xanh dương khi chuột được nhấn
                textLabel.setForeground(Color.WHITE);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                panel.setBackground(Color.WHITE); // Đổi màu nền trở lại trắng khi chuột được thả
                textLabel.setFont(new Font("Serif", Font.BOLD, 15));
                textLabel.setForeground(Color.BLACK);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                panel.setBackground(Color.decode("#B1DAF2")); // Đổi màu nền sang xám khi chuột vào
            }

            @Override
            public void mouseExited(MouseEvent e) {
                panel.setBackground(Color.WHITE); // Đổi màu nền trở lại trắng khi chuột rời đi
            }
        });
        return panel;
    }

    public void runInTextEncrypt() {
        String selecItem = String.valueOf(this.dropdown.getSelectedItem());
        int keySize;
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (textArea.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please fill your input!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!outputKey.getText().isEmpty()) {
                if (selecItem.equalsIgnoreCase("RSA")) {
                    selecItem = "RSA/ECB/PKCS1Padding";
                    this.asymmetricModel.convertStringToPrivateKey(this.outputKey.getText());
                    this.asymmetricModel.convertStringToPublicKey(this.outputKeyPublic.getText());
                    this.textArea1.setText(this.asymmetricModel.encrypt(textArea.getText(), selecItem));
                    return;
                }
                if (selecItem.equalsIgnoreCase("Vigenère")) {
                    String key = outputKey.getText();
                    this.textArea1.setText(this.vigenereModel.encryptLangue(textArea.getText(), key));
                    return;
                }
                if (selecItem.equalsIgnoreCase("Hill-2x2")) {
                    String key = outputKey.getText();
                    this.hillModel.getKeyMatrix(key);
                    this.hillModel.checkPlaintext(this.textArea.getText());
                    this.textArea1.setText(this.hillModel.encrypt(this.textArea.getText()));
                    return;
                }
                this.symmetricModel.changeStringToSecretKey(outputKey.getText(), selecItem);
                if (selecItem.equalsIgnoreCase("DES")) {
                    keySize = 56;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("3-DES")) {
                    selecItem = "DESede";
                    keySize = 168;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Blowfish")) {
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Twofish")) {
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("IDEA")) {
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("AES-128")) {
                    selecItem = "AES";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("AES-192")) {
                    selecItem = "AES";
                    keySize = 192;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("AES-256")) {
                    selecItem = "AES";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("CAST6-128")) {
                    selecItem = "CAST6";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("CAST6-256")) {
                    selecItem = "CAST6";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Serpent-128")) {
                    selecItem = "Serpent";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Serpent-256")) {
                    selecItem = "Serpent";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Camellia-128")) {
                    selecItem = "Camellia";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("Camellia-256")) {
                    selecItem = "Camellia";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("RC4-128")) {
                    selecItem = "RC4";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("RC5-128")) {
                    selecItem = "RC5";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("RC5-256")) {
                    selecItem = "RC5";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("RC6-128")) {
                    selecItem = "RC6";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }
                if (selecItem.equalsIgnoreCase("RC6-256")) {
                    selecItem = "RC6";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize, this.symmetricModel);
                }

            } else {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to create key?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    if (selecItem.equalsIgnoreCase("DES")) {
                        keySize = 56;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("3-DES")) {
                        selecItem = "DESede";
                        keySize = 168;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Blowfish")) {
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Twofish")) {
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("IDEA")) {
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("AES-128")) {
                        selecItem = "AES";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("AES-192")) {
                        selecItem = "AES";
                        keySize = 192;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("AES-256")) {
                        selecItem = "AES";
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("CAST6-128")) {
                        selecItem = "CAST6";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("CAST6-256")) {
                        selecItem = "CAST6";
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Serpent-128")) {
                        selecItem = "Serpent";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Serpent-256")) {
                        selecItem = "Serpent";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Camellia-128")) {
                        selecItem = "Camellia";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("Camellia-256")) {
                        selecItem = "Camellia";
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RC4-128")) {
                        selecItem = "RC4";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RC5-128")) {
                        selecItem = "RC5";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RC5-256")) {
                        selecItem = "RC5";
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RC6-128")) {
                        selecItem = "RC6";
                        keySize = 128;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RC6-256")) {
                        selecItem = "RC6";
                        keySize = 256;
                        extracted(selecItem, keySize, this.symmetricModel);
                    }
                    if (selecItem.equalsIgnoreCase("RSA")) {
                        selecItem = "RSA/ECB/PKCS1Padding";
                        this.asymmetricModel.genKey();
                        this.textArea1.setText(this.asymmetricModel.encrypt(textArea.getText(), selecItem));
                        this.outputKey.setText(asymmetricModel.exportKey(this.asymmetricModel.getPrivateKey()));
                        this.outputKeyPublic.setText(asymmetricModel.exportKey(this.asymmetricModel.getPublicKey()));
                    }
                    if (selecItem.equalsIgnoreCase("Vigenère")) {
                        String key = vigenereModel.generateRandomString();
                        this.textArea1.setText(this.vigenereModel.encryptLangue(textArea.getText(), key));
                        this.outputKey.setText(key);
                    }
                    if (selecItem.equalsIgnoreCase("Hill-2x2")) {
                        String key = this.hillModel.matrixToString(hillModel.checkKeyTrue());
                        this.hillModel.getKeyMatrix(key);
                        this.hillModel.checkPlaintext(this.textArea.getText());
                        this.textArea1.setText(this.hillModel.encrypt(this.textArea.getText()));
                        this.outputKey.setText(key);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runCreateKey() {
        String selecItem = String.valueOf(this.dropdown.getSelectedItem());
        int keySize;
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selecItem.equalsIgnoreCase("RSA")) {
                selecItem = "RSA/ECB/PKCS1Padding";
                this.asymmetricModel.genKey();
                this.outputKey.setText(asymmetricModel.exportKey(this.asymmetricModel.getPrivateKey()));
                this.outputKeyPublic.setText(asymmetricModel.exportKey(this.asymmetricModel.getPublicKey()));
            }
            if (selecItem.equalsIgnoreCase("Vigenère")) {
                String key = vigenereModel.generateRandomString();
                this.outputKey.setText(key);
            }
            if (selecItem.equalsIgnoreCase("Hill-2x2")) {
                String key = this.hillModel.matrixToString(hillModel.checkKeyTrue());
                this.hillModel.getKeyMatrix(key);
                this.outputKey.setText(key);
            }
            if (selecItem.equalsIgnoreCase("DES")) {
                keySize = 56;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("3-DES")) {
                selecItem = "DESede";
                keySize = 168;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Blowfish")) {
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Twofish")) {
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("IDEA")) {
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-128")) {
                selecItem = "AES";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-192")) {
                selecItem = "AES";
                keySize = 192;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-256")) {
                selecItem = "AES";
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("CAST6-128")) {
                selecItem = "CAST6";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("CAST6-256")) {
                selecItem = "CAST6";
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Serpent-128")) {
                selecItem = "Serpent";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Serpent-256")) {
                selecItem = "Serpent";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Camellia-128")) {
                selecItem = "Camellia";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Camellia-256")) {
                selecItem = "Camellia";
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC4-128")) {
                selecItem = "RC4";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC5-128")) {
                selecItem = "RC5";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC5-256")) {
                selecItem = "RC5";
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC6-128")) {
                selecItem = "RC6";
                keySize = 128;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC6-256")) {
                selecItem = "RC6";
                keySize = 256;
                extractedKey(selecItem, keySize, this.symmetricModel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runCreateKeyFile() {
        String selecItem = String.valueOf(this.dropdownFile.getSelectedItem());
        int keySize;
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selecItem.equalsIgnoreCase("RSA")) {
                selecItem = "RSA/ECB/PKCS1Padding";
                this.asymmetricModel.genKey();
                this.outputKeyFile.setText(asymmetricModel.exportKey(this.asymmetricModel.getPrivateKey()));
                this.outputPublicKeyFile.setText(asymmetricModel.exportKey(this.asymmetricModel.getPublicKey()));
            }
            if (selecItem.equalsIgnoreCase("Vigenère")) {
                String key = vigenereModel.generateRandomString();
                this.outputKeyFile.setText(key);
            }
            if (selecItem.equalsIgnoreCase("Hill-2x2")) {
                String key = this.hillModel.matrixToString(hillModel.checkKeyTrue());
                this.hillModel.getKeyMatrix(key);
                this.outputKeyFile.setText(key);
            }
            if (selecItem.equalsIgnoreCase("DES")) {
                keySize = 56;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("3-DES")) {
                selecItem = "DESede";
                keySize = 168;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Blowfish")) {
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Twofish")) {
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("IDEA")) {
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-128")) {
                selecItem = "AES";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-192")) {
                selecItem = "AES";
                keySize = 192;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("AES-256")) {
                selecItem = "AES";
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("CAST6-128")) {
                selecItem = "CAST6";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("CAST6-256")) {
                selecItem = "CAST6";
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Serpent-128")) {
                selecItem = "Serpent";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Serpent-256")) {
                selecItem = "Serpent";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Camellia-128")) {
                selecItem = "Camellia";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("Camellia-256")) {
                selecItem = "Camellia";
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC4-128")) {
                selecItem = "RC4";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC5-128")) {
                selecItem = "RC5";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC5-256")) {
                selecItem = "RC5";
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC6-128")) {
                selecItem = "RC6";
                keySize = 128;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
            if (selecItem.equalsIgnoreCase("RC6-256")) {
                selecItem = "RC6";
                keySize = 256;
                extractedKeyFile(selecItem, keySize, this.symmetricModel);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void extracted(String selecItem, int keySize, Symmetric symmetricModel) throws Exception {
        symmetricModel.createKey(selecItem, keySize);
        this.outputKey.setText(this.symmetricModel.exportKey());
        this.textArea1.setText(this.symmetricModel.encryptToBase64(textArea.getText(), selecItem));
    }

    private void extractedNotCreateKey(String selecItem, int keySize, Symmetric symmetricModel) throws Exception {
        this.textArea1.setText(this.symmetricModel.encryptToBase64(textArea.getText(), selecItem));
    }

    private void extractedKey(String selecItem, int keySize, Symmetric symmetricModel) throws Exception {
        symmetricModel.createKey(selecItem, keySize);
        this.outputKey.setText(this.symmetricModel.exportKey());
    }

    private void extractedKeyFile(String selecItem, int keySize, Symmetric symmetricModel) throws Exception {
        symmetricModel.createKey(selecItem, keySize);
        this.outputKeyFile.setText(this.symmetricModel.exportKey());
    }

    public void runInTextDecrypt() {
        String selecItem = String.valueOf(this.dropdown.getSelectedItem());
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selecItem.equalsIgnoreCase("RSA")) {
                this.asymmetricModel.convertStringToPrivateKey(this.outputKey.getText());
                this.textArea1.setText(this.asymmetricModel.decrypt(textArea.getText()));
                return;
            }
            if (selecItem.equalsIgnoreCase("Vigenère")) {
                this.textArea1.setText(this.vigenereModel.decryptLangue(textArea.getText(), this.outputKey.getText()));
                return;
            }
            if (selecItem.equalsIgnoreCase("Hill-2x2")) {
                this.hillModel.getKeyMatrix(outputKey.getText());
                this.hillModel.checkPlaintext(this.textArea.getText());
                this.textArea1.setText(this.hillModel.decrypt(this.textArea.getText()));
                return;
            } else {
                this.textArea1.setText(this.symmetricModel.decryptFromBase64(textArea.getText(), selecItem, symmetricModel.createKey(this.outputKey.getText(), selecItem)));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runTextInHash() {
        String selectItem = String.valueOf(this.dropdown1.getSelectedItem());
        try {
            if (selectItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selectItem.equalsIgnoreCase("SHA-1")) {
                selectItem = "SHA1";
            }
            if (selectItem.equalsIgnoreCase("SHA-224")) {
                selectItem = "SHA224";
            }
            if (selectItem.equalsIgnoreCase("SHA-256")) {
                selectItem = "SHA256";
            }
            if (selectItem.equalsIgnoreCase("SHA-384")) {
                selectItem = "SHA384";
            }
            if (selectItem.equalsIgnoreCase("SHA-512")) {
                selectItem = "SHA512";
            }
            this.textArea1.setText(this.hashModel.shaText(textArea.getText(), selectItem));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void runFileInHash() {
        String selectItem = String.valueOf(this.dropdown1.getSelectedItem());
        try {
            if (selectItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selectItem.equalsIgnoreCase("SHA-1")) {
                selectItem = "SHA1";
            }
            if (selectItem.equalsIgnoreCase("SHA-224")) {
                selectItem = "SHA224";
            }
            if (selectItem.equalsIgnoreCase("SHA-256")) {
                selectItem = "SHA256";
            }
            if (selectItem.equalsIgnoreCase("SHA-384")) {
                selectItem = "SHA384";
            }
            if (selectItem.equalsIgnoreCase("SHA-512")) {
                selectItem = "SHA512";
            }
            this.outputHash.setText(this.hashModel.hash(sourceFile.getText(), selectItem));
        } catch (Exception e) {
            if (e.getMessage().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(this.frame, "Please check again!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String runCheckFileInHash() {
        String selectItem = String.valueOf(this.dropdown1.getSelectedItem());
        try {
            if (selectItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            if (this.sourceFile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            if (this.outputHash.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your key!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return null;
            }
            if (selectItem.equalsIgnoreCase("SHA-1")) {
                selectItem = "SHA1";
            }
            if (selectItem.equalsIgnoreCase("SHA-224")) {
                selectItem = "SHA224";
            }
            if (selectItem.equalsIgnoreCase("SHA-256")) {
                selectItem = "SHA256";
            }
            if (selectItem.equalsIgnoreCase("SHA-384")) {
                selectItem = "SHA384";
            }
            if (selectItem.equalsIgnoreCase("SHA-512")) {
                selectItem = "SHA512";
            }
            return this.hashModel.hash(sourceFile.getText(), selectItem);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    public void runInFileEncrypt() {
        String selecItem = String.valueOf(this.dropdownFile.getSelectedItem());
        int keySize;
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (sourceFileInFile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your source file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (destFileInFile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your dest file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (sourceFileInFile.getText().equalsIgnoreCase(destFileInFile.getText())) {
                JOptionPane.showMessageDialog(this.frame, "The destination file is the same as the source file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (!outputKeyFile.getText().isEmpty()) {
                if (selecItem.equalsIgnoreCase("RSA")) {
                    this.asymmetricModel.convertStringToPrivateKey(this.outputKeyFile.getText());
                    this.asymmetricModel.convertStringToPublicKey(this.outputPublicKeyFile.getText());
                    this.asymmetricModel.encryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText());
                    return;
                }
                this.symmetricModel.changeStringToSecretKey(outputKeyFile.getText(), selecItem);
                if (selecItem.equalsIgnoreCase("DES")) {
                    keySize = 56;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("3-DES")) {
                    selecItem = "DESede";
                    keySize = 168;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Blowfish")) {
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Twofish")) {
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("IDEA")) {
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("AES-128")) {
                    selecItem = "AES";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("AES-192")) {
                    selecItem = "AES";
                    keySize = 192;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("AES-256")) {
                    selecItem = "AES";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("CAST6-128")) {
                    selecItem = "CAST6";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("CAST6-256")) {
                    selecItem = "CAST6";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Serpent-128")) {
                    selecItem = "Serpent";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Serpent-256")) {
                    selecItem = "Serpent";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Camellia-128")) {
                    selecItem = "Camellia";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("Camellia-256")) {
                    selecItem = "Camellia";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("RC4-128")) {
                    selecItem = "RC4";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("RC5-128")) {
                    selecItem = "RC5";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("RC5-256")) {
                    selecItem = "RC5";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("RC6-128")) {
                    selecItem = "RC6";
                    keySize = 128;
                    extractedNotCreateKey(selecItem, keySize);
                }
                if (selecItem.equalsIgnoreCase("RC6-256")) {
                    selecItem = "RC6";
                    keySize = 256;
                    extractedNotCreateKey(selecItem, keySize);
                }
                JOptionPane.showMessageDialog(this.frame, "Encrypt success!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                int choice = JOptionPane.showConfirmDialog(null, "Do you want to create key?", "Confirmation", JOptionPane.YES_NO_OPTION);

                if (choice == JOptionPane.YES_OPTION) {
                    if (selecItem.equalsIgnoreCase("DES")) {
                        keySize = 56;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("3-DES")) {
                        selecItem = "DESede";
                        keySize = 168;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Blowfish")) {
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Twofish")) {
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("IDEA")) {
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("AES-128")) {
                        selecItem = "AES";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("AES-192")) {
                        selecItem = "AES";
                        keySize = 192;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("AES-256")) {
                        selecItem = "AES";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("CAST6-128")) {
                        selecItem = "CAST6";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("CAST6-256")) {
                        selecItem = "CAST6";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Serpent-128")) {
                        selecItem = "Serpent";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Serpent-256")) {
                        selecItem = "Serpent";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Camellia-128")) {
                        selecItem = "Camellia";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("Camellia-256")) {
                        selecItem = "Camellia";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RC4-128")) {
                        selecItem = "RC4";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RC5-128")) {
                        selecItem = "RC5";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RC5-256")) {
                        selecItem = "RC5";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RC6-128")) {
                        selecItem = "RC6";
                        keySize = 128;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RC6-256")) {
                        selecItem = "RC6";
                        keySize = 256;
                        extracted(selecItem, keySize);
                    }
                    if (selecItem.equalsIgnoreCase("RSA")) {
                        this.asymmetricModel.genKey();
                        this.asymmetricModel.encryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText());
                        this.outputKeyFile.setText(asymmetricModel.exportKey(this.asymmetricModel.getPrivateKey()));
                        this.outputPublicKeyFile.setText(asymmetricModel.exportKey(this.asymmetricModel.getPublicKey()));
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void extracted(String selecItem, int keySize) throws Exception {
        this.symmetricModel.createKey(selecItem, keySize);
        this.symmetricModel.encryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText(), selecItem);
        this.outputKeyFile.setText(this.symmetricModel.exportKey());
    }

    private void extractedNotCreateKey(String selecItem, int keySize) throws Exception {
        this.symmetricModel.encryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText(), selecItem);
    }

    public void runInFileDecrypt() {
        String selecItem = String.valueOf(this.dropdownFile.getSelectedItem());
        try {
            if (selecItem.equalsIgnoreCase("Choose")) {
                JOptionPane.showMessageDialog(this.frame, "Please check algorithm!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (this.sourceFileInFile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your source file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (this.destFileInFile.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this.frame, "Please check your dest file!", "Notification", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            if (selecItem.equalsIgnoreCase("RSA")) {
                this.asymmetricModel.convertStringToPrivateKey(this.outputKeyFile.getText());
                this.asymmetricModel.decryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText());
            } else {
                this.symmetricModel.changeStringToSecretKey(this.outputKeyFile.getText(), selecItem);
                this.symmetricModel.decryptFile(this.sourceFileInFile.getText(), this.destFileInFile.getText(), selecItem);
            }
            JOptionPane.showMessageDialog(this.frame, "Decrypt success!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this.frame, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EncryptSwing ne = new EncryptSwing();
        ne.createAndShowGUI();
    }
}

