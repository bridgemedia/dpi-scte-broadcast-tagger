package ru.bridgetv;


/**
 * Sungle TV Channel config
 */
public class ChannelConfigBean implements ChannelConfig {
    public String channel_key;
    public String channel_name;

    public String playlist_folder_in;
    public String playlist_folder_out;
    public String playlist_file_extension;
    public String playlist_file_charset;

    public String block_start_substring;
    public String block_stop_substring;
    public String clip_start_substring;

    @Override
    public void setChannel_key(String _channel_key) {
        channel_key = _channel_key;
    }
}
