//package red.mlz.module.dataSource;
//
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//
//
//@Aspect
//@Component
//public class DataSourceAspect {
//
//    @Before("execution(* red.mlz.module.module.*.mapper..*.select*(..)) || execution(* red.mlz.module.module.*.mapper..*.find*(..))")
//    public void setReadDataSourceType() {
//        DataSourceContextHolder.setDataSourceType("slave");
//    }
//
//    @Before("execution(* red.mlz.module.module.*.mapper..*.insert*(..)) || execution(* red.mlz.module.module.*.mapper..*.update*(..)) || execution(* red.mlz.module.module.*.mapper..*.delete*(..))")
//    public void setWriteDataSourceType() {
//        DataSourceContextHolder.setDataSourceType("master");
//    }
//
//    @After("execution(* red.mlz.module.module.*.mapper..*.*(..))")
//    public void clearDataSourceType() {
//        DataSourceContextHolder.clearDataSourceType();
//    }
//}