package wisesynth;
import java.io.FilterInputStream;
import java.io.InputStream;
import java.io.IOException;

public class FilteredSoundStream extends FilterInputStream {
	private static final int REMAINING_SIZE_UNKNOWN = -1;
	private Delay delay;
	private int remainingSize;

	public FilteredSoundStream(InputStream in, Delay filter) {
		super(in);
		this.delay = filter;
		remainingSize = REMAINING_SIZE_UNKNOWN;
	}

	public int read(byte[] samples, int offset, int length) throws IOException {
		int bytesRead = super.read(samples, offset, length);
		if (bytesRead > 0) {
			delay.filter(samples, offset, bytesRead);
			return bytesRead;
		}
		if (remainingSize == REMAINING_SIZE_UNKNOWN) {
			remainingSize = delay.getRemainingSize();
			remainingSize = remainingSize / 4 * 4;
		}
		if (remainingSize > 0) {
			length = Math.min(length, remainingSize);
			for (int i = offset; i < offset + length; i++) {
				samples[i] = 0;
			}
			delay.filter(samples, offset, length);
			remainingSize -= length;
			return length;
		} else {
			return -1;
		}
	}
}