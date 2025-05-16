package md.mirrerror.pweblab7.models;

import lombok.Getter;

@Getter
public enum TVSeriesStatus {

    WATCHING("Watching"),
    PLAN_TO_WATCH("Plan to Watch"),
    WATCHED("Watched");

    private final String displayName;

    TVSeriesStatus(String displayName) {
        this.displayName = displayName;
    }

    public static TVSeriesStatus fromDisplayName(String displayName) {
        for (TVSeriesStatus status : TVSeriesStatus.values()) {
            if (status.getDisplayName().equals(displayName)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No status with display name: " + displayName);
    }

}
