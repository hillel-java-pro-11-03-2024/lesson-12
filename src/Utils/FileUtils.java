package Utils;

import Exceptions.CantReadFileException;
import Exceptions.CantWriteToFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

  private static final String ROOT_FOLDER = "src/";
  private static final String STAT_EXTENSION = "_statistic.txt";
  private static final String TXT_EXTENSION = ".txt";

  public static void writeStatisticsToFile(String bookName, Map<String, Integer> wordCounts)
      throws CantWriteToFileException {
    String outputFilePath = String.format("%s%s%s", ROOT_FOLDER, bookName, STAT_EXTENSION);
    int totalWords = 0;
    try (FileWriter writer = new FileWriter(outputFilePath)) {
      for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
        writer.write(entry.getKey() + " : " + entry.getValue() + "\n");
        totalWords += entry.getValue();
      }
      writer.write("Total unique words: " + totalWords);
    } catch (IOException e) {
      throw new CantWriteToFileException();
    }
  }

  public static File searchBook(String bookName) throws FileNotFoundException {
    String filePath = String.format("%s%s%s", ROOT_FOLDER, bookName, TXT_EXTENSION);
    File file = new File(filePath);
    if (!file.exists()) {
      throw new FileNotFoundException();
    }
    return file;
  }

  public static Map<String, Integer> analyzeBook(File bookFile) throws CantReadFileException {
    Map<String, Integer> wordCounts = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(bookFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] words = line.split("\\s+");
        for (String word : words) {
          word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
          if (word.length() > 2) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
          }
        }
      }
    } catch (IOException e) {
      throw new CantReadFileException();
    }
    return wordCounts;
  }



}
