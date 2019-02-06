package ru.bridgetv;

public interface  ChannelConfig {
    public String channel_key = null;
    public String channel_name = null;

    public String playlist_folder_in = null;
    public String playlist_folder_out = null;
    public String playlist_file_extension = null;
    public String playlist_file_charset = null;

    public String block_start_substring = null;
    public String block_stop_substring = null;
    public String clip_start_substring = null;

    void setChannel_key( String _channel_key );


}
