package framework.helper;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashSet;

public class FastClasspathScanner {
	/** The unique elements of the classpath, as an ordered list. */
	private final ArrayList<File> classpathElements = new ArrayList<File>();

	/** The unique elements of the classpath, as a set. */
	private final HashSet<String> classpathElementsSet = new HashSet<String>();

	/** Clear the classpath. */
	private void clearClasspath() {
	    classpathElements.clear();
	    classpathElementsSet.clear();
	}

	/** Add a classpath element. */
	private void addClasspathElement(String pathElement) {
	    if (classpathElementsSet.add(pathElement)) {
	    	String filePath= URLDecoder.decode(pathElement);
	        final File file = new File(filePath);
	        if (file.exists()) {
	            classpathElements.add(file);
	        }
	    }
	}

	/** Parse the system classpath. */
	public void parseSystemClasspath() {
	    // Look for all unique classloaders.
	    // Keep them in an order that (hopefully) reflects the order in which class resolution occurs.
	    ArrayList<ClassLoader> classLoaders = new ArrayList<ClassLoader>();
	    HashSet<ClassLoader> classLoadersSet = new HashSet<ClassLoader>();
	    classLoadersSet.add(ClassLoader.getSystemClassLoader());
	    classLoaders.add(ClassLoader.getSystemClassLoader());
	    if (classLoadersSet.add(Thread.currentThread().getContextClassLoader())) {
	        classLoaders.add(Thread.currentThread().getContextClassLoader());
	    }
	    // Dirty method for looking for any other classloaders on the call stack
	    try {
	        // Generate stacktrace
	        throw new Exception();
	    } catch (Exception e) {
	        StackTraceElement[] stacktrace = e.getStackTrace();
	        for (StackTraceElement elt : stacktrace) {
	            try {
	                ClassLoader cl = Class.forName(elt.getClassName()).getClassLoader();
	                if (classLoadersSet.add(cl)) {
	                    classLoaders.add(cl);
	                }
	            } catch (ClassNotFoundException e1) {
	            }
	        }
	    }

	    // Get file paths for URLs of each classloader.
	    clearClasspath();
	    for (ClassLoader cl : classLoaders) {
	        if (cl != null) {
	            for (URL url : ((URLClassLoader) cl).getURLs()) {
	                if ("file".equals(url.getProtocol())) {
	                    addClasspathElement(url.getFile());
	                }
	            }
	        }
	    }
	}

	/** Override the system classpath with a custom classpath to search. */
	public FastClasspathScanner overrideClasspath(String classpath) {
	    clearClasspath();
	    for (String pathElement : classpath.split(File.pathSeparator)) {
	        addClasspathElement(pathElement);
	    }
	    return this;
	}

	/**
	 * Get a list of unique elements on the classpath (directories and files) as File objects, preserving order.
	 * Classpath elements that do not exist are not included in the list.
	 */
	public String getClassPathStr()
	{
		StringBuilder classPathBuilder=new StringBuilder();
		
		for(File f:classpathElements)
		{
			classPathBuilder.append(f.getAbsolutePath()).append(File.pathSeparatorChar);
			
		}
		classPathBuilder.deleteCharAt(classPathBuilder.length()-1);
		return classPathBuilder.toString();
	}
	public ArrayList<File> getUniqueClasspathElements() {
	    return classpathElements;
	}
}
