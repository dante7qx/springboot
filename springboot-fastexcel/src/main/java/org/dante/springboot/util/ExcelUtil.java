package org.dante.springboot.util;

import cn.idev.excel.EasyExcel;
import cn.idev.excel.write.builder.ExcelWriterBuilder;
import cn.idev.excel.write.builder.ExcelWriterSheetBuilder;
import cn.idev.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.function.Consumer;

public class ExcelUtil {

    /**
     * 导出Excel
     *
     * @param response  HttpServletResponse
     * @param fileName  文件名
     * @param sheetName sheet名
     * @param clazz     实体类
     * @param data      数据列表
     * @param <T>       泛型
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                       Class<T> clazz, List<T> data) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        EasyExcel.write(response.getOutputStream(), clazz)
                .sheet(sheetName)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()) // 自动列宽
                .doWrite(data);
    }

    /**
     * 高级导出Excel
     *
     * @param response       HttpServletResponse
     * @param fileName       文件名
     * @param sheetName      sheet名
     * @param clazz          实体类
     * @param data           数据列表
     * @param writerConsumer 自定义写入配置
     * @param <T>            泛型
     * @throws IOException IO异常
     */
    public static <T> void exportExcel(HttpServletResponse response, String fileName, String sheetName,
                                       Class<T> clazz, List<T> data,
                                       Consumer<ExcelWriterBuilder> writerConsumer) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");

        ExcelWriterBuilder writerBuilder = EasyExcel.write(response.getOutputStream(), clazz);

        // 应用自定义配置
        if (writerConsumer != null) {
            writerConsumer.accept(writerBuilder);
        }

        writerBuilder.sheet(sheetName).doWrite(data);
    }

    /**
     * 自定义导出到输出流
     *
     * @param outputStream   输出流
     * @param sheetName      sheet名
     * @param clazz          实体类
     * @param data           数据列表
     * @param writerConsumer 自定义写入配置
     * @param sheetConsumer  自定义sheet配置
     * @param <T>            泛型
     */
    public static <T> void exportExcel(OutputStream outputStream, String sheetName,
                                       Class<T> clazz, List<T> data,
                                       Consumer<ExcelWriterBuilder> writerConsumer,
                                       Consumer<ExcelWriterSheetBuilder> sheetConsumer) {
        ExcelWriterBuilder writerBuilder = EasyExcel.write(outputStream, clazz);

        if (writerConsumer != null) {
            writerConsumer.accept(writerBuilder);
        }

        ExcelWriterSheetBuilder sheetBuilder = writerBuilder.sheet(sheetName);

        if (sheetConsumer != null) {
            sheetConsumer.accept(sheetBuilder);
        }

        sheetBuilder.doWrite(data);
    }

    /**
     * 导入Excel
     *
     * @param file  上传的文件
     * @param clazz 实体类
     * @param <T>   泛型
     * @return 数据列表
     * @throws IOException IO异常
     */
    public static <T> List<T> importExcel(MultipartFile file, Class<T> clazz) throws IOException {
        ExcelListener<T> listener = new ExcelListener<>();
        EasyExcel.read(file.getInputStream(), clazz, listener).sheet().doRead();
        return listener.getDataList();
    }

}
