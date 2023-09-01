package org.dante.springboot.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.ffmpeg.global.avutil;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.FrameRecorder;
import org.bytedeco.javacv.Java2DFrameConverter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OpenCVUtils {

	public static void Image2Mp4(String imgDir, String videoPath) throws Exception {
        int width = 1600;
        int height = 900;
        //读取所有图片
        File file = new File(imgDir);
        File[] files = file.listFiles();
        Map<Integer, File> imgMap = new HashMap<>();
        int num = 0;
        for (File imgFile : files) {
            imgMap.put(num, imgFile);
            num++;
        }
        createMp4(videoPath, imgMap, width, height);

	}
	
	private static void createMp4(String mp4SavePath, Map<Integer, File> imgMap, int width, int height) throws FrameRecorder.Exception {
        //视频宽高最好是按照常见的视频的宽高  16：9  或者 9：16
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(mp4SavePath, width, height);
        //设置视频编码层模式
        recorder.setVideoCodec(avcodec.AV_CODEC_ID_H264);
        //设置视频为25帧每秒
        recorder.setFrameRate(25);
        //设置视频图像数据格式
        recorder.setPixelFormat(avutil.AV_PIX_FMT_YUV420P);
        recorder.setFormat("mp4");
        Java2DFrameConverter converter = new Java2DFrameConverter();
        try {
            recorder.start();
            //录制一个22秒的视频
            for (int i = 0; i < imgMap.size(); i++) {
                BufferedImage read = ImageIO.read(imgMap.get(i));
                //一秒是25帧 所以要记录25次
                for (int j = 0; j < 25; j++) {
                    recorder.record(converter.getFrame(read));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //最后一定要结束并释放资源
            recorder.stop();
            recorder.release();
            recorder.close();
            converter.close();
        }
    }
	
	/**
	 * 视频融合音频
	 * 
	 * @param videoPath
	 * @param audioPath
	 * @param outPut
	 * @return
	 * @throws Exception
	 */
	public static boolean mergeAudioAndVideo(String videoPath, String audioPath, String outPut) throws Exception {
        boolean isCreated = true;
        File file = new File(videoPath);
        if (!file.exists()) {
            return false;
        }
        FrameRecorder recorder = null;
        FrameGrabber grabber1 = null;
        FrameGrabber grabber2 = null;
        try {
            //抓取视频帧
            grabber1 = new FFmpegFrameGrabber(videoPath);
            //抓取音频帧
            grabber2 = new FFmpegFrameGrabber(audioPath);
            grabber1.start();
            grabber2.start();
            //创建录制
            recorder = new FFmpegFrameRecorder(outPut,
                    grabber1.getImageWidth(), grabber1.getImageHeight(),
                    grabber2.getAudioChannels());

            recorder.setFormat("mp4");
            recorder.setFrameRate(grabber1.getFrameRate());
            recorder.setSampleRate(grabber2.getSampleRate());
            recorder.start();

            Frame frame1;
            Frame frame2 ;
            //先录入视频
            while ((frame1 = grabber1.grabFrame()) != null ){
                recorder.record(frame1);
            }
            //然后录入音频
            while ((frame2 = grabber2.grabFrame()) != null) {
                recorder.record(frame2);
            }
            grabber1.stop();
            grabber2.stop();
            recorder.stop();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (recorder != null) {
                    recorder.release();
                }
                if (grabber1 != null) {
                    grabber1.release();
                }
                if (grabber2 != null) {
                    grabber2.release();
                }
            } catch (FrameRecorder.Exception e) {
                e.printStackTrace();
            }
        }
        return isCreated;

    }

	public static void main(String[] args) throws Exception {
		String DIR = "/Users/dante/Documents/Project/java-world/springboot/springboot-opencv/sample/";
		//合成的MP4
        String mp4SavePath = DIR + "img.mp4";
        //图片地址
        String img = DIR + "img";
		Image2Mp4(img, mp4SavePath);
		// BGM
		String audioPath = DIR + "bg.mp3";
		// BGM MP4
		String outPut = DIR + "img_bg.mp4";
		mergeAudioAndVideo(mp4SavePath, audioPath, outPut);
	}
}
