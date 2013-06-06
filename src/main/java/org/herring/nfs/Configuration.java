package org.herring.nfs;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * << Description >>
 * User: hyunje
 * Date: 13. 6. 6.
 * Time: 오후 10:51
 */
public class Configuration {
    private static Configuration instance;

    public String root;
    public String fileName;
    public String delimiter;

    private static final class ConfigurationHolder {
        private static final Configuration INSTANCE = new Configuration();
    }

    public static Configuration getInstance(){
        return ConfigurationHolder.INSTANCE;
    }


    private Configuration(){
        try {
            XMLConfiguration configuration = new XMLConfiguration("config.xml");
            configuration.load();

            root = configuration.getString("root");
            fileName = configuration.getString("filename");
            delimiter = configuration.getString("delimiter");

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}