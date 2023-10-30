// Aaron Pelto
// CST - 183
// Fall 2023

/*

The chosen encryption routine requires a one-word key known by both the sender and receiver.

The key should be read in from an external file.

The letters of the key are used to shift the characters of the message.

For example

if the key is: MATH
and
the message is: Delta College is open

then the encryption would be:
DELTACOLLEGEISOPEN Capitalize the message and remove spaces and punctuation.
MATHMATHMATHMATHMA Repeat the key as needed and align with characters of message.
PEEAMCHSXEZLUSHWQN The encrypted message.

Note that 'A' shifts zero positions, 'B' shifts one position, and so on through 'Z' that shifts 25 positions.
If the shifting rolls past the end of the alphabet, then it must "wrap around" (i.e. 'Z' shifts next to 'A', etc.)


You are only responsible for the encryption routine. Assume another team is handling the decryption.


The behavior of your program should include a simple input via a dialog box. This could be an example:
Message: P,Delta College is open.
Key: MATH

All messages should be prefaced with one of four possible characters priority codes (taken from military
contexts):
Z - FLASH O - IMMEDIATE P - PRIORITY R - ROUTINE


These decrease in criticality as your read left-to-right. FLASH implies life or death urgency, while ROUTINE
of course deals with messages in accords to its name.


Output from the example above there would be:
PRIORITY
PEEAMCHSXEZLUSHWQN


A legitimate message can only include one of the four valid characters, exactly one comma, and then one or
more characters following the comma. Be sure to include an error message if these basic formatting
requirements are not followed for the input strings.

 */

public class encryption {
}
