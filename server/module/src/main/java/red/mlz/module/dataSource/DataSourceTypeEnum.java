package red.mlz.module.dataSource;


public enum DataSourceTypeEnum {

    /**
     * mysqlMaster主库
     */
    MASTER("master"),

    /**
     * slave从库
     */
    SLAVE("slave");

    /**
     * 数据库类型
     */
    private String type;

    /**
     * 构造函数
     */
    DataSourceTypeEnum(String type) {
        this.type = type;
    }

    /**
     * 数据库类型
     *
     * @return type
     */
    public String getType() {
        return type;
    }

}
