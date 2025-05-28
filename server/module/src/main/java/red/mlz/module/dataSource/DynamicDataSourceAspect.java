//package red.mlz.module.dataSource;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.core.Ordered;
//import org.springframework.stereotype.Component;
//
//@Component
//@Aspect
//public class DynamicDataSourceAspect implements Ordered {
//    // 前置
//    @Before("within(red.mlz.module.module.*.*) && @annotation(wr)")
//    public void before(JoinPoint point, WR wr){
//        String name = wr.value();
//        DynamicDataSource.name.set(name);
//    }
//    @Override
//    public int getOrder() {
//        return 0;
//    }
//}
