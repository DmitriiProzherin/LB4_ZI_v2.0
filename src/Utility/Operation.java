package Utility;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
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

    public static BigInteger bigIntPow(BigInteger a, BigInteger b){
        BigInteger temp = new BigInteger(a.toByteArray()), i = new BigInteger(b.toByteArray());
        while (!i.equals(BigInteger.ONE)) {
            temp = temp.multiply(a);
            i = i.subtract(BigInteger.ONE);
        }
        return temp;
    }
}
