package model;

import java.security.SecureRandom;

public class Vigenere {

    public String vigenereEncryptVn(String plainText, String key) {
        StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < plainText.length(); i++) {
            char charAtI = plainText.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            char encryptedChar;
            if (Character.isLetter(charAtI)) {
                boolean isUpperCase = Character.isUpperCase(charAtI);
                charAtI = Character.toUpperCase(charAtI);
                keyChar = Character.toUpperCase(keyChar);
                encryptedChar = vigenereEncryptChar(charAtI, keyChar);
                encryptedChar = isUpperCase ? encryptedChar : Character.toLowerCase(encryptedChar);
            } else {
                encryptedChar = charAtI;
            }
            encryptedText.append(encryptedChar);
        }
        return encryptedText.toString();
    }

    public String vigenereDecryptVn(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < encryptedText.length(); i++) {
            char charAtI = encryptedText.charAt(i);
                char keyChar = key.charAt(i % keyLength);
            char decryptedChar;

            if (Character.isLetter(charAtI)) {
                boolean isUpperCase = Character.isUpperCase(charAtI);
                charAtI = Character.toUpperCase(charAtI);
                keyChar = Character.toUpperCase(keyChar);

                decryptedChar = vigenereDecryptChar(charAtI, keyChar);
                decryptedChar = isUpperCase ? decryptedChar : Character.toLowerCase(decryptedChar);
            } else {
                decryptedChar = charAtI;
            }

            decryptedText.append(decryptedChar);
        }

        return decryptedText.toString();
    }

    private char vigenereEncryptChar(char plainChar, char keyChar) {
        String alphabet = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỞỠỚỜỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴ";
        int plainIndex = alphabet.indexOf(plainChar);
        int keyIndex = alphabet.indexOf(keyChar);
        int encryptedIndex = (plainIndex + keyIndex) % alphabet.length();
        return alphabet.charAt(encryptedIndex);
    }

    private char vigenereDecryptChar(char encryptedChar, char keyChar) {
        String alphabet = "AÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬBCDĐEÉÈẺẼẸÊẾỀỂỄỆGHIÍÌỈĨỊKLMNOÓÒỎÕỌÔỐỒỔỖỘƠỞỠỚỜỢPQRSTUÚÙỦŨỤƯỨỪỬỮỰVXYÝỲỶỸỴ";
        int encryptedIndex = alphabet.indexOf(encryptedChar);
        int keyIndex = alphabet.indexOf(keyChar);
        int decryptedIndex = (encryptedIndex - keyIndex + alphabet.length()) % alphabet.length();
        return alphabet.charAt(decryptedIndex);
    }

    public String vigenereEncryptEn(String plainText, String key) {
        StringBuilder encryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < plainText.length(); i++) {
            char charAtI = plainText.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            if (Character.isLetter(charAtI)) {
                boolean isUpperCase = Character.isUpperCase(charAtI);
                charAtI = Character.toUpperCase(charAtI);
                keyChar = Character.toUpperCase(keyChar);

                char encryptedChar = (char) ((charAtI + keyChar - 2 * 'A') % 26 + 'A');
                encryptedChar = isUpperCase ? encryptedChar : Character.toLowerCase(encryptedChar);
                encryptedText.append(encryptedChar);
            } else {
                encryptedText.append(charAtI);
            }
        }

        return encryptedText.toString();
    }

    public static String vigenereDecryptEn(String encryptedText, String key) {
        StringBuilder decryptedText = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < encryptedText.length(); i++) {
            char charAtI = encryptedText.charAt(i);
            char keyChar = key.charAt(i % keyLength);

            if (Character.isLetter(charAtI)) {
                boolean isUpperCase = Character.isUpperCase(charAtI);
                charAtI = Character.toUpperCase(charAtI);
                keyChar = Character.toUpperCase(keyChar);

                char decryptedChar = (char) ((charAtI - keyChar + 26) % 26 + 'A');
                decryptedChar = isUpperCase ? decryptedChar : Character.toLowerCase(decryptedChar);
                decryptedText.append(decryptedChar);
            } else {
                decryptedText.append(charAtI);
            }
        }

        return decryptedText.toString();
    }

    public String encryptLangue(String plaintext, String key) {
        if (containsVietnameseChars(plaintext)) {
            return vigenereEncryptVn(plaintext, key);
        }else{
            return vigenereEncryptEn(plaintext, key);
        }
    }

    public String decryptLangue(String plaintext, String key) {
        if (containsVietnameseChars(plaintext)) {
            return vigenereDecryptVn(plaintext, key);
        }else{
            return vigenereDecryptEn(plaintext, key);
        }
    }

    //Is plaintext VN
    private boolean containsVietnameseChars(String text) {
        String vietnameseChars = "ÁÀẢÃẠẮẰẲẴẶẤẦẨẪẬÉÈẺẼẸẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌỐỒỔỖỘỚỜỞỠỢÚÙỦŨỤỨỪỬỮỰÝỲỶỸỴ";
        Boolean aBoolean = false;

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            c = Character.toUpperCase(c);
            String cc = Character.toString(c);
            while (vietnameseChars.contains(cc)) {
                aBoolean = true;
                break;
            }
        }
        return aBoolean;
    }

    public String generateRandomString() {
        String characters = "ABCDEGHIKLMNOPQRSTUVXYabcdeghiklmnopqrstuvxy";

        int length = 20;
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(length);

        if (characters.length() > 0) {
            for (int i = 0; i < length; i++) {
                int randomIndex = random.nextInt(characters.length());
                char randomChar = characters.charAt(randomIndex);
                stringBuilder.append(randomChar);
            }
        } else {
            throw new IllegalArgumentException("The 'characters' string must not be empty.");
        }
        return stringBuilder.toString();
    }

}
