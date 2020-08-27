package filesharing;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/media/*")
public class MediaFileServlet extends FileServlet {

    private File folder;

    public void init() throws ServletException {
        folder = new File("E:/Videos");
    }

    protected File getFile(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();

        if (pathInfo == null || pathInfo.isEmpty() || "/".equals(pathInfo)) {
            throw new IllegalArgumentException();
        }

        return new File(folder, pathInfo);
    }

}