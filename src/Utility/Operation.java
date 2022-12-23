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

}
