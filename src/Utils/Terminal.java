package Utils;

import static Utils.ConsoleUtils.clearConsole;
import static Utils.ConsoleUtils.print;
import static Utils.FileUtils.analyzeBook;
import static Utils.FileUtils.searchBook;
import static Utils.FileUtils.writeStatisticsToFile;

import Exceptions.CantReadFileException;
import Exceptions.CantWriteToFileException;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Terminal {

  static Scanner scanner = new Scanner(System.in);
  static Map<String, Integer> wordCounts = new HashMap<>();

  public static void run() {
    clearConsole();
    print("Good day. Book reader terminal is loading...", true);
    String input;

    while (true) {
      print("===================================================", true);
      print("If you wish to exit just type 'exit'", true);
      print("Please enter a book name for analyze: ", false);
      input = scanner.nextLine();

      if (input.equals("exit")) {
        print("Exiting. Have a good day.", false);
        break;
      }

      print("Book name: " + input, true);
      print("Searching... ", false);
      File book = searchBook(input);
      if (!book.exists()) {
        print("The book \"" + input + "\" not found.", true);
        print("Press enter for continue...", false);
        scanner.nextLine();
        clearConsole();
        continue;
      } else {
        print("File found, size: " + book.length() + " bytes", true);
      }

      try {
        wordCounts = analyzeBook(book);
      } catch (CantReadFileException e) {
        print("Cannot read the file, it may be corrupted.", true);
        print("Press enter for continue...", false);
        scanner.nextLine();
        clearConsole();
        continue;
      }

      if (wordCounts.isEmpty()) {
        print("The book is empty. Please choose another.", true);
        print("Press enter for continue...", false);
        scanner.nextLine();
        clearConsole();
      } else {
        try {
          print("Write statistics to file...", true);
          writeStatisticsToFile(input, wordCounts);
        } catch (CantWriteToFileException e) {
          print("Error writing statistics to file. Please try again.", true);
        }
        printStatistics(wordCounts);
      }
    }
    scanner.close();
  }

  private static void printStatistics(Map<String, Integer> wordCounts) {
    print("Printing statistics:", true);
    wordCounts.entrySet().stream()
        .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
        .limit(10)
        .forEach(entry -> print(entry.getKey() + " : " + entry.getValue(), true));
    print("Press enter for continue...", false);
    scanner.nextLine();
    clearConsole();
  }
}
