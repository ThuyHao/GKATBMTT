package controller;

import view.EncryptSwing;

import javax.swing.*;
import java.awt.event.*;

public class TextControl implements ActionListener {
    private final EncryptSwing encryptSwing;

    public TextControl(EncryptSwing encryptSwing) {
        this.encryptSwing = encryptSwing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("ENCRYPT")) {
            try {
                this.encryptSwing.runInTextEncrypt();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
        if (src.equals("DECRYPT")) {
            try {
                this.encryptSwing.runInTextDecrypt();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
