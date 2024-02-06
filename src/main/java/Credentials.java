import java.nio.charset.StandardCharsets;

public class Credentials {
    static String number;
    boolean checkIfAdmin(){
        return number.getBytes(StandardCharsets.UTF_8)[0]=='М';
    }
    boolean checkIfCashier(){
        return number.getBytes(StandardCharsets.UTF_8)[0]=='К';
    }
}
