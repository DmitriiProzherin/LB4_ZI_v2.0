package DES;

import static Utility.Operation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DES_Encryptor {

    String inputFileName, outputFileName, secretKeyFileName;
    PlainText plainText;
    DES_Key key;
    public String performEncryption(String strKey) throws IOException {

        BufferedReader readerInput = new BufferedReader(new FileReader(inputFileName));
        plainText = new PlainText(readerInput.lines().collect(Collectors.joining("\n")));
        readerInput.close();

        BufferedReader readerKey = new BufferedReader(new FileReader(secretKeyFileName));
        key = new DES_Key(readerKey.lines().collect(Collectors.joining("\n")));
        readerKey.close();

        ArrayList<boolean[]> inBlocks = plainText.getBlocksList(), eBlocks = new ArrayList<>();

        inBlocks.forEach(b -> eBlocks.add(encryptBlock(b, key)));

        StringBuilder result = new StringBuilder();

        eBlocks.forEach(b -> result.append(boolArrToString(b)));

        if (outputFileName != null) {
            BufferedWriter writerOutput = new BufferedWriter(new FileWriter(outputFileName));
            writerOutput.write(result.toString());
            writerOutput.close();
        }


        return result.toString();
    }

    public DES_Encryptor fromFile(String fileName){
        inputFileName = fileName;
        return this;
    }

    public DES_Encryptor toFile(String fileName){
        outputFileName = fileName;
        return this;
    }

    public DES_Encryptor loadSecretKey(String fileName){
        secretKeyFileName = fileName;
        return this;
    }

    // Шифровка одного блока.
    private boolean[] encryptBlock(boolean[] inputBlock, DES_Key key) {
        assert inputBlock.length == 64 : "Длина одного кодируемого блока равна 64 бита.";

        inputBlock = IP(inputBlock);

        boolean[][] left = new boolean[17][];
        boolean[][] right = new boolean[17][];


        left[0] = getLeftPart(inputBlock);
        right[0] = getRightPart(inputBlock);

        for (int i = 1; i<=16; i++) {
            left[i] = right[i-1];
            right[i] = xor(left[i - 1], feistel(right[i - 1], key.getKeysArr()[i - 1]));
        }

        return FP(concat(right[16], left[16]));
    }


}
