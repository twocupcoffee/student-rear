package com.nwang.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class PrintUtil {

    public static void response(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.write(msg);
        out.flush();
    }
}
