package red.mlz.module.utils;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ResponseStatus {
    private int code;
    private String msg;
}
