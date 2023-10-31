import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Message {
    private String message;
    private String originalMessage;
    private String key;
    private char priority;

    // Priority is
    // Z - FLASH
    // O - IMMEDIATE
    // P - PRIORITY
    // R - ROUTINE

    public Message() {
    }


    public void setOriginalMessage(String userOriginalMessage){
        originalMessage = userOriginalMessage;
    }
    public void setMessage(String userMessage){
        message = userMessage;
    }

    public void setKey(String userKey) {
        key = userKey;
    }

    public void setPriority(char userPriority) {
        priority = userPriority;
    }

    public String getOriginalMessage(){
        return originalMessage;
    }

    public String getMessage() {
        return message;
    }

    public String getKey() {
        return key;
    }

    public char getPriority() {
        return priority;
    }

    // This will use regex to validate the message
    // The regex 4 entries match the priority
    // The , matches the required comma format
    public boolean messageValidation(String originalMessage) {

        // I was having issues with my message input erroring with one character
        // Because the pattern requires Priority Character, Comma and then a message
        if (originalMessage.length() < 3) {
            return false;
        }

        String validationPattern = "^[OPRZ],[a-zA-Z ]+$";
        return originalMessage.matches(validationPattern);
    }


    public static String encryptMessage(Message message) {
        // Get the key and message from the getters
        String key = message.getKey();
        String originalMessage = message.getMessage();

        // Variables needed for the repeated loops
        int keyIndex = 0;

        // This will use regex to remove all the spaces
        originalMessage = originalMessage.replaceAll("[^a-zA-Z]", "").toUpperCase();

        // Repeat the key
        StringBuilder repeatedKey = new StringBuilder();
        for (int i = 0; i < originalMessage.length(); i++) {
            char keyChar = key.charAt(keyIndex);
            repeatedKey.append(keyChar);
            keyIndex = (keyIndex + 1) % key.length();
        }

        // Encrypt the message
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < originalMessage.length(); i++) {
            char originalChar = originalMessage.charAt(i);
            char keyChar = repeatedKey.charAt(i);
            int shiftChar = keyChar - 'A';
            char encryptedChar = (char) ((originalChar - 'A' + shiftChar + 26) % 26 + 'A');
            encryptedMessage.append(encryptedChar);
        }

        return encryptedMessage.toString();
    }

    // This is a method to pull the text from a key.txt file
    public static String readKeyFromFile(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String key = reader.readLine();
        reader.close();
        return key;
    }
}
