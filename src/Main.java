import RSA.*;
import static Utility.Operation.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

    // Generating random 64-bit key
    createRandom64BitKey("src/key.txt");

    // Encrypting the key and saving key pairs
    RSA.encryptor.fromFile("src/key.txt").toFile("src/key_with_rsa").performEncryption();





    }
}