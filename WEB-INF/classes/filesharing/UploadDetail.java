package filesharing;
 
import java.io.Serializable;
 
public class UploadDetail implements Serializable {
 
    private long fileSize;
    private String fileName, uploadStatus, username;
 
    private static final long serialVersionUID = 1L;
 
    public long getFileSize() {
        return fileSize;
    }
 
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
 
    public String getFileName() {
        return fileName;
    }
 
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
 
    public String getUploadStatus() {
        return uploadStatus;
    }
 
    public void setUploadStatus(String uploadStatus) {
        this.uploadStatus = uploadStatus;
    }
}