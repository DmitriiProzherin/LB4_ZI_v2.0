import DES.DES;
import RSA.*;
import static Utility.Operation.createRandom64BitKey;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    // Generating random 64-bit key
    String given = createRandom64BitKey("src/key.txt");


    RSA rsa = new RSA();
    // Encrypting the key and saving key pairs
    String encrypted = rsa.encryptor.fromFile("src/key.txt").toFile("src/key_with_rsa.txt").performEncryption();


    // Decrypting key for DES
    String res = rsa.decryptor.fromFile("src/key_with_rsa.txt")
                    .loadSecretKey("src/private_key_pair.txt")
                    .toFile("src/decrypted_key.txt")
                    .performDecryption();


    // Encrypting text with DES
    DES des = new DES();
    des.encryptor.fromFile("src/input.txt").toFile("src/output.txt").loadSecretKey("src/key.txt").performEncryption(given);


    }
}