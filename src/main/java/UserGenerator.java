import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserGenerator {

    public static User getNewValidUser() {
        return new User("steshov1992@gmail.com", "steshser1234", "steshser");
    }
    public static User getUserWithoutEmail() {
        return new User(null, "steshser1234", "steshser");
    }
    public static User getUserWithoutPassword() {
        return new User("steshov1992@gmail.com", null, "steshser");
    }
    public static User getUserWithoutName() {
        return new User("steshov1992@gmail.com", "steshser1234", null);
    }

    public static User getChangedEmailUser() {
        return new User(DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now())+"steshov1992@gmail.com", "steshser1234", "steshser");
    }

    public static User getChangedPasswordUser() {
        return new User("steshov1992@gmail.com", "steshser1234"+ DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now()), "steshser");
    }

    public static User getChangedNameUser() {
        return new User("steshov1992@gmail.com", "steshser1234", "steshser"+DateTimeFormatter.ofPattern("yyyy/MM/dd-HH:mm:ss").format(LocalDateTime.now()));
    }


}
