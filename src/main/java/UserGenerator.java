import io.qameta.allure.Step;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserGenerator {
    @Step("New valid user data")
    public static User getNewValidUser() {
        return new User("steshov1992@gmail.com", "steshser1234", "steshser");
    }

    @Step("New user without email")
    public static User getUserWithoutEmail() {
        return new User(null, "steshser1234", "steshser");
    }

    @Step("New user without password")
    public static User getUserWithoutPassword() {
        return new User("steshov1992@gmail.com", null, "steshser");
    }

    @Step("New user without name")
    public static User getUserWithoutName() {
        return new User("steshov1992@gmail.com", "steshser1234", null);
    }

    @Step("User with changed email")
    public static User getChangedEmailUser() {
        return new User(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now()) + "steshov1992@gmail.com", "steshser1234", "steshser");
    }

    @Step("User with changed password")
    public static User getChangedPasswordUser() {
        return new User("steshov1992@gmail.com", "steshser1234" + DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now()), "steshser");
    }

    @Step("User with changed name")
    public static User getChangedNameUser() {
        return new User("steshov1992@gmail.com", "steshser1234", "steshser" + DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now()));
    }
}
