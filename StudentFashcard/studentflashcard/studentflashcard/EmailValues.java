/**
 * 
 */
package studentflashcard;


public class EmailValues {

	/**
	 * @param toBuffer
	 * 
	 */
	StringBuffer buffer = new StringBuffer();
	boolean okStatus = false;

	public EmailValues(StringBuffer buffer, boolean okStatus) {
		this.buffer = buffer;
		this.okStatus = okStatus;
	}

	/**
	 * @return the okStatus
	 */
	public boolean isOkStatus() {
		return okStatus;
	}

	/**
	 * @param okStatus
	 *            the okStatus to set
	 */
	public void setOkStatus(boolean okStatus) {
		this.okStatus = okStatus;
	}

	/**
	 * @return the buffer
	 */
	public StringBuffer getBuffer() {
		return buffer;
	}

	/**
	 * @param buffer
	 *            the buffer to set
	 */
	public void setBuffer(StringBuffer buffer) {
		this.buffer = buffer;
	}

}
