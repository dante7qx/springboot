package org.dante.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.dante.springboot.dto.SpiritResult;
import org.dante.springboot.po.TagPO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/tag")
@Tag(name = "标签管理", description = "标签相关接口")
public class TagController {

    @GetMapping("/{id}")
    @Operation(summary = "根据Id获取标签", description = "根据 ID 查询标签")
    @Parameter(name = "id", description = "标签ID", required = true)
    // @Parameters({@Parameter(...), @Parameter(...)})
    public SpiritResult<TagPO> sayHello(@PathVariable("id") Long id) {
        return SpiritResult.success(new TagPO(id, "标签" + id));
    }

    @PostMapping("/persist")
    @Operation(summary = "持久化标签", description = "持久化标签")
    public SpiritResult<Void> sayHello(@RequestBody TagPO tagPO) {
        return SpiritResult.success();
    }

}
