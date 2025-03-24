package red.mlz.module.utils;

import org.apache.commons.lang3.ArrayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {
    private static final String[] officialPhone = {
            "***"
    };

    public static boolean isPhoneNumber(String phone) {
        if (ArrayUtils.contains(officialPhone, phone)) {
            return true;
        }
        String regex = "[1|2]\\d{10}";
        return Pattern.matches(regex, phone);
    }

    public static boolean isEmail(String email) {
        final String regex = "[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+@[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+\\.[\\w.\\\\+\\-\\*\\/\\=\\`\\~\\!\\#\\$\\%\\^\\&\\*\\{\\}\\|\\'\\_\\?]+";

        final Pattern pattern = Pattern.compile(regex);
        final Matcher mat = pattern.matcher(email);
        return mat.matches();
    }
}
