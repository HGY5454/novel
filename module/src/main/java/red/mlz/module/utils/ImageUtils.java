package red.mlz.module.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.net.URL;

public class ImageUtils {
    private static String defaultMaleAvatar = "/photo/avatar.png";
    private static String defaultFeMaleAvatar = "/photo/avatar.png";

    public static String getDefaultMaleAvatar(){
        return defaultMaleAvatar;
    }

    public static String getDefaultFeMaleAvatar(){
        return defaultFeMaleAvatar;
    }

    public static int[] getImageWidthAndHeight(String imageUrl){
        int[] wh = new int[2];
        String[] imageStr = imageUrl.split("_");
        if(imageStr.length >= 2){
            String[] imageStrEnd = imageStr[imageStr.length -1].split("\\.");
            String[] imageParam = imageStrEnd[0].split("x");
            if(imageParam.length == 2 && BaseUtils.isNumeric(imageParam[0]) && BaseUtils.isNumeric(imageParam[1])){
                wh[0] = new Integer(imageParam[0]);
                wh[1] = new Integer(imageParam[1]);
                return wh;
            }
        }

        try {
            URL url = new URL(imageUrl);
            BufferedImage sourceImg = ImageIO.read(new BufferedInputStream(url.openStream()));
            wh[0] = sourceImg.getWidth();
            wh[1] = sourceImg.getHeight();
        } catch (Exception e) {
            wh[0] = 0;
            wh[1] = 0;
        }
        return wh;
    }
}
