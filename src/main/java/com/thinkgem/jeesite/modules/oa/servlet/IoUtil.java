package com.thinkgem.jeesite.modules.oa.servlet;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;


/**
 * IO--closing, getting file name ... main function method
 */
public class IoUtil {
    static final Pattern RANGE_PATTERN = Pattern.compile("bytes \\d+-\\d+/\\d+");

    /**
     * According the key, generate a file (if not exist, then create
     * a new file).
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static File getFile(String filename,HttpServletRequest request) throws IOException {
        if (filename == null || filename.isEmpty())
            return null;
        String name =filename.replaceAll("/", Matcher.quoteReplacement(File.separator));
        String realPath=request.getSession().getServletContext().getRealPath("/");
        System.out.println(realPath+"-----");
        File f = new File(realPath+Configurations.getFileRepository() + File.separator + name);
        System.out.println(f.getName()+" 00000");
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();

        return f;
    }

    /**
     * Acquired the file.
     *
     * @param key
     * @return
     * @throws java.io.IOException
     */
    public static File getTokenedFile(String key,HttpServletRequest request) throws IOException {
        if (key == null || key.isEmpty())
            return null;
        String realPath=request.getSession().getServletContext().getRealPath("/");
        File f = new File(realPath+Configurations.getFileRepository() + File.separator + key);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();

        return f;
    }

    public static void storeToken(String key,HttpServletRequest request) throws IOException {
        if (key == null || key.isEmpty())
            return;
        String realPath=request.getSession().getServletContext().getRealPath("/");
        File f = new File(realPath+Configurations.getFileRepository() + File.separator + key);
        if (!f.getParentFile().exists())
            f.getParentFile().mkdirs();
        if (!f.exists())
            f.createNewFile();
    }

    /**
     * close the IO stream.
     *
     * @param stream
     */
    public static void close(Closeable stream) {
        try {
            if (stream != null)
                stream.close();
        } catch (IOException e) {
        }
    }

    /**
     * 获取Range参数
     *
     * @param req
     * @return
     * @throws java.io.IOException
     */
    public static Range parseRange(HttpServletRequest req) throws IOException {
        String range = req.getHeader(StreamServlet.CONTENT_RANGE_HEADER);
        Matcher m = RANGE_PATTERN.matcher(range);
        if (m.find()) {
            range = m.group().replace("bytes ", "");
            String[] rangeSize = range.split("/");
            String[] fromTo = rangeSize[0].split("-");

            long from = Long.parseLong(fromTo[0]);
            long to = Long.parseLong(fromTo[1]);
            long size = Long.parseLong(rangeSize[1]);

            return new Range(from, to, size);
        }
        throw new IOException("Illegal Access!");
    }

    /**
     * From the InputStream, write its data to the given file.
     */
    public static long streaming(InputStream in, String key, String fileName,HttpServletRequest request) throws IOException {
        OutputStream out = null;
        File f = getTokenedFile(key,request);
        try {
            out = new FileOutputStream(f);

            int read = 0;
            final byte[] bytes = new byte[FormDataServlet.BUFFER_LENGTH];
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
        } finally {
            close(out);
        }
        /** rename the file * fix the `renameTo` bug */
        File dst = IoUtil.getFile(fileName,request);
        dst.delete();
        f.renameTo(dst);

        long length = getFile(fileName,request).length();
        /** if `STREAM_DELETE_FINISH`, then delete it. */
        if (Configurations.isDeleteFinished()) {
            dst.delete();
        }

        return length;
    }
}
