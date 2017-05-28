package spet.sbwo.control.channel;

public class UserPreferenceChannel {
    private String language;
    private String theme;
    private int draftResumeDelay;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getDraftResumeDelay() {
        return draftResumeDelay;
    }

    public void setDraftResumeDelay(int draftResumeDelay) {
        this.draftResumeDelay = draftResumeDelay;
    }

}
