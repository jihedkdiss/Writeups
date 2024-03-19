# Randomware Writeup
**Description:**

One of my friends got targeted by a ransomware attack. He had a really important file called *flag.txt* but the malware removed it and replaced it with a file called *flag.txt.enc*. Once he opened his laptop he got this message:
    
    Victim ID: 1498185700
    File ID: 1268616
    You have been targeted by a ransomware attack! Send bitcoin or you won't get your files back!

The ransomware code and the encrypted file are attached below. Help him recover his file.

**Attachements:** 
[Randomware.java](Files\Randomware.java) • [flag.txt.enc](Files\flag.txt.enc)

## Solution
### Understanding the title and description
The task description says a lot about the solution itself. **Random**ware is hinting at randomness, say seeds prediction and so on. The other part of the story hints at decryption as we're also provided with an encrypted flag file. One more important thing: **The values in the description are the key to solve the challenge!**

### Reading the "ransomware" code
After having a look at the provided code, we can deduce its functioanlity pretty quickly:

 1. It takes a secret key and seeds it to the Random object
 2. It generates two random values and performs some basic operations on them
 3. It generates the decryption key, which is the one we're looking for.
 4. It encrypts *flag.txt* writes it to *flag.txt.enc* then removes the original *flag.txt* file and prints a message.

### Learning about randomness in Java
After some googling as usual we stumble upon [this answer](https://crypto.stackexchange.com/questions/51686/how-to-determine-the-next-number-from-javas-random-method) on stack overflow that provides the following code snippet that can predict the third random value based on two random values using some mathematical formula.

    import java.util.Random;

    public class lol {
        public static int next(long seed) {
            int bits=32;
            long seed2 = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
            return (int)(seed2 >>> (48 - bits));
        }

    public static void main(String[] args) {
        System.out.println("Starting");
        long i1 = -1952542633L;
        long i2 = -284611532L;
        long seed =0;
        for (int i = 0; i < 65536; i++) {
            seed = i1 *65536 + i;
            if (next(seed) == i2) {
                System.out.println("Seed found: " + seed);
               break;
            }
        }
        Random random = new Random((seed ^ 0x5DEECE66DL) & ((1L << 48) - 1));
        int o1 = random.nextInt();
        int o2 = random.nextInt();
        System.out.println("So we have that nextInt is: "+o1+" and the third one is: "+o2+" with seed: "+seed);
        }
    }

### Writing a solver
Now all we need to do is to adapt this snippet to our specific case.

 1. We implement the decrypt function. It's a basic decrypt function that you can find here: [Wr4pped Writeup](Wr4pped.md)
 2. We brute force the file ID to get back to the original number since we divided it by 1337.
 3. We check if we have the correct value, if so we decrypt the file.

Here is what the final code looks like:

    import java.util.*;
    import java.io.*;

    public class Solver {
        public static String decrypt(String encryptedFlag, String key) {
            String[] hexValues = encryptedFlag.substring(3, encryptedFlag.length() - 1).split(", 0x");
            StringBuilder flag = new StringBuilder();
            for (int i = 0; i < hexValues.length; i++) {
                char encryptedChar = (char) Integer.parseInt(hexValues[i], 16);
                char keyChar = key.charAt(i % key.length());
                char originalChar = (char) ((encryptedChar - i) ^ keyChar);
                flag.append(originalChar);
            }
            return flag.toString();
        }
        public static int next(long seed) {
            int bits=32;
            long seed2 = (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1);
            return (int)(seed2 >>> (48 - bits));
        }
        public static void main(String[] args) {
            long i1 = 1498185700L;
            for (int j = 0; j < 1337; j++) {
                for (int i = 0; i < 65536; i++) {
                    long i2 = -1268616 * 1337 - j;
                    long seed = 0;
                    seed = i1 * 65536 + i;
                    if (next(seed) == i2) {
                        try {
                            File enc = new File("flag.txt.enc");
                            Scanner myReader = new Scanner(enc);
                            String flag = myReader.nextLine();
                            myReader.close();
                            Random random = new Random((seed ^ 0x5DEECE66DL) & ((1L << 48) - 1));
                            random.nextInt();
                            int key = random.nextInt();
                            System.out.println(decrypt(flag, "" + key));
                            System.exit(0);
                        } catch (FileNotFoundException e) {
                            System.out.println("An error occurred.");
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
    }


We run our solver by typing the next commands in the terminal `javac Solver.java` then `java Solver` and we get our flag!

    Good job genius! Here is your flag: Securinets{st4ck_OV3RFL0W_r0cks!!}

Hope you had fun solving this task!

***Author: jio***
