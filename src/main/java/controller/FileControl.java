package controller;

import view.EncryptSwing;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileControl implements ActionListener {

    private final EncryptSwing encryptSwing;

    public FileControl(EncryptSwing encryptSwing) {
        this.encryptSwing = encryptSwing;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String src = e.getActionCommand();
        if (src.equals("ENCRYPT")) {
            try {
                this.encryptSwing.runInFileEncrypt();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(encryptSwing.frame, "Sorry! I can't encript.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.getMessage();
            }
        }
        if (src.equals("DECRYPT")) {
            try {
                this.encryptSwing.runInFileDecrypt();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(encryptSwing.frame, "Sorry! I can't decript.", "ERROR", JOptionPane.ERROR_MESSAGE);
                ex.getMessage();
            }
        }
    }

}
