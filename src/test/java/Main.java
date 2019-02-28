import ch.ethz.geco.g4j.impl.DefaultGECoClient;
import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.obj.LanUser;
import ch.ethz.geco.g4j.util.APIException;

public class Main {
    public static void main(String[] args) {
        GECoClient gecoClient = new DefaultGECoClient("");

        gecoClient.getLanUserByID(1L).doOnError(e -> {
            if (e instanceof APIException) {
                if (((APIException) e).getError() == APIException.Error.NOT_FOUND) {
                    System.out.println("User not found");
                } else {
                    e.printStackTrace();
                }
            } else {
                e.printStackTrace();
            }
        }).flatMapMany(LanUser::getBorrowedItems).collectList().subscribe(l -> {
            if (l.isEmpty()) {
                System.out.println("The user has no borrowed items.");
            }
        });

        while (true) {
        }
    }
}
