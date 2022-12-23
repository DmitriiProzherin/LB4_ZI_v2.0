import DES.DES;
import RSA.*;
import static Utility.Operation.createRandom64BitKey;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    // Generating random 64-bit key
    createRandom64BitKey("src/key.txt");

    // Encrypting text with DES
    DES des = new DES();
    des.encryptor.fromFile("src/input_text.txt")
                .toFile("src/encrypted_text.txt")
                .loadSecretKey("src/key.txt")
                .performEncryption();

    RSA rsa = new RSA();
    // Encrypting the key and saving key pairs
    rsa.encryptor.fromFile("src/key.txt")
                .toFile("src/key_with_rsa.txt")
                .performEncryption();


    // Decrypting key for DES
    rsa.decryptor.fromFile("src/key_with_rsa.txt")
                .loadSecretKey("src/private_key_pair.txt")
                .toFile("src/decrypted_key.txt")
                .performDecryption();


    // Decrypting text with DES
    des.decryptor.fromFile("src/encrypted_text.txt")
                .toFile("src/decrypted_text.txt")
                .loadSecretKey("src/decrypted_key.txt")
                .performDecryption();

    }
}