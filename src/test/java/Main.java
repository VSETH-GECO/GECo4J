import ch.ethz.geco.g4j.impl.DefaultGECoClient;
import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.obj.User;

public class Main {
    public static void main(String[] args) {
        GECoClient gecoClient = new DefaultGECoClient("");

        User user = gecoClient.getUserByID(1L);

        System.out.println(user.getUserName());
    }
}
