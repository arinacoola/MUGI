public class MugiCore {
    private MugiState state;
    public MugiCore(MugiState state) {
        this.state = state;
    }

    private static long[] rho1(long a0, long a1, long a2, long w1, long w2) {
        long newA0 = a1;
        long newA1 = a2 ^ f(a1, w1) ^ MugiTables.D1;
        long newA2 = a0 ^ f(a1, Long.rotateLeft(w2, 17)) ^ MugiTables.D2;
        return new long[]{newA0, newA1, newA2};
    }

    private static long[] lambda1(long[] b, long a) {
        if (b == null || b.length != 16) {
            throw new IllegalArgumentException("b must contain exactly 16 words");
        }
        long[] newB = new long[16];
        for (int j = 0; j < 16; j++) {
            if (j != 0 && j != 4 && j != 10) {
                newB[j] = b[j - 1];
            }
        }
        newB[0] = b[15]^ a;
        newB[4] = b[3]^ b[7];
        newB[10] = b[9]^ Long.rotateLeft(b[13], 32);
        return newB;
    }

    private static int[] m(int[] x) {
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

    private static long f(long x, long t){
        long xPrime = x ^ t;
        int[] bytes = new int[8];
        for (int i = 0; i < 8; i++) {
            bytes[i] = (int) ((xPrime >>> (56 - 8 * i)) & 0xFF);
        }
        int[] p = new int[8];
        for (int i = 0; i < 8; i++) {
            p[i] = MugiTables.sr(bytes[i]);
        }
        int[] left = {p[0], p[1], p[2], p[3]};
        int[] right = {p[4], p[5], p[6], p[7]};
        int[] ql = m(left);
        int[] qr = m(right);
        int[] resBytes = new int[8];
        resBytes[0] = qr[0]; // Q4
        resBytes[1] = qr[1]; // Q5
        resBytes[2] = ql[2]; // Q2
        resBytes[3] = ql[3]; // Q3
        resBytes[4] = ql[0]; // Q0
        resBytes[5] = ql[1]; // Q1
        resBytes[6] = qr[2]; // Q6
        resBytes[7] = qr[3]; // Q7
        long y = 0;
        for (int i = 0; i < 8; i++) {
            y = (y << 8) | (resBytes[i] & 0xFFL);
        }
        return y;
    }

    public void next() {
        long[] newA = rho1(state.a0, state.a1, state.a2, state.b[4], state.b[10]);
        long[] newB = lambda1(state.b, state.a0);
        state.a0 = newA[0];
        state.a1 = newA[1];
        state.a2 = newA[2];
        state.b = newB;
    }

    public long stream() {
        return state.a2;
    }

}
