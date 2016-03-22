package missionarogya.sherpdownloader;

/**
 * Created by Sonali Sinha on 3/14/2016.
 */
public class SherpData {

    private static SherpData ourInstance = new SherpData();
    private String httpStatusUpload;
    private String httpStatusDownload;
    private String messageUpload;
    private String messageDownload;
    private String interviewData;


    private SherpData(){
    }

    public static SherpData getInstance() {
        return ourInstance;
    }

    public static void setInstance(SherpData sherpData) {
        SherpData.ourInstance = sherpData;
    }

    public String getInterviewData() {
        return interviewData;
    }

    public void setInterviewData(String interviewData) {
        this.interviewData = interviewData;
    }

    public String getHttpStatusUpload() {
        return httpStatusUpload;
    }

    public void setHttpStatusUpload(String httpStatusUpload) {
        this.httpStatusUpload = httpStatusUpload;
    }

    public String getHttpStatusDownload() {
        return httpStatusDownload;
    }

    public void setHttpStatusDownload(String httpStatusDownload) {
        this.httpStatusDownload = httpStatusDownload;
    }

    public String getMessageUpload() {
        return messageUpload;
    }

    public void setMessageUpload(String messageUpload) {
        this.messageUpload = messageUpload;
    }

    public String getMessageDownload() {
        return messageDownload;
    }

    public void setMessageDownload(String messageDownload) {
        this.messageDownload = messageDownload;
    }
}
