package RSA;

import static Utility.Operation.*;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RSA_Encryptor {
    BigInteger SEARCH_PRIME_AROUND = new BigInteger("10000");
    String plainText;
    String cipherText;
    String inputFileName, outputFileName;
    BigInteger p, q, n, f, e, d;


    // Get filename for input
    public RSA_Encryptor fromFile(String fileName) {
        inputFileName = fileName;
        return this;
    }

    // Get filename for output
    public RSA_Encryptor toFile(String fileName) {
        outputFileName = fileName;
        return this;
    }

    // Perform RSA encryption
    public String performEncryption() throws IOException {

        // Read from a file
        BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
        plainText = reader.lines().collect(Collectors.joining("\n"));
        reader.close();

        p = getRandomPrimeNumber(SEARCH_PRIME_AROUND);
        q = getRandomPrimeNumber(SEARCH_PRIME_AROUND);

        n = p.multiply(q);
        f = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = randomCoprimeToNumber(f);

        d = get_d(e, f);

        // Saving public key to a file
        BufferedWriter openKeyWriter = new BufferedWriter(new FileWriter("src/open_key_pair.txt"));
        openKeyWriter.write(e.toString() + "\n" + n.toString());
        openKeyWriter.close();

        // Saving public key to a file
        BufferedWriter privateKeyWriter = new BufferedWriter(new FileWriter("src/private_key_pair.txt"));
        privateKeyWriter.write(d.toString() + "\n" + n.toString());
        privateKeyWriter.close();

        int block_length = log2(n);

        String[] blocks = splitStringIntoBlocks(plainText, block_length);

        ArrayList<BigInteger> encrypted_blocks = new ArrayList<>();

        for (String block : blocks) {
            // Численное представление блока
            BigInteger p = binaryStringToBigInt(block);
            // Шифрование численного представления
            BigInteger c = bigIntPow(p, e).mod(n);
            encrypted_blocks.add(c);
        }

        cipherText = "";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName));
            encrypted_blocks.forEach(block -> cipherText = cipherText + block.toString() + " ");
            writer.write(cipherText.trim());
            writer.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        return cipherText.trim();
    }

    // Get random number which is coprime to number
    private BigInteger randomCoprimeToNumber(BigInteger number){
        BigInteger res = BigInteger.TWO;

        while (!res.gcd(number).equals(BigInteger.ONE)) res = res.add(BigInteger.ONE);

        return res;
    }

    // Evaluate d for rsa algorithm
    private BigInteger get_d(BigInteger e, BigInteger f) {
        BigInteger d, k = new BigInteger("1");
        while (!f.multiply(k).add(BigInteger.ONE).mod(e).equals(BigInteger.ZERO)) {
            k = k.add(BigInteger.ONE);
        }
        d = f.multiply(k).add(BigInteger.ONE).divide(e);
        assert f.min(d).signum() == 1;
        return d;
    }

}
