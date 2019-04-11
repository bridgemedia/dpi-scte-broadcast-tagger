/**
 * Bridgetv.ru
 * AlexBabak.com
 * 2019
 *
 * Moscow, Russia
 *
*/

package ru.bridgetv;

import java.awt.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static final String STANDARD_CONFIG_FILENAME = "config.ini";

    /**
     * Enter program
     * args[0] is a config file name which is 'config.ini' by default
     *
     * @param args
     */
    public static void main( String[] args ) {
	    // read Config
        Config config = new Config( args.length > 0 ? args[0] : STANDARD_CONFIG_FILENAME );
        String csvpath = config.csvpath;

        ArrayList<ChannelConfigBean> channelConfigs = config.getChannelConfigs();
        ArrayList<TVChannel> tvChannels = new ArrayList<>();

	    // foreach ChannelCongig
        for ( ChannelConfigBean channelConfig : channelConfigs ) {

            // foreach TVChannel
            try {
                TVChannel channel = new TVChannel( channelConfig );
                tvChannels.add( channel );

                System.out.println("");

            } catch (Exception e) {
                e.printStackTrace();

            }

        }


        Report report = new Report( tvChannels );
        report.writeCsv( csvpath );
        Toolkit.getDefaultToolkit().beep();

        if ( args[1].toString() == "await" ) {
            // Wait for user input
            System.out.println("Done. Press <enter> to exit program");
            Scanner keyIn = new Scanner(System.in);
            keyIn.nextLine();


        }

    }

}
