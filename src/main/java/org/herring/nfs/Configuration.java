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

    public static Configuration getInstance(){
        if(instance == null)
            instance = new Configuration();
        return instance;
    }


    private Configuration(){
        try {
            XMLConfiguration configuration = new XMLConfiguration("config.xml");
            configuration.load();

            root = configuration.getString("root");

        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

}