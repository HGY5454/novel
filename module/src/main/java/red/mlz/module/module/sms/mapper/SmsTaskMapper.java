package red.mlz.module.module.sms.mapper;

import red.mlz.module.module.sms.entity.SmsTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface SmsTaskMapper{

        @Select("select * from sms_task where is_deleted = 0")
        List<SmsTask>  getListSmsTask();


        @Select("select * from sms_task where  id = #{id}")
        SmsTask getById(@Param("id") Integer id);

        @Select("select * from sms_task where  id = #{id}")
        SmsTask extractById(@Param("id") Integer id);

        int insert(@Param("smsTask") SmsTask smsTask);

        int update(@Param("smsTask") SmsTask smsTask);

        @Update("update sms_task set is_deleted = 1 and update_time=#{timestamp} where id = #{id} limit 1")
        int delete(@Param("id") Integer id,
                   @Param("timestamp")int timestamp);
}
