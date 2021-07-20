package ca.jrvs.apps.grep;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class javaGrepLambdaImp extends JavaGrepImp {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public void process() throws IOException {
        try {
            listFiles(getRootPath()).stream().forEach(
                    file -> {
                        try {
                            writeToFile(readLines(file).stream()
                                    .filter(s -> containsPattern(s))
                                    .collect(Collectors.toList()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
            );
        } catch (IOException ex) {
            logger.error("USAGE: JavaGrep regex rootPath outFile", ex);
        }
    }

    @Override
    public List<File> listFiles(String rootDir) throws IOException {
        Path path = Paths.get(rootDir);
        Stream<File> files = Files.walk(path).map(Path::toFile);
        return files.filter(File::isFile).collect(Collectors.toList());
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException {
        Stream<String> lines = Files.lines(inputFile.toPath());
        return lines.collect(Collectors.toList());
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        javaGrepLambdaImp javaGrepLambdaImp = new javaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try {
            javaGrepLambdaImp.process();
        } catch (Exception ex) {
            JavaGrepImp.logger.error("Unable to run Java Grep!", ex);
        }
    }
}