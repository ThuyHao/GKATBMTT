package model;

import java.util.Random;

public class HillCipher {
    private String ALPHABET = "abcdefghijklmnopqrstuvwxyz";
    private final int[][] keyMatrix = new int[2][2];
    private int MOD = ALPHABET.length();


    public String checkPlaintext(String plaintext) {
      for(char c : plaintext.toCharArray()){
          String VIETNAMESE_CHARACTERS = "áàảãạắằẳẵặấầẩẫậéèẻẽẹếềểễệíìỉĩịóòỏõọốồổỗộớờởỡợúùủũụứừửữựýỳỷỹỵ";
          if(VIETNAMESE_CHARACTERS.contains(String.valueOf(c))){
              ALPHABET = "aáàảãạăắằẳẵặâấầẩẫậbcdđeéèẻẽẹêếềểễệghiíìỉĩịklmnoóòỏõọôốồổỗộơởỡớờợpqrstuúùủũụưứừửữựvxyýỳỷỹỵ";
              break;
          }else{
              ALPHABET = "abcdefghijklmnopqrstuvwxyz";
          }
      }
       MOD = ALPHABET.length();
        return ALPHABET;
    }

    public void getKeyMatrix(String key) throws Exception {
        String[] keyArray = key.split(" ");
        if (keyArray.length != 4) {
            throw new Exception ("Invalid lock, please enter 4 integers separated by spaces.");
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                keyMatrix[i][j] = Integer.parseInt(keyArray[i * 2 + j]) % MOD;
            }
        }
    }

    private int modInverse(int a) {
        for (int i = 1; i < MOD; i++) {
            if ((a * i) % MOD == 1) {
                return i;
            }
        }
        return -1;
    }

    public int[][] getInverseKeyMatrix() {
        int det = (keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0] + MOD) % MOD;
        int invDet = modInverse(det);

        int[][] inverseKeyMatrix = new int[2][2];
        inverseKeyMatrix[0][0] = (keyMatrix[1][1] * invDet) % MOD;
        inverseKeyMatrix[0][1] = (-keyMatrix[0][1] * invDet + MOD) % MOD;
        inverseKeyMatrix[1][0] = (-keyMatrix[1][0] * invDet + MOD) % MOD;
        inverseKeyMatrix[1][1] = (keyMatrix[0][0] * invDet) % MOD;

        return inverseKeyMatrix;
    }

    private String encryptPair(String pair) {
        if (Character.isLetter(pair.charAt(0)) && Character.isLetter(pair.charAt(1))) {
            int[][] pairMatrix = new int[2][1];
            pairMatrix[0][0] = ALPHABET.indexOf(Character.toLowerCase(pair.charAt(0)));
            pairMatrix[1][0] = ALPHABET.indexOf(Character.toLowerCase(pair.charAt(1)));

            int[][] resultMatrix = new int[2][1];
            resultMatrix[0][0] = (keyMatrix[0][0] * pairMatrix[0][0] + keyMatrix[0][1] * pairMatrix[1][0]) % MOD;
            resultMatrix[1][0] = (keyMatrix[1][0] * pairMatrix[0][0] + keyMatrix[1][1] * pairMatrix[1][0]) % MOD;

            return ALPHABET.charAt((resultMatrix[0][0] + MOD) % MOD) + "" +
                    ALPHABET.charAt((resultMatrix[1][0] + MOD) % MOD);
        } else {
            return pair;
        }
    }

    public String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < plaintext.length(); i += 2) {
            String pair;
            char char1 = plaintext.charAt(i);
            if (i + 1 < plaintext.length()) {
                char char2 = plaintext.charAt(i + 1);
                // Check if both characters are alphabetic
                if (Character.isLetter(char1) && Character.isLetter(char2)) {
                    pair = char1 + String.valueOf(char2);

                    // Preserve the case of the original characters
                    char encryptedChar1 = encryptPair(pair).charAt(0);
                    char encryptedChar2 = encryptPair(pair).charAt(1);

                    // Convert back to uppercase if the original character was uppercase
                    if (Character.isUpperCase(char1)) {
                        encryptedChar1 = Character.toUpperCase(encryptedChar1);
                    }
                    if (Character.isUpperCase(char2)) {
                        encryptedChar2 = Character.toUpperCase(encryptedChar2);
                    }

                    ciphertext.append(encryptedChar1);
                    ciphertext.append(encryptedChar2);
                } else {
                    // If either or both characters are non-alphabetic, append them without encryption
                    ciphertext.append(char1);
                    ciphertext.append(char2);
                }
            } else {
                // Handle the case when there's only one character left
                pair = Character.isLetter(char1) ? char1 + "Z" : String.valueOf(char1);

                // Preserve the case of the original character
                char encryptedChar = encryptPair(pair).charAt(0);
                if (Character.isUpperCase(char1)) {
                    encryptedChar = Character.toUpperCase(encryptedChar);
                }

                ciphertext.append(encryptedChar);
            }
        }
        return ciphertext.toString();
    }

    private String decryptPair(String pair) {
        if (Character.isLetter(pair.charAt(0)) && Character.isLetter(pair.charAt(1))) {
            int[][] pairMatrix = new int[2][1];
            pairMatrix[0][0] = ALPHABET.indexOf(Character.toLowerCase(pair.charAt(0)));
            pairMatrix[1][0] = ALPHABET.indexOf(Character.toLowerCase(pair.charAt(1)));

            int[][] inverseKeyMatrix = getInverseKeyMatrix();

            int[][] resultMatrix = new int[2][1];
            resultMatrix[0][0] = (inverseKeyMatrix[0][0] * pairMatrix[0][0] + inverseKeyMatrix[0][1] * pairMatrix[1][0]) % MOD;
            resultMatrix[1][0] = (inverseKeyMatrix[1][0] * pairMatrix[0][0] + inverseKeyMatrix[1][1] * pairMatrix[1][0]) % MOD;

            return ALPHABET.charAt((resultMatrix[0][0] + MOD) % MOD) + "" +
                    ALPHABET.charAt((resultMatrix[1][0] + MOD) % MOD);
        } else {
            return pair;
        }
    }

    public String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();

        for (int i = 0; i < ciphertext.length(); i += 2) {
            String pair;

            if (i + 1 < ciphertext.length()) {
                pair = ciphertext.substring(i, i + 2);
            } else {
                pair = ciphertext.substring(i) + "Z";
            }

            // Decrypt the pair
            String decryptedPair = decryptPair(pair);

            // Preserve the case of the original characters
            char decryptedChar1 = decryptedPair.charAt(0);
            char decryptedChar2 = decryptedPair.charAt(1);

            // Find the corresponding case in the original pair
            char originalChar1 = Character.isUpperCase(pair.charAt(0)) ? Character.toUpperCase(decryptedChar1) : decryptedChar1;
            char originalChar2 = Character.isUpperCase(pair.charAt(1)) ? Character.toUpperCase(decryptedChar2) : decryptedChar2;

            plaintext.append(originalChar1);
            plaintext.append(originalChar2);
        }

        return plaintext.toString();
    }

    // Create random matrix 2x2 key
    public int[][] generateRandomInverseMatrix() {
        Random random = new Random();
        int a = random.nextInt(10) + 1; // Tránh giá trị 0 để đảm bảo nghịch đảo tồn tại
        int b = random.nextInt(10);
        int c = random.nextInt(10);
        int d = random.nextInt(10) + 1;
        return new int[][]{{a, b}, {c, d}};
    }

    // print matrix
    public void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    // change matrix to string
    public String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : matrix) {
            for (int value : row) {
                sb.append(value).append(" ");
            }
        }
        return sb.toString().trim();
    }

    // check: Is it inverse matrix?
    public boolean isInverseMatrix(int[][] matrix) {
        // Calculate the determinant
        int determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

        // Check if the determinant is not zero (i.e., the matrix is invertible)
        if (determinant != 0) {
            // Calculate the inverse matrix
            int[][] inverseMatrix = {
                    {matrix[1][1], -matrix[0][1]},
                    {-matrix[1][0], matrix[0][0]}
            };

            // Multiply each element of the inverse matrix by 1/det(A)
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    inverseMatrix[i][j] /= determinant;
                }
            }

            // Check additional conditions for the inverse matrix
            int[][] product = multiplyMatrices(matrix, inverseMatrix);

            return product[0][0] == 1 && product[0][1] == 0 && product[1][0] == 0 && product[1][1] == 1;
        } else {
            return false;
        }
    }

    // Function to multiply two matrices
    public int[][] multiplyMatrices(int[][] a, int[][] b) {
        int rowsA = a.length;
        int colsA = a[0].length;
        int colsB = b[0].length;

        int[][] result = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return result;
    }

    public int[][] checkKeyTrue() {
        int[][] re = generateRandomInverseMatrix();
        while (!isInverseMatrix(re)) {
            re = generateRandomInverseMatrix();
        }
        return re;
    }

