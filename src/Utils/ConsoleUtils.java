package Utils;

import static java.lang.Thread.sleep;

public class ConsoleUtils {

  public static void clearConsole() {
    for (int i = 0; i < 20; i++) {
      System.out.println();
    }
  }

  public static void print(String str, boolean newLine) {
    for (int i = 0; i < str.length(); i++) {
      char ch = str.charAt(i);
      System.out.print(ch);
      try {
        sleep(20);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
    if (newLine) System.out.println();
  }
}
