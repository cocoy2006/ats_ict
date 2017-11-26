package molab.main.java.util.fileupload;

import org.apache.commons.fileupload.ProgressListener;

public class FileUploadListener implements ProgressListener {
	
	private long num100Ks = 0;
	private long theBytesRead = 0;
	private long theContentLength = -1;
	private int whichItem = 0;
	private double percentDone = 0;
	private boolean contentLengthKnown = false;
	private long time = 0;

	public void update(long bytesRead, long contentLength, int items) {

		if (contentLength > -1) {
			contentLengthKnown = true;
		}
		theBytesRead = bytesRead;
		theContentLength = contentLength;
		whichItem = items;

		long nowNum100Ks = bytesRead / 100000;
		if (nowNum100Ks > num100Ks) {
			num100Ks = nowNum100Ks;
			if (contentLengthKnown) {
				percentDone = Math.floor(100.0 * bytesRead / contentLength);
			}
		}
	}
	
	public long getBytesRead() {
		return theBytesRead;
	}
	
	public long getContentLength() {
		return theContentLength;
	}
	
	public int getItemIndex() {
		return whichItem;
	}

	public double getPercentDone() {
		return percentDone;
	}
	
	/**
	 * @param percentDone the percentDone to set
	 */
	public void setPercentDone(double percentDone) {
		this.percentDone = percentDone;
	}

	/**
	 * @return the time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
}
