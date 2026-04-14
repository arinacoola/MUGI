public class MugiCipher {
    public static byte[] encryptData(byte[] input, byte[] key, byte[] iv){
        if (input == null) {
            throw new IllegalArgumentException("input must not be null");
        }
        MugiCore gen = new MugiCore();
        gen.initCipher(key, iv);
        byte[] res =new byte[input.length];
        byte[] gBytes =new byte[8];
        int blockCount = input.length/ 8;
        int tail = input.length % 8;
        for (int block = 0;block < blockCount;block++) {
            long gamma = gen.nextBlock();
            ByteUtils.longToBytes(gamma,gBytes,0);
            int start = block*8;
            for (int i = 0;i < 8; i++) {
                res[start + i]=(byte) (input[start + i] ^ gBytes[i]);
            }
        }
        if (tail > 0){
            long gamma = gen.nextBlock();
            ByteUtils.longToBytes(gamma, gBytes, 0);
            int start = blockCount * 8;
            for (int i = 0;i < tail; i++) {
                res[start + i] = (byte) (input[start +i] ^ gBytes[i]);
            }
        }
        return res;
    }
}
