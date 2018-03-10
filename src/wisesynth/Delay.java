package wisesynth;
public class Delay{
	private short[] delayBuffer;
	private int delayBufferPos;
	private float decay;

	public Delay(int numDelaySamples, float decay) {
		delayBuffer = new short[numDelaySamples];
		this.decay = decay;
	}

	public int getRemainingSize() {
		float finalDecay = 0.01f;
		int numRemainingBuffers = (int) Math.ceil(Math.log(finalDecay)
				/ Math.log(decay));
		int bufferSize = delayBuffer.length * 2;
		return bufferSize * numRemainingBuffers;
	}
	public void reset() {
		for (int i = 0; i < delayBuffer.length; i++) {
			delayBuffer[i] = 0;
		}
		delayBufferPos = 0;
	}

	 public static short getSample(byte[] buffer, int position) {
		 return (short)(
		 ((buffer[position+1] & 0xff) << 8) |
		 (buffer[position] & 0xff));
		 }
	 
	 public static void setSample(byte[] buffer, int position,
			 short sample)
			 {
			 buffer[position] = (byte)(sample & 0xff);
			 buffer[position+1] = (byte)((sample >> 8) & 0xff);
			 }

	 public void filter(byte[] samples, int offset, int length) {
		for (int i = offset; i < offset + length; i += 2) {
			// update the sample
			short oldSample = getSample(samples, i);
			short newSample = (short) (oldSample + decay
					* delayBuffer[delayBufferPos]);
			setSample(samples, i, newSample);
			// update the delay buffer
			delayBuffer[delayBufferPos] = newSample;
			delayBufferPos++;
			if (delayBufferPos == delayBuffer.length) {
				delayBufferPos = 0;
			}
		}
	}
}
