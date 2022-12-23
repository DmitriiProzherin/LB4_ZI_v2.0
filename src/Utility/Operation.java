package Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Operation {

    public static BigInteger getRandomPrimeNumber(BigInteger aroundValue){
        Random random = new Random();
        BigInteger randomNumber;
        do {
            randomNumber = new BigInteger(aroundValue.bitLength(), random);
        } while (randomNumber.compareTo(aroundValue) >= 0);

        if (randomNumber.mod(BigInteger.TWO).equals(BigInteger.ZERO))
            {
                randomNumber = randomNumber.add(BigInteger.ONE);
            }
        do {
            randomNumber = randomNumber.add(BigInteger.TWO);
        } while (!randomNumber.isProbablePrime(Integer.MAX_VALUE));

        return randomNumber;

    }

    public static String createRandom64BitKey(String fileName){
        boolean[] b = new boolean[64];
        Random r = new Random();

        for (int i = 0; i < 64; i++) {
            b[i] = r.nextBoolean();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(boolArrToString(b));
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return boolArrToString(b);
    }

    public static String boolArrToString(boolean[] arr){
        StringBuilder r = new StringBuilder();
        for (boolean b : arr) {
            if (b) r.append("1");
            else r.append("0");
        }
        return r.toString();
    }

    public static int log2(BigInteger number){
        int i = 0;
        while (number.min(BigInteger.TWO).signum() == 1) {
            number = number.divide(BigInteger.TWO);
            i++;
        }
        return i - 1;
    }

    public static String[] splitStringIntoBlocks(String s, int block_length){
        int block_numbers = s.length() % block_length == 0 ? s.length() / block_length : s.length() / block_length + 1;

        int delta = block_length * block_numbers - s.length();
        char[] chars = new char[delta];
        Arrays.fill(chars, '0');
        String prefix = new String(chars);
        s = prefix + s;

        return s.split("(?<=\\G.{"+block_length+"})");
    }

    public static BigInteger binaryStringToBigInt(String s){
        BigInteger res = BigInteger.ZERO;
        for (int i = s.length() - 1; i >=0 ; i--) {
            if (s.charAt(i) == '1') res = res.add(BigInteger.TWO.pow(s.length() - i - 1));
        }
        return res;
    }

    public static BigInteger bigIntPower(BigInteger a, BigInteger b){
        BigInteger temp = new BigInteger(a.toByteArray()), i = new BigInteger(b.toByteArray());
        while (!i.equals(BigInteger.ONE)) {
            temp = temp.multiply(a);
            i = i.subtract(BigInteger.ONE);
        }
        return temp;
    }

    public static String bigIngegerToBinaryString(BigInteger integer){
        StringBuilder s = new StringBuilder();

        while (!integer.equals(BigInteger.ZERO)) {
            s.append(integer.mod(BigInteger.TWO));
            integer = integer.divide(BigInteger.TWO);
        }

        return s.reverse().toString();

    }

    public static ArrayList<boolean[]> splitBlockIntoParts(boolean[] block, int n) {
        assert n <= block.length : "Количество частей не должно превышать длину блока.";

        ArrayList<boolean[]> resultList = new ArrayList<>();
        int length = block.length / n;

        for (int i = 0; i < n; i++) {
            resultList.add(Arrays.copyOfRange(block, i * length, i * length + length));
        }


        return resultList;
    }

    public static boolean[] binaryStringToBoolArr(String str) {
        boolean[] result = new boolean[str.length()];

        for (int i = 0; i < result.length; i++){
            result[i] = (str.charAt(i) == '1');
        }
        return result;

    }

    // Выполняет циклический сдвиг введённого массива влево на n позиций.
    public static void shiftLeft(boolean[] booleans, int n) {
        int shiftLength = n % booleans.length;
        boolean[] temp = booleans.clone();

        System.arraycopy(temp, shiftLength, booleans, 0, booleans.length - shiftLength);
        System.arraycopy(temp, 0, booleans, booleans.length - shiftLength, booleans.length - (booleans.length - shiftLength));
    }

    // Конкатенация двух булевых массивов в 1 результирующий массив.
    public static boolean[] concat(boolean[] arr1, boolean[] arr2) {

        if (arr1 == null) return arr2;
        else if (arr2 == null) return arr1;

        boolean[] result = Arrays.copyOf(arr1, arr1.length + arr2.length);
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        return result;
    }

    // Перемешивание элементов в массиве согласно данной матрице.
    public static boolean[] mix(boolean[] inputArr, int resLength, byte[] mixArr) {
        boolean[] result = new boolean[resLength];

        for (int i = 0; i < resLength; i++) {
            result[i] = inputArr[mixArr[i] - 1];
        }
        return result;


    }

    public static boolean[] byteToBoolArr(byte[] in) {
        boolean[] result = new boolean[in.length];

        for (int i = 0; i < in.length; i++) {
            assert in[i] == 1 || in[i] == 0 : "Перевод возможен только для массива из 0 и 1.";
            result[i] = (in[i] == 1);
        }
        return result;
    }

    public static byte[] strToByteArr(String str) {
        StringBuilder resStrB = new StringBuilder();
        byte[] result = new byte[str.length() * 16];

        for (int i = 0; i < str.length(); i++) {
            String s = Integer.toBinaryString(str.charAt(i));
            resStrB.append("0".repeat(Math.max(0, 16 - s.length())));
            resStrB.append(s);
        }
        for (int i = 0; i < result.length; i++) {
            result[i] = (byte) Character.getNumericValue(resStrB.charAt(i));
        }

        return result;
    }

    // Конечная перестановка, обратная начальной перестановке.
    public static boolean[] FP(boolean[] inp) {
        assert inp.length == 64 : "Длина блока для перестановки должна быть 64 бита.";
        byte[] permVector = new byte[] {
                40,  8, 48, 16, 56, 24, 64, 32,
                39,  7, 47, 15, 55, 23, 63, 31,
                38,  6, 46, 14, 54, 22, 62, 30,
                37,  5, 45, 13, 53, 21, 61, 29,
                36,  4, 44, 12, 52, 20, 60, 28,
                35,  3, 43, 11, 51, 19, 59, 27,
                34,  2, 42, 10, 50, 18, 58, 26,
                33,  1, 41,  9, 49, 17, 57, 25
        };
        return mix(inp, 64, permVector);
    }

    // Выполнение первоначальной перестановки.
    public static boolean[] IP(boolean[] inp) {
        assert inp.length == 64 : "Длина блока для перестановки должна быть 64 бита.";

        byte[] permVector = new byte[] {
                58, 50, 42, 34, 26, 18, 10, 2,
                60, 52, 44, 36, 28, 20, 12, 4,
                62, 54, 46, 38, 30, 22, 14, 6,
                64, 56, 48, 40, 32, 24, 16, 8,
                57, 49, 41, 33, 25, 17,  9, 1,
                59, 51, 43, 35, 27, 19, 11, 3,
                61, 53, 45, 37, 29, 21, 13, 5,
                63, 55, 47, 39, 31, 23, 15, 7
        };

        return mix(inp, 64, permVector);
    }

    // Применение функции Фейстеля.
    public static boolean[] feistel(boolean[] inBlock, boolean[] key) {
        assert inBlock.length == 32 : "Длина блока в функции Фейстеля должна быть 32 бита.";
        assert key.length == 48 : "Длина ключа в функции Фейстеля должна быть 48 бит.";

        boolean[] e = ext(inBlock);

        ArrayList<boolean[]> blocks = splitBlockIntoParts(xor(e, key), 8);

        boolean[] res = null;

        for (boolean[] b :
                blocks) {
            b = sTransform(b, blocks.indexOf(b));
            res = concat(res, b);
        }

        assert res != null;

        return P(res);
    }


    // Перестановка в функции Фейстеля.
    public static boolean[] P(boolean[] inp) {
        assert inp.length == 32 : "Длина блока для перестановки должна быть 32 бита.";

        byte[] permVector = new byte[] {
                16,  7,  20,  21,
                29,  12,  28,  17,
                1,  15,  23,  26,
                5,  18,  31,  10,
                2,   8,  24,  14,
                32,  27,  3,   9,
                19,  13,  30,   6,
                22,  11,   4,  25
        };

        return mix(inp, 32, permVector);
    }

    // Применение функции S-трансформации к расширенному блоку.
    public static boolean[] sTransform(boolean[] block, int blockNo) {
        assert block.length == 6 : "Длина блока для S-преобразоания должна быть 6 бит";

        byte[][] sTable = getSTable(blockNo);

        int i = getRowIndex(block);
        // System.out.println("Индекс строки: " + i);
        int j = getColIndex(block);
        //   System.out.println("Индекс столбца: " + j);

        String str = Integer.toBinaryString(sTable[i][j]);
        boolean[] resultBlock = new boolean[4];

        int p = 3;
        for (int k = str.length() - 1; k >= 0; k--) {
            resultBlock[p] = str.charAt(k) == '1';
            p--;
        }
        return resultBlock;
    }

    // Расширение блока до размера в 48 бит.
    public static boolean[] ext(boolean[] booleans) {
        assert booleans.length == 32 : "Длина блока для расширения должна быть 32 бита";

        byte[] mixVector = new byte[] {
                32,  1,  2,  3,  4,  5,
                4,  5,  6,  7,  8,  9,
                8,  9, 10, 11, 12, 13,
                12, 13, 14, 15, 16, 17,
                16, 17, 18, 19, 20, 21,
                20, 21, 22, 23, 24, 25,
                24, 25, 26, 27, 28, 29,
                28, 29, 30, 31, 32,  1
        };

        return mix(booleans, 48, mixVector);
    }

    public static byte[][] getSTable(int i) {
        assert i>=0 && i<=7 : "Индексы таблиц от 0 до 7.";


        // 8 S-таблиц

        byte[][] s1 = new byte[][] {
                {14,  4, 13,  1,  2, 15, 11,  8,  3, 10,  6, 12,  5,  9,  0,  7},
                { 0, 15,  7,  4, 14,  2, 13,  1, 10,  6, 12, 11,  9,  5,  3,  8},
                { 4,  1, 14,  8, 13,  6,  2, 11, 15, 12,  9,  7,  3, 10,  5,  0},
                {15, 12,  8,  2,  4,  9,  1,  7,  5, 11,  3, 14, 10,  0,  6, 13}
        };

        byte[][] s2 = new byte[][] {
                { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
                { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
        };

        byte[][] s3 = new byte[][] {
                { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
                { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
        };

        byte[][] s4 = new byte[][] {
                { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
                { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
        };

        byte[][] s5 = new byte[][] {
                { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
                { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
        };

        byte[][] s6 = new byte[][] {
                { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
                { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }
        };

        byte[][] s7 = new byte[][] {
                { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
                { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
        };

        byte[][] s8 = new byte[][] {
                { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
                { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }
        };


        return switch (i) {
            case 0 -> s1;
            case 1 -> s2;
            case 2 -> s3;
            case 3 -> s4;
            case 4 -> s5;
            case 5 -> s6;
            case 6 -> s7;
            case 7 -> s8;
            default -> null;
        };
    }

    public static int getRowIndex(boolean[] block) {

        byte i, j;

        if (block[0]) i = 1;
        else i = 0;

        if (block[5]) j = 1;
        else j = 0;

        return i * 2 + j;
    }

    public static int getColIndex(boolean[] block) {

        int p = 3, res = 0;

        for (int i = 1; i < 5; i++) {
            if (block[i]) {
                res += Math.pow(2, p);
            }
            p--;
        }

        return res;
    }

    // Применение xor к двум массивам битов одинаковой длины.
    public static boolean[] xor(boolean[] block1, boolean[] block2) {
        assert block1.length == block2.length : "Блоки должны быть равной длины.";

        boolean[] result = new boolean[block1.length];

        for (int i = 0; i < block1.length; i++) {
            result[i] = block1[i] ^ block2[i];
        }

        return result;
    }

    // Выделение левой части блока.
    public static boolean[] getLeftPart(boolean[] b) {
        return splitBlockIntoParts(b, 2).get(0);
    }

    // Выделение правой части блока.
    public static boolean[] getRightPart(boolean[] b) {
        return splitBlockIntoParts(b, 2).get(1);
    }

}
