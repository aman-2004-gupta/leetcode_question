class Solution {
    public int numOfWays(int n) {
        long MOD = 1_000_000_007;

        long a = 6; // type ABC
        long b = 6; // type ABA

        for (int i = 2; i <= n; i++) {
            long newA = (2 * a + 2 * b) % MOD;
            long newB = (2 * a + 3 * b) % MOD;
            a = newA;
            b = newB;
        }

        return (int)((a + b) % MOD);
    }
}
