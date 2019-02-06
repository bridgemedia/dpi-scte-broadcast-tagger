package ru.bridgetv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Report builder
 */
public class Report {

    ArrayList<String> result = new ArrayList<>();


    Report(ArrayList<TVChannel> TVChannels) {

        TVChannels.forEach( (TVChannel) -> {
            String TVChannel_prefix = TVChannel.channelConfig.channel_key;

            TVChannel.Playlists.forEach( (Playlist)->{
                String Playlists_prefix = Playlist.date + "|" + Playlist.path;

                Playlist.broadcastingBlocks.forEach( (Block)->{
                    String Block_prefix = Block.start_file_line_number + "|" + Block.duration_sec + "|" + Block.duration_formatted;

                    Block.clips.forEach( (clip)->{
                        String Clip_string =  clip.file_line_number + "|" + clip.clip_file + "|" + clip.duration_sec + "|" + clip.clip_name;
                        result.add( TVChannel_prefix + "|" + Playlists_prefix  + "|" + Block_prefix  + "|" + Clip_string );

                    });

                });

            });

        });

    }

    void writeCsv( String csvPath ) {

//        if ( csvPath == null ) {
//            System.out.println( "NuN" );
//        }

        System.out.println("---Report:---");

        StringBuilder sb = new StringBuilder();
        sb.append( "Channel|Playlist.date|Playlist.path|Block.flieLine|Block.duration(sec)|Block.duration(formatted)|Clip.file|Clip.eventLine|Clip.duration_sec|Clip.clip_name" ).append("\r\n");

        result.forEach( (string)->{
//            System.out.println( string );
            sb.append( string ).append("\r\n");
        });

//        File file = new File( csvPath );
        File file = new File( "report.csv" );
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write( sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
