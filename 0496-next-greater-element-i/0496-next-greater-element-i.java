class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer,Integer> map = new HashMap<>();
        int[] ans = new int[nums1.length];
        Arrays.fill(ans,-1);
        for(int i =0 ; i< nums2.length ; i++){
            map.put(nums2[i],i);
        }
        for(int j =0 ; j< nums1.length ; j++){
            for(int i = map.get(nums1[j])+1 ; i<nums2.length; i++){
                if(nums2[i] > nums1[j]) {
                    ans[j] = nums2[i];
                    break;
                }

            }
        }
        return ans;
        
    }
}