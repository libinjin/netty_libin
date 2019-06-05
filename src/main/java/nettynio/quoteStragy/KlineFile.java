package nettynio.quoteStragy;

import java.util.List;

public class KlineFile {

    private List<CommonKlinePoint> klist;

    private String fileName;

    private List<String> file;

    private String absolutFile;

    private int code;//股票代码

    private String name;//股票名称

    public List<CommonKlinePoint> getKlist() {
        return klist;
    }

    public void setKlist(List<CommonKlinePoint> klist) {
        this.klist = klist;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<String> getFile() {
        return file;
    }

    public void setFile(List<String> file) {
        this.file = file;
    }

    public String getAbsolutFile() {
        return absolutFile;
    }

    public void setAbsolutFile(String absolutFile) {
        this.absolutFile = absolutFile;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public KlineFile(List<CommonKlinePoint> klist, String fileName, List<String> file, String absolutFile, int code, String name) {
        this.klist = klist;
        this.fileName = fileName;
        this.file = file;
        this.absolutFile = absolutFile;
        this.code = code;
        this.name = name;
    }
}
