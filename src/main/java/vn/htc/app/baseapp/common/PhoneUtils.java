package vn.htc.app.baseapp.common;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {

    public static void main(String[] args) throws IOException {
//        String str = "cong hoa xa hoi chu nghia viet nam, Doc lap tu do mieng nao to thi gap thu mot mieng neu an duoc thi an khong an duoc thi cho cho co duoc khong ha cac che thic]";
//        System.out.println(countLengthNew(str));
        String phone = "841238709119";
        System.out.println(convert11To10(phone));
    }

    public static String phoneTo84(String number) {
        if (number == null) {
            number = "";
            return number;
        }
        number = number.replaceAll("o", "0");
        number = number.replaceAll("\\+", "");
        if (number.startsWith("84")) {
            return number;
        } else if (number.startsWith("0")) {
            number = "84" + number.substring(1);
        } else if (number.startsWith("+84")) {
            number = number.substring(1);
        } else {
            number = "84" + number;
        }
        return number;
    }

    public static String getPhone84(String number) {
        if (number == null) {
            number = "";
            return number;
        }
        String get09String410 = "";
        String get09String411 = "";

        int lengthNumber = number.length();
        if (lengthNumber < 10) {
            return "";
        }
        if (lengthNumber == 10) {
            return number;
        } else {
            if (lengthNumber == 11) {
                return number;
            } else {
                get09String410 = number.substring(lengthNumber - 10, lengthNumber);
                get09String411 = number.substring(lengthNumber - 11, lengthNumber);
                if (get09String411.startsWith("84")) {
                    return get09String411;
                } else {
                    if (get09String410.startsWith("0")) {
                        return get09String410;
                    }
                }
            }
        }

        return number;
    }

    public static boolean validPhoneVN(String phone) {
        phone = phoneTo84(phone);
        String regex = ""
                + "^"
                + "((843|845|847|848|849)\\d{8})|(841\\d{9})"
                + "$";
        // Create a Pattern object
        Pattern pattern = Pattern.compile(regex);
        // Now create matcher object.
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static enum OPER {

        VIETTEL("VTE"),
        VINA("GPC"),
        MOBI("VMS"),
        VNM("VNM"),
        BEELINE("BL"),
        ITELECOM("DDG"),
        OTHER("OTHER");
        public String val;

        public String getVal() {
            return val;
        }

        private OPER(String val) {
            this.val = val;
        }
    }

    public static String buildMobileOperator(String phone) {
        String mobileOperator = "OTHER";
        phone = phoneTo84(phone);
        boolean isPhoneVn = validPhoneVN(phone);    // Fix loi So dien thoai dai hon 10 hoac 11 so
        if (!isPhoneVn) {
            return mobileOperator;
        }
//        System.out.println("is phone Valid");
        if (//-
                phone.startsWith("8491")
                || phone.startsWith("8494")
                || phone.startsWith("84123")
                || phone.startsWith("84124")
                || phone.startsWith("84125")
                || phone.startsWith("84127")
                || phone.startsWith("84129")
                || phone.startsWith("8481")
                || phone.startsWith("8482")
                || phone.startsWith("8483")
                || phone.startsWith("8484")
                || phone.startsWith("8485")
                || phone.startsWith("8487")
                || phone.startsWith("8488")// NEW
                ) {
            //VINA
            mobileOperator = OPER.VINA.val;
        } else if (//--
                phone.startsWith("8490")
                || phone.startsWith("8493")
                || phone.startsWith("84120")
                || phone.startsWith("84121")
                || phone.startsWith("84122")
                || phone.startsWith("84126")
                || phone.startsWith("84128")
                || phone.startsWith("8489")
                || phone.startsWith("8470")
                || phone.startsWith("8476")
                || phone.startsWith("8477")
                || phone.startsWith("8478")
                || phone.startsWith("8479") // NEW
                ) {
            // MOBILE
            mobileOperator = OPER.MOBI.val;
        } else if (//--
                phone.startsWith("8498")
                || phone.startsWith("8497")
                || phone.startsWith("8496")
                || phone.startsWith("8416")
                || phone.startsWith("8486")
                || phone.startsWith("843") // Chuyen Mang Moi 016 -> 03x
                ) {
            mobileOperator = OPER.VIETTEL.val;
        } else if (phone.startsWith("8492")
                || phone.startsWith("84188")
                // || phone.startsWith("84187") 
                || phone.startsWith("84186")
                // || phone.startsWith("84184") 
                || phone.startsWith("8452")
                || phone.startsWith("8456") // 0186 -> 056
                || phone.startsWith("8458") // 0188 -> 058
                ) {
            // VIET NAM MOBILE
            mobileOperator = OPER.VNM.val;
        } else if (phone.startsWith("8499")
                || phone.startsWith("84199")
                || phone.startsWith("8459")) {
            mobileOperator = OPER.BEELINE.val;
        } else if (phone.startsWith("8487")
                || phone.startsWith("8489")) {
            mobileOperator = OPER.ITELECOM.val;
        } else {
            mobileOperator = OPER.OTHER.val;
        }
        return mobileOperator;
    }

    public static String convert11To10(String phoneString) {
        String result = phoneString;
        String beginPart = phoneString.substring(0, 5);
//        System.out.println("phan dau="+beginPart);
        switch (beginPart) {
            case "84162":
                result = "8432" + phoneString.substring(5);
                break;
            case "84163":
                result = "8433" + phoneString.substring(5);
                break;
            case "84164":
                result = "8434" + phoneString.substring(5);
                break;
            case "84165":
                result = "8435" + phoneString.substring(5);
                break;
            case "84166":
                result = "8436" + phoneString.substring(5);
                break;
            case "84167":
                result = "8437" + phoneString.substring(5);
                break;
            case "84168":
                result = "8438" + phoneString.substring(5);
                break;
            case "84169":
                result = "8439" + phoneString.substring(5);
                break;
            case "84120":
                result = "8470" + phoneString.substring(5);
                break;
            case "84121":
                result = "8479" + phoneString.substring(5);
                break;
            case "84122":
                result = "8477" + phoneString.substring(5);
                break;
            case "84126":
                result = "8476" + phoneString.substring(5);
                break;
            case "84128":
                result = "8478" + phoneString.substring(5);
                break;
            case "84123":
                result = "8483" + phoneString.substring(5);
                break;
            case "84124":
                result = "8484" + phoneString.substring(5);
                break;
            case "84125":
                result = "8485" + phoneString.substring(5);
                break;
            case "84127":
                result = "8481" + phoneString.substring(5);
                break;
            case "84129":
                result = "8482" + phoneString.substring(5);
                break;
            case "84188":
                result = "8458" + phoneString.substring(5);
                break;
            case "84199":
                result = "8459" + phoneString.substring(5);
                break;
            case "84186":
                result = "8456" + phoneString.substring(5);
                break;
            default:
                break;
        }
        return result;
    }

}
