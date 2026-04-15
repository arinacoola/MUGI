import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MugiTest {
    @Test
    void testKeystreamZeroKeyIV() {
        byte[] key = new byte[16];
        byte[] iv = new byte[16];
        MugiCore core = new MugiCore();
        core.initCipher(key, iv);
        long[] expected ={
                0xc76e14e70836e6b6L,
                0xcb0e9c5a0bf03e1eL,
                0x0acf9af49ebe6d67L,
                0xd5726e374b1397acL
        };
        for (long exp : expected) {
            long actual = core.nextBlock();
            assertEquals(exp, actual);
        }
    }

    @Test
    void testNextBlockChangesState() {
        byte[] key = new byte[16];
        byte[] iv = new byte[16];
        MugiCore core = new MugiCore();
        core.initCipher(key, iv);
        long f = core.nextBlock();
        long s = core.nextBlock();
        assertNotEquals(f, s);
    }

    @Test
    void testDeterminism(){
        byte[] key = {
                0x00, 0x01, 0x02, 0x03,
                0x04, 0x05, 0x06, 0x07,
                0x08, 0x09, 0x0A, 0x0B,
                0x0C, 0x0D, 0x0E, 0x0F
        };
        byte[] iv = {
                (byte) 0xF0, (byte) 0xE0, (byte) 0xD0, (byte) 0xC0,
                (byte) 0xB0, (byte) 0xA0, (byte) 0x90, (byte) 0x80,
                0x70, 0x60, 0x50, 0x40,
                0x30, 0x20, 0x10, 0x00
        };
        MugiCore core1 = new MugiCore();
        MugiCore core2 = new MugiCore();
        core1.initCipher(key, iv);
        core2.initCipher(key, iv);
        for (int i = 0; i < 10; i++) {
            assertEquals(core1.nextBlock(), core2.nextBlock());
        }
    }

    @Test
    void testDifferentKey() {
        byte[] key1 = new byte[16];
        byte[] key2 = new byte[16];
        key2[0] = 1;
        byte[] iv = new byte[16];
        MugiCore core1 = new MugiCore();
        MugiCore core2 = new MugiCore();
        core1.initCipher(key1, iv);
        core2.initCipher(key2, iv);
        long block1 = core1.nextBlock();
        long block2 = core2.nextBlock();
        assertNotEquals(block1, block2);
    }

    @Test
    void testDifferentIv() {
        byte[] key = new byte[16];
        byte[] iv1 = new byte[16];
        byte[] iv2 = new byte[16];
        iv2[15] = 1;
        MugiCore core1 = new MugiCore();
        MugiCore core2 = new MugiCore();
        core1.initCipher(key, iv1);
        core2.initCipher(key, iv2);
        long block1 = core1.nextBlock();
        long block2 = core2.nextBlock();
        assertNotEquals(block1, block2);
    }

    @Test
    void testNoRepeatedBlocks() {
        byte[] key = new byte[16];
        byte[] iv = new byte[16];
        MugiCore core = new MugiCore();
        core.initCipher(key, iv);
        long b1 =core.nextBlock();
        long b2 = core.nextBlock();
        long b3 = core.nextBlock();
        long b4 = core.nextBlock();
        assertTrue(b1 != b2 || b2 != b3 || b3 != b4);
    }

    @Test
    void testNullKey() {
        MugiCore core = new MugiCore();
        byte[] iv = new byte[16];
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> core.initCipher(null, iv));
        assertEquals("key must be exactly 16 bytes", ex.getMessage());
    }

    @Test
    void testNullIv() {
        MugiCore core = new MugiCore();
        byte[] key = new byte[16];
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> core.initCipher(key, null));
        assertEquals("iv must be exactly 16 bytes", ex.getMessage());
    }




}