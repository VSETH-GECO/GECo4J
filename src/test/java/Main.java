import ch.ethz.geco.g4j.impl.GECoClient;
import ch.ethz.geco.g4j.obj.IGECoClient;
import ch.ethz.geco.g4j.obj.IUser;

public class Main {
    public static void main(String[] args) {
        IGECoClient gecoClient = new GECoClient("");

        IUser user = gecoClient.getUserByID(1L);

        System.out.println(user.getUserName());
    }
}
