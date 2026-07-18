// class Solution {
//     public int findGCD(int[] nums) {
//         int n = nums.length;
//         Arrays.sort(nums);
//         if(nums[n-1] % nums[0] == 0) return nums[0];
//         int hcf = 1;
//         // for(int i =1 ; i<nums[n-1]*nums[0];i++){
//         //     if(nums[n-1] % i ==0 && nums[0] % i ==0) hcf = i;
//         // }  // Also correct
//         for(int i = nums[0] ; i>1 ; i--){    
//             if(nums[n-1] % i ==0 && nums[0] % i ==0) return i;
//         }
//         return hcf;
//     }
// }

// More Optimised
class Solution {
    public int findGCD(int[] nums) {
        int min = nums[0];
        int max = nums[0];
        
        // Find min and max in one pass: O(N)
        for (int num : nums) {
            if (num < min) min = num;
            if (num > max) max = num;
        }
        
        return gcd(max, min);
    }
    
    // Classic Euclidean Algorithm: O(log(min))
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}