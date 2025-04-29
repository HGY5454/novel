//package red.mlz.crond.controller;
//
//@Component
//public class SmsSendTask {
//
//    @Autowired
//    private SmsTaskMapper smsTaskMapper;
//    @Autowired
//    private SmsService smsService;
//
//    @Scheduled(cron = "0 */5 * * * ?") // 每5分钟执行一次
//    public void execute() {
//        // 查询所有新任务
//        List<SmsTask> tasks = smsTaskMapper.selectByStatus(0);
//
//        for (SmsTask task : tasks) {
//            try {
//                // 发送短信
//                SmsRecord record = smsService.sendSms(task.getPhone(), task.getContent(), task.getTemplateCode());
//
//                // 更新任务状态
//                smsTaskMapper.updateStatus(task.getId(), record.getStatus(),
//                        record.getErrorMsg(), record.getBizId());
//            } catch (Exception e) {
//                smsTaskMapper.updateStatus(task.getId(), 2, e.getMessage(), null);
//            }
//        }
//    }
//}
