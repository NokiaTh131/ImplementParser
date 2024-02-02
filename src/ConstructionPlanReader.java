import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConstructionPlanReader {
    public String read(String filename) throws IOException {
        StringBuilder constructionPlanCode = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            while ((line = reader.readLine()) != null) {
                int indexOfHash = line.indexOf('#');
                if (indexOfHash != -1) {
                    line = line.substring(0, indexOfHash);
                }
                constructionPlanCode.append(line.trim()).append(" ");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return constructionPlanCode.toString();
    }
}
