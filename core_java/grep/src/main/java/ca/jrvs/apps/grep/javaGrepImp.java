package ca.jrvs.apps.grep;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;

public class javaGrepImp implements JavaGrep{
    static final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    public static void main(String[] args) {
        // testing arguments: .*IllegalArgumentException.* ./grep/src /tmp/grep.out
        if (args.length != 3) {
            throw new IllegalArgumentException("Not enough parameters");
        }

        BasicConfigurator.configure();

        javaGrepImp javaGrepImp = new javaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try {
            javaGrepImp.process();
        } catch (Exception e) {
            javaGrepImp.logger.error(e.getMessage(), e);
        }

    }
    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();
        List<File> allFiles = listFiles(rootPath);
        if (allFiles == null) {
            throw new FileNotFoundException("ERROR: root path is empty or cannot access root path.");
        }
        for (File file : allFiles) {
            List<String> lines = readLines(file);
            for (String line : lines) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        writeToFile(matchedLines);
    }

    @Override
    public List<File> listFiles(String rootDir) throws IOException {
        File dirPath = new File(rootDir);
        File[] fileList = dirPath.listFiles();
        if (fileList == null) {
            throw new FileNotFoundException("ERROR: root path is empty or cannot access root path.");
        }
        List<File> result = new ArrayList<>();
        for (File file : fileList)
            if (file.isFile()) {
                result.add(file);
            } else {
                String subDirName = file.getAbsolutePath();
                /* recursively collect all files in the directory. */
                result.addAll(listFiles(subDirName));
            }

        return result;

    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        List<String> lines = new ArrayList<>();
        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            reader.close();
            return lines;
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("ERROR: input file not found.");
        } catch (IOException ex) {
            throw new RuntimeException("ERROR: unable to utilize buffered reader.");
        }

    }

    @Override
    public boolean containsPattern(String line) {
        return line.matches(regex);
    }

    public void writeToFile(List<String> lines) throws IOException {
        File outputFile = new File(outFile);

        FileOutputStream outStream = new FileOutputStream(outputFile);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }

        writer.close();
    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
    this.regex=regex;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile=outFile;
    }
}
