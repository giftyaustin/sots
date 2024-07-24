package com.memesots.MemesOTS.socialLogin;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GoogleProfile {
    private String resourceName;
    private String etag;
    private List<Name> names;
    private List<EmailAddress> emailAddresses;
    private List<Photo> photos;

    @Getter
    @Setter
    @ToString
    public static class Name {
        private String displayName;
        private String familyName;
        private String givenName;
        private String displayNameLastFirst;
        private String unstructuredName;
    }

    @Getter
    @Setter
    @ToString
    public static class EmailAddress {
        private String value;
    }

    @Getter
    @Setter
    @ToString
    public static class Photo {
        private String url;
    }
}
