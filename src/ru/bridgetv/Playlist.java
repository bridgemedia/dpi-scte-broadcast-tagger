package ru.bridgetv;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Single playlist file from channelConfig input folder
 */
class Playlist {
    public String path;
    public String filename;
    public String date;
    public ChannelConfigBean channelConfig;
    public ArrayList<BroadcastingBlock> broadcastingBlocks = new ArrayList<>();

    /**
     * Create new playlist
     * @param _path
     * @param _channelConfig
     */
    Playlist(String _path, ChannelConfigBean _channelConfig) {
        path = _path;
        channelConfig = _channelConfig;

        File file = new File( path );
        filename = file.getName(); //2019_01_28_00_00_00.ply
        String[] filename_array = filename.split("_");
        date = filename_array[0] + "-" + filename_array[1] + "-" + filename_array[2];

        InputStream ins = null; // raw byte-stream
        Reader r = null; // cooked reader
        BufferedReader br = null; // buffered for readLine()

        System.out.println( "--" );
        System.out.println( path );

        try {
            Integer lineCounter=0;
            Integer BlockUid=0;
            String line;
            BroadcastingBlock broadcastingBlock = null;

            ins = new FileInputStream( path );
//            r = new InputStreamReader( ins, channelConfig.playlist_file_charset ); // leave charset out for default
            r = new InputStreamReader( ins, "windows-1251" ); // leave charset out for default
            br = new BufferedReader( r );

            while ((line = br.readLine()) != null) {
                lineCounter++;

                // Новый рекламный блок
                if ( line.toLowerCase().contains( channelConfig.block_start_substring.toLowerCase() ) ) {
                    System.out.println( "--" );
                    System.out.println( "Start ["+lineCounter+"]: " + line );
                    broadcastingBlock = new BroadcastingBlock( lineCounter, line );
                }

                // добавялем клип в рекламный блок
                if ( line.toLowerCase().contains( channelConfig.clip_start_substring.toLowerCase()) ) {
                    System.out.println( "Clip ["+lineCounter+"]: " + line );
                    broadcastingBlock.addClip( new Clip( lineCounter, line ) );
                }

                // закрываем рекламный блок
                if ( line.toLowerCase().contains( channelConfig.block_stop_substring.toLowerCase()) ) {
                    System.out.println( "Stop ["+lineCounter+"]: " + line );
                    broadcastingBlock.close( lineCounter, line );
                    broadcastingBlocks.add(broadcastingBlock);

                }

            }

        } catch (Exception e) {
            System.err.println(e.getMessage()); // handle exception

        } finally {
            if (br != null) { try { br.close(); } catch(Throwable t) { /* ensure close happens */ } }
            if (r != null) { try { r.close(); } catch(Throwable t) { /* ensure close happens */ } }
            if (ins != null) { try { ins.close(); } catch(Throwable t) { /* ensure close happens */ } }

        }

        updateBlockLines();

        //move file to processed folder (and create if not exists)
        File playlist_folder_processed = new File( channelConfig.playlist_folder_processed );
        if (! playlist_folder_processed.exists()){
            playlist_folder_processed.mkdir();
        }

        file.renameTo( new File( channelConfig.playlist_folder_processed + File.separator + filename ) );

    }

    /**
     * Update processed block
     */
    void updateBlockLines() {
        // copy file to the new destination
        Path source = FileSystems.getDefault().getPath( path );
        Path newdir = FileSystems.getDefault().getPath( channelConfig.playlist_folder_out );
        Path target = newdir.resolve( source.getFileName() );

        List<String> newLines;
        try {
            newLines = Files.readAllLines( source, Charset.forName("ISO-8859-1") );

            broadcastingBlocks.forEach( (block)->{
                newLines.set( (block.start_file_line_number-1), block.start_file_line_string__new );
                newLines.set( (block.stop_file_line_number-1), block.stop_file_line_string__new );

            });

            Files.write( target, newLines, Charset.forName("ISO-8859-1"), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException e) {
            System.out.println("Can't read file: ");
            e.printStackTrace();

        }

    }

}
