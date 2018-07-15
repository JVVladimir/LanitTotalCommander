package tools;

public class FileInfo {

    private java.lang.String name;
    private java.lang.String extend;
    private String type;
    private String dateCreation;
    private String dateMod;
    private long size;


    public FileInfo(java.lang.String name, java.lang.String extend, String type, String dateCreation, String dateMod, long size) {
        this.name = name;
        this.extend = extend;
        this.type = type;
        this.dateCreation = dateCreation;
        this.dateMod = dateMod;
        this.size = (long) ((double) size / 1024.0);
    }

    public FileInfo() {
    }


    private java.lang.String getNameByType(FileType type) {
        switch (type) {
            case DIRECTORY:
                return "директория";
            case REGULAR_FILE:
                return "файл";
            case SYMBOLIC_LINK:
                return "символическая ссылка";
            default:
                return "не известно";
        }
    }

    public java.lang.String getName() {
        return name;
    }

    public void setName(java.lang.String name) {
        this.name = name;
    }

    public java.lang.String getExtend() {
        return extend;
    }

    public void setExtend(java.lang.String extend) {
        this.extend = extend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getDateMod() {
        return dateMod;
    }

    public void setDateMod(String dateMod) {
        this.dateMod = dateMod;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public java.lang.String toString() {
        return java.lang.String.format("Имя: %s, расширение: %s, тип: %s, дата создания: [%s]," +
                        " дата изменения: [%s], размер: %s кБайт",
                name, extend, type, dateCreation, dateMod, size);
    }

}
