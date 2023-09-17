package von.rims.logistics.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RouterResponse {
    private int from;
    private int to;
    private boolean hasDirectRoute;
}
