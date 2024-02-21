package model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class Symmetric {
    public Symmetric() {
        Security.addProvider(new BouncyCastleProvider());
    }

    private SecretKey key;

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    //DES
    public SecretKey createKey(String algorithm, int keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
        keyGenerator.init(keySize);
        key = keyGenerator.generateKey();
        return key;
    }

    public SecretKey createKey(String keyCreate, String algorithm) {
        byte[] bytes = Base64.getDecoder().decode(keyCreate);
        SecretKey secretKey = new SecretKeySpec(bytes, algorithm);
        this.key = secretKey;
        return secretKey;
    }

    // encrypt base64
    public String encryptToBase64(String text, String algorithm) throws Exception {
        if (key == null) return "";
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] plaintext = text.getBytes(StandardCharsets.UTF_8);
        byte[] cipherText = cipher.doFinal(plaintext);
        return Base64.getEncoder().encodeToString(cipherText);
    }

    //decrypt base64 string
    public String decryptFromBase64(String text, String algorithm, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] plaintext = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(plaintext, StandardCharsets.UTF_8);
    }

    //export key to base64 String
    public String exportKey() {
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }

    // encrypt file
    public void encryptFile(String sourceFile, String destFile, String algorithm) throws Exception {
        if (key == null) return;
        File file = new File(sourceFile);
        if (file.isFile()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);
            byte[] input = new byte[64];
            int byteRead;
            while ((byteRead = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, byteRead);
                if (output != null) fos.write(output);
            }
            byte[] output = cipher.doFinal();
            if (output != null) fos.write(output);
            fis.close();
            fos.flush();
            fos.close();
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    // decrypt file
    public void decryptFile(String sourcefile, String destFile, String algorithm) throws Exception {
        if (key == null) return;
        File file = new File(sourcefile);
        if (file.exists()) {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fis = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(destFile);

            byte[] input = new byte[64];
            int readByte;
            while ((readByte = fis.read(input)) != -1) {
                byte[] output = cipher.update(input, 0, readByte);
                if (output != null) {
                    fos.write(output);
                }
            }
            byte[] output = cipher.doFinal();
            if (output != null) {
                fos.write(output);
            }

            fis.close();
            fos.flush();
            fos.close();
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    public void changeStringToSecretKey(String keyStr, String algorithm) throws InvalidKeyException {
        try {
            byte[] decodedKey = Base64.getDecoder().decode(keyStr); // change base64 to byte []
            key = new SecretKeySpec(decodedKey, 0, decodedKey.length, algorithm);
        } catch (Exception e) {
            throw new InvalidKeyException("Please check your key!");
        }
    }
}
