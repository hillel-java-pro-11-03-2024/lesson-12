package Utils;

import Exceptions.CantReadFileException;
import Exceptions.CantWriteToFileException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

  public static void writeStatisticsToFile(String bookName, Map<String, Integer> wordCounts)
      throws CantWriteToFileException {
    String outputFilePath = "src/" + bookName + "_statistic.txt";
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

  public static File searchBook(String bookName) {
    String filePath = "src/" + bookName + ".txt";
    return new File(filePath);
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
