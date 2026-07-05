// class Solution {
//     public int[] nextGreaterElement(int[] nums1, int[] nums2) {
//         HashMap<Integer,Integer> map = new HashMap<>();
//         int[] ans = new int[nums1.length];
//         Arrays.fill(ans,-1);
//         for(int i =0 ; i< nums2.length ; i++){
//             map.put(nums2[i],i);
//         }
//         for(int j =0 ; j< nums1.length ; j++){
//             for(int i = map.get(nums1[j])+1 ; i<nums2.length; i++){
//                 if(nums2[i] > nums1[j]) {
//                     ans[j] = nums2[i];
//                     break;
//                 }

//             }
//         }
//         return ans;
        
//     }
// }
import java.util.*;

class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        // Map to store: [Element -> Its Next Greater Element]
        HashMap<Integer, Integer> map = new HashMap<>();
        Stack<Integer> stack = new Stack<>();
        
        // Traverse nums2 to find the next greater element for every number
        for (int num : nums2) {
            // While the current number is greater than the top element of the stack
            while (!stack.isEmpty() && stack.peek() < num) {
                map.put(stack.pop(), num); // map the popped element to its next greater
            }
            stack.push(num); // push current number to find its next greater later
        }
        
        // Build the answer array for nums1
        int[] ans = new int[nums1.length];
        for (int i = 0; i < nums1.length; i++) {
            // If it exists in map, take it; otherwise, default to -1
            ans[i] = map.getOrDefault(nums1[i], -1);
        }
        
        return ans;
    }
}