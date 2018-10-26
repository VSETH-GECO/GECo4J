package ch.ethz.geco.g4j.internal.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LanUserObject {
    public Long id;
    public String status;
    public Short status_code;
    public String username;
    public String first_name;
    public String last_name;
    public String seat;
    public Long birthday;
    public Boolean sa_verified;
    public String legi_number;
    @JsonProperty("package")
    public String package_name;
    public String student_association;
}
