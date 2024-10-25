# Wr4pped Writeup

**Description:**

It's valentine's day so I wrapped a gift for you. Look closely and you'll be able to retrieve it. Happy valentine's!

**Attachement:**
[Wr4pped.exe](Files/Wr4pped.exe)

## Solution

### Gathering information

We start by running `file` as ususal.

    Wr4pped.exe: PE32 executable (GUI) Intel 80386 (stripped to external PDB), for MS Windows
It turns out this is a Windows executable. Let's run it and see what happens. It's actually asking for a password, we try some random input, it says Wrong password! Let's try `strings Wr4pped.exe` to check if the password is in plain text or at least notice if there is anything unusual. This is one portion of the output right at the beginning:

    --l4j-no-splash
    --l4j-dont-wait
    STATIC
    --l4j-no-splash-err
    Exit code:      0
    Exit code:      %d, restarting the application!
    Exit code:      %d
    Launch4j
    \launch4j.log
    --l4j-debug
    debug
    --l4j-debug-all
    debug-all
    3.50
After some digging around we couldn't find the password in plaintext but we see these unusual strings. Let's google them and find out what **Launch4j** is. [The first link](https://launch4j.sourceforge.net/) on google says:

    Launch4j is a free and open source tool that allows you to create Windows native executables from Java applications distributed as jars.

   There is also some mentions of java at the end which tells us that the executable is just a wrapper around the original java **.jar** file. At this point, dogbolt, ghidra or any other static analysis tool or website don't give us the expected results. **We should retrieve the jar file.**

### Unwrapping the executable

After googling for some time we stumble upon [this answer](https://reverseengineering.stackexchange.com/questions/3532/get-jar-back-from-wrappedinto-exe-jar) on how to get the .jar file from the .exe file. We follow the steps:

 1. Open .exe file in a hex editor (such as [HxD](https://mh-nexus.de/en/hxd/))
 2. Remove anything before the string PK
 3. Save the new file as Wr4pped.jar

### Decompiling the jar file

Now we have the jar file we can decompile it online using [this website](http://www.javadecompilers.com/).
Let's have a quick look at the `Wr4pped.java` file. First we have this function which performs some kind of encryption:

    public static String encrypt(String plaintext) {
        String key = "v4l3nt1n3";
        String[] ciphertextArray = new String[plaintext.length()];
        for(int i = 0; i < plaintext.length(); ++i) {
            char originalChar = plaintext.charAt(i);
            char keyChar = key.charAt(i % key.length());
            char encryptedChar = (char)((originalChar ^ keyChar) + i);
            ciphertextArray[i] = "0x" + Integer.toHexString(encryptedChar);
        }
        return Arrays.toString(ciphertextArray);
    }

Then we have the main funtion:

    private void verifyActionPerformed(ActionEvent evt) {
        String encryptedFlag = "[0x3, 0x5b, 0x1d, 0x44, 0x5e, 0x9, 0x22, 0x57, 0x5f, 0x1c, 0x61, 0x67, 0x6a, 0x2b, 0x2b, 0x6c, 0x1b, 0x2f, 0x5a, 0x79, 0x3d, 0x7a, 0x73, 0x3d, 0x7a, 0x44]";
        if (encrypt(this.password.getText()).equals(encryptedFlag)) {
            this.output.setText("Good job! Securinets{" + this.password.getText() + "}");
        } else {
            this.output.setText("Wrong password!");
        }

    }

### Understanding the code

Based on these two functions, when we type a password and press VERIFY it encrypts our password using the mentioned algorithm then it compares it with the `encryptedFlag` string.

### Writing a solver

We will be using java as a programming language to write our solver. The steps are pretty straight forward:

 1. We declare the encrypted flag and key as String variables
 2. We make an array of the hexadecimal values in the encrypted flag String so that we can handle them easier.
 3. We loop through the array, subtract i and then xor the character with the corresponding character from the key.
 4. Append the resulting letter to the flag and print it to the screen.

Here is what the final code looks like:

    import java.util.Arrays;

    public class Solver {
        public static void main(String[] args) {
            String encryptedFlag = "3, 0x5b, 0x1d, 0x44, 0x5e, 0x9, 0x22, 0x57, 0x5f, 0x1c, 0x61, 0x67, 0x6a, 0x2b, 0x2b, 0x6c, 0x1b, 0x2f, 0x5a, 0x79, 0x3d, 0x7a, 0x73, 0x3d, 0x7a, 0x44";
            String key = "v4l3nt1n3";
            String[] hexValues = encryptedFlag.split(", 0x");
            StringBuilder flag = new StringBuilder();
            for (int i = 0; i < hexValues.length; i++) {
                char encryptedChar = (char) Integer.parseInt(hexValues[i], 16);
                char keyChar = key.charAt(i % key.length());
                char originalChar = (char) ((encryptedChar - i) ^ keyChar);
                flag.append(originalChar);
            }
            System.out.println(flag.toString());
        }
    }

We run our solver by typing the next commands in the terminal `javac Solver.java` then `java Solver` and we get our password!

    unwr4p->dec0mpile->REV3RSE

We put the password in the Wr4pper.exe program and it gives us our flag!

    Securinets{unwr4p->dec0mpile->REV3RSE}

Hope you had fun solving this task!

***Author: jio***
