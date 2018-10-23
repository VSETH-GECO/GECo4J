package ch.ethz.geco.g4j.internal.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanUserObject {
    Long id;
    String status;
    Short status_code;
    String username;
    String first_name;
    String last_name;
    String seat;
    Long birthday;
    Boolean sa_verified;
    String legi_number;
    @JsonProperty("package")
    String package_name;
    String student_association;
}
