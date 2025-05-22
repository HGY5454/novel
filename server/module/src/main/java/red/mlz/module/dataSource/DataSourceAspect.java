package red.mlz.module.dataSource;

import org.springframework.stereotype.Component;

@Aspect
@Component
public class DataSourceAspect {
    @Before("execution(* red.mlz.module.module.*.mapper..*.select*(..)) || " +
            "execution(* red.mlz.module.module.*.mapper..*.get*(..))")
    public void setSlaveDataSource() {
        DynamicDataSource.setDataSource("slave");
    }

    @Before("execution(* red.mlz.module.module.*.mapper..*.insert*(..)) || " +
            "execution(* red.mlz.module.module.*.mapper..*.update*(..)) || " +
            "execution(* red.mlz.module.module.*.mapper..*.delete*(..))")
    public void setMasterDataSource() {
        DynamicDataSource.setDataSource("master");
    }
}