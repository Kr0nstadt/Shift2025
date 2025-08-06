package output;

public class FileWriterFactory {
    public static FileWriterData getFileWriter(boolean appendMode) {
        return appendMode ? new AppendFileWriter() : new OverwriteFileWriter();
    }
}