class Solution {
    ArrayList<String> list = new ArrayList<>();

    public void printParenthesis(int open , int close, int n, String s) {
       if(s.length() == 2*n) {
           list.add(s);
           return;
       }
if(open<n) printParenthesis(open+1,close,n,s+"(");
if(close < open) printParenthesis(open, close+1 ,n,s +")");

    }
    public List<String> generateParenthesis(int n) {
     printParenthesis(0, 0 , n , "");   
     return list;
    }
}