package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

public class UserManager
{
    private static UserManager ourInstance = new UserManager();

    public static UserManager getInstance()
    {
        return ourInstance;
    }

    private UserManager()
    {

    }

    private static File usersFile = new File("Users.properties");
    static
    {
        if (!usersFile.exists())
        {
            try
            {
                Files.createFile(usersFile.toPath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


    private static boolean loggedIn = false;
    private String login;
    private String password;

    public void logIn(String login, String password)
    {
        loggedIn = true;
        this.login = login;
        this.password = password;
    }

    public void logOut()
    {
        loggedIn = false;
        login = null;
        password = null;
    }

    public void registryUser(String newUserLogin, String newUserPassword)
    {
        userLoginPasswordUpdate(newUserLogin, newUserPassword);
    }

    public void editUserPassword(String newPassword)
    {
        userLoginPasswordUpdate(login, newPassword);
    }

    private void userLoginPasswordUpdate(String newUserLogin, String newUserPassword)
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(usersFile));

            properties.put(newUserLogin, newUserPassword);
            properties.store(new FileOutputStream(usersFile), null);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void deleteUser(String login)
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(usersFile));

            properties.remove(login);
            properties.store(new FileOutputStream(usersFile), null);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public boolean isUserAdmin()
    {
        return "admin".equals(login);
    }

    public boolean isUserExist(String login)
    {
        Properties properties = getUsers();

        return properties != null && properties.containsKey(login);
    }

    public boolean isUserDataCorrect(String login, String password)
    {
        Properties properties = getUsers();
        boolean exist = isUserExist(login);

        if (login.equals("admin") && !exist)
        {
            registryUser("admin", "admin");
            exist = true;
            properties = getUsers();
        }

        return exist && password.equals(properties.getProperty(login));
    }

    //Обслуживающий метод
    private Properties getUsers()
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(usersFile));

            return properties;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }
}
