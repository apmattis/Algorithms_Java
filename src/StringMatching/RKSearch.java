package StringMatching;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RKSearch {

    private static final long q = longRandomPrime();
    private static final int R = 256;

    private static int count;
    private static long hPat, ops;
    private static long hText;
    private static long x;
    private static String t, p;
    private static List<Integer> idxFound;

    public RKSearch() {

    }

    public RKSearch(String t, String p) {
        this.t = t;
        this.p = p;
        hPat = hash(p, 0, p.length());
        hText = hash(t, 0, p.length());
        x = setX();
        count = 0;
        ops = 0;
        idxFound = new ArrayList<>();
    }

    public String getT() {
        return t;
    }

    public String getP() {
        return p;
    }

    public List getIdxFound() {
        return idxFound;
    }

    public int getCount() {
        return count;
    }

    public static long setX(){
        long x = 1;
        for(int i = 0; i < p.length() - 1; i++)
            x = (x * R) % q;
        return x;
    }

    private static void RabinKarp() {
        int n = t.length();
        int m = p.length();

        for(int i = 0; i <= n - m; i++) {
            ops++;
            if (hText == hPat) {
                int j;
                for (j = 0; j < m; j++) {
                    ops++;
                    if (t.charAt(i+j) != p.charAt(j)) break;
                }
                if (j == m){
                    count++;
                    idxFound.add(i);
                }
            }
            hText = rehash(hText, t, i);
        }
        //System.out.println("Operations: " + ops);
    }

    private static long rehash(long h, String key, int i) {
        int n = t.length();
        int m = p.length();
        if ( i < n - m ) {
            h = (R * (h - key.charAt(i) * x) + key.charAt(i + m)) % q;
            ops++;

            if (h < 0)
                h = (h + q);
        }
        return h;
    }

    private static long hash(String key, int i, int m) {
        long h = 0;
        if(m <= key.length()){
            for (int j = i; j < m; j++)
                h = (R * h + key.charAt(j)) % q;
        }
        return h;
    }

    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

    public void run() {
        RabinKarp();
    }
}
