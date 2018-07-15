package tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class FileManager implements FileVisitor {

    private ArrayList<FileInfo> list;
    private Path p;

    public ArrayList<FileInfo> loadFileTree(Path start) {
        try {
            list = new ArrayList();
            p = start;
            Files.walkFileTree(start, this);
        } catch (IOException e) {
            System.out.println("****Ошибка при посещении файлов****");
            e.printStackTrace();
        }
        return list;
    }

    public void copyFilesIfExist(Path p1, Path p2) {
        try {
            Files.copy(p1, p2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("****Не удалось скопировать файл****");
            e.printStackTrace();
        }
    }

    // Если директория, то копируется без вложенных файлов и папок
    public void copyFiles(Path p1, Path p2) {
        try {
            Files.copy(p1, p2);
        } catch (IOException e) {
            System.out.println("****Не удалось скопировать файл****");
            e.printStackTrace();
        }
    }

    public void moveFilesIfExist(Path p1, Path p2) {
        try {
            Files.move(p1, p2, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println("****Не удалось переместить файлы****");
            e.printStackTrace();
        }
    }

    public void moveFiles(Path p1, Path p2) {
        try {
            Files.move(p1, p2);
        } catch (IOException e) {
            System.out.println("****Не удалось переместить файлы****");
            e.printStackTrace();
        }
    }

    public void deleteFile(Path p1) {
        try {
            Files.delete(p1);
        } catch (IOException e) {
            System.out.println("****Невозможно удалить файл:" + p1.toString() + "****");
            e.printStackTrace();
        }
    }

    public void deleteFiles(Path[] array) {
        for (Path p : array)
            deleteFile(p);
    }

    private void addPath(FileInfo s) {
        list.add(s);
    }

    @Override
    public FileVisitResult preVisitDirectory(Object dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("Посещаем директорию " + ((Path) dir).toAbsolutePath().toString());
        Path p = (Path) dir;
        if (p == null || p.getFileName() == null)
            return FileVisitResult.CONTINUE;
        int n = p.getNameCount();
        if (p.getFileName().equals(this.p.getFileName()))
            return FileVisitResult.CONTINUE;
        else {
            FileInfo fInfo = new FileInfo(p.getName(n - 1).toString(), null, getStringByType(FileType.DIRECTORY),
                    new TimeKeeper(attrs.creationTime()).toString(), new TimeKeeper(attrs.lastModifiedTime()).toString(), 0);
            addPath(fInfo);
            return FileVisitResult.SKIP_SUBTREE;
        }
    }

    @Override
    public FileVisitResult visitFile(Object file, BasicFileAttributes attrs) throws IOException {
        Path p = (Path) file;
        FileType type;
        if (attrs.isRegularFile())
            type = FileType.REGULAR_FILE;
        else if (attrs.isSymbolicLink())
            type = FileType.SYMBOLIC_LINK;
        else
            type = FileType.OTHER;
        int n = p.getNameCount();
        if (n < 2)
            return FileVisitResult.CONTINUE;
        FileInfo fInfo = new FileInfo(getFileName(p.getName(n - 1).toString()), getFileExtension(p.getName(n - 1).toString()), getStringByType(type),
                new TimeKeeper(attrs.creationTime()).toString(), new TimeKeeper(attrs.lastModifiedTime()).toString(), attrs.size());
        addPath(fInfo);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Object file, IOException exc) throws IOException {
        System.out.println("****Ошибка при посещении файла: " + ((Path) file).toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Object dir, IOException exc) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    private java.lang.String getFileExtension(java.lang.String str) {
        if (str == null)
            return "";
        int index = str.lastIndexOf('.');
        return index == -1 ? "" : str.substring(index);
    }

    private java.lang.String getFileName(java.lang.String str) {
        if (str == null)
            return "";
        int index = str.lastIndexOf('.');
        return index == -1 ? str : str.substring(0, index);
    }

    public static String getStringByType(FileType type) {
        switch (type) {
            case DIRECTORY: return "Директория";
            case REGULAR_FILE: return "Файл";
            case SYMBOLIC_LINK: return "Ссылка";
            default: return "Другое";
        }
    }

    public static ArrayList<String> getRoots() {
        ArrayList<String> list = new ArrayList<>();
        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++)
            list.add(roots[i].toString());
        return list;
    }

}
