package com.monosun.jmailing.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author Jin-Hee Kang
 * filename:MailProperties.java
 * date:2003-01-02
 * time:���� 10:47:23
 * @version 1.0
 */

public class MailProperties extends java.util.Properties {

    /**
     * Creates an empty property list with no default values.
     */
    public MailProperties() {
		super(null);
    }
    /**
     * Creates an empty property list with the specified defaults.
     *
     * @param   defaults   the defaults.
     */
    public MailProperties(Properties defaults) {
	    super(defaults);
    }


    private static final String specialSaveChars = "=: \t\r\n\f#!";

    /*
     * Converts unicodes to encoded &#92;uxxxx
     * and writes out any of the characters in specialSaveChars
     * with a preceding slash
     */
    private String saveConvert(String theString, boolean escapeSpace) {
        int len = theString.length();
        StringBuffer outBuffer = new StringBuffer(len*2);

        for(int x=0; x<len; x++) {
            char aChar = theString.charAt(x);
            switch(aChar) {
		case ' ':
		    if (x == 0 || escapeSpace) 
			outBuffer.append('\\');

		    outBuffer.append(' ');
		    break;
                case '\\':outBuffer.append('\\'); outBuffer.append('\\');
                          break;
                case '\t':outBuffer.append('\\'); outBuffer.append('t');
                          break;
                case '\n':outBuffer.append('\\'); outBuffer.append('n');
                          break;
                case '\r':outBuffer.append('\\'); outBuffer.append('r');
                          break;
                case '\f':outBuffer.append('\\'); outBuffer.append('f');
                          break;
                default:
                    if ((aChar < 0x0020) || (aChar > 0x007e)) {
                        outBuffer.append('\\');
                        outBuffer.append('u');
                        outBuffer.append(toHex((aChar >> 12) & 0xF));
                        outBuffer.append(toHex((aChar >>  8) & 0xF));
                        outBuffer.append(toHex((aChar >>  4) & 0xF));
                        outBuffer.append(toHex( aChar        & 0xF));
                    } else {
                        if (specialSaveChars.indexOf(aChar) != -1)
                            outBuffer.append('\\');
                        outBuffer.append(aChar);
                    }
            }
        }
        return outBuffer.toString();
    }

    /**
     * Writes this property list (key and element pairs) in this
     * <code>Properties</code> table to the output stream in a format suitable
     * for loading into a <code>Properties</code> table using the
     * <code>load</code> method.
     * The stream is written using the ISO 8859-1 character encoding.
     * <p>
     * Properties from the defaults table of this <code>Properties</code>
     * table (if any) are <i>not</i> written out by this method.
     * <p>
     * If the header argument is not null, then an ASCII <code>#</code>
     * character, the header string, and a line separator are first written
     * to the output stream. Thus, the <code>header</code> can serve as an
     * identifying comment.
     * <p>
     * Next, a comment line is always written, consisting of an ASCII
     * <code>#</code> character, the current date and time (as if produced
     * by the <code>toString</code> method of <code>Date</code> for the
     * current time), and a line separator as generated by the Writer.
     * <p>
     * Then every entry in this <code>Properties</code> table is written out,
     * one per line. For each entry the key string is written, then an ASCII
     * <code>=</code>, then the associated element string. Each character of
     * the element string is examined to see whether it should be rendered as
     * an escape sequence. The ASCII characters <code>\</code>, tab, newline,
     * and carriage return are written as <code>\\</code>, <code>\t</code>,
     * <code>\n</code>, and <code>\r</code>, respectively. Characters less
     * than <code>&#92;u0020</code> and characters greater than
     * <code>&#92;u007E</code> are written as <code>&#92;u</code><i>xxxx</i> for
     * the appropriate hexadecimal value <i>xxxx</i>. Leading space characters,
     * but not embedded or trailing space characters, are written with a
     * preceding <code>\</code>. The key and value characters <code>#</code>,
     * <code>!</code>, <code>=</code>, and <code>:</code> are written with a
     * preceding slash to ensure that they are properly loaded.
     * <p>
     * After the entries have been written, the output stream is flushed.  The
     * output stream remains open after this method returns.
     *
     * @param   out      an output stream.
     * @param   header   a description of the property list.
     * @param   keyTitle configure key ex)smtp.
     * @exception  IOException if writing this property list to the specified
     *             output stream throws an <tt>IOException</tt>.
     * @exception  ClassCastException  if this <code>Properties</code> object
     *             contains any keys or values that are not <code>Strings</code>.
     * @exception  NullPointerException  if <code>out</code> is null.
     * @since 1.2
     */
    public synchronized void store(OutputStream out, String header, String keyTitle)
    throws IOException
    {
        BufferedWriter awriter;
        awriter = new BufferedWriter(new OutputStreamWriter(out, "8859_1"));
        if (header != null)
            writeln(awriter, "#" + header);
        writeln(awriter, "#" + new Date().toString());
        
		storeTitle(awriter,keyTitle);

        awriter.flush();
    }

	/**
	 * ���� ����ÿ� �ش�� Ÿ��Ʋ��� ���۵� �͸� �����Ѵ�.
	 */
	private void storeTitle(BufferedWriter awriter, String title)
		throws IOException
	{
		for(Enumeration e = keys();e.hasMoreElements();)
		{
			String key = (String)e.nextElement();
			if(key.startsWith(title))
			{
				String val = (String)get(key);
				key = saveConvert(key, true);
		
		/* No need to escape embedded and trailing spaces for value, hence
		 * pass false to flag.
		 */
		    	val = saveConvert(val, false);
		    	writeln(awriter, key + "=" + val);				
			}
		}
		awriter.newLine();
		
	}
	/**
	 * ���ڿ� ���� new line�Ѵ�.
	 */
    private static void writeln(BufferedWriter bw, String s) throws IOException {
        bw.write(s);
        bw.newLine();
    }

    /**
     * Convert a nibble to a hex character
     * @param	nibble	the nibble to convert.
     */
    private static char toHex(int nibble) {
	return hexDigit[(nibble & 0xF)];
    }

    /** A table of hex digits */
    private static final char[] hexDigit = {
	'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'
    };
}