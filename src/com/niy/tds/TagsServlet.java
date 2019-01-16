package com.niy.tds;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/spxtags")
public class TagsServlet extends HttpServlet {
    String rootFolder = System.getenv("SPF_SCHEMA");
    Path rootPath = Paths.get(rootFolder);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            FileVisitor fv = new FileVisitor();
            Files.walkFileTree(rootPath, fv);
            Map<String, String> test = new HashMap<>();
            for (Path p: fv.foundFiles) {
                try (StreamProcessor processor = new StreamProcessor(Files.newInputStream(p))) {
                    while (processor.getInnerElementAttributes("EnumEnum", "IObject")) {
                        test.put(processor.getAttribute("UID"), processor.getAttribute("Name"));
                    }
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            JsonBuilder jsonBuilder = new JsonBuilder(test);
            String jsonResult = jsonBuilder.getJsonObject();
            response.setHeader("Content-type", "application/json;charset=UTF-8");
            try(PrintWriter pw = response.getWriter()){
                pw.println(jsonResult);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
