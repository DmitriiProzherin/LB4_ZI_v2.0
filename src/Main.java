import RSA.*;
import static Utility.Operation.createRandom64BitKey;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    // Generating random 64-bit key
    String given = createRandom64BitKey("src/key.txt");
        System.out.println(given);

    RSA rsa = new RSA();
    // Encrypting the key and saving key pairs
    String encrypted = rsa.encryptor.fromFile("src/key.txt").toFile("src/key_with_rsa.txt").performEncryption();
        System.out.println(encrypted);

    // Decrypting key for DES
    String res = rsa.decryptor.fromFile("src/key_with_rsa.txt")
                    .loadSecretKey("src/private_key_pair.txt")
                    .performDecryption();
        System.out.println(res);





    }
}