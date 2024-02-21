package model;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.*;
import java.math.BigInteger;
import java.security.*;

public class Hash {

    public Hash() {
        Security.addProvider(new BouncyCastleProvider());
    }


    public String shaText(String input, String algorithms) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithms);
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            return number.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new NullPointerException("Please check your input!");
        }
    }

    public String hash(String file, String algorithms) throws Exception{
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithms);
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            DigestInputStream disIS = new DigestInputStream(is, digest);

            byte[] buffer = new byte[1024];
            int read;
            do {
                read = disIS.read(buffer);
            } while (read != -1);
            BigInteger number = new BigInteger(1, disIS.getMessageDigest().digest());
            return number.toString(16);
        }catch (Exception e){
            throw new FileNotFoundException("File not found!");
        }
    }

}
