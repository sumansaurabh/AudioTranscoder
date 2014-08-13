/*******************************************************************************
 * Copyright (c)  .
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *      - initial API and implementation
 ******************************************************************************/
package org.apache.stanbol.enhancer.engines.xuggletranscoder;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.AudioSamplesEvent;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.mediatool.event.IAudioSamplesEvent;
import com.xuggle.xuggler.IAudioResampler;
import com.xuggle.xuggler.IAudioSamples;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.io.XugglerIO;

public class ConvertAudio extends MediaToolAdapter implements Runnable {
	
	 private IMediaWriter writer;
	 private IMediaReader reader;
	 private File outputFile;
	 private OutputStream output;
	 
	 public ConvertAudio(InputStream input) {
		 //this.outputFile = outputFile;
		 IContainer container = IContainer.make();

		 if (container.open(XugglerIO.map(input),IContainer.Type.READ, null) < 0)
			 throw new IllegalArgumentException("Could not Stream the Content " );
		 reader = ToolFactory.makeReader(container);
		 reader.addListener(this);
	}
	 
	public OutputStream getOutputStream()	{
		return this.output;
	}
	 
	private IAudioResampler audioResampler = null;
	@Override
	 public void onAddStream(IAddStreamEvent event) {
		 int streamIndex = event.getStreamIndex();
		 IStreamCoder streamCoder = event.getSource().getContainer()
		 		.getStream(streamIndex).getStreamCoder();
		 if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
		 	//writer.addAudioStream(streamIndex, streamIndex, 1, 16000);
	    		writer.addAudioStream(1, 0,ICodec.ID.CODEC_ID_PCM_S16LE, 1, 16000); 
		 }
		 super.onAddStream(event);
	}

	@Override
	public void onAudioSamples(IAudioSamplesEvent event) {
		IAudioSamples samples = event.getAudioSamples();
		if (audioResampler == null) {
			audioResampler = IAudioResampler.make(1, samples.getChannels(),
					16000, samples.getSampleRate());
		 	}
		if (event.getAudioSamples().getNumSamples() > 0) {
			IAudioSamples out = IAudioSamples.make(samples.getNumSamples(),
					samples.getChannels());
			audioResampler.resample(out, samples, samples.getNumSamples());
			AudioSamplesEvent asc = new AudioSamplesEvent(event.getSource(),
					out, event.getStreamIndex());
			super.onAudioSamples(asc);
			out.delete();
		 }
		 }

	@Override
	public void run() {
		outputFile=new File("/tmp/res.wav");
		writer = ToolFactory.makeWriter(outputFile.getAbsolutePath(), reader);
		this.addListener(writer);
		while (reader.readPacket() == null) {
		}
	}
}
