package cphbusiness.ufo.letterfrequencies;

import java.io.*;
import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import static java.util.stream.Collectors.toMap;

/**
 * Frequency analysis Inspired by
 * https://en.wikipedia.org/wiki/Frequency_analysis
 *
 * @author kasper
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        String fileName = "/Users/benjaminschultzlarsen/Desktop/letterfrequencies/src/main/resources/FoundationSeries.txt";
        long[] times = new long[20];
        long sum = 0;
        for (int i = 0; i < 20; i++) {

            Reader reader = new FileReader(fileName);
            Map<Integer, Long> freq = new HashMap<>();

            long startTime = System.currentTimeMillis();
            tallyChars(reader, freq);
            long endTime = System.currentTimeMillis();

            times[i] = endTime-startTime;
            sum += endTime-startTime;
            System.out.println( (endTime-startTime) );
        }
        System.out.println("avg");
        System.out.println(sum/20);


    }

    /* gr 1 - original
    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        while ((b = reader.read()) != -1) {
            try {
                freq.put(b, freq.get(b) + 1);
            } catch (NullPointerException np) {
                freq.put(b, 1L);
            };
        }
    }

    gr 2
    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        for (int i = 0; i < 128; i++) {
            freq.put(i, 1L);
        }
        while ((b = reader.read()) != -1) {
            freq.put(b, freq.get(b) + 1);
        }
    }

    gr 3
    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        Long[] charCodeArray = new Long[128];
        for (int i = 0; i < 128; i++) {
            charCodeArray[i] = 0L;
        }


        while ((b = reader.read()) != -1) {
            charCodeArray[b]++;
        }

        for (int i = 0; i < charCodeArray.length; i++) {
            freq.put(i, charCodeArray[i]);
        }
    }

    gr4
    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int b;
        int[] charCodeArray = new int[128];
        for (int i = 0; i < 128; i++) {
            charCodeArray[i] = 0;
        }


        while ((b = reader.read()) != -1) {
            charCodeArray[b]++;
        }

        for (int i = 0; i < charCodeArray.length; i++) {
            freq.put(i, (long) charCodeArray[i]);
        }
    }

    gr5
    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int[] charCodeArray = new int[128];
        for (int i = 0; i < 128; i++) {
            charCodeArray[i] = 0;
        }


        try (BufferedReader buffer = new BufferedReader(reader)) {
            String b;
            while ((b = buffer.readLine()) != null) {
                for(char c: b.toCharArray()){
                    charCodeArray[c]++;
                }
            }
        }

        for (int i = 0; i < charCodeArray.length; i++) {
            freq.put(i, (long) charCodeArray[i]);
        }
    }*/




    // group 6

    private static void tallyChars(Reader reader, Map<Integer, Long> freq) throws IOException {
        int[] charCodeArray = new int[128];
        for (int i = 0; i < 128; i++) {
            charCodeArray[i] = 0;
        }


        try (BufferedReader buffer = new BufferedReader(reader)) {
            int b;
            while ((b = buffer.read()) != -1) {
                charCodeArray[b]++;
            }
        }

        for (int i = 0; i < charCodeArray.length; i++) {
            freq.put(i, (long) charCodeArray[i]);
        }
    }


    private static void print_tally(Map<Integer, Long> freq) {
        int dist = 'a' - 'A';
        Map<Character, Long> upperAndlower = new LinkedHashMap();
        for (Character c = 'A'; c <= 'Z'; c++) {
            upperAndlower.put(c, freq.getOrDefault(c, 0L) + freq.getOrDefault(c + dist, 0L));
        }
        Map<Character, Long> sorted = upperAndlower
                .entrySet()
                .stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));
        for (Character c : sorted.keySet()) {
            System.out.println("" + c + ": " + sorted.get(c));;
        }
    }
}
