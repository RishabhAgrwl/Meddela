package com.example.rishabh.meddela;

public class Notes {

    private String fileName;
    private String fileSize;
    private String uploadedOn;
    private String downloadUrl;

    public Notes(String fileName, String fileSize, String uploadedOn) {
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadedOn = uploadedOn;
    }

    public String getFileName() {

        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setFileSize(String fileSize) {

        int size = Integer.parseInt(fileSize);

        if (size < 1024) {
            this.fileSize = fileSize + " bytes";
        } else if ((size/1024)<1024) {
            this.fileSize = size/1024 + " KB";
        } else if ((size/(1024*1024))<1024) {
            this.fileSize = size/(1024*1024) + " MB";
        }

    }

    public String getUploadedOn() {
        return uploadedOn;
    }

    public void setUploadedOn(String uploadedOn) {
        this.uploadedOn = "-  " + uploadedOn.substring(6, 8) + "/" + uploadedOn.substring(4, 6) + "/" + uploadedOn.substring(0, 4);
    }

    public Notes() {
    }

}

