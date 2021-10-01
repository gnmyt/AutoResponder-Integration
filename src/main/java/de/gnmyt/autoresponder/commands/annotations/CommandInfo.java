package de.gnmyt.autoresponder.commands.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface CommandInfo {

    String[] aliases();

    String[] bannedGroupNames() default {};

    String[] bannedUserNames() default {};

    String description() default "No description set";

}
