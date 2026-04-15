import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
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
        Path inputPath = Path.of("src/malenkyi_prynts_input.txt");
        Path encrpPath = Path.of("encrypted.bin");
        Path decrpPath = Path.of("decrypted.txt");
        byte[] orig = Files.readAllBytes(inputPath);
        byte[] encrp = MugiCipher.encryptData(orig, key, iv);
        Files.write(encrpPath,encrp);
        byte[] decrp = MugiCipher.decryptData(encrp, key, iv);
        Files.write(decrpPath,decrp);
        System.out.println("original size  = " + orig.length);
        System.out.println("encrypted size = " + encrp.length);
        System.out.println("decrypted size = " + decrp.length);
        if (Arrays.equals(orig, decrp)) {
            System.out.println("files are identical after decrypting");
        }
        else {
            System.out.println("files are not identical");
        }
    }
}