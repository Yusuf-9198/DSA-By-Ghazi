class Solution {
    // public int maxOperations(int[] nums, int k) {
    //     int n = nums.length;
    //     int countop= 0;
    //     HashMap<Integer,Integer> map = new HashMap<>();
    //     for(int i =0 ; i<n; i++){
    //         int key = k- nums[i];
    //         if(map.containsKey(key) && map.get(key) > 0){
    //             map.put(key,map.get(key)-1);
    //             countop++;

    //         }
    //         else map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
    //     }
    //     return countop;
    // }
    public int maxOperations(int[] nums, int k) {
        IntHashMap map = new IntHashMap(nums.length, 1f);
        int count = 0;
        for(int num : nums) {
            if(map.getOrDefault(k - num, 0) > 0) {
                map.merge(k - num, -1);
                count++;
            }else if(num < k) map.merge(num, 1);
        }
        return count;
    }
}

class IntHashMap {
    private long[] tab;
    private int mask, size, resizeAt;
    private final float loadFactor;
    public IntHashMap() {
        this(32, 0.75f);
    }
    public IntHashMap(int expectedKeys) {
        this(expectedKeys, 0.75f);
    }
    public IntHashMap(int expectedKeys, float loadFactor) {
        int cap = 1;
        int need = (int)(expectedKeys / loadFactor) + 1;
        while(cap < need) cap <<= 1;

        this.tab = new long[cap];
        this.mask = cap - 1;
        this.resizeAt = (int)(cap * loadFactor);
        this.loadFactor = loadFactor;
    }

    public Integer get(int key) {
        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) return null;
            if((slot >>> 32) == k1) return (int)slot;
            idx = idx + 1 & mask;
        }
    }
    public int getOrDefault(int key, int val) {
        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) return val;
            if((slot >>> 32) == k1) return (int)slot;
            idx = idx + 1 & mask;
        }
    }
    public int computeIfAbsent(int key, int valueIfAbsent) {
        if(size >= resizeAt) rehash();

        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) {
                tab[idx] = k1 << 32 | valueIfAbsent & 0xffffffffL;
                size++;
                return valueIfAbsent;
            }
            if((slot >>> 32) == k1) return (int)slot;
            idx = idx + 1 & mask;
        }
    }
    public boolean containsKey(int key) {
        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) return false;
            if((slot >>> 32) == k1) return true;
            idx = idx + 1 & mask;
        }
    }
    public void put(int key, int value) {
        if(size >= resizeAt) rehash();

        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) {
                tab[idx] = k1 << 32 | value & 0xffffffffL;
                size++;
                return;
            }
            if((slot >>> 32) == k1) {
                tab[idx] = k1 << 32 | value & 0xffffffffL;
                return;
            }
            idx = idx + 1 & mask;
        }
    }
    public void merge(int key, int value) {
        if(size >= resizeAt) rehash();

        final long k1 = key + 1L;
        int idx = mix(key) & mask;
        while(true) {
            final long slot = tab[idx];
            if(slot == 0L) {
                tab[idx] = k1 << 32 | value & 0xffffffffL;
                size++;
                return;
            }
            if((slot >>> 32) == k1) {
                tab[idx] = k1 << 32 | ((int)slot + value) & 0xffffffffL;
                return;
            }
            idx = idx + 1 & mask;
        }
    }

    private void rehash() {
        final long[] old = tab;
        tab = new long[old.length << 1];
        mask = tab.length - 1;
        resizeAt = (int)(tab.length * loadFactor);
        size = 0;

        for(long slot : old) {
            if(slot == 0L) continue;
            final int key = (int)((slot >>> 32) - 1L);
            int idx = mix(key) & mask;
            while(tab[idx] != 0L) idx = idx + 1 & mask;
            tab[idx] = slot;
            size++;
        }
    }
    private static int mix(int x) {
        return x * 0x9E3779B9;
    }
}