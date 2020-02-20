import com.github.ffcfalcos.logger.handler.persisting.FilePersistingHandler;

import java.io.*;

public class FilePersistingHandlerTest implements TestInterface {

    private final FilePersistingHandler filePersistingHandler = new FilePersistingHandler();
    private final String filePath = System.getProperty("user.dir") + "/src/test/resources/logs.log";

    @Override
    public void run() throws IOException {
        emptyingTestFile();
        try {
            filePersistingHandler.setFilePath(filePath);
            String string = "string";
            filePersistingHandler.persist(string);
            File file = new File(filePath);
            BufferedReader bufferedReader;
            try {
                bufferedReader = new BufferedReader(new FileReader(file));
            } catch (Exception e) {
                System.out.println("FilePersistingHandler failed the file creation test");
                throw e;
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(string)) {
                    break;
                }
            }
            if (line == null) {
                System.out.println("FilePersistingHandler failed the persisting test");
                throw new Exception("");
            }
            bufferedReader.close();
            filePersistingHandler.persist(string);
            bufferedReader = new BufferedReader(new FileReader(file));
            int lineCount = 0;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.equals(string)) {
                    lineCount++;
                }
            }
            if(lineCount != 2) {
                System.out.println("FilePersistingHandler failed the appending test");
                throw new Exception("");
            }
            try {
                filePersistingHandler.persist(null);
            } catch (Exception e) {
                System.out.println("FilePersistingHandler failed null message test");
                throw e;
            }
            try {
                filePersistingHandler.setFilePath(null);
                filePersistingHandler.persist(string);
            } catch (Exception e) {
                System.out.println("FilePersistingHandler failed null path test");
                throw e;
            }
            System.out.println("FilePersistingHandler succeed the test\n");
        } catch (Exception e) {
            System.out.println("FilePersistingHandler failed the test\n");
        }

    }

    private void emptyingTestFile() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write("FirstLine");
        bufferedWriter.close();
    }


}
