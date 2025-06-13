package org.dante.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpiritResult<T> {

    @Schema(description = "响应码")
    private int code;
    @Schema(description = "响应信息")
    private String message;
    @Schema(description = "响应数据")
    private T data;

    public static <T> SpiritResult<T> success(T data) {
        return new SpiritResult<>(200, "操作成功", data);
    }

    public static <T> SpiritResult<T> success() {
        return new SpiritResult<>(200, "操作成功", null);
    }

    public static <T> SpiritResult<T> error(int code, String message) {
        return new SpiritResult<>(code, message, null);
    }
}
