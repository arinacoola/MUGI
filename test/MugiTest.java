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
}