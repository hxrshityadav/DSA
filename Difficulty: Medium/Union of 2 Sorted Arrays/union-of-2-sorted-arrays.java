class Solution {
    public static ArrayList<Integer> findUnion(int a[], int b[]) {
        // code here
        ArrayList<Integer> result = new ArrayList<>();
        
        int i = 0, j= 0;
        
        while (i <a.length && j<b.length){
            int val;
            if(a[i]< b[j]){
                val=a[i++];
            }
            else if(a[i]> b[j]){
                val=b[j++];
            } 
            else{
                val= a[i];
                i++;
                j++;
            }
            
            if(result.size() == 0 || result.get(result.size() -1)!= val){
                result.add(val);
            }
        }
        
        while(i< a.length){
            if(result.size()== 0 || result.get(result.size()-1)!= a[i]){
                result.add(a[i]);
            }
            i++;
        }
        while(j<b.length){
            if(result.size()==0 || result.get(result.size()-1)!=b[j]){
                result.add(b[j]);
            }
            j++;
        }
        return result;
    }
}
