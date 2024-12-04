package dha.libapp.utils.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateToMySQL {
    public static String formatDateToMySQL(Date date) {
        if (date == null) {
            return null;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
}
