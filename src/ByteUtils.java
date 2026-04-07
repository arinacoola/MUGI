public class ByteUtils{
    public static long bytesToLong(byte[] bytes, int startInd){
        long res=0;
        for(int i=0;i<8;i++){
            res=res<<8;
            res=res | (bytes[startInd + i] & 0xFF);
        }
        return res;
    }

    public static void longToBytes(long val, byte[] bytes, int startInd) {
        for (int i = 7;i >= 0;i--) {
            bytes[startInd + i] = (byte)(val & 0xFF);
            val= val >>> 8;
        }
    }
}