//    public static void main(String[] args) throws Exception {
//        // Define the key
////        String key = "8 9 9 7"; // Replace with your key
//
//        HillCipher h = new HillCipher();
//        // Set the key matrix
//        boolean isInverse = true;
////        printMatrix(h.checkKeyTrue());
//        while (isInverse) {
//            // Tạo ma trận nghịch đảo ngẫu nhiên
//            int[][] inverseMatrix = h.checkKeyTrue();
//
//            // In ma trận nghịch đảo
//            System.out.println("Ma trận nghịch đảo:");
//            h.printMatrix(inverseMatrix);
//
//            // Chuyển ma trận thành chuỗi và in ra
//            String matrixString = h.matrixToString(inverseMatrix);
//            System.out.println("Chuỗi từ ma trận nghịch đảo: " + matrixString);
//
//            // Kiểm tra xem ma trận có phải là ma trận nghịch đảo hay không
//            isInverse = h.isInverseMatrix(inverseMatrix);
//            System.out.println("Có phải là ma trận nghịch đảo không? " + isInverse);
//
//            h.getKeyMatrix(matrixString);
//
//            // Define the plaintext
//            String plaintext = "Ban Long Coa Phai La Mot Nguoi Dang GHet? !.:''| MTCHG 123@#..&"; // Replace with your plaintext
//            System.out.println(h.checkPlaintext(plaintext));
//
//            // Encrypt the plaintext
//            String ciphertext = h.encrypt(plaintext);
//            System.out.println("Chuỗi đã mã hóa: " + ciphertext);
//
//            // Decrypt the ciphertext
//            String decryptedText = h.decrypt(ciphertext);
//            System.out.println("Chuỗi đã giải mã: " + decryptedText);
//        }
//
//
//
////        boolean isInverse = true;
////        while (isInverse){
////            int[][] inverseMatrix = h.generateRandomInverseMatrix();
////
////            // In ma trận nghịch đảo
////            System.out.println("Ma trận nghịch đảo:");
////            h.printMatrix(inverseMatrix);
////
////            // Chuyển ma trận thành chuỗi và in ra
////            String matrixString = h.matrixToString(inverseMatrix);
////            System.out.println("Chuỗi từ ma trận nghịch đảo: " + matrixString);
////            isInverse= h.isInverseMatrix(inverseMatrix);
////            System.out.println("Có phải là ma trận nghịch đảo không? " + isInverse);
////        }
//
//    }
}
