Audio Transcoder-incubating
============


Engine uses [Xuggler](http://www.xuggle.com/xuggler/) libraries to transcode audio files. Xuggler under [GPL Version 3 license](https://www.gnu.org/copyleft/gpl.html). This is required given that the pre-compiled version distributes X264 for H264 video encoding. 

##### Usage
This engine is converts audio (mp3,aac, wav, e.t.c.) to 

	Frequency: 16 kHz 
    Depth: 16 bit
    Type: mono
    little-endian byte order

The Audio Stream parsed through ContentItem is converted to above format and parsed as Stream to [SpeechToText Enhancement Engine](https://github.com/sumansaurabh/SpeechToTextEngine). 

##### Note
This project is under development, you might face lot of issues.



