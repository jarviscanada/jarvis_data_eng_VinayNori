# Introduction
This project is an implementation of the Linux grep command using grep app in Java. The app searches for a text pattern recursively in a given directory, 
and output matched lines to a file

```
$ java -cp <jar file> <class_file> <regex/pattern> <directory> <out_file>
```
There are two implementations of this application:
1. JavaGrepImp : uses for loops for searching
2. JavaGrepLambdaImp : uses Streams for the implementation instead of loops

To get the jar file, you can use Maven to compile the project or run a container. 
Use either Github or docker hub to get the files.

* `mvn clean package`
* `docker pull norivinay/grep`

# Quick Start
1. Maven
```
$ mvn clean package

$ java -cp target/*.jar <class_file> <regex/pattern> <directory> <out_file>
```

2. Docker
```
$ docker pull norivinay/grep

$ docker run --rm -v <directory> -v <out_file> barlowza/grep <regex/pattern> <directory> <out_file>
```

# Implementation
There are two implementations for this:
1. JavaGrepImp:
```java
matchedLines = []
for file in listFilesRecursively(rootDir)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

2. JavaGrepLambdaImp
```java
listFiles(getRootPath()).stream()
    .forEach(writeToFile(readLines(file).stream()
    .filter(containsPattern(line)).toList)
);
```

## Performance Issue
The performance issue with `JavaGrepImp` was dealing with Lists and looping through
each individual peace causing the heap memory to get bloated due to creating an Object for each piece and hence throwing an OutOfMemoryError exception..
This was solved by using Streams instead of lists enabling the application to process huge data with a small heap memory.

# Testing
Testing was done manually by creating files for output the test for certain cases that it could run against.

# Deployment
To deploy, a Dockerfile is created and the image of the program is uploaded to Docker Hub
to allow an easier distribution and access to the data inorder to use the application.

# Improvement
1. Modify interface to use either Streams of BufferedReader.
2. Matching Subdirectories : Expand the project to match lines recursively from all the files in subdirectories.
3. Display Line Number: Expand the application to display the matched line number along with the line in the output file.
