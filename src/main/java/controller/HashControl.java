package controller;

import view.EncryptSwing;

import javax.swing.*;
import java.awt.event.*;

public class HashControl implements ActionListener {
    private final EncryptSwing encryptSwing;

    public HashControl(EncryptSwing encryptSwing) {
        this.encryptSwing = encryptSwing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("RUN")) {
            try {
                this.encryptSwing.runTextInHash();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (src.equals("Create Hash")) {
            try {
                this.encryptSwing.runFileInHash();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (src.equals("Check File")) {
            if (this.encryptSwing.runCheckFileInHash() == null) {
                return;
            }
            if (this.encryptSwing.outputHash.getText().equals(this.encryptSwing.runCheckFileInHash())) {
                JOptionPane.showMessageDialog(this.encryptSwing.frame, "Equals!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this.encryptSwing.frame, "Not equals!", "Notification", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }
}
