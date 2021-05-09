import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lichi
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String msg = Test.check("00930811111");
        System.out.print(msg);
        FileWriter fw = new FileWriter("test.txt");
        fw.write(msg);
        fw.flush();
        fw.close();
    }

    private static String check(String input) {
        String insertCommaToInput = Test.insertCommaToInput(input);
        int[] intArrayToAccount = Arrays.stream(insertCommaToInput.split(",")).mapToInt(Integer::parseInt).toArray();
        String msg = "正確";
        String account = input;
        String[] stringArrayAccount = new String[] {account};
        List<String> integerList = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();

        if ("20".equals(input.substring(3, 5)) || "001".equals(input.substring(0, 3)) || "002".equals(input.substring(0, 3))
                || "003".equals(input.substring(0, 3)) || "004".equals(input.substring(0, 3)) || "005".equals(input.substring(0, 3))
                || "006".equals(input.substring(0, 3)) || "007".equals(input.substring(0, 3)) || "008".equals(input.substring(0, 3))) {
            if ("30".equals((input.substring(3, 5))) || "40".equals(input.substring(3, 5)) || "66".equals(input.substring(3, 5))) {
                account = addZeroForNum(input.substring(5, 11), 11);
                System.out.println("補0測試" + Test.addZeroForNum(account, 11));
            }
        } else {
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < intArrayToAccount.length-1; i++) {
                switch(i) {
                    case 10:
                    case 3:
                        list.add(intArrayToAccount[i] * 2);
                        break;
                    case 9:
                    case 2:
                        list.add(intArrayToAccount[i] * 3);
                        break;
                    case 8:
                    case 1:
                        list.add(intArrayToAccount[i] * 4);
                        break;
                    case 7:
                    case 0:
                        list.add(intArrayToAccount[i] * 5);
                        break;
                    case 6:
                        list.add(intArrayToAccount[i] * 6);
                        break;
                    case 5:
                        list.add(intArrayToAccount[i] * 7);
                        break;
                    case 4:
                        list.add(intArrayToAccount[i] * 8);
                        break;
                    default:
                        System.out.println(list);
                }
            }
            System.out.println(list);
        }

        //1.	輸入起始帳號7長度需為11碼。若不是回覆＂起始帳號長度錯誤＂。
        if (input.length() != 11) {
            msg = "起始帳號長度錯誤";
            return msg;
        }

        //2.	輸入起始帳號需為數字。若不是回覆＂起始帳號需為數字＂。
        for (int i = input.length(); --i >= 0; ) {
            if (!Character.isDigit(input.charAt(0))) {
                msg = "起始帳號需為數字";
                return msg;
            }
        }

        //3.	分行別需為001-099,不得為019。若錯誤回覆”分行別錯誤”。
        if ("19".equals(input.substring(1, 3)) || !"0".equals(input.substring(0, 1))) {
            msg = "分行別錯誤";
            return msg;
        }

        //4.	科目需為10 20 30 40 50 51 66 77。若不是回覆＂科目別錯誤”。
        if (!"10".equals(input.substring(3, 5)) && !"20".equals(input.substring(3, 5)) && !"30".equals(input.substring(3, 5))
                && !"40".equals(input.substring(3, 5)) && !"50".equals(input.substring(3, 5)) && !"51".equals(input.substring(3, 5))
                && !"66".equals(input.substring(3, 5)) && !"77".equals(input.substring(3, 5))) {
            msg = "科目別錯誤";
            return msg;
        }

        //5.	帳號數為01-9999組。若不是回覆＂帳號數錯誤＂。
        return msg;
    }

    /**
     * 補零
     * @param str
     * @param strLength
     * @return
     */
    private static String addZeroForNum (String str,int strLength){
        int strLen = str.length();
        if (strLen < strLength) {
            while (strLen < strLength) {
                StringBuffer sb = new StringBuffer();
                sb.append("0").append(str);
                str = sb.toString();
                strLen = str.length();
            }
        }
        return str;
    }

    /**
     * 插入逗號
     * @param input
     * @return
     */
    public static String insertCommaToInput(String input) {
        StringBuffer sb = new StringBuffer(input);
        sb.insert(1,",");
        sb.insert(3,",");
        sb.insert(5,",");
        sb.insert(7,",");
        sb.insert(9,",");
        sb.insert(11,",");
        sb.insert(13,",");
        sb.insert(15,",");
        sb.insert(17,",");
        sb.insert(19,",");
        return sb.toString();
    }
}
