// not done yet
import java.util.*;

class SegmentTree {
    private int n;
    private int[] tree;

    public SegmentTree(int[] values) {
        this.n = values.length;
        this.tree = new int[4 * Math.max(1, n)];

        if (this.n > 0) {
            build(1, 0, this.n - 1, values);
        }
    }

    private void build(int node, int left, int right, int[] values) {
        if (left == right) {
            this.tree[node] = values[left];
            return;
        }

        int mid = (left + right) / 2;
        build(node * 2, left, mid, values);
        build(node * 2 + 1, mid + 1, right, values);
        this.tree[node] = Math.max(this.tree[node * 2], this.tree[node * 2 + 1]);
    }

    public int query(int queryLeft, int queryRight) {
        if (queryLeft > queryRight || this.n == 0) {
            return 0;
        }

        return query(1, 0, this.n - 1, queryLeft, queryRight);
    }

    private int query(int node, int left, int right, int queryLeft, int queryRight) {
        if (queryLeft <= left && right <= queryRight) {
            return this.tree[node];
        }

        int mid = (left + right) / 2;
        int answer = 0;

        if (queryLeft <= mid) {
            answer = Math.max(answer, query(node * 2, left, mid, queryLeft, queryRight));
        }

        if (queryRight > mid) {
            answer = Math.max(answer, query(node * 2 + 1, mid + 1, right, queryLeft, queryRight));
        }

        return answer;
    }
}

public class Solution {
    private int bisectLeft(List<Integer> list, int target) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (list.get(mid) < target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int bisectRight(List<Integer> list, int target) {
        int low = 0, high = list.size();
        while (low < high) {
            int mid = (low + high) >>> 1;
            if (list.get(mid) <= target) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();

        int originalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                originalOnes++;
            }
        }

        List<Integer> zeroLengths = new ArrayList<>();
        List<Integer> zeroStarts = new ArrayList<>();
        List<Integer> zeroEnds = new ArrayList<>();

        int index = 0;
        while (index < n) {
            int start = index;
            while (index < n && s.charAt(index) == s.charAt(start)) {
                index++;
            }

            if (s.charAt(start) == '0') {
                zeroLengths.add(index - start);
                zeroStarts.add(start);
                zeroEnds.add(index - 1);
            }
        }

        int zeroCount = zeroLengths.size();

        List<Integer> answers = new ArrayList<>(queries.length);
        if (zeroCount < 2) {
            for (int i = 0; i < queries.length; i++) {
                answers.add(originalOnes);
            }
            return answers;
        }

        int[] gains = new int[zeroCount - 1];
        for (int i = 0; i < zeroCount - 1; i++) {
            gains[i] = zeroLengths.get(i) + zeroLengths.get(i + 1);
        }

        SegmentTree segmentTree = new SegmentTree(gains);

        for (int[] query : queries) {
            int left = query[0];
            int right = query[1];

            int firstBlock = bisectLeft(zeroEnds, left);
            int lastBlock = bisectRight(zeroStarts, right) - 1;

            if (firstBlock >= zeroCount || lastBlock < 0 || firstBlock >= lastBlock) {
                answers.add(originalOnes);
                continue;
            }

            int firstLength = zeroEnds.get(firstBlock) - Math.max(zeroStarts.get(firstBlock), left) + 1;
            int lastLength = Math.min(zeroEnds.get(lastBlock), right) - zeroStarts.get(lastBlock) + 1;

            if (firstBlock + 1 == lastBlock) {
                int bestGain = firstLength + lastLength;
                answers.add(originalOnes + bestGain);
                continue;
            }

            int leftBoundaryGain = firstLength + zeroLengths.get(firstBlock + 1);
            int rightBoundaryGain = zeroLengths.get(lastBlock - 1) + lastLength;
            int internalGain = segmentTree.query(firstBlock + 1, lastBlock - 2);

            int bestGain = Math.max(leftBoundaryGain, Math.max(rightBoundaryGain, internalGain));
            answers.add(originalOnes + bestGain);
        }

        return answers;
    }
}

// class Solution {
//     List<Integer> ans = new ArrayList<>();
//     public void maxActiveSection(String s){
//         int n = s.length(), ones = 0;
//         List<Integer> zeroBlockSize = new ArrayList<>();
//         for(int i = 0 ; i < n;){
//             int j = i;
//             while(j<n && s.charAt(j) == s.charAt(i)) j++;
//             int len = j-i;
//             if(s.charAt(i) == '1') ones += len;
//             else zeroBlockSize.add(len);
//             i=j;
//         }
//         int maxZerosSum = 0;
//         if (zeroBlockSize.size() == 1) {
//             maxZerosSum = zeroBlockSize.get(0);
//         }
//         else if (zeroBlockSize.size() > 1) {
//             for (int i = 0; i < zeroBlockSize.size() - 1; i++) {
//                 maxZerosSum = Math.max(maxZerosSum, zeroBlockSize.get(i) + zeroBlockSize.get(i + 1));
//             }
//         }
//         ans.add(ones + maxZerosSum);
//     }
//     public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
//         int n = s.length();
//         for(int i = 0 ; i<queries.length; i++ ){
//             String s1 = "";
//             if(queries[i][1] == n-1){
//                 s1 = s.substring(queries[i][0]);
//             }
//             else s1 = s.substring(queries[i][0] , queries[i][1] +1);
//             maxActiveSection(s1);
//         }
//         return ans;
        
//     }
// }
