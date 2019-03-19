package ru.bridgetv;

import org.ini4j.Ini;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Config class
 * contains program options,
 * such as playlist files input / output folders, extensions and charsets,
 * events identification substrings
 */
public class Config {

    ArrayList<ChannelConfigBean> channelConfigs = new ArrayList<>();
    public String csvpath;

    /**
     * @param filename
     */
    Config( String filename ) {

        System.out.println( "Config file: " + filename );

        try {

            Ini ini = new Ini(new FileReader(filename));
            csvpath = ini.get( "defaults" ).fetch( "csvpath" );

            String default_playlist_file_extension = ini.get( "defaults" ).fetch( "playlist_file_extension" );
            String default_playlist_file_charset = ini.get( "defaults" ).fetch( "playlist_file_charset" );
            String default_block_start_substring = ini.get( "defaults" ).fetch( "block_start_substring" );
            String default_block_stop_substring = ini.get( "defaults" ).fetch( "advert_stop_substring" );
            String default_clip_start_substring = ini.get( "defaults" ).fetch( "clip_start_substring" );

            assert null != default_playlist_file_extension;
            assert null != default_playlist_file_charset;
            assert null != default_block_start_substring;
            assert null != default_block_stop_substring;
            assert null != default_clip_start_substring;

            for ( String channel_key : ini.get("channels").keySet()) {

                ChannelConfigBean channelConfig = new ChannelConfigBean();
                channelConfig.channel_key = channel_key;

                channelConfig.playlist_file_extension = ini.get( channel_key ).fetch( "playlist_file_extension", default_playlist_file_extension );
                channelConfig.playlist_file_charset = ini.get( channel_key ).fetch( "playlist_file_charset", default_playlist_file_charset );
                channelConfig.block_start_substring = ini.get( channel_key ).fetch( "block_start_substring", default_block_start_substring );
                channelConfig.block_stop_substring = ini.get( channel_key ).fetch( "block_stop_substring", default_block_stop_substring );
                channelConfig.clip_start_substring = ini.get( channel_key ).fetch( "clip_start_substring", default_clip_start_substring );

                channelConfig.playlist_folder_in = ini.get( channel_key ).fetch( "playlist_folder_in" );
                channelConfig.playlist_folder_out = ini.get( channel_key ).fetch( "playlist_folder_out" );
                channelConfig.playlist_folder_processed = ini.get( channel_key ).fetch( "playlist_folder_processed" );

                channelConfigs.add( channelConfig );

            }

        } catch ( IOException e ) {
            System.err.println( "-- File " + filename + " not found :(" );
            System.err.println( " " );
            e.printStackTrace();

        }

    }

    public ArrayList<ChannelConfigBean> getChannelConfigs() {
        return channelConfigs;

    }

}
