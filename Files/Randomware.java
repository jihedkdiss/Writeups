import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Randomware {
    public static String encrypt(String data, String key) {
        String[] ciphertextArray = new String[data.length()];
        for (int i = 0; i < data.length(); i++) {
            char originalChar = data.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = (char) ((originalChar ^ keyChar) + i);
            ciphertextArray[i] = "0x" + Integer.toHexString(encryptedChar);
        }
        return Arrays.toString(ciphertextArray);
    }
    public static void main(String[] args) throws FileNotFoundException {
        File enc = new File("secret.key");
        Scanner input = new Scanner(enc);
        long secret = input.nextLong();
        Random random = new Random(secret);
        int victimId = random.nextInt();
        System.out.println("Victim ID: " + victimId);           // Victim ID: 1498185700
        int fileId = random.nextInt() / 1337;
        System.out.println("File ID: " + Math.abs(fileId));     // File ID: 1268616
        long encryptionKey = random.nextInt();
        try {
            String data = new String(Files.readAllBytes(Paths.get("flag.txt")));
            String encryptedData = encrypt(data, "" + encryptionKey);
            try (FileWriter writer = new FileWriter("flag.txt.enc")) {
                writer.write(encryptedData);
            }
            File file = new File("flag.txt");
            file.delete();
            System.out.println("You have been targeted by a ransomware attack! Send bitcoin or you won't get your files back!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
