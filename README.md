# dpi-scte-broadcast-tagger
Программа для подсчёта и разметки эфирных блоков в метках прямого эфира ([SCTE-35/104](https://en.wikipedia.org/wiki/SCTE-35)) в плейлистах оборудования для вещания. Например, Airbox (DPI inserter module) Broadcast. Например, для разметки рекламных блоков.
 
Кроссплатформенный (Windows, Mac, Linux) JAR и файл EXE для Windows доступны в папке [dpi_scte_jar](https://github.com/bridgemedia/dpi-scte-broadcast-tagger/tree/master/dpi_scte_jar)
 
Поддержка нескольких телеканалов. Поддержка сетевых путей.
 
Демонстрация работы:
 
[![Видос](https://img.youtube.com/vi/ROd0PoMJpEo/0.jpg)](https://www.youtube.com/watch?v=ROd0PoMJpEo)
 
Пример событий плейлиста вещания эфира с рекламными метками SCTE-35/104, содержащих продолжительность и уникальный идентификатор рекламного блока:

```
#EXTEVENT /ModName DPI Plugin;DPI Plugin :: 'Splice Start Normal', Duration 00:1:15:000;0;1-00:1:15:000-143-0-0;0.00000;0
#LISTID 2064845452452
#DYNAMICMEDIA FALSE
#TYPE MPEG2 (48kHz)
#TC 0.00000
"V:\VIDEO_bridge tv\Commercial\806203.mpg";0.00000;25.00000;;McDonalds
#LISTID 2064845535211
#DYNAMICMEDIA FALSE
#TYPE MPEG2 (48kHz)
#TC 0.00000
"V:\VIDEO_bridge tv\Commercial\845689.mpg";0.00000;30.00000;;Intel
#LISTID 2064845618365
#DYNAMICMEDIA FALSE
#TYPE MPEG2 (48kHz)
#TC 0.00000
"V:\VIDEO_bridge tv\Commercial\812278.mpg";0.00000;20.00000;;Blistanie.com
#LISTID 2064845618551
#DYNAMICMEDIA FALSE
#EXTEVENT /ModName DPI Plugin;DPI Plugin :: 'Splice End Normal';0;3-00:00:00:000-143-0-0;0.00000;0
```

На основе обработанных данных генерируется отчёт в формате CSV.
Может быть использован в качестве источника данных для эфирных справок.
 
Для настройки используйте:

 | Параметр | Назначение | 
 | --- | --- | 
 | playlist_folder_in | Путь к папкае с плейлистами на вход | 
 | playlist_folder_out | Путь к папке с плейлистами на выход | 
 | playlist_folder_processed | Путь к папке с обработанными плейлистами | 
 | playlist_file_extension | Расширение файлов плейлистов | 
 | playlist_file_charset | Кодировка | 
 | block_start_substring | Подстрока для определения начала рекламного блока | 
 | block_stop_substring | Подстрока для определения окончания рекламного блока | 
 | clip_start_substring | Подстрока для определения запуска рекламного ролика | 

в файле [config.ini](https://github.com/bridgemedia/dpi-scte-broadcast-tagger/blob/master/config.ini)

Параметры командной строки:  
args[0] -- путь к конфигурационному .ini-файлу  
args[1] -- "nowait" для завершения программы без ожидания  

