## Java 导出 Word、PDF

### 一. Word

| 方案           | 移植性                       | 功能性                                                  | 易用性                                                       |
| :------------- | :--------------------------- | :------------------------------------------------------ | :----------------------------------------------------------- |
| **Poi-tl**     | Java跨平台                   | Word模板引擎，基于Apache POI，提供更友好的API           | 低代码，准备文档模板和数据即可                               |
| Apache POI     | Java跨平台                   | Apache项目，封装了常见的文档操作，也可以操作底层XML结构 | 文档不全，这里有一个教程：[Apache POI Word快速入门](http://deepoove.com/poi-tl/apache-poi-guide.html) |
| Freemarker<br>Tymeleaf | XML跨平台                    | 仅支持文本，很大的局限性                                | 不推荐，XML结构的代码几乎无法维护                            |
| OpenOffice     | 部署OpenOffice，移植性较差   | -                                                       | 需要了解OpenOffice的API                                      |
| HTML浏览器导出 | 依赖浏览器的实现，移植性较差 | HTML不能很好的兼容Word的格式，样式糟糕                  | -                                                            |
| Jacob、winlib  | Windows平台                  | -                                                       | 复杂，完全不推荐使用                                         |

详情请查看 http://deepoove.com/poi-tl/

### 二. PDF