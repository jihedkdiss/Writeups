# Welcome again Writeup

**Description:**

This binary contains a free flag. Find it!

**Attachment:**
[welcomeagain](../Files/welcomeagain)

## Solution

The challenge title Welcome *again* suggests that it is similar to [Welcome](Welcome.md) where we used `strings`. This time, we look at the hexdump of the file using the `xxd` Linux command and we can see the flag, near the banner and other strings, separated by dots which represent null characters or empty bytes.

    00002000: 0100 0200 0000 0000 5300 6500 6300 7500  ........S.e.c.u.
    00002010: 7200 6900 6e00 6500 7400 7300 7b00 7800  r.i.n.e.t.s.{.x.
    00002020: 7800 6400 5f00 6900 7300 5f00 6200 6500  x.d._.i.s._.b.e.
    00002030: 7400 7400 6500 7200 5f00 7400 6800 6100  t.t.e.r._.t.h.a.
    00002040: 6e00 5f00 7300 7400 7200 6900 6e00 6700  n._.s.t.r.i.n.g.
    00002050: 7300 7d00 003d 3d20 5745 4c43 4f4d 4520  s.}..== WELCOME
    00002060: 4147 4149 4e20 3d3d 002d 2d20 2041 7574  AGAIN ==.--  Aut
    00002070: 686f 723a 206a 696f 2020 2d2d 0066 6c61  hor: jio  --.fla
    00002080: 673f 2000 7772 6f6e 6721 0063 6f72 7265  g? .wrong!.corre
    00002090: 6374 2100 011b 033b 3000 0000 0500 0000  ct!....;0.......

We remove the dots and we get our flag:

        Securinets{xxd_is_better_than_strings}

***Author: jio***
