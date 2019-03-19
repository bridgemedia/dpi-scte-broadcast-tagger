package ru.bridgetv;

/**
 * Single clip event between blocks start & stop
 */
class Clip {
    Integer file_line_number; // Clip start event: line number
    String start_file_line_string; // Clip start event: line string

    String clip_file; // Video file
    String clip_name; // Clip name

    Integer duration_sec; // Unformatted seconds

    /**
     * @param _file_line_number
     * @param _start_file_line_string
     */
    Clip (Integer _file_line_number, String _start_file_line_string) {
        file_line_number = _file_line_number;
        start_file_line_string = _start_file_line_string;

        String [] line_array = start_file_line_string.split(";");
        setDuration_sec( (int) Float.parseFloat( line_array[2] ) );
        clip_file = line_array[0];
        clip_name = line_array[4];

    }

    public Integer getDuration_sec() {
        return duration_sec;

    }

    public void setDuration_sec(Integer duration_sec) {
        this.duration_sec = duration_sec;

    }

}
