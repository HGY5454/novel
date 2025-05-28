package red.mlz.module.dataSource;



import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import red.mlz.module.dataSource.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class RoutingDataSourceAspect {

    /**
     * master主库写库AOP切入点
     */
    @Pointcut("execution(* red.mlz.module.module.*.mapper..*.insert*(..)) "
            + "|| execution(* red.mlz.module.module.*.mapper..*.save*(..))"
            + "|| execution(* red.mlz.module.module.*.mapper..*.update*(..))"
            + "|| execution(* red.mlz.module.module.*.mapper..*.delete*(..))"
            + "|| execution(* red.mlz.module.module.*.mapper..*.reomove*(..))")
    private void masterDataSourcePointcut() {
        log.info("切换到master主数据源");
    }

    /**
     * slave数据库读库服务AOP切入点
     */
    @Pointcut("execution(* red.mlz.module.module.*.mapper.ImMessageMapper.select*(..))")
    private void slaveDataSourcePointcut() {
        log.info("切换到slave从数据源");
    }

    /**
     * 切换master数据源
     */
    @Before("masterDataSourcePointcut()")
    public void master() {
        DbContextHandler.setDbType(DataSourceTypeEnum.MASTER);
    }

    /**
     * 返回后清除master数据源
     */
    @AfterReturning("masterDataSourcePointcut()")
    public void masterClear() {
        DbContextHandler.clearDbType();
    }

    /**
     * 异常时清除master数据源
     */
    @AfterThrowing("masterDataSourcePointcut()")
    public void masterExceptionClear() {

        DbContextHandler.clearDbType();
    }

    /**
     * 切换slave数据源
     */
    @Before("slaveDataSourcePointcut()")
    public void slave() {

        DbContextHandler.setDbType(DataSourceTypeEnum.SLAVE);
    }

    /**
     * 返回后清除slave数据源
     */
    @AfterReturning("slaveDataSourcePointcut()")
    public void slaveClear() {

        DbContextHandler.clearDbType();
    }

    /**
     * 异常时清除slave数据源
     */
    @AfterThrowing("slaveDataSourcePointcut()")
    public void slaveExceptionClear() {

        DbContextHandler.clearDbType();
    }



}