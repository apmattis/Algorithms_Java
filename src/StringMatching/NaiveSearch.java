package StringMatching;

import java.util.ArrayList;
import java.util.List;


public class NaiveSearch {

    private static int count, ops;
    private static List<Integer> idxFound;
    private static String t, p;

    public NaiveSearch() {

    }

    public NaiveSearch(String t, String p) {
        this.t = t;
        this.p = p;
        idxFound = new ArrayList<>();
        count = 0;
    }

    public String getT() {
        return t;
    }

    String getP() {
        return p;
    }

    List getIdxFound() {
        return idxFound;
    }

    int getCount() {
        return count;
    }

    private static void Naive() {
        int n, m;

        n = t.length();
        m = p.length();

        for(int i = 0; i <= n - m; i++) {
            int j;
            ops++;

            for(j = 0; j < m; j++) {
                ops++;
                if(t.charAt(i+j) != p.charAt(j))
                    break;
            }
            if(j == m) {
                //System.out.println("Pattern match found at index: " + i);
                idxFound.add(i);
                count++;
            }
        }
        //System.out.println("Operations: " + ops);

    }

    public void run(){
        Naive();
    }
}
