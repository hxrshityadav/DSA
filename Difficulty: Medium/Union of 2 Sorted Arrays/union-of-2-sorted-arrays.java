import java.util.ArrayList;

class Solution {
    public static ArrayList<Integer> findUnion(int a[], int b[]) {
        ArrayList<Integer> union = new ArrayList<>();
        int i = 0, j = 0;
        int n = a.length;
        int m = b.length;

        
        while (i < n && j < m) {
            
            if (a[i] < b[j]) {
                addIfNew(union, a[i]);
                i++;
            }
            
            else if (b[j] < a[i]) {
                addIfNew(union, b[j]);
                j++;
            }
            
            else {
                addIfNew(union, a[i]);
                i++;
                j++;
            }
        }

        
        while (i < n) {
            addIfNew(union, a[i]);
            i++;
        }

        
        while (j < m) {
            addIfNew(union, b[j]);
            j++;
        }

        return union;
    }

    
    private static void addIfNew(ArrayList<Integer> list, int val) {
        
        if (list.isEmpty() || list.get(list.size() - 1) != val) {
            list.add(val);
        }
    }
}