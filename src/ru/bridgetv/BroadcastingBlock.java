package ru.bridgetv;

import java.util.ArrayList;

/**
 * Single playlist block with start and stop events
 */
class BroadcastingBlock {
    Integer uid; // Unique daytime id

    ArrayList<Clip> clips = new ArrayList<>(); // Block clips collection
    Integer duration_sec = 0; // Total block duration
    String duration_formatted; // Total block duration

    Integer start_file_line_number; // Advertising Block start event: line number
    String start_file_line_string; // Advertising Block start event: line string
    String start_file_line_string__new; // Advertising Block start event: line string (updated)

    Integer stop_file_line_number; // Advertising Block stop event: line number
    String stop_file_line_string; // Advertising Block stop event: line string
    String stop_file_line_string__new; // Advertising Block stop event: line string (updated)

    public BroadcastingBlock(Integer _start_file_line_number, String _start_file_line_string)  {
        uid = _start_file_line_number;
        start_file_line_number = _start_file_line_number;
        start_file_line_string = _start_file_line_string;

    }

    Integer addClip(Clip clip) {
        clips.add( clip );
        return clips.size();

    }

    void close(Integer _stop_file_line_number, String _start_file_line_string) {
        stop_file_line_number = _stop_file_line_number;
        stop_file_line_string = _start_file_line_string;

        // count total clips duration
        clips.forEach( (clip) -> {
            duration_sec += clip.getDuration_sec();
        });
        duration_formatted = splitToComponentTimes(duration_sec)+":000";

        System.out.println( "" );
        System.out.println( "So, Block duration: " + duration_sec + " ( "+duration_formatted+" ), updating playlist lines:" );

        // update lines with new uid and duration,
        // like Start:  #EXTEVENT /ModName DPI Plugin;DPI Plugin :: 'Splice Start Normal', Duration 00:00:15:000;0;1-00:00:15:000-1-0-0;0.00000;0
        // like Stop:  #EXTEVENT /ModName DPI Plugin;DPI Plugin :: 'Splice End Normal';0;3-00:00:00:000-1-0-0;0.00000;0

        String[] start_file_line_string_array = start_file_line_string.split(";");
//        System.out.println( "0: " + start_file_line_string_array[0] ); // ModName DPI Plugin
//        System.out.println( "1: " + start_file_line_string_array[1] ); // DPI Plugin :: 'Splice Start Normal', Duration 00:00:15:000
//        System.out.println( "2: " + start_file_line_string_array[2] ); // 0
//        System.out.println( "3: " + start_file_line_string_array[3] ); // 1-00:00:15:000-1-0-0
//        System.out.println( "4: " + start_file_line_string_array[4] ); // 0.00000
//        System.out.println( "5: " + start_file_line_string_array[5] ); // 0
//        System.out.println( "****" );

        start_file_line_string_array[1] = start_file_line_string_array[1].replaceAll("Duration 00:00:15:000", "Duration " + duration_formatted );

        String[] scte = start_file_line_string_array[3].split( "-" ); // 1-00:00:15:000-1-0-0
//        System.out.println( "0: " + scte[0] ); // 1
//        System.out.println( "1: " + scte[1] ); // 00:00:15:000
//        System.out.println( "2: " + scte[2] ); // 1
//        System.out.println( "3: " + scte[3] ); // 0
//        System.out.println( "4: " + scte[4] ); // 0
//        System.out.println( "***" );

        scte[1] = duration_formatted;
        scte[2] = start_file_line_number.toString();

        start_file_line_string_array[3] = String.join( "-", scte );
        start_file_line_string__new = String.join( ";", start_file_line_string_array );

        System.out.println( start_file_line_string );
        System.out.println( start_file_line_string__new );

        String[] stop_file_line_string_array =  stop_file_line_string.split( ";" );
//        System.out.println( "0: " + stop_file_line_string_array[0] ); // #EXTEVENT /ModName DPI Plugin
//        System.out.println( "1: " + stop_file_line_string_array[1] ); // DPI Plugin :: 'Splice End Normal'
//        System.out.println( "2: " + stop_file_line_string_array[2] ); // 0
//        System.out.println( "3: " + stop_file_line_string_array[3] ); // 3-00:00:00:000-1-0-0
//        System.out.println( "4: " + stop_file_line_string_array[4] ); // 0.00000
//        System.out.println( "5: " + stop_file_line_string_array[5] ); // 0
//        System.out.println( "* * *" );

        scte = stop_file_line_string_array[3].split( "-" );
//        System.out.println( "0: " + scte[0] ); // 1
//        System.out.println( "1: " + scte[1] ); // 00:00:15:000
//        System.out.println( "2: " + scte[2] ); // 1
//        System.out.println( "3: " + scte[3] ); // 0
//        System.out.println( "4: " + scte[4] ); // 0
//        System.out.println( "* * *" );

        scte[2] = start_file_line_number.toString();
        stop_file_line_string_array[3] =  String.join( "-", scte );
        stop_file_line_string__new = String.join( ";", stop_file_line_string_array );

        System.out.println( stop_file_line_string );
        System.out.println( stop_file_line_string__new );

    }

    public static String splitToComponentTimes( Integer _duration_sec ) {
        long longVal = _duration_sec.longValue();
        int hours = (int) longVal / 3600;
        int remainder = (int) longVal - hours * 3600;
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        return  String.format("%02d" , hours)
                + ":"
                + String.format("%02d" , mins)
                + ":"
                + String.format("%02d" , secs);

    }


//    public static int[] splitToComponentTimes( BigDecimal biggy ) {
//        long longVal = biggy.longValue();
//        int hours = (int) longVal / 3600;
//        int remainder = (int) longVal - hours * 3600;
//        int mins = remainder / 60;
//        remainder = remainder - mins * 60;
//        int secs = remainder;
//
//        int[] ints = {hours , mins , secs};
//        return ints;
//    }

    public ArrayList<Clip> getClips() {
        return clips;

    }


}
