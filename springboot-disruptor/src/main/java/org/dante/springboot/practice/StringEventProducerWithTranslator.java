package org.dante.springboot.practice;

import com.lmax.disruptor.EventTranslatorOneArg;
import com.lmax.disruptor.RingBuffer;

public class StringEventProducerWithTranslator {
	
	// 存储数据的环形队列
    private final RingBuffer<StringEvent> ringBuffer;
    
    public StringEventProducerWithTranslator(RingBuffer<StringEvent> ringBuffer) {
    	this.ringBuffer = ringBuffer;
    }
    
   /**
    * 内部类
    */
   private static final EventTranslatorOneArg<StringEvent, String> TRANSLATOR = new EventTranslatorOneArg<StringEvent, String>() {
       @Override
       public void translateTo(StringEvent event, long sequence, String arg0) {
           event.setValue(arg0);
       }
   };
    
    public void onData(String content) {
//        ringBuffer.publishEvent((StringEvent event, long sequence, String arg0) -> event.setValue(arg0), content);
    	ringBuffer.publishEvent(TRANSLATOR, content);
    }
}
