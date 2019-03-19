package ru.bridgetv;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * TV Channel class
 * contains config, playlists, broadcasting blocks and clips
 */
public class TVChannel {

    public ChannelConfigBean channelConfig;
    static ArrayList<String> pathesToProcess;
    ArrayList<Playlist> Playlists = new ArrayList<Playlist>();

    /**
     * @param _channelConfig
     * @throws Exception
     */
    TVChannel( ChannelConfigBean _channelConfig ) throws Exception {
        channelConfig = _channelConfig;

        //Playlist folder in
        assert channelConfig.playlist_folder_in != null;
        File playlist_folder_in = new File( channelConfig.playlist_folder_in );
        if ( !playlist_folder_in.isDirectory() ) { throw new Exception( "No such directory: " + playlist_folder_in ); }
        System.out.println( "playlist_folder_in dir: " + channelConfig.playlist_folder_in );

        //Playlist folder out
        File playlist_folder_out = new File( channelConfig.playlist_folder_out );
        if ( !playlist_folder_out.isDirectory() ) {
            if ( !playlist_folder_out.exists()) {
                playlist_folder_out.mkdirs();
                System.out.println( "No such directory: " + playlist_folder_out + " (created)" );

            }

        }
        System.out.println( "playlist_folder_out dir: " + channelConfig.playlist_folder_out );

        //Scan playlist input folder
        assert channelConfig.playlist_file_extension != null;
        Stream<Path> paths = Files.walk( Paths.get( channelConfig.playlist_folder_in ));

        this.pathesToProcess = new ArrayList<String>();

        paths
            .filter( Files::isRegularFile )
            .filter( p -> p.toString().endsWith( "." + channelConfig.playlist_file_extension ) )
            .forEach( item->pathesToProcess.add( item.toString() ) );

        pathesToProcess.forEach( (path)->{
            Playlist playlist = new Playlist( path, channelConfig );
            this.Playlists.add( playlist );


        });

    }

//    public static ArrayList<String> getPathesToProcess() {
//        return pathesToProcess;
//    }

}
