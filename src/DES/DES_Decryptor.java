package DES;

import static Utility.Operation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DES_Decryptor {
    String inputFileName, outputFileName, secretKeyFileName;
    DES_Key key;
    String plainText;

    public String performDecryption() throws IOException {

        BufferedReader readerInput = new BufferedReader(new FileReader(inputFileName));
        plainText = readerInput.lines().collect(Collectors.joining("\n"));
        readerInput.close();

        BufferedReader readerKey = new BufferedReader(new FileReader(secretKeyFileName));
        key = new DES_Key(readerKey.lines().collect(Collectors.joining("\n")));
        readerKey.close();


        boolean[] bool = binaryStringToBoolArr(plainText);

        ArrayList<boolean[]> inBlocks = splitBlockIntoParts(bool, bool.length / 64), dBlocks = new ArrayList<>();

        inBlocks.forEach(b -> dBlocks.add(decryptBlock(b)));

        StringBuilder result = new StringBuilder();

        dBlocks.forEach(b -> result.append(boolArrToString(b)));


        if (outputFileName != null) {
            BufferedWriter writerOutput = new BufferedWriter(new FileWriter(outputFileName));
            writerOutput.write(formatedBoolStringtoString(result.toString()));
            writerOutput.close();
        }

        return formatedBoolStringtoString(result.toString());
    }

    private boolean[] decryptBlock(boolean[] inputBlock) {
        assert inputBlock.length == 64 : "Длина одного декодируемого блока равна 64 бита.";

        inputBlock = IP(inputBlock);

        boolean[][] left = new boolean[17][];
        boolean[][] right = new boolean[17][];

        left[0] = getLeftPart(inputBlock);
        right[0] = getRightPart(inputBlock);

        int j = 15;
        for (int i = 1; i <= 16; i++) {
            left[i] = right[i-1];
            right[i] = xor(left[i - 1], feistel(right[i - 1], key.getKeysArr()[j]));
            j--;
        }

        return FP(concat(right[16], left[16]));
    }

    public DES_Decryptor fromFile(String fileName){
        inputFileName = fileName;
        return this;
    }

    public DES_Decryptor toFile(String fileName){
        outputFileName = fileName;
        return this;
    }

    public DES_Decryptor loadSecretKey(String fileName){
        secretKeyFileName = fileName;
        return this;
    }

}
