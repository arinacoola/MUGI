public class GF256{
    public static int mul2(int x){
        x= x & 0xFF;
        if((x & 0x80) != 0){
            return ((x << 1) ^ 0x1b) & 0xFF;
        }
        else{
            return (x << 1) & 0xFF;
        }
    }
    public static int mul3(int x){
        return (mul2(x) ^ x) & 0xFF;
    }
}
