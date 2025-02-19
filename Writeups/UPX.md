# UPX Writeup

**Description:**

`strings` is a great linux command. It shows you all the strings in a file. Sometimes the flag is there, sometimes it isn't. But there will be always something useful there!

**Attachment:**
[upx](../Files/upx)

## Solution

We start by running the `strings` command as mentioned in the description and look for anything interesting:

    $Info: This file is packed with the UPX executable packer http://upx.sf.net $
    $Id: UPX 3.96 Copyright (C) 1996-2020 the UPX Team. All Rights Reserved. $

1. We install UPX (which is an executable packer): `sudo apt install upx`
2. We unpack the executable using UPX: `upx -d upx`
3. We run `strings upx | grep Securinets` and we find the flag!

        Securinets{y3s_1T_1S_4S_S1MLP3_4S_1T_L00KS}

***Author: jio***
