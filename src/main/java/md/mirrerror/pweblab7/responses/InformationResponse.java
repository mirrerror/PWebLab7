package md.mirrerror.pweblab7.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InformationResponse {

    private String message;
    private long timestamp;

    public InformationResponse(String message) {
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

}