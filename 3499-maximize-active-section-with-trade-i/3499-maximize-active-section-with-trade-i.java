// //correct   tc= O(n) , sc = O(n)
// import java.util.ArrayList;
// class Solution {
//     public int maxActiveSectionsAfterTrade(String s) {
//         int ones = 0, n = s.length();
//         List<Integer> zerosBlockSize = new ArrayList<>();
//         for (int i = 0; i < n; ) {
//             int j = i;
//             while (j < n && s.charAt(j) == s.charAt(i)) j++;
//             int len = j - i;
//             if (s.charAt(i) == '1') {
//                 ones += len;
//             } else {
//                 zerosBlockSize.add(len);
//             }
//             i = j;
//         }
//         int maxZeroSum = 0;
//         for (int i = 0; i < zerosBlockSize.size() - 1; i++) {
//             maxZeroSum = Math.max(maxZeroSum, zerosBlockSize.get(i) + zerosBlockSize.get(i + 1));
//         }
//         return ones + maxZeroSum;
//     }
// }


// other way  tc= O(n) , sc = O(1)
class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int totalOnes = 0;
        int maxZeroSum = 0;
        int prevZeroLen = 0; // Tracks the length of the previous '0' segment
        
        int i = 0, n = s.length();
        while (i < n) {
            int j = i;
            while (j < n && s.charAt(j) == s.charAt(i)) {
                j++;
            }
            int len = j - i;
            
            if (s.charAt(i) == '1') {
                totalOnes += len;
            } else {
                // If we have seen at least one zero segment prior, consider merging
                if (prevZeroLen > 0) {
                    maxZeroSum = Math.max(maxZeroSum, prevZeroLen + len);
                }
                prevZeroLen = len;
            }
            i = j;
        }
        
        return totalOnes + maxZeroSum;
    }
}