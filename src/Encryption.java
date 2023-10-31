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

// Imports for Swing because I don't want to use JavaFX

import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Encryption {

    // Message Field Text Field
    private static JTextField createMessageTextField() {
        JTextField messageText = new JTextField();
        messageText.setMaximumSize(new Dimension(Short.MAX_VALUE, messageText.getPreferredSize().height));
        return messageText;
    }

    // Submit Button Method
    // This houses the lamba action for the submit button
    // When you press this button, it gets the key from key.txt
    // It then completes the message input validation, and encryption
    private static JPanel submitButton(JTextField messageText) {

        // JPanel for the submit button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        JButton submitButton = new JButton("Submit");
        buttonPanel.add(submitButton);

        // Since I am using a newer java, I can move this to a lamba
        // This will house the action for the submit button
        // I will get the key from key.txt
        // When you hit submit, it will validate the original message before alteration then encrypt it
        submitButton.addActionListener(e -> {
            String input = messageText.getText();


            // Validate input using Message class
            Message userMessage = new Message();

            // Validation the Message Input
            if (userMessage.messageValidation(input)) {
                char priority = input.charAt(0);
                String message = input.substring(2).trim();

                userMessage.setOriginalMessage(input);
                userMessage.setMessage(message);
                userMessage.setPriority(priority);

                String keyFileName = "key.txt";

                try {
                    String key = Message.readKeyFromFile(keyFileName);
                    userMessage.setKey(key);

                    // Encrypt the message
                    String encryptedMessage = Message.encryptMessage(userMessage);

                    // Show the encrypted message in a dialog box
                    JOptionPane.showMessageDialog(null, "Encrypted Message: " + encryptedMessage);
                } catch (IOException ex) {
                    // This is the error when the key.txt file is not read
                    JOptionPane.showMessageDialog(null, "Error reading the key from the file.", "Key File Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Show an error message for invalid input
                JOptionPane.showMessageDialog(null, "Invalid input. Please follow the specified format.", "Message Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return buttonPanel;
    }


    public static void main(String[] args) {

        // Creating the JFrame
        // Citing my source for the JPanel content I used
        // https://www.codejava.net/java-se/swing/jpanel-basic-tutorial-and-examples
        JFrame frame = new JFrame("Message Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setResizable(false);

        // Main Frame
        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Message Text Field Method
        JTextField messageText = createMessageTextField();


        // Message input panel
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.X_AXIS));
        JLabel messageLabel = new JLabel("Message: ");
        messagePanel.add(messageLabel);
        messagePanel.add(messageText);


        // Submit button panel
        // I wanted to try to move this to a Method because it's continually been changed
        // I've readjusted this button text over and over
        JPanel buttonPanel = submitButton(messageText);

        // Add components to the main panel
        mainPanel.add(messagePanel);
        mainPanel.add(buttonPanel);

        // Setting the mainPanel as true/visible
        frame.add(mainPanel);
        frame.setVisible(true);

        }
    }


