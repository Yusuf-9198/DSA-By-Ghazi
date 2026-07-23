// find nearest power of 2 after n only for n>=3
// class Solution {
//     public int uniqueXorTriplets(int[] nums) {
//         int n = nums.length;
//         if (n<=) return n;
//         int bits = 32 - Integer.numberOfLeadingZeros(n);
//         return 1 << bits;
//     }
// }

class Solution {
    public int uniqueXorTriplets(int[] nums) {
        int n = nums.length;
        if (n<=2) return n;
        for(int i =1 ; ; i++){
            if(Math.pow(2,i) > n) return (int)Math.pow(2,i);
        }
        
    }
}