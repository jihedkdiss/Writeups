# BATman Writeup
**Description:** BATman is trying to ask you out. He told me that he SET some variables for you. I don't know what that means. Do you?
## Solution

This is a warmup task to test your batch scripting skills. The file is obfuscated and we cannot deobfuscate it. The only chance we have is by running it and figuring out what it does.

 1. First we run the file, a black cmd windows appears and disappears quickly.
 2. We run it from the cmd and we have some output:

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

***Author: jio***