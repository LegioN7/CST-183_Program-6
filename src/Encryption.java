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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Encryption {
    // Submit Button Method
    // This houses the lambda action for the submit button
    // When you press this button, it gets the key from key.txt
    // It then completes the message input validation, and encryption
    private static JPanel createSubmitButtonPanel(JTextField messageText, JTextField fileText) {
        JPanel panel = new JPanel();
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            String input = messageText.getText();
            String fileInput = fileText.getText();

            try {
                // Validate input and perform encryption using Message.createMessage method
                Message userMessage = new Message(input);

                // Calls the isValid Method from Message.java
                if (userMessage.isValid()) {
                    // get the priority
                    char priority = userMessage.getPriority();

                    String keyFileName = fileInput.trim() + ".txt";
                    String key = readKeyFromFile(keyFileName);
                    userMessage.setKey(key);

                    // Encrypt the message
                    String encryptedMessage = userMessage.encryptMessage();
                    String priorityString = Message.setPriorityString(priority);

                    // Show the encrypted message
                    JOptionPane.showMessageDialog(null, "Priority: " + priorityString + "\n" + "Encrypted Message: " + encryptedMessage);
                } else {
                    // Show an error message for invalid input
                    JOptionPane.showMessageDialog(null, "Invalid input. Please follow the specified format.", "Message Input Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Message.InvalidInputException ex) {
                // Handle invalid input exception
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Invalid Input Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                // Handle file reading error
                JOptionPane.showMessageDialog(null, "Error reading the key from the file.", "Key File Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(submitButton);
        return panel;
    }

    // Creating the text fields for the panel
    private static JPanel textFieldComponent(String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel label = new JLabel(labelText);
        panel.add(label);
        panel.add(textField);

        return panel;
    }

    // This is a method to pull the text from a key.txt file
    public static String readKeyFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String key = reader.readLine();
        reader.close();
        return key;
    }


    public static void main(String[] args) {

        // Main Panel
        // Title and Size
        JFrame frame = new JFrame("Message Encryption");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setResizable(false);

        // Main Panel
        // Grid with 3 rows
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // The text fields
        // File text field
        // Message text field
        JTextField fileText = new JTextField();
        JTextField messageText = new JTextField();

        // Migrated this to a method
        JPanel filePanel = textFieldComponent("File Name: ", fileText);

        //Im going to just match the length for visual purposes
        JPanel messagePanel = textFieldComponent("Message:   ", messageText);

        // Add the submit button
        JPanel buttonPanel = createSubmitButtonPanel(messageText, fileText);

        mainPanel.add(filePanel);
        mainPanel.add(messagePanel);
        mainPanel.add(buttonPanel);

        frame.add(mainPanel);
        frame.setVisible(true);

    }
}


