public class Message {
    private final String message;
    private final String originalMessage;
    private final char priority;
    private String key;

    // Priority is
    // Z - FLASH
    // O - IMMEDIATE
    // P - PRIORITY
    // R - ROUTINE

    public Message(String input) throws InvalidInputException {
        if (input.length() < 3) {
            throw new InvalidInputException("Invalid input. Message must be at least 3 characters long.");
        }
        originalMessage = input;
        priority = input.charAt(0);
        message = input.substring(2).trim();
    }

    public static String setPriorityString(char priority) {
        String priorityString = "";

        return switch (priority) {
            case 'Z' -> {
                priorityString = "FLASH";
                yield priorityString;
            }
            case 'O' -> {
                priorityString = "IMMEDIATE";
                yield priorityString;
            }
            case 'P' -> {
                priorityString = "PRIORITY";
                yield priorityString;
            }
            case 'R' -> {
                priorityString = "ROUTINE";
                yield priorityString;
            }
            default -> {
                priorityString = "ERROR";
                yield priorityString;
            }
        };

    }

    public boolean isValid() {
        // Check if the input message follows the specified format and length
        String validationPattern = "^[OPRZ],[a-zA-Z ]+$";

        // Redoing the constructor for the validation
        boolean hasValidLength = originalMessage.length() >= 3;

        return hasValidLength && originalMessage.matches(validationPattern);
    }

    // I think I caught an error and am redoing this entire method
    public String encryptMessage() {

        // Convert the message to uppercase without spaces
        String uppercaseMessage = message.replaceAll("[^a-zA-Z]", "").toUpperCase();

        // We need to repeat the string
        // I prefer string builder based on my previous program. APPENDING IS GREAT
        StringBuilder repeatedKey = new StringBuilder();
        for (int i = 0; i < uppercaseMessage.length(); i++) {
            char keyChar = key.charAt(i % key.length());
            repeatedKey.append(keyChar);
        }

        // I need to shift the characters
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < uppercaseMessage.length(); i++) {
            char originalChar = uppercaseMessage.charAt(i);
            char keyChar = repeatedKey.charAt(i);
            int shift = keyChar - 'A';
            char encryptedChar = (char) ((originalChar - 'A' + shift + 26) % 26 + 'A');
            encryptedMessage.append(encryptedChar);
        }

        return encryptedMessage.toString();
    }

    public void setKey(String userKey) {
        key = userKey;
    }

    public char getPriority() {
        return priority;
    }

    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

}

