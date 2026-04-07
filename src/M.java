public class M {
    public static int[] apply(int[] x) {
        if (x == null || x.length != 4) {
            throw new IllegalArgumentException("input for M must be exactly 4 bytes");
        }
        int x0 = x[0] & 0xFF;
        int x1 = x[1] & 0xFF;
        int x2 = x[2] & 0xFF;
        int x3 = x[3] & 0xFF;
        int[] y = new int[4];
        y[0] = (GF256.mul2(x0) ^ GF256.mul3(x1) ^ x2 ^ x3) & 0xFF;
        y[1] = (x0 ^ GF256.mul2(x1) ^ GF256.mul3(x2) ^ x3) & 0xFF;
        y[2] = (x0 ^ x1 ^ GF256.mul2(x2) ^ GF256.mul3(x3)) & 0xFF;
        y[3] = (GF256.mul3(x0) ^ x1 ^ x2 ^ GF256.mul2(x3)) & 0xFF;
        return y;
    }
}