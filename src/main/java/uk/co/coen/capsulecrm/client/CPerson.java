package uk.co.coen.capsulecrm.client;

import com.google.common.base.Objects;
import org.joda.time.DateTime;

import java.util.List;

public class CPerson extends CParty {
    public Title title;
    public String firstName;
    public String lastName;
    public String jobTitle;
    public Long organisationId;
    public String organisationName;

    @Override
    protected String writeContextPath() {
        return "/person";
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("title", title)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("jobTitle", jobTitle)
                .add("organisationId", organisationId)
                .add("organisationName", organisationName)
                .add("name", getName())
                .toString();
    }
}
