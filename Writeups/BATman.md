# BATman Writeup

**Description:**

BATman is trying to ask you out. He told me that he **SET** some variables for you. I don't know what that means. Do you?

**Attachment:**
[batman.bat](../Files/batman.bat)

## Solution

This is a warmup task to test your [batch](https://en.wikipedia.org/wiki/Batch_file) scripting skills. The file is obfuscated and we cannot easily deobfuscate it. Why don't we just run it and see what it does.

1. First we run the file on windows. A black cmd window appears and disappears quickly.
2. We run it from the cmd to see the output. Make sure you're running cmd.exe not powershell:

        i have SET some variables for you. Can you find them?

3. We know that this is a batch file so we google `batch set` and here is what we find:

        Display, set, or remove CMD environment variables. Changes made with SET will remain only for the duration of the current CMD session.

4. We type the `SET` command in our cmd.exe session and it prints all of the existing environment variables.
5. We look around and see 5 variables that look like this:

        part1=Securinets{W1ll_
        part2=y0u_
        part3=b3_
        part4=my_
        part5=v4l3nt1ne?}

6. We concatenate them and submit our flag:

        Securinets{W1ll_y0u_b3_my_v4l3nt1ne?}

I hope you have learned something new: What is batch and how to set environment variables with it. Stay tuned for BATman 2!

***Author: jio***
