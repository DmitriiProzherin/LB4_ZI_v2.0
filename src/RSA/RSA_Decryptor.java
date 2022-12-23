package RSA;

import static Utility.Operation.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class RSA_Decryptor {
    String inputFileName, outputFileName, secretKeyFileName;
    ArrayList<BigInteger> cipherTextBlocks = new ArrayList<>(), decryptedBlocks = new ArrayList<>();
    String cipherText;
    BigInteger d, n, m;


    public RSA_Decryptor fromFile(String fileName){
        inputFileName = fileName;
        return this;
    }

    public RSA_Decryptor toFile(String fileName) {
        outputFileName = fileName;
        return this;
    }

    public RSA_Decryptor loadSecretKey(String fileName){
        secretKeyFileName = fileName;
        return this;
    }

    public String performDecryption() throws IOException {

        // Read text to decrypt
        BufferedReader readerEncrypted = new BufferedReader(new FileReader(inputFileName));
        cipherText = readerEncrypted.readLine();
        readerEncrypted.close();

        // Read secret key
        BufferedReader secretKeyReader = new BufferedReader(new FileReader(secretKeyFileName));
        d = new BigInteger(secretKeyReader.readLine());
        n = new BigInteger(secretKeyReader.readLine());
        secretKeyReader.close();

        // Iterate through all encrypted blocks of text
        String[] temp = cipherText.trim().split(" ");

        for (String b : temp) { cipherTextBlocks.add(new BigInteger(b)); }

        for (BigInteger block : cipherTextBlocks) {
            // Расшифрование численного представления
            m = bigIntPow(block, d).mod(n);
            decryptedBlocks.add(m);
        }

        int block_length = log2(n);
        StringBuilder result = new StringBuilder();

        for (BigInteger block : decryptedBlocks) {
            String binary = bigIngegerToBinaryString(block);
            int delta = block_length - binary.length();
            result.append("0".repeat(Math.max(0, delta)));
            result.append(binary);
        }
        result.reverse().setLength(64);
        result.reverse();

        if (outputFileName != null) {
            BufferedWriter outWriter = new BufferedWriter(new FileWriter(outputFileName));
            outWriter.write(result.toString());
            outWriter.close();
        }

        return result.toString();
    }

}
