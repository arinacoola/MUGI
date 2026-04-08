public class F{
    public static long apply(long x, long t){
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
        int[] ql = M.apply(left);
        int[] qr = M.apply(right);
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
}
