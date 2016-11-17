package StringMatching;

import java.util.ArrayList;
import java.util.List;


public class KMPSearch {

    private static int count;
    private static String t, p;
    private static List<Integer> idxFound;

    public KMPSearch() {

    }

    public KMPSearch(String t, String p) {
        this.t = t;
        this.p = p;
        count = 0;
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


    private static void KMP() {
        int firstMatchIdx, currPosition;
        firstMatchIdx = currPosition = 0;
        int T[] = new int[t.length() + 1];
        KMPTableBuilder(p, T);

        while(firstMatchIdx + currPosition < t.length()) {
            if(currPosition < p.length() && p.charAt(currPosition) == t.charAt(firstMatchIdx + currPosition)) {
                if(currPosition == p.length() - 1){
                    //System.out.println("Pattern match found at index: " + firstMatchIdx);
                    count++;
                    idxFound.add(firstMatchIdx);
                }
                currPosition++;
            }else{
                if(T[currPosition] > -1){
                    firstMatchIdx = firstMatchIdx + currPosition - T[currPosition];
                    currPosition = T[currPosition];
                }else{
                    firstMatchIdx++;
                    currPosition = 0;
                }
            }
        }
        //System.out.println("Occurrences: " + count);
    }

    private static void KMPTableBuilder(String word, int arr[]) {
        int pos = 2;
        int cnd = 0;

        arr[0] = -1;
        arr[1] = 0;

        while(pos < word.length()) {
            if(word.charAt(pos-1) == word.charAt(cnd)) {
                arr[pos] = cnd + 1;
                cnd++;
                pos++;
            }else if(cnd > 0) {
                cnd = arr[cnd];
                arr[pos] = 0;
            }else {
                arr[pos] = 0;
                pos++;
            }
        }
    }

    public void run(){
        KMP();
    }
}
