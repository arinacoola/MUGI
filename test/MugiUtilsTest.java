import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MugiUtilsTest {
    @Test
    void testBytesLong() {
        long val = 0x0123456789ABCDEFL;
        byte[] ar = new byte[8];
        ByteUtils.longToBytes(val,ar, 0);
        long rest = ByteUtils.bytesToLong(ar,0);
        assertEquals(val,rest);
    }

    @Test
    void testBytesLongOffset() {
        long val = 0x0F1E2D3C4B5A6978L;
        byte[] ar = new byte[20];
        ByteUtils.longToBytes(val, ar, 5);
        long rest = ByteUtils.bytesToLong(ar, 5);
        assertEquals(val, rest);
    }

    @Test
    void testMul2() {
        assertEquals(0xAE, GF256.mul2(0x57));
        assertEquals(0x1B, GF256.mul2(0x80));
        assertEquals(0x00, GF256.mul2(0x00));
    }

    @Test
    void testMul3() {
        assertEquals((GF256.mul2(0x57) ^ 0x57) & 0xFF, GF256.mul3(0x57));
        assertEquals(0x00, GF256.mul3(0x00));
    }

    @Test
    void testSBox() {
        assertEquals(0x63, MugiTables.sr(0x00));
        assertEquals(0xED, MugiTables.sr(0x53));
        assertEquals(0x16, MugiTables.sr(0xFF));
    }
}
