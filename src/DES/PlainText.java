package DES;

import static Utility.Operation.*;

import java.util.ArrayList;

public class PlainText {

    private final ArrayList<boolean[]> blocksList;

    PlainText(String string) {
        boolean[] binary = byteToBoolArr(strToByteArr(string));
        int tail = (64 - binary.length % 64) % 64;
        boolean[] extBinary = new boolean[binary.length + tail];
        System.arraycopy(binary, 0, extBinary, tail, binary.length);
        blocksList = splitBlockIntoParts(extBinary, extBinary.length / 64);
    }

    public ArrayList<boolean[]> getBlocksList() {
        return blocksList;
    }

}
