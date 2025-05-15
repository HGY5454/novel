package red.mlz.module.module.sms.mapper;

import red.mlz.module.module.sms.entity.SmsRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


@Mapper
public interface SmsRecordMapper{

        @Select("select * from sms_record where is_deleted = 0")
        List<SmsRecord>  getListSmsRecord();


        @Select("select * from sms_record where  id = #{id}")
        SmsRecord getById(@Param("id") Integer id);

        @Select("select * from sms_record where  id = #{id}")
        SmsRecord extractById(@Param("id") Integer id);

        int insert(@Param("smsRecord") SmsRecord smsRecord);

        int update(@Param("smsRecord") SmsRecord smsRecord);

        @Update("update sms_record set is_deleted = 1 and update_time=#{timestamp} where id = #{id} limit 1")
        int delete(@Param("id") Integer id,
                   @Param("timestamp")int timestamp);
}
