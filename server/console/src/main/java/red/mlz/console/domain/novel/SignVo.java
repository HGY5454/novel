package red.mlz.console.domain.novel;

import lombok.Data;

import java.math.BigInteger;

@Data
public class SignVo {
    private BigInteger id;
    private long expiryDate;
}
