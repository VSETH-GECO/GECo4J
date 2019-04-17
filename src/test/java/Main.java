import ch.ethz.geco.g4j.GECo4J;
import ch.ethz.geco.g4j.impl.DefaultGECoClient;
import ch.ethz.geco.g4j.obj.GECoClient;
import ch.ethz.geco.g4j.util.APIException;

public class Main {
    public static void main(String[] args) {
        // Set debug logging
        ((GECo4J.GECo4JLogger) GECo4J.LOGGER).setLevel(GECo4J.GECo4JLogger.Level.TRACE);

        // Test Android
        System.setProperty("java.vm.vendor", "The Android Project");

        GECoClient gecoClient = new DefaultGECoClient("");

        gecoClient.getLanUserByID(1L).doOnError(e -> {
            // Error handling
            if (e instanceof APIException) {
                if (((APIException) e).getError() == APIException.Error.NOT_FOUND) {
                    System.out.println("User not found");
                    return;
                }
            }
            e.printStackTrace();
        }).subscribe(lanUser -> lanUser.getBorrowedItems().collectList().subscribe(l -> {
            if (l.isEmpty()) {
                System.out.printf("%s has no borrowed items.\n", lanUser.getFullName());
            } else {
                System.out.printf("Items borrowed by %s: %s", lanUser.getFullName(), l.get(0).getName());
                for (int i = 1; i < l.size(); i++) {
                    System.out.print(", " + l.get(i).getName());
                }
                System.out.println();
            }
        }));

        while (true) {
        }
    }
}
