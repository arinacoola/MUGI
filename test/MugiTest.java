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
    void testVectorCustom() {
        byte[] key = {
                0x69, (byte) 0xE7, 0x06, (byte) 0xEE,
                0x52, (byte) 0x95, 0x37, 0x2C,
                0x75, 0x13, 0x01, 0x47,
                0x30, 0x23, 0x79, (byte) 0x93
        };
        byte[] iv = {
                0x2A, 0x00, 0x45, (byte) 0xC8,
                0x49, 0x27, 0x49, (byte) 0xD5,
                0x3A, (byte) 0x9B, 0x16, 0x4A,
                0x25, (byte) 0xE4, 0x49, 0x15
        };
        MugiCore core =new MugiCore();
        core.initCipher(key,iv);
        long[] expected ={
                0xe3cc67a0255b0f28L,
                0x2d9a5b1bbdf7f2dfL,
                0x84eb46f607d6e6ddL,
                0x3286134394dd95fbL
        };
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], core.nextBlock());
        }
    }

    @Test
    void testBufferInit() {
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
        MugiCore core = new MugiCore();
        long[] actual = core.getInitPhaseState(key, iv, 100);
        assertEquals(0x7dea261cb61d4feaL, actual[0]);
        assertEquals(0xeafb528479bb687dL, actual[1]);
        assertEquals(0xeb8189612089ff0bL, actual[2]);
    }

    @Test
    void testRho0() {
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
        MugiCore core = new MugiCore();
        long[] actual = core.getInitPhaseState(key, iv, 200);
        assertEquals(0x8d0af6dc06bddf6aL, actual[0]);
        assertEquals(0x9a9b02c4499b787dL, actual[1]);
        assertEquals(0xf100cffe031d365bL, actual[2]);
    }

    @Test
    void testRho1() {
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
        MugiCore core = new MugiCore();
        long[] actual = core.getInitPhaseState(key, iv, 201);
        assertEquals(0x9a9b02c4499b787dL, actual[0]);
        assertEquals(0x435407f3bbc2c760L, actual[1]);
        assertEquals(0xb8576326c43c7141L, actual[2]);
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

    @Test
    void testInvalidKeyLength() {
        MugiCore core = new MugiCore();
        byte[] key = new byte[15];
        byte[] iv = new byte[16];
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,  () -> core.initCipher(key, iv));
        assertEquals("key must be exactly 16 bytes", ex.getMessage());
    }

    @Test
    void testInvalidIvLength() {
        MugiCore core = new MugiCore();
        byte[] key = new byte[16];
        byte[] iv = new byte[15];
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,  () -> core.initCipher(key, iv));
        assertEquals("iv must be exactly 16 bytes", ex.getMessage());
    }



}