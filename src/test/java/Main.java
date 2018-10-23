import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.impl.GECoClient;
import ch.ethz.geco.g4j.obj.IUser;

public class Main {
    public static void main(String[] args) {
        GECoClient gecoClient = new GECoClient("");

        IUser user = gecoClient.getUserByID(1l);

        System.out.println(user.getUserName());
    }
}
