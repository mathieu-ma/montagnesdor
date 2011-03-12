package fr.mch.mdo.security.util;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.util.Enumeration;

/**
 * This class can be used to import a key/certificate pair from a pkcs12 file
 * into a regular JKS format keystore for use with jetty and other java based
 * SSL applications, etc.
 * 
 * <PRE>
 *    usage: java PKCS12Import {pkcs12file} [newjksfile]
 * </PRE>
 * 
 * If you don't supply newjksfile, newstore.jks will be used. This can be an
 * existing JKS keystore.
 * <P>
 * Upon execution, you will be prompted for the password for the pkcs12 keystore
 * as well as the password for the jdk file. After execution you should have a
 * JKS keystore file that contains the private key and certificate that were in
 * the pkcs12
 * <P>
 * You can generate a pkcs12 file from PEM encoded certificate and key files
 * using the following openssl command:
 * 
 * <PRE>
 *    openssl pkcs12 -export -out keystore.pkcs12 -in www.crt -inkey www.key
 * </PRE>
 * 
 * then run:
 * 
 * <PRE>
 *    java PKCS12Import keystore.pkcs12 keytore.jks
 * </PRE>
 * 
 * @author Jason Gilbert &lt;jason@doozer.com&gt;
 */
public class PKCS12Import {
    
    private static int exitCount = 0;
 
    public static final String PKCS12_KEY_STORE_INSTANCE_TYPE = "pkcs12";
    public static final String JKS_KEY_STORE_INSTANCE_TYPE = "jks";
    public static final String KEY_STORE_TYPE_IN_EXCEPTION_MESSAGE = "Could not retrieve PKCS12 key store instance";
    public static final String KEY_STORE_TYPE_OUT_EXCEPTION_MESSAGE = "Could not retrieve JKS key store instance";
    public static final String ILLEGAL_FILE_IN_NOT_FOUND_EXCEPTION_MESSAGE = "Could not found the input file";
    public static final String ILLEGAL_KEY_STORE_LOAD_FILE_IN = "Could not load key store input file";
    public static final String ILLEGAL_KEY_STORE_LOAD_FILE_OUT = "Could not load key store output file";
    
    public static final String ILLEGAL_NUMBER_ARGUMENT_EXCEPTION_MESSAGE = "Number of arguments is between 1 and 3:\n Usage: java PKCS12Import {pkcs12file} [newjksfile] [passfile]";
    public static final String ILLEGAL_FILE_READ_EXCEPTION_MESSAGE = "File can not be read";
    public static final String ILLEGAL_FILE_WRITE_EXCEPTION_MESSAGE = "File can not be written";
    
    public static final String NO_SUCH_FIELD_EXCEPTION_MESSAGE = "No Such field";
    
    public static final String DEFAULT_OUTPUT_JSK_FILE = "newstore.jks";
    
    /**
     * This method checks parameters and returns an arrays of files
     * 
     * @param args
     *            parameters to be checked
     * @return arrays of files
     */
    private static File[] processParameters(String... args) {
	File fileIn = null;
	File fileOut = null;
	File filePass = null;

	switch (args.length) {
	case 3:
	    filePass = new File(args[2]);
	case 2:
	    fileOut = new File(args[1]);
	case 1:
	    fileIn = new File(args[0]);
	    if (fileOut == null) {
		fileOut = new File(PKCS12Import.DEFAULT_OUTPUT_JSK_FILE);
	    }
	    break;
	default:
	    System.err.println("Usage: java PKCS12Import {pkcs12file} [newjksfile] [passfile]");
	    throw new IllegalArgumentException(PKCS12Import.ILLEGAL_NUMBER_ARGUMENT_EXCEPTION_MESSAGE);
	}

	if (!fileIn.canRead()) {
	    System.err.println("Unable to access input keystore: " + fileIn.getPath());
	    throw new IllegalArgumentException(PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE);
	}

	if (fileOut.exists() && !fileOut.canWrite()) {
	    System.err.println("Output file is not writable: " + fileOut.getPath());
	    throw new IllegalArgumentException(PKCS12Import.ILLEGAL_FILE_WRITE_EXCEPTION_MESSAGE);
	}

	if (filePass != null && !filePass.canRead()) {
	    System.err.println("Password file is not readable: " + filePass.getPath());
	    throw new IllegalArgumentException(PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE);
	}

	return new File[] { fileIn, fileOut, filePass };
    }

