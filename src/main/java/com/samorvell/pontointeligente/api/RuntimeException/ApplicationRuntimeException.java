package com.samorvell.pontointeligente.api.RuntimeException;

import java.text.MessageFormat;

/**
 * Class created to handle application runtime exceptions
 *
 * @author Samuel A. Silva
 * @see RuntimeException
 */
public class ApplicationRuntimeException extends RuntimeException {

 



	/**
	 * Contructor
	 */
    public ApplicationRuntimeException() {
        super();
    }

    /**
     * Creates an exception informing the key of the error message, 
     * its cause, and message parameters
     *
     * @param message
     * @param cause
     * @param params
     */
    public ApplicationRuntimeException(String message, Throwable cause, Object... params) {
        super(MessageFormat.format(message, params), cause);
    }

    /**
     * Creates an exception by entering the error message key and the message parameters
     *
     * @param message
     * @param params
     */
    public ApplicationRuntimeException(String message, Object... params) {
        super(MessageFormat.format(message, params));
    }

    /**
     * Creates an exception stating only its cause
     *
     * @param cause 
     */
    public ApplicationRuntimeException(Throwable cause) {
        super(cause);
    }
}
