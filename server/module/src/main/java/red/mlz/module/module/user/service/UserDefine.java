package red.mlz.module.module.user.service;

public enum UserDefine {
    GENDER_UNKNOWN(0, "未知"),
    GENDER_MALE(1, "男"),
    GENDER_FEMALE(2, "女");

    private int code;
    private String name;

    private UserDefine(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String convertGender(int code) {
        String name = null;
        switch (code) {
            case 1:
                name = GENDER_MALE.name;
                break;
            case 2:
            default:
                name = GENDER_FEMALE.name;
                break;
        }
        return name;
    }

    public static boolean isGender(int code) {
        if (GENDER_UNKNOWN.getCode() == code) {
            return true;
        }
        if (GENDER_MALE.getCode() == code) {
            return true;
        }
        if (GENDER_FEMALE.getCode() == code) {
            return true;
        }
        return false;
    }
}
