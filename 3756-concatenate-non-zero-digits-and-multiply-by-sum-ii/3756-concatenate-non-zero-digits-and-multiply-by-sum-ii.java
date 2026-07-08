 // My code TLE error

// private static final int MOD = 1_000_000_007; // Underscores make it easy to read
// class Solution {
//     public int number(String s , int l , int r){
//         String temp = "";
//         long sum =0;
//         for(int i =l ; i<r+1 ; i++){
//             if(s.charAt(i) !='0') temp = temp.concat(String.valueOf(s.charAt(i)));
//             sum += Character.getNumericValue(s.charAt(i));
//         }
//         if(temp.isEmpty()) return 0;
//         long num = Long.parseLong(temp.toString()) % MOD;
//         sum = sum % MOD;  
//     int result = (int) ((num * sum) % MOD);
//     return result;
//     }
//     public int[] sumAndMultiply(String s, int[][] queries) {
//         int len = s.length();
//         int n = queries.length;
//         int[] ans = new int[n];
//         for(int i =0 ; i<n ; i++){
//             ans[i] = number(s,queries[i][0],queries[i][1]);
//         }
//         return ans;
//     }
// }
class Solution {
    private static final int MOD = 1_000_000_007;

    public int[] sumAndMultiply(String s, int[][] queries) {
        int n = s.length();
        int numQueries = queries.length;
        
        // 1. Precompute powers of 10 modulo MOD
        int[] powerOf10 = new int[n + 1];
        powerOf10[0] = 1;
        for (int i = 1; i <= n; i++) {
            powerOf10[i] = (int) (((long) powerOf10[i - 1] * 10) % MOD);
        }

        // 2. Build Prefix Arrays
        int[] prefixSum = new int[n + 1];
        int[] prefixNum = new int[n + 1];
        int[] nonZeroCount = new int[n + 1]; // Tracks count of non-zero digits up to index i

        for (int i = 0; i < n; i++) {
            int digit = s.charAt(i) - '0';
            
            prefixSum[i + 1] = (prefixSum[i] + digit) % MOD;
            
            if (digit != 0) {
                prefixNum[i + 1] = (int) (((long) prefixNum[i] * 10 + digit) % MOD);
                nonZeroCount[i + 1] = nonZeroCount[i] + 1;
            } else {
                // Since your logic ignores zeros completely, the value doesn't change
                prefixNum[i + 1] = prefixNum[i];
                nonZeroCount[i + 1] = nonZeroCount[i];
            }
        }

        // 3. Answer every query instantly in O(1) time
        int[] ans = new int[numQueries];
        for (int q = 0; q < numQueries; q++) {
            int l = queries[q][0];
            int r = queries[q][1];

            // How many non-zero numbers are actually in this window?
            int nonZerosInWindow = nonZeroCount[r + 1] - nonZeroCount[l];
            
            // If the window is all '0's, the result is 0
            if (nonZerosInWindow == 0) {
                ans[q] = 0;
                continue;
            }

            // Calculate range sum: (prefixSum[r+1] - prefixSum[l])
            int sum = prefixSum[r + 1] - prefixSum[l];
            if (sum < 0) sum += MOD;

            // Calculate range number mathematically by stripping away the left prefix side
            long rem = ((long) prefixNum[l] * powerOf10[nonZerosInWindow]) % MOD;
            int num = (int) (prefixNum[r + 1] - rem);
            if (num < 0) num += MOD;
            
            // Multiply the calculated parts together
            ans[q] = (int) (((long) num * sum) % MOD);
        }

        return ans;
    }
}