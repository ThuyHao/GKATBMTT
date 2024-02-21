package model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.nio.charset.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Asymmetric {
    private KeyPair keyPair;
    private PublicKey publicKey;
    private PrivateKey privateKey;

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setKeyPair(KeyPair keyPair) {
        this.keyPair = keyPair;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public String encrypt(String text, String transformation) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance(transformation);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] output = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(output);
    }

    public void genKey() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    public String decrypt(String text) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] ouput = cipher.doFinal(Base64.getDecoder().decode(text));
        return new String(ouput, StandardCharsets.UTF_8);
    }

    //encript file
    public void encryptFile(String source, String dest) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            byte[] iv = new byte[16];
            IvParameterSpec spec = new IvParameterSpec(iv);
            SecretKey secretKey = keyGenerator.generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, spec);

            CipherInputStream cis = new CipherInputStream(new BufferedInputStream(new FileInputStream(source)), cipher);
            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(dest)));

            String keyString = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            dos.writeUTF(encrypt(keyString, "RSA/ECB/PKCS1Padding"));
            dos.writeLong(new File(source).length());
            dos.writeUTF(Base64.getEncoder().encodeToString(iv));

            byte[] buff = new byte[1024];
            int i;
            while ((i = cis.read(buff)) != -1) {
                dos.write(buff, 0, i);
            }
            cis.close();
            dos.flush();
            dos.close();
        } catch (Exception e) {
            throw new FileNotFoundException("File not found!");
        }
    }

    public void decryptFile(String source, String dest) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        try {
            DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(source)));
            String keyString = dis.readUTF();
            long size = dis.readLong();
            byte[] iv = Base64.getDecoder().decode(dis.readUTF());

            SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(decrypt(keyString)), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            CipherInputStream cis = new CipherInputStream(dis, cipher);
            BufferedOutputStream bof = new BufferedOutputStream(new FileOutputStream(dest));

            byte[] buff = new byte[1024];
            int i;
            while ((i = cis.read(buff)) != -1) {
                bof.write(buff, 0, i);
            }
            cis.close();
            bof.close();
        } catch (Exception e) {
            throw new FileNotFoundException("File not found!");
        }
    }

    public String exportKey(Key key) {
        byte[] keyBytes = key.getEncoded();
        return Base64.getEncoder().encodeToString(keyBytes);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public PrivateKey convertStringToPrivateKey(String privateKeyStr) throws Exception {
        try {
            byte[] decoded = Base64.getDecoder().decode(privateKeyStr);
            // Tạo đối tượng PKCS8EncodedKeySpec từ byte array
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
            // Sử dụng KeyFactory để chuyển đối PKCS8EncodedKeySpec thành PrivateKey
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            throw new Exception("Key not exit!", e);
        }
    }
    public PublicKey convertStringToPublicKey(String publicKeyStr) throws Exception {
        try {
            byte[] decoded = Base64.getDecoder().decode(publicKeyStr);
            // Tạo đối tượng X509EncodedKeySpec từ byte array
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decoded);
            // Sử dụng KeyFactory để chuyển đối X509EncodedKeySpec thành PublicKey
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (Exception e) {
            throw new Exception("Key not exist!", e);
        }
    }
}
