package com.niy.tds;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileVisitor extends SimpleFileVisitor<Path> {
    ArrayList<Path> foundFiles = new ArrayList<>();

    @Override
    public FileVisitResult visitFile (Path file, BasicFileAttributes attrs) {

        String fileName = file.getFileName().toString().toLowerCase();

        if (fileName.endsWith(".xml")){
            foundFiles.add(file);
        }

        return FileVisitResult.CONTINUE;
    }
}
