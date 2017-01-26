package operations;

import model.UserManager;

public class UserSystemController
{
    private static UserManager userManager = UserManager.getInstance();

    /**
     * "Administration" menu button operations
     */
    public static void logIn(String login, String password)
    {
        userManager.logIn(login, password);
    }

    public static void logOut()
    {
        userManager.logOut();
    }

    public static void registryUser(String login, String password)
    {
        userManager.registryUser(login, password);
    }

    public static void editPassword(String password)
    {
        userManager.editUserPassword(password);
    }

    public static void deleteUser(String login)
    {
        userManager.deleteUser(login);
    }


    /**
     * User status check
     */
    public static boolean isUserExist(String login)
    {
        return userManager.isUserExist(login);
    }

    public static boolean isUserAdmin()
    {
        return userManager.isUserAdmin();
    }

    public static boolean isUserDataCorrect(String login, String password)
    {
        return userManager.isUserDataCorrect(login, password);
    }

    public static boolean isLoggedIn()
    {
        return userManager.isLoggedIn();
    }
}
