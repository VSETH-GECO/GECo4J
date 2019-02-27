package ch.ethz.geco.g4j.internal.json;

public class VerifyRequest {
    public final Boolean sa_verified;
    public final String legi_number;

    public VerifyRequest(Boolean isVerified, String legiNumber) {
        this.sa_verified = isVerified;
        this.legi_number = legiNumber;
    }
}
