package org.dante.springboot.vo;

import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.annotation.write.style.ColumnWidth;
import cn.idev.excel.annotation.write.style.ContentRowHeight;
import cn.idev.excel.annotation.write.style.HeadRowHeight;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@HeadRowHeight(20)
@ContentRowHeight(15)
public class UserVO {

    @ExcelProperty("用户ID")
    @ColumnWidth(10)
    private Long id;

    @ExcelProperty("用户名")
    @ColumnWidth(30)
    private String name;

    @ExcelProperty("年龄")
    @ColumnWidth(10)
    private Integer age;


}
