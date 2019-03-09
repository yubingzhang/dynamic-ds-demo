package com.belle.interceptor;

import com.belle.common.DataSourceContextHolder;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Properties;
//mybatis 拦截器
@Intercepts({
        @Signature(type = Executor.class,method = "update",args = {MappedStatement.class,Object.class}),
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class})
})
public class DynamicDataSourcePlugin implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        /**事物的操作，都用写库**/
        //使用的数据库
        String routeKey = null;
        //获取增删改时，sql的参数(也就是这个拦截器的update方法的args参数值)
        Object[] args = invocation.getArgs();
        MappedStatement mappedStatement = (MappedStatement)args[0];
        //事物是否激活
        boolean isActive = TransactionSynchronizationManager.isActualTransactionActive();
        if (isActive){
            routeKey = DataSourceContextHolder.MASTER_DS;
        }else{
            if (mappedStatement.getSqlCommandType().equals(SqlCommandType.SELECT)){
                //如果是selectkey
                if(mappedStatement.getId().equals(SelectKeyGenerator.SELECT_KEY_SUFFIX)){
                    routeKey = DataSourceContextHolder.MASTER_DS;
                }
                else{
                    routeKey = DataSourceContextHolder.DEFAULT_DS;
                }
            }else{
                routeKey = DataSourceContextHolder.DEFAULT_DS;
            }
        }
        DataSourceContextHolder.setDB(routeKey);
        System.out.println("使用了方法："+invocation.getMethod().getName()+",使用的数据库："+routeKey+",执行的命令： "+mappedStatement.getSqlCommandType().name());
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target,this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
