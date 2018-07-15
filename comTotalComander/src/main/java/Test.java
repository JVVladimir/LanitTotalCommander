import java.io.File;

public class Test {

    public static void main(String[] args) {

        File[] roots = File.listRoots();
        for (int i = 0; i < roots.length; i++)
            System.out.println("Root[" + i + "]:" + roots[i]);
        /*FileManager file = new FileManager();
        while()
        ArrayList<FileInfo> list = file.loadFileTree(Paths.get("D:\\"));

        System.out.println(list.size());
        for(FileInfo s: list)
            System.out.println(s);*/

/**
 *
 * if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
 *                 System.out.println(path.getFileName() + " is a directory");
 *             } else {
 *                 System.out.println(path.getFileName() + " is a file");
 *             }
 *  System.out.printf("Readable: %b, Writable: %b, Executable: %b ",
 *                 Files.isReadable(path), Files.isWritable(path),
 *                 Files.isExecutable(path));
 *    Path path = Paths.get("Вставьте сюда путь к какому-либо файлу");
 *
 *
 *
 *         try {
 *             Object object = Files.getAttribute(path, "creationTime");
 *             System.out.println("Creation time: " + object);
 *
 *             //Здесь указан третий параметр
 *             object = Files.getAttribute(path, "lastModifiedTime",
 *                     LinkOption.NOFOLLOW_LINKS);
 *             System.out.println("Last modified time: " + object);
 *
 *             object = Files.getAttribute(path, "size");
 *             System.out.println("Size: " + object);
 *
 *             object = Files.getAttribute(path, "isDirectory");
 *             System.out.println("isDirectory: " + object);
 *         } catch (IOException e) {
 *             e.p
 *
 *
 *
 *
 * */
    }
}
