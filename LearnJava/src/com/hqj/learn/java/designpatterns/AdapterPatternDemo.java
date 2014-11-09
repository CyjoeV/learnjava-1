/**
 * 2014 Nov 8, 2014
 */
package com.hqj.learn.java.designpatterns;

/**
 * @author hqj
 * Version : 1.0
 * Description : 属于结构模式Structural Pattern。该类使用于演示适配器设计模式。
 * 该模式用于融合两个不相容的接口，从而使用两个接口提供的服务。
 * 举例来说：读卡器就连接笔记本与SD卡的一个适配器。
 */
public class AdapterPatternDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AudioPlayer audioPlayer = new AudioPlayer();
		audioPlayer.play("MP3", "You raise me up.mp3");
		audioPlayer.play("vlc", "Django.vlc");
		audioPlayer.play("Mp4", "Goodbye Earl.mp4");
		audioPlayer.play("avi", "Dont stop the party.avi");
	}

}

interface MediaPlayer{
	public void play(String auidoType,String fileName);
}
//提供mp4,vlc的播放支持
interface AdvancedMediaPlayer{
	public void playMp4(String fileName);
	public void playVlc(String fileName);
}

class Mp4Player implements AdvancedMediaPlayer{
	public void playMp4(String fileName){
		System.out.println("Play MP4 file : "+fileName);
	}
	
	public void playVlc(String fileName){
		//do nothing
	}
}

class VlcPlayer implements AdvancedMediaPlayer{
	public void playVlc(String fileName){
		System.out.println("Play VLC file : "+fileName);
	}
	public void playMp4(String fileName){
		//do nothing
	}
}
//MediaAdapter提供mp4,vlc的整合功能。
class MediaAdapter implements MediaPlayer{
	AdvancedMediaPlayer advancedMediaPlayer;
	MediaAdapter(String mediaType){
		if("vlc".equalsIgnoreCase(mediaType)){
			advancedMediaPlayer = new VlcPlayer();
		}else if("mp4".equalsIgnoreCase(mediaType)){
			advancedMediaPlayer = new Mp4Player();
		}
	}
	
	public void play(String mediaType, String fileName){
		if("vlc".equalsIgnoreCase(mediaType)){
			advancedMediaPlayer.playVlc(fileName);
		}else if("mp4".equalsIgnoreCase(mediaType)){
			advancedMediaPlayer.playMp4(fileName);
		}
	}
}

class AudioPlayer implements MediaPlayer{
	public void play(String mediaType,String fileName){
		if("mp3".equalsIgnoreCase(mediaType)){
			System.out.println("Play MP3 file : "+fileName);
		 //MediaAdapter is providing support for other media types.	
		}else if("mp4".equalsIgnoreCase(mediaType)
				|| "vlc".equalsIgnoreCase(mediaType)){
			MediaAdapter mediaAdapter = new MediaAdapter(mediaType);
			mediaAdapter.play(mediaType, fileName);
		}else{
			System.out.println("Can not play media type : "+mediaType);
		}
	}
}