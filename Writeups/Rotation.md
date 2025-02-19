# Rotation Writeup

**Description:**

This one is pretty straightforward. Just read the code and figure it out!

**Attachment:**
[rotation.py](../Files/rotation.py)

## Solution

We are given a simple python script that takes our input and performs a "rotation" of each character by its index in the input string then comparing it with another string which is the "rotated" flag. We simply have to do the opposite by rotating each character in the opposite direction.

        flag = ""
        rotatedFlag = "Sdarndh^ljqg$g&e!^\\LU^I<=7+7CHC[YXWZ"
        for i in range(len(rotatedFlag)):
                flag = flag + chr(ord(rotatedFlag[i]) + i)
        print(flag)

We easily get our flag:

        Securinets{r0t4t1on_is_SUPER_eazyyy}

***Author: jio***
