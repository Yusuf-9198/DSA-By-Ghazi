class Solution {
    public int maxProfit(int[] prices) {
        int n = prices.length;
        if (prices == null || n < 2) return 0;
        int minprice=prices[0], maxpro= 0;
        for(int i =1; i< n; i++){
            if(minprice > prices[i]) minprice = prices[i];
            else{
            int temp = prices[i] - minprice;
            if(temp > maxpro) maxpro = temp;
            }

        }
        if(maxpro < 0) return 0;
        return maxpro;

    }    
}