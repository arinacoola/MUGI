import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) throws IOException {
        byte[] key = {
                (byte)0x69, (byte)0xE7, (byte)0x06, (byte)0xEE,
                (byte)0x52, (byte)0x95, (byte)0x37, (byte)0x2C,
                (byte)0x75, (byte)0x13, (byte)0x01, (byte)0x47,
                (byte)0x30, (byte)0x23, (byte)0x79, (byte)0x93
        };
        byte[] iv = {
                (byte)0x2A, (byte)0x00, (byte)0x45, (byte)0xC8,
                (byte)0x49, (byte)0x27, (byte)0x49, (byte)0xD5,
                (byte)0x3A, (byte)0x9B, (byte)0x16, (byte)0x4A,
                (byte)0x25, (byte)0xE4, (byte)0x49, (byte)0x15
        };
        Files.write(Path.of("key.bin"), key);
        Files.write(Path.of("iv.bin"), iv);
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