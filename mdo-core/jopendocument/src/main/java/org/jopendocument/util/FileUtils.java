/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 * 
 * Copyright 2008 jOpenDocument, by ILM Informatique. All rights reserved.
 * 
 * The contents of this file are subject to the terms of the GNU
 * General Public License Version 3 only ("GPL").  
 * You may not use this file except in compliance with the License. 
 * You can obtain a copy of the License at http://www.gnu.org/licenses/gpl-3.0.html
 * See the License for the specific language governing permissions and limitations under the License.
 * 
 * When distributing the software, include this License Header Notice in each file.
 * 
 */

package org.jopendocument.util;

import org.jopendocument.util.StringUtils.Escaper;
import org.jopendocument.util.cc.ExnTransformer;
import org.jopendocument.util.cc.IClosure;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FileUtils {

    private FileUtils() {
        // all static
    }

    /**
     * All the files (see {@link File#isFile()}) contained in the passed dir.
     * 
     * @param dir the root directory to search.
     * @return a List of String.
     */
    public static List<String> listR(File dir) {
        return listR_rec(dir, ".");
    }

    private static List<String> listR_rec(File dir, String prefix) {
        if (!dir.isDirectory())
            return null;

        final List<String> res = new ArrayList<String>();
        final File[] children = dir.listFiles();
        for (int i = 0; i < children.length; i++) {
            final String newPrefix = prefix + "/" + children[i].getName();
            if (children[i].isFile()) {
                // MAYBE add a way to restrict added files
                res.add(newPrefix);
            } else if (children[i].isDirectory()) {
                res.addAll(listR_rec(children[i], newPrefix));
            }
        }
        return res;
    }

    public static void walk(File dir, IClosure<File> c) {
        walk(dir, c, RecursionType.BREADTH_FIRST);
    }

    public static void walk(File dir, IClosure<File> c, RecursionType type) {
        if (type == RecursionType.BREADTH_FIRST)
            c.executeChecked(dir);
        if (dir.isDirectory()) {
            for (final File child : dir.listFiles()) {
                walk(child, c, type);
            }
        }
        if (type == RecursionType.DEPTH_FIRST)
            c.executeChecked(dir);
    }

    public static final List<File> list(File root, final int depth) {
        return list(root, depth, null);
    }

    /**
     * Finds all files at the specified depth below <code>root</code>.
     * 
     * @param root the base directory
     * @param depth the depth of the returned files.
     * @param ff a filter, can be <code>null</code>.
     * @return a list of files <code>depth</code> levels beneath <code>root</code>.
     */
    public static final List<File> list(File root, final int depth, final FileFilter ff) {
        if (!root.exists())
            return Collections.<File> emptyList();
        if (depth == 0) {
            return ff.accept(root) ? Collections.singletonList(root) : Collections.<File> emptyList();
        } else if (depth == 1) {
            final File[] listFiles = root.listFiles(ff);
            if (listFiles == null)
                throw new IllegalStateException("cannot list " + root);
            return Arrays.asList(listFiles);
        } else {
            final File[] childDirs = root.listFiles(DIR_FILTER);
            if (childDirs == null)
                throw new IllegalStateException("cannot list " + root);
            final List<File> res = new ArrayList<File>();
            for (final File child : childDirs) {
                res.addAll(list(child, depth - 1, ff));
            }
            return res;
        }
    }

    /**
     * Returns the relative path from one file to another in the same filesystem tree.
     * 
     * @param fromDir the starting directory, eg /a/b/.
     * @param to the file to get to, eg /a/x/y.txt.
     * @return the relative path, eg "../x/y.txt".
     * @throws IOException if an error occurs while canonicalizing the files.
     * @throws IllegalArgumentException if fromDir is not directory, or the files have no common
     *         ancestors (for example on Windows on 2 different letters).
     */
    public static final String relative(File fromDir, File to) throws IOException {
        if (!fromDir.isDirectory())
            throw new IllegalArgumentException(fromDir + " is not a directory");

        final File fromF = fromDir.getCanonicalFile();
        final File toF = to.getCanonicalFile();
        final List<File> toPath = getAncestors(toF);
        final List<File> fromPath = getAncestors(fromF);

        if (!toPath.get(0).equals(fromPath.get(0)))
            throw new IllegalArgumentException("'" + fromF + "' and '" + toF + "' have no common ancestor");

        int commonIndex = Math.min(toPath.size(), fromPath.size()) - 1;
        boolean found = false;
        while (commonIndex >= 0 && !found) {
            found = fromPath.get(commonIndex).equals(toPath.get(commonIndex));
            if (!found)
                commonIndex--;
        }

        // on remonte jusqu'à l'ancêtre commun
        final List<String> complete = new ArrayList<String>(Collections.nCopies(fromPath.size() - 1 - commonIndex, ".."));
        if (complete.isEmpty())
            complete.add(".");
        // puis on descend vers 'to'
        for (File f : toPath.subList(commonIndex + 1, toPath.size())) {
            complete.add(f.getName());
        }

        return CollectionUtils.join(complete, File.separator);
    }

    // return each ancestor of f (including itself)
    // eg [/, /folder, /folder/dir] for /folder/dir
    public final static List<File> getAncestors(File f) {
        final List<File> path = new ArrayList<File>();
        File currentF = f;
        while (currentF != null) {
            path.add(0, currentF);
            currentF = currentF.getParentFile();
        }
        return path;
    }

    public final static File addSuffix(File f, String suffix) {
        return new File(f.getParentFile(), f.getName() + suffix);
    }

    // ** shell

    /**
     * Behave like the 'mv' unix utility, ie handle cross filesystems mv and <code>dest</code> being
     * a directory.
     * 
     * @param f the source file.
     * @param dest the destination file or directory.
     * @return the error or <code>null</code> if there was none.
     */
    public static String mv(File f, File dest) {
        final File canonF;
        File canonDest;
        try {
            canonF = f.getCanonicalFile();
            canonDest = dest.getCanonicalFile();
        } catch (IOException e) {
            return ExceptionUtils.getStackTrace(e);
        }
        if (canonF.equals(canonDest))
            // nothing to do
            return null;
        if (canonDest.isDirectory())
            canonDest = new File(canonDest, canonF.getName());

        final File destF;
        if (canonDest.exists())
            return canonDest + " exists";
        else if (!canonDest.getParentFile().exists())
            return "parent of " + canonDest + " does not exist";
        else
            destF = canonDest;
        if (!canonF.renameTo(destF)) {
            try {
                copyDirectory(canonF, destF);
                if (destF.exists())
                    rmR(canonF);
            } catch (IOException e) {
                return ExceptionUtils.getStackTrace(e);
            }
        }
        return null;
    }

    public static void copyFile(File in, File out) throws IOException {
        final FileChannel sourceChannel = new FileInputStream(in).getChannel();
        final FileChannel destinationChannel = new FileOutputStream(out).getChannel();
        sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
        sourceChannel.close();
        destinationChannel.close();
    }

    public static void copyDirectory(File in, File out) throws IOException {
        copyDirectory(in, out, Collections.<String> emptySet());
    }

    public static final Set<String> VersionControl = CollectionUtils.createSet(".svn", "CVS");

    public static void copyDirectory(File in, File out, final Set<String> toIgnore) throws IOException {
        if (toIgnore.contains(in.getName()))
            return;

        if (in.isDirectory()) {
            if (!out.exists()) {
                out.mkdir();
            }

            String[] children = in.list();
            for (int i = 0; i < children.length; i++) {
                copyDirectory(new File(in, children[i]), new File(out, children[i]), toIgnore);
            }
        } else {
            if (!in.getName().equals("Thumbs.db")) {
                copyFile(in, out);
            }
        }
    }

    /**
     * Delete recursively the passed directory. If a deletion fails, the method stops attempting to
     * delete and returns false.
     * 
     * @param dir the dir to be deleted.
     * @return <code>true</code> if all deletions were successful.
     */
    public static boolean rmR(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            for (int i = 0; i < children.length; i++) {
                boolean success = rmR(children[i]);

                if (!success) {

                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }

    public static final File mkdir_p(File dir) throws IOException {
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("cannot create directory " + dir);
            }
        }
        return dir;
    }

    // **io

    /**
     * Read a file line by line with the default encoding and returns the concatenation of these.
     * 
     * @param f the file to read.
     * @return the content of f.
     * @throws IOException if a pb occur while reading.
     */
    public static final String read(File f) throws IOException {
        return read(f, null);
    }

    /**
     * Read a file line by line and returns the concatenation of these.
     * 
     * @param f the file to read.
     * @param charset the encoding of <code>f</code>, <code>null</code> means default encoding.
     * @return the content of f.
     * @throws IOException if a pb occur while reading.
     */
    public static final String read(File f, String charset) throws IOException {
        return read(new FileInputStream(f), charset);
    }

    public static final String read(InputStream ins, String charset) throws IOException {
        final Reader reader;
        if (charset == null)
            reader = new InputStreamReader(ins);
        else
            reader = new InputStreamReader(ins, charset);
        return read(reader);
    }

    public static final String read(final Reader reader) throws IOException {
        final BufferedReader in = new BufferedReader(reader);
        String line;
        String res = "";
        while ((line = in.readLine()) != null) {
            res += line + "\n";
        }
        in.close();
        return res;
    }

    /**
     * Read the whole content of a file.
     * 
     * @param f the file to read.
     * @return its content.
     * @throws IOException if a pb occur while reading.
     * @throws IllegalArgumentException if f is longer than <code>Integer.MAX_VALUE</code>.
     */
    public static final byte[] readBytes(File f) throws IOException {
        // no need for a Buffer since we read everything at once
        final InputStream in = new FileInputStream(f);
        if (f.length() > Integer.MAX_VALUE)
            throw new IllegalArgumentException("file longer than Integer.MAX_VALUE" + f.length());
        final byte[] res = new byte[(int) f.length()];
        in.read(res);
        in.close();
        return res;
    }

    public static void write(String s, File f) throws IOException {
        write(s, f, false);
    }

    public static void write(String s, File f, boolean append) throws IOException {
        final BufferedWriter w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f, append)));
        try {
            w.write(s);
        } finally {
            w.close();
        }
    }

    /**
     * Execute the passed transformer with the lock on the passed file.
     * 
     * @param <T> return type.
     * @param f the file to lock.
     * @param transf what to do on the file.
     * @return what <code>transf</code> returns.
     * @throws Exception if an error occurs.
     */
    public static final <T> T doWithLock(final File f, ExnTransformer<RandomAccessFile, T, ?> transf) throws Exception {
        // don't use FileOutputStream : it truncates the file on creation
        RandomAccessFile out = null;
        try {
            mkdir_p(f.getParentFile());
            // we need write to obtain lock
            out = new RandomAccessFile(f, "rw");
            out.getChannel().lock();
            final T res = transf.transformChecked(out);
            // this also release the lock
            out.close();
            out = null;
            return res;
        } catch (final Exception e) {
            // if anything happens, try to close
            // don't use finally{close()} otherwise if it raise an exception
            // the original error is discarded
            Exception toThrow = e;
            if (out != null)
                try {
                    out.close();
                } catch (final IOException e2) {
                    // too bad, just add the error
                    toThrow = ExceptionUtils.createExn(IOException.class, "couldn't close: " + e2.getMessage(), e);
                }
            throw toThrow;
        }
    }

    private static final Map<URL, File> files = new HashMap<URL, File>();

    private static final File getShortCutFile() throws IOException {
        return getFile(FileUtils.class.getResource("shortcut.vbs"));
    }

    // windows cannot execute a string, it demands a file
    public static final File getFile(final URL url) throws IOException {
        final File shortcutFile;
        final File currentFile = files.get(url);
        if (currentFile == null || !currentFile.exists()) {
            shortcutFile = File.createTempFile("windowsIsLame", ".vbs");
            shortcutFile.deleteOnExit();
            files.put(url, shortcutFile);
            final InputStream stream = url.openStream();
            final FileOutputStream out = new FileOutputStream(shortcutFile);
            try {
                StreamUtils.copy(stream, out);
            } finally {
                out.close();
                stream.close();
            }
        } else
            shortcutFile = currentFile;
        return shortcutFile;
    }

    /**
     * Create a symbolic link from <code>link</code> to <code>target</code>.
     * 
     * @param target the target of the link, eg ".".
     * @param link the file to create or replace, eg "l".
     * @return the link if the creation was successfull, <code>null</code> otherwise, eg "l.LNK".
     * @throws IOException if an error occurs.
     */
    public static final File ln(final File target, final File link) throws IOException {
        final String os = System.getProperty("os.name");
        final Process ps;
        final File res;
        if (os.startsWith("Windows")) {
            // using the .vbs since it doesn't depends on cygwin
            // and cygwin's ln is weird :
            // 1. needs CYGWIN=winsymlinks to create a shortcut, but even then "ln -f" doesn't work
            // since it tries to delete l instead of l.LNK
            // 2. it sets the system flag so "dir" doesn't show the shortcut (unless you add /AS)
            // 3. the shortcut is recognized as a symlink thanks to a special attribute that can get
            // lost (e.g. copying in eclipse)
            ps = Runtime.getRuntime().exec(new String[] { "cscript", getShortCutFile().getAbsolutePath(), link.getAbsolutePath(), target.getCanonicalPath() });
            res = new File(link.getParentFile(), link.getName() + ".LNK");
        } else {
            final String rel = FileUtils.relative(link.getAbsoluteFile().getParentFile(), target);
            // add -f to replace existing links
            // add -n so that ln -sf aDir anExistantLinkToIt succeed
            final String[] cmdarray = { "ln", "-sfn", rel, link.getAbsolutePath() };
            ps = Runtime.getRuntime().exec(cmdarray);
            res = link;
        }
        try {
            final int exitValue = ps.waitFor();
            if (exitValue == 0)
                return res;
            else
                throw new IOException("Abnormal exit value: " + exitValue);
        } catch (InterruptedException e) {
            throw ExceptionUtils.createExn(IOException.class, "interrupted", e);
        }
    }

    /**
     * Resolve a symbolic link or a windows shortcut.
     * 
     * @param link the shortcut, e.g. shortcut.lnk.
     * @return the target of <code>link</code>, <code>null</code> if not found, e.g. target.txt.
     * @throws IOException if an error occurs.
     */
    public static final File readlink(final File link) throws IOException {
        final String os = System.getProperty("os.name");
        final Process ps;
        if (os.startsWith("Windows")) {
            ps = Runtime.getRuntime().exec(new String[] { "cscript", "//NoLogo", getShortCutFile().getAbsolutePath(), link.getAbsolutePath() });
        } else {
            // add -f to canonicalize
            ps = Runtime.getRuntime().exec(new String[] { "readlink", "-f", link.getAbsolutePath() });
        }
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            final String res = reader.readLine();
            reader.close();
            if (ps.waitFor() != 0 || res == null || res.length() == 0)
                return null;
            else
                return new File(res);
        } catch (InterruptedException e) {
            throw ExceptionUtils.createExn(IOException.class, "interrupted", e);
        }
    }

    /**
     * Tries to open the passed file as if it were graphically opened by the current user (respect
     * user's "open with"). If a native way to open the file can't be found, tries the passed list
     * of executables.
     * 
     * @param f the file to open.
     * @param executables a list of executables to try, e.g. ["ooffice", "soffice"].
     * @throws IOException if the file can't be opened.
     */
    public static final void open(File f, String[] executables) throws IOException {
        try {
            openNative(f);
        } catch (IOException exn) {
            for (int i = 0; i < executables.length; i++) {
                final String executable = executables[i];
                try {
                    Runtime.getRuntime().exec(new String[] { executable, f.getCanonicalPath() });
                    return;
                } catch (IOException e) {
                    // try the next one
                }
            }
            throw ExceptionUtils.createExn(IOException.class, "unable to open " + f + " with: " + Arrays.asList(executables), exn);
        }
    }

    /**
     * Open the passed file as if it were graphically opened by the current user (user's "open
     * with").
     * 
     * @param f the file to open.
     * @throws IOException if f couldn't be opened.
     */
    private static final void openNative(File f) throws IOException {
        final String os = System.getProperty("os.name");
        final String[] cmdarray;
        if (os.startsWith("Windows")) {
            cmdarray = new String[] { "cmd", "/c", "start", "\"\"", f.getCanonicalPath() };
        } else if (os.startsWith("Mac OS")) {
            cmdarray = new String[] { "open", f.getCanonicalPath() };
        } else if (os.startsWith("Linux")) {
            cmdarray = new String[] { "xdg-open", f.getCanonicalPath() };
        } else {
            throw new IOException("unknown way to open " + f);
        }
        try {
            // can wait since the command return as soon as the native application is launched
            // (i.e. this won't wait 30s for OpenOffice)
            final int res = Runtime.getRuntime().exec(cmdarray).waitFor();
            if (res != 0)
                throw new IOException("error (" + res + ") executing " + Arrays.asList(cmdarray));
        } catch (InterruptedException e) {
            throw ExceptionUtils.createExn(IOException.class, "interrupted waiting for " + Arrays.asList(cmdarray), e);
        }
    }

    static final boolean gnomeRunning() {
        try {
            return Runtime.getRuntime().exec(new String[] { "pgrep", "-u", System.getProperty("user.name"), "nautilus" }).waitFor() == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static final Map<String, String> ext2mime;
    static {
        ext2mime = new HashMap<String, String>();
        ext2mime.put(".xml", "text/xml");
        ext2mime.put(".jpg", "image/jpeg");
        ext2mime.put(".png", "image/png");
        ext2mime.put(".tiff", "image/tiff");
    }

    /**
     * Try to guess the media type of the passed file name (see <a
     * href="http://www.iana.org/assignments/media-types">iana</a>).
     * 
     * @param fname a file name.
     * @return its mime type.
     */
    public static final String findMimeType(String fname) {
        for (final Map.Entry<String, String> e : ext2mime.entrySet()) {
            if (fname.toLowerCase().endsWith(e.getKey()))
                return e.getValue();
        }
        return null;
    }

    /**
     * Chars not valid in filenames.
     */
    public static final Collection<Character> INVALID_CHARS;

    /**
     * An escaper suitable for producing valid filenames.
     */
    public static final Escaper FILENAME_ESCAPER = new StringUtils.Escaper('\'', 'Q');
    static {
        // from windows explorer
        FILENAME_ESCAPER.add('"', 'D').add(':', 'C').add('/', 'S').add('\\', 'A');
        FILENAME_ESCAPER.add('<', 'L').add('>', 'G').add('*', 'R').add('|', 'P').add('?', 'M');
        INVALID_CHARS = FILENAME_ESCAPER.getEscapedChars();
    }

    public static final FileFilter DIR_FILTER = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isDirectory();
        }
    };
    public static final FileFilter REGULAR_FILE_FILTER = new FileFilter() {
        @Override
        public boolean accept(File f) {
            return f.isFile();
        }
    };

    /**
     * Return a filter that select regular files ending in <code>ext</code>.
     * 
     * @param ext the end of the name, eg ".xml".
     * @return the corresponding filter.
     */
    public static final FileFilter createEndFileFilter(final String ext) {
        return new FileFilter() {
            @Override
            public boolean accept(File f) {
                return f.isFile() && f.getName().endsWith(ext);
            }
        };
    }
}