    /**
     * This method retrieves an array of passwords
     * 
     * @param filePass
     *            a password file
     * @return an array of passwords
     */
    private static char[][] processPassPhrases(File filePass) {
	char[] inPhrase = null;
	char[] outPhrase = null;
	if (filePass != null) {
	    BufferedReader bufferedReader = null;
	    try {
		bufferedReader = new BufferedReader(new FileReader(filePass));
	    } catch (FileNotFoundException e) {
		System.err.println("This could not be happened because of processParameters method.\nFile Not Found: " + filePass.getAbsolutePath());
		throw new IllegalArgumentException(PKCS12Import.ILLEGAL_FILE_READ_EXCEPTION_MESSAGE);
	    }

	    if (bufferedReader != null) {
		// Read input keystore passphrase
		String inPhraseString = null;
		try {
		    inPhraseString = bufferedReader.readLine();
		} catch (IOException e) {
		    System.err.println("IOException Could not retrieve the input password phrase");
		    throw new NoSuchFieldError(PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE);
		}
		if (inPhraseString != null) {
		    inPhraseString = inPhraseString.trim();
		    inPhrase = inPhraseString.toCharArray();
		} else {
		    System.err.println("Could not retrieve the input password phrase");
		    throw new NoSuchFieldError(PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE);
		}
		// Read output keystore passphrase
		String outPhraseString = null;
		try {
		    outPhraseString = bufferedReader.readLine();
		} catch (IOException e) {
		    System.err.println("IOException Could not retrieve the output password phrase");
		    throw new NoSuchFieldError(PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE);
		}
		if (outPhraseString != null) {
		    outPhraseString = outPhraseString.trim();
		    outPhrase = outPhraseString.toCharArray();
		} else {
		    System.err.println("Could not retrieve the output password phrase");
		    throw new NoSuchFieldError(PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE);
		}

		try {
		    bufferedReader.close();
		} catch (IOException e) {
		    System.err.println("Could not close the file " + filePass.getAbsolutePath());
		}
	    }
	} else {
	    inPhrase = readPassphrase("Enter input keystore passphrase: ");
	    outPhrase = readPassphrase("Enter output keystore passphrase: ");
	}
	return new char[][] { inPhrase, outPhrase };
    }

