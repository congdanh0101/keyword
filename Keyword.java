import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;

public class Keyword {

    public static String loadStopWord() throws IOException {
        File file = new File("stopwordlist.txt");
        Scanner sc = new Scanner(file);
        StringBuilder stringBuilder = new StringBuilder();
        while (sc.hasNextLine()) {
            stringBuilder.append(sc.nextLine());
            stringBuilder.append(" ");
        }

        return stringBuilder.toString().trim();

    }

    public static String loadText() throws IOException {
        File file = new File("demotext.txt");
        Scanner sc = new Scanner(file);
        String fileContent = "";
        while (sc.hasNextLine()) {
            fileContent += sc.nextLine();
            fileContent += " ";
        }
        return fileContent;
    }

    public static String[] SplitText(String s) throws IOException {
        return s.toLowerCase().split(" +");
    }

    public static String[] StopWordList() throws IOException {
        String[] s = new String[25];
        File file = new File("stopwordlist.txt");
        Scanner sc = new Scanner(file);
        for (int i = 0; i < 25; i++) {
            s[i] = sc.nextLine();
        }
        return s;
    }

    public static void main(String[] args) throws IOException {
        String fileContent = loadStopWord();
        // String[] stopwordList = fileContent.split(" ");\
        String text = loadText();
        String original = "The quick brown fox jumps over the lazy dog";
        String target = "quick brown fox jumps lazy dog";
        String[] allWord = SplitText(text);

        String lastTarget = "";
        for (String word : allWord) {
            if (!fileContent.contains(word)) {
                lastTarget += word;
                lastTarget += " ";
            }
            lastTarget.trim();
        }

        String[] keys = SplitText(lastTarget);
        double length = keys.length;

        for (int i = 0; i < length; i++)
            keys[i] = removeSymbol(keys[i]);

        String abc = "'romeo'";
        abc = removeSymbol(abc);
        System.out.println(abc);

        lastTarget = "";
        for (String word : keys) {
            if (!fileContent.contains(word)) {
                lastTarget += word;
                lastTarget += " ";
            }
            lastTarget.trim();
        }

        keys = SplitText(lastTarget);
        length = keys.length;
        double[] sortPercent = new double[(int) length];
        String[] sortString = new String[(int) length];
        String[] uniqueKeys;
        int count = 0;
        uniqueKeys = getUniqueKeys(keys);
        int k = 0;

        for (String key : uniqueKeys) {
            if (key == null)
                break;
            for (String s : keys)
                if (key.equals(s))
                    count++;
            double percent = (count / length) * 100;
            sortPercent[k] = percent;
            sortString[k] = key;
            // fw.write("Count of [" + key + "] is : " + count + " | " +
            // percent + "%\n");
            count = 0;
            k++;
        }

        Sort(sortPercent, sortString);
        PrintArray(sortPercent, sortString);
    }

    private static String[] getUniqueKeys(String[] keys) {
        String[] uniqueKeys = new String[keys.length];

        uniqueKeys[0] = keys[0];
        int uniqueKeyIndex = 1;
        boolean keyAlreadyExists = false;

        for (int i = 1; i < keys.length; i++) {
            for (int j = 0; j <= uniqueKeyIndex; j++) {
                if (keys[i].equals(uniqueKeys[j])) {
                    keyAlreadyExists = true;
                }
            }

            if (!keyAlreadyExists) {
                uniqueKeys[uniqueKeyIndex] = keys[i];
                uniqueKeyIndex++;
            }
            keyAlreadyExists = false;
        }
        return uniqueKeys;
    }

    private static String removeSymbol(String s) {
        String substr = s;
        for (int i = 0; i < s.length(); i++) {
            if (substr.charAt(i) == '.' || substr.charAt(i) == ',' ||
                    substr.charAt(i) == ')' || substr.charAt(i) == '(' || substr.charAt(i) == '"'
                    ||
                    substr.charAt(i) == '/' || substr.charAt(i) == '?' || substr.charAt(i) == ':'
                    || substr.charAt(i) == ';' || substr.charAt(i) == '<'
                    || substr.charAt(i) == '>' || substr.charAt(i) == ' ' || substr.charAt(i) == '}'
                    || substr.charAt(i) == '{' || substr.charAt(i) == '[' || substr.charAt(i) == ']'
                    || substr.charAt(i) == '+' || substr.charAt(i) == '-' || substr.charAt(i) == '*'
                    || substr.charAt(i) == '&' || substr.charAt(i) == '%' || substr.charAt(i) == '$'
                    || substr.charAt(i) == '@' || substr.charAt(i) == '!' || substr.charAt(i) == '^'
                    || substr.charAt(i) == '#' || substr.charAt(i) == '*' || substr.charAt(i) == '\\'
                    || substr.charAt(i) == '~' || substr.charAt(i) == '`' || substr.charAt(i) == '_'
                    || substr.charAt(i) == '=' || substr.charAt(i) == '|' || substr.charAt(i) == '\'')
                // if (substr.charAt(i) == '.' || substr.charAt(i) == ',' ||
                // substr.charAt(i) == ')' || substr.charAt(i) == '(' || substr.charAt(i) == '"'
                // ||
                // substr.charAt(i) == '/' || substr.charAt(i) == '?')
                substr = substr.replace(s.charAt(i), ' ');

        }
        for (int i = 0; i < s.length() - 1; i++) {
            if (substr.charAt(i) == ' ' && substr.charAt(i + 1) == 's') {
                substr = substr.replace(s.charAt(i), ' ');
                substr = substr.replace(s.charAt(i + 1), ' ');
            }
        }
        substr = substr.replace(" ", "");
        return substr;
    }

    public static void Sort(double[] Array, String[] str) {
        for (int i = 0; i < Array.length - 1; i++) {
            for (int j = i + 1; j < Array.length; j++) {
                if (Array[i] < Array[j]) {
                    double t = Array[i];
                    Array[i] = Array[j];
                    Array[j] = t;
                    String s = str[i];
                    str[i] = str[j];
                    str[j] = s;
                }
            }
        }
    }

    public static void PrintArray(double[] a, String[] s) throws IOException {
        FileWriter fw = new FileWriter("output.txt");
        try {
            for (int i = 0; i < a.length; i++) {
                fw.write("Percent of [" + s[i] + "] is : " + " | " +
                        a[i] + "%\n");
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
