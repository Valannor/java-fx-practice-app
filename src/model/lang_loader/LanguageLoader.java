package model.lang_loader;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Properties;

public class LanguageLoader
{
    private static LanguageLoader langLoaderInstance = new LanguageLoader();

    public static LanguageLoader getInstance()
    {
        return langLoaderInstance;
    }

    private LanguageLoader()
    {

    }

    //Config loader can be moved to special class (if there would be more settings)
    private static File config = new File("CONFIG.properties");
    static
    {
        if (!config.exists())
        {
            try
            {
                Files.createFile(config.toPath());
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static void setConfig(Language language)
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(config));

            properties.put("localization", language.toString());
            properties.store(new FileOutputStream(config), null);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Language checkConfigLang()
    {
        try
        {
            Properties properties = new Properties();
            properties.load(new FileInputStream(config));

            if (properties.isEmpty())
                setConfig(Language.ENGLISH);

            return Language.valueOf((String) properties.get("localization"));
        } catch (IOException e)
        {
            e.printStackTrace();
            return Language.ENGLISH;
        }
    }

    public boolean isEnglish()
    {
        return checkConfigLang() == Language.ENGLISH;
    }


    //Localisation loading
    private static Properties getWindowProps(WindowType windowType)
    {
        // TODO: 27.03.2017 Terrific code. Refactor needed.
        try
        {
            Properties properties = new Properties();

            // TODO: 30.03.2017 Be careful with encoding!!! IntelliJ IDEA creates .properties files in "Windows-1251".
            properties.load(new InputStreamReader(new FileInputStream("src/model/localizations/" + checkConfigLang().toString().toLowerCase()
                            + "/local_" + windowType.toString().toLowerCase() + ".properties"),
                    Charset.forName("Windows-1251")));

            return properties;
        } catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    //Returns element filler
    public static String elementName(WindowType type, String elementType)
    {
        String elementName;

        try
        {
            elementName = getWindowProps(type).getProperty(elementType);
        } catch (NullPointerException e)
        {
            e.printStackTrace();
            elementName = "<-Not specified-> " + checkConfigLang();
        }

        return elementName;
    }
}
