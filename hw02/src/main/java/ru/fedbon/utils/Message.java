package ru.fedbon.utils;

public class Message {
    public static final String MSG_HEADER = "Welcome to the student's testing system";

    public static final String HEADER_DELIMITER = "----------------------------------------";

    public static final String MSG_RIGHT_ANSWER = "This is the right answer!\n";

    public static final String MSG_WRONG_ANSWER = "This is the wrong answer!\n";

    public static final String MSG_ENTER_ANSWER = "Enter the number of answer:";

    public static final String MSG_INTRODUCE = "Please introduce yourself";

    public static final String MSG_ENTER_FIRST_NAME = "Enter your first name:";

    public static final String MSG_ENTER_LAST_NAME = "Enter your last name:";

    public static final String MSG_INVALID_STRING_INPUT = "Invalid input! Please use only letters.";

    public static final String MSG_INVALID_INT_INPUT = "Invalid input! Please enter a number between 1 and ";

    public static final String TEST_RESULT_PATTERN = "Dear %s %s,\nTotal questions amount: %d. " +
            "Right answers: %d, must be at least %d. %s";

    public static final String MSG_TEST_PASSED = "\nTest passed! \nCongratulations!";

    public static final String MSG_TEST_FAILED = "\nTest failed! \nPlease try again.";

    private Message() {

    }
}