    /**
     * This is the main method to convert the PKCS12 to JKS
     * @param fileIn the PKCS12 input file
     * @param inPhrase the pass phrase of the PKCS12 input file
     * @param fileOut the JKS output file
     * @param outPhrase the pass phrase of the JKS output file
     */
    private static void processKeyStore(String keySoreTypeIn, File fileIn, char[] inPhrase,
	    String keySoreTypeOut, File fileOut, char[] outPhrase)
    {
	FileInputStream pkcs12FileInputStream = null;
	FileInputStream jksFileInputStream = null;
	OutputStream jksOutputStream = null;
	KeyStore kspkcs12 = null;
	try { // To close all open stream

	    try {
		kspkcs12 = KeyStore.getInstance(keySoreTypeIn);
	    } catch (KeyStoreException e) {
		System.err.println("Could not retrieve pkcs12 key store instance");
		throw new IllegalArgumentException(PKCS12Import.KEY_STORE_TYPE_IN_EXCEPTION_MESSAGE);
	    }
	    KeyStore ksjks = null;
	    try {
		ksjks = KeyStore.getInstance(keySoreTypeOut);
	    } catch (KeyStoreException e) {
		System.err.println("Could not retrieve jks key store instance");
		throw new IllegalArgumentException(PKCS12Import.KEY_STORE_TYPE_OUT_EXCEPTION_MESSAGE);
	    }
	    try {
		if (fileIn==null) {
		    throw new FileNotFoundException();
		}
		pkcs12FileInputStream = new FileInputStream(fileIn);
	    } catch (FileNotFoundException e) {
		System.err.println("File Not Found " + fileIn);
		throw new IllegalArgumentException(PKCS12Import.ILLEGAL_FILE_IN_NOT_FOUND_EXCEPTION_MESSAGE);
	    }
	    try {
		kspkcs12.load(pkcs12FileInputStream, inPhrase);
	    } catch (NoSuchAlgorithmException e) {
		System.err.println("Could not load pkcs12 NoSuchAlgorithmException");
		System.exit(++exitCount);
	    } catch (CertificateException e) {
		System.err.println("Could not load pkcs12 CertificateException");
		System.exit(++exitCount);
	    } catch (IOException e) {
		System.err.println("Could not load pkcs12 IOException");
		throw new IllegalArgumentException(PKCS12Import.ILLEGAL_KEY_STORE_LOAD_FILE_IN);
	    }

	    try {
		if (fileOut == null) {
		    fileOut = new File(PKCS12Import.DEFAULT_OUTPUT_JSK_FILE);
		}
		jksFileInputStream = new FileInputStream(fileOut);
	    } catch (Exception e) {
		System.out.println("WARNING File Not Found " + fileOut.getAbsolutePath());
	    }
	    try {
		ksjks.load(jksFileInputStream, outPhrase);
	    } catch (NoSuchAlgorithmException e) {
		System.err.println("Could not load jks NoSuchAlgorithmException");
		System.exit(++exitCount);
	    } catch (CertificateException e) {
		System.err.println("Could not load jks CertificateException");
		System.exit(++exitCount);
	    } catch (IOException e) {
		System.err.println("Could not load jks IOException");
		throw new IllegalArgumentException(PKCS12Import.ILLEGAL_KEY_STORE_LOAD_FILE_OUT);
	    }

	    Enumeration<String> eAliases = null;
	    try {
		eAliases = kspkcs12.aliases();
	    } catch (KeyStoreException e) {
		System.err.println("Could get pkcs12 aliases");
		System.exit(++exitCount);
	    }
	    if (eAliases != null) {
		int n = 0;
		while (eAliases.hasMoreElements()) {
		    String strAlias = (String) eAliases.nextElement();
		    System.out.println("Alias " + n++ + ": " + strAlias);

		    boolean isKeyEntry = false;
		    try {
			isKeyEntry = kspkcs12.isKeyEntry(strAlias);
		    } catch (KeyStoreException e) {
			System.err.println("pkcs12 not initialize");
			System.exit(++exitCount);
		    }

		    if (isKeyEntry) {
			System.out.println("Adding key for alias " + strAlias);
			Key key = null;
			try {
			    key = kspkcs12.getKey(strAlias, inPhrase);
			} catch (UnrecoverableKeyException e) {
			    System.err
				    .println("UnrecoverableKeyException Could not get pkcs12 key with alias "
					    + strAlias);
			    System.exit(++exitCount);
			} catch (KeyStoreException e) {
			    System.err
				    .println("KeyStoreException Could not get pkcs12 key with alias "
					    + strAlias);
			    System.exit(++exitCount);
			} catch (NoSuchAlgorithmException e) {
			    System.err
				    .println("NoSuchAlgorithmException Could not get pkcs12 key with alias "
					    + strAlias);
			    System.exit(++exitCount);
			}

			Certificate[] chain = null;
			try {
			    chain = kspkcs12.getCertificateChain(strAlias);
			} catch (KeyStoreException e) {
			    System.err
				    .println("Could not get pkcs12 certificate chain with alias "
					    + strAlias);
			    System.exit(++exitCount);
			}

			try {
			    ksjks.setKeyEntry(strAlias, key, outPhrase, chain);
			} catch (KeyStoreException e) {
			    System.err
				    .println("Could not set jks entry key with alias "
					    + strAlias);
			    System.exit(++exitCount);
			}
		    }
		}
		try {
		    jksOutputStream = new FileOutputStream(fileOut);
		} catch (FileNotFoundException e) {
		    System.err.println("File Not Found "
			    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		}
		try {
		    ksjks.store(jksOutputStream, outPhrase);
		} catch (KeyStoreException e) {
		    System.err
			    .println("KeyStoreException Could not store jsk in "
				    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		} catch (NoSuchAlgorithmException e) {
		    System.err
			    .println("NoSuchAlgorithmException Could not store jsk in "
				    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		} catch (CertificateException e) {
		    System.err
			    .println("CertificateException Could not store jsk in "
				    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		} catch (IOException e) {
		    System.err.println("IOException Could not store jsk in "
			    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		}
		try {
		    jksOutputStream.close();
		} catch (IOException e) {
		    System.err.println("Could not close "
			    + fileOut.getAbsolutePath());
		    System.exit(++exitCount);
		}
	    } else {
		System.err.println("Could get pkcs12 aliases");
		System.exit(++exitCount);
	    }
	} finally { // To close all open stream
	    try {
		jksOutputStream.close();
	    } catch (Exception e) {
		System.err.println("Could not close the JKS OutputStream " + fileOut);
	    }
	    try {
		jksFileInputStream.close();
	    } catch (Exception e) {
		System.err.println("Could not close the JKS InputStream " + fileOut);
	    }
	    try {
		pkcs12FileInputStream.close();
	    } catch (Exception e) {
		System.err.println("Could not close the PKCS12 InputStream " + fileIn);
	    }
	}
    }

    /**
     * Entry point to convert PKCS12 to JKS
     * @param args the parameters used to convert PKCS12 to JKS
     */
    public static void processImport(String... args) {
	File[] files = processParameters(args);
	File fileIn = files[0]; 
	File fileOut = files[1];
	File filePass = files[2]; //Could be null
	char[][] passPhrases = processPassPhrases(filePass);
	processKeyStore(PKCS12Import.PKCS12_KEY_STORE_INSTANCE_TYPE, fileIn, passPhrases[0], PKCS12Import.JKS_KEY_STORE_INSTANCE_TYPE, fileOut, passPhrases[1]);

    }

    /**
     * Entry point for this class
     * @param args the parameters used to convert PKCS12 to JKS
     * @throws Exception when any errors occur
     */
    public static void main(String... args) throws Exception {
	PKCS12Import.processImport(args);
    }
     private static char[] readPassphrase(String label) {
	Console console = System.console();
	char[] phrase = null;
	
	if (console != null) {
	    phrase = console.readPassword(label);
	} else {
	    System.err.println("Console not found");
	}
	if (phrase == null || phrase.length == 0) {
	    System.err.println("Could not retrieve the input password phrase");
	    throw new NoSuchFieldError(PKCS12Import.NO_SUCH_FIELD_EXCEPTION_MESSAGE);
	}
	return phrase;
    }
}
