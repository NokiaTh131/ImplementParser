import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ConstructionPlanReader {
    public String code() throws IOException {
        StringBuilder constructionPlanCode = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/constructionplan.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                constructionPlanCode.append(line).append("\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IOException();
        }
        return constructionPlanCode.toString();
    }
}
