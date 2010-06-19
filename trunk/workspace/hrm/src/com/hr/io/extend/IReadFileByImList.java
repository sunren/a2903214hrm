package com.hr.io.extend;

import com.hr.io.domain.Inmatch;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public abstract class IReadFileByImList {
    protected File inputFile;
    protected List<Inmatch> imList;
    protected IOMessages ioMessages;
    protected FileInputStream inputStream;
    protected int hasTitle;

    public IReadFileByImList(File inputFile, List<Inmatch> imList, int hasTitle,
            IOMessages ioMessages) {
        setInputFile(inputFile);
        setImList(imList);
        setHasTitle(hasTitle);
        setIoMessages(ioMessages);
    }

    public List<List> getContent() throws Exception {
        openFile();
        List result = readContent();
        closeFile();
        return result;
    }

    protected abstract List<List> readContent();

    protected void openFile() throws Exception {
        this.inputStream = new FileInputStream(this.inputFile);
    }

    protected void closeFile() throws Exception {
        this.inputStream.close();
        this.inputStream = null;
    }

    public File getInputFile() {
        return this.inputFile;
    }

    public List<Inmatch> getImList() {
        return this.imList;
    }

    public IOMessages getIoMessages() {
        return this.ioMessages;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setImList(List<Inmatch> imList) {
        this.imList = imList;
    }

    public void setIoMessages(IOMessages ioMessages) {
        this.ioMessages = ioMessages;
    }

    public int getHasTitle() {
        return this.hasTitle;
    }

    public void setHasTitle(int hasTitle) {
        this.hasTitle = hasTitle;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\365hrm.jar Qualified Name:
 * com.hr.io.extend.IReadFileByImList JD-Core Version: 0.5.4
 */