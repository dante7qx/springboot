## 图表和数据集之间的关系

图表和数据集是强关联关系，一个图表需要什么样的数据才能进行展示下面都有说明，以柱状图举例，只能用俩个字段的数据集进行数据展示，那使用数据集有2个以上字段那肯定图表无法正常展示，反之有个数据集是3个字段，那就找能展示3个字段的图表，千万别搞小聪明定了好多个字段的数据集，然后从里面挑几个字段进行展示，不要这么干。<br>

## 文本框

![img_3.png](../picture/dashboard/img_3.png) <br>
![img_4.png](../picture/dashboard/img_4.png) <br>
![img_5.png](../picture/dashboard/img_5.png) <br>
![img_6.png](../picture/dashboard/img_6.png) <br>

### 数据格式

![img1](../picture/charts/img.png) <br>

## 滚动文本

同上文本，操作无异 <br>

## 超链接

![img2](../picture/charts/img_1.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

## 当前时间

已支持多种时间格式，其他格式请提Issue <br>
![img_1.png](../picture/dashboard/img_1.png) <br>

## 图片

![img_7.png](../picture/dashboard/img_7.png) <br>
**注：** 如果没有在bootstrap.yml配置文件中没有正确配置上传下载的路径，这里肯定是失败的 <br>

## 视屏

**暂不支持循环播放**<br>
**注意视频的地址应是直接可以访问的，不需要登录之类** <br>

## 表格

![img](../picture/dashboard/img_22.png) <br>
表格字段对应的数据只选择“文本数字”。<br>
![img14](../picture/dashboard/img_23.png) <br>
**注意：** 多个字段的时候，需要在“配置-新增”添加你选择数据集所对应的字段，即“配置”中“表体设置”的“key值”要和你“动态数据对应的字段名”保持一致，类似于映射关系。 <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

## 内联框架

当超链接和视频链接无效的时候，请尝试使用内联框架<br>

## 柱状图

柱状图数据集对应字典值需要选择一个“X轴”、“柱状”，只需要2个字段 <br>
![img_13](../picture/charts/img_13.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img3](../picture/charts/img_2.png) <br>

## 柱形图-渐变色

同上柱形图，颜色调整为支持渐变色 <br>

## 折线图

折线图数据集对应字典值需要选择一个“X轴”、“折线”，只需要2个字段 <br>
![img14.png](../picture/charts/img_14.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

同上柱形图数据格式 <br>

## 柱线图

柱线图数据集对应字典值需要选择一个“X轴”、“柱状”、“折线”，需要3个字段，图例名称用 | 进行分隔。<br>
![img15](../picture/charts/img_15.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img5](../picture/charts/img_4.png) <br>

## 多柱线图

多柱线图对应数据字典需要选择一个“X轴”，剩下的字段可任意选择为“柱状”、“折线”，图例名称用 | 进行分隔。<br>
![img16](../picture/charts/img_16.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img17](../picture/charts/img_17.png) <br>

## 饼图

饼图的数据集选择的时候，只能选择饼图对应的字典，即“Name”、“Value”，不明白可以参考静态数据 <br>
![img8](../picture/charts/img_8.png) <br>
![img9](../picture/charts/img_9.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img6](../picture/charts/img_5.png) <br>

## 漏斗图

![img18](../picture/charts/img_18.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

和饼图数据格式一致。<br>

## 仪表盘

![img21](../picture/dashboard/img_21.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img7](../picture/charts/img_6.png) <br>

## 南丁格尔玫瑰图

![img71](../picture/charts/img_7.png) <br>
![img10](../picture/charts/img_10.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

和饼图数据格式保持一致。 <br>

## 百分百图

![img19](../picture/charts/img_19.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

和仪表盘数据格式保持一致。<br>

## 中国地图-路线图

路线图的动态数据集，需要3个字段，“源端”代表起点，“目标端”代表终点，“数值”代表俩点之间的值
![img24](../picture/charts/img_24.png) <br>

### 数据格式

可以参考静态数据，目前路线地图仅能显示“市”级别，注意地图数据名称要和地图底层数据名称保持一致，即完整的市名，可以看静态数据，如果和你提供的动态数据名称有差异，请参考源码自行修改 <br>
![img25](../picture/charts/img_25.png) <br>

## 中国地图-气泡图

气泡地图是中国地图气泡样式展示形式 <br>
气泡地图动态数据集，和饼图一样，对应字典值需要选择一个“Name”、“Value”，且name的字段值要和echarts图表里面的值能对应上，可参考静态数据 <br>
![img15](../picture/dashboard/img_15.png) <br>

### 数据格式

可参考静态数据，目前气泡地图仅能显示“省”级别，注意动态数据的名称要和地图底层数据名称保持一致，即完整的省名，可以看静态数据，如果和你提供的动态数据名称有差异，请参考源码自行修改 <br>

## 柱状堆叠图

动态的数据集对应字典值需要选择一个“X轴”、“Y轴”、“柱状”，也就是说需要3个字段，不明白可以看看静态数据 <br>
![img.17](../picture/dashboard/img_17.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img4](../picture/charts/img_3.png) <br>

## 折线堆叠图

动态的数据集对应字典值需要选择一个“X轴”、“Y轴”、“折线”，也就是说需要3个字段，不明白可以看看静态数据 <br>
![img.17](../picture/dashboard/img_17.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

和柱状堆叠图数据格式保持一致。<br>

## 柱状对比图

数据集需要3个字段，其中一个作为对比的字段只能为2种值，只有2种值作为对比的字段要选择“y轴字段”字典。因为底层的解析用的是堆叠图的解析，这里的y轴字段并不是指的图表上面的y轴，还请注意，有强迫症可以自行修改源码的解析，剩下的2个字段对应字典看图<br>
![img18](../picture/dashboard/img_18.png) <br>
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

![img11](../picture/charts/img_11.png) <br>

## 折线对比图

数据集需要3个字段，其中一个作为对比的字段只能为2种值，只有2种值作为对比的字段要选择“y轴字段”字典，剩下的字典对应看图<br>
![img19](../picture/dashboard/img_19.png) <br>
**注**：如果提示语设置选择“十字形”，请注意需要选择 “X轴颜色、上Y轴颜色、下Y轴颜色”，不然预览图表鼠标选择是全白色，还请注意。<br>
![img20](../picture/dashboard/img_20.png)
**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

### 数据格式

和柱状对比图数据格式一致。<br>

## 装饰饼图

装饰用，可单独使用或者配合文本框等图表组件来使整个大屏立体、丰满。<br>
![img12](../picture/charts/img_12.png) <br>

## 词云图

最大最小角度都为0时则文字显示为正。所有词云颜色皆是随机产生，动态数据每请求一次数据，所有词云颜色皆改变一次。<br>
![img20](../picture/charts/img_20.png) <br>

### 数据格式

和饼图、南丁格尔玫瑰图数据保持一致。<br>

## 热力图

热力图是类似坐标轴一样的数据，当前的热力图数据集需要3个字段值，对应的字典是选择“X轴”，“Y轴”，“数值”，不明白可以看静态数据<br>
![img21](../picture/charts/img_21.png) <br>

注意：图设置功能中的最大最小值将会对热力图中的数值产生反应，主要根据设定的颜色来反应，数值越靠近最大值，颜色将更深 <br>
![img22](../picture/charts/img_22.png) <br>

### 数据格式

![img23](../picture/charts/img_23.png) <br>

**如有问题，请提交 [Issue](https://gitee.com/anji-plus/report/issues) <br>**

## 雷达图

基本操作和表格是一致，通过配置栏的新增按钮和操作按钮对雷达顶点数量进行修改，可生成5边雷达，6边雷达等等，key值和雷达顶点保持一致，从最上端顶点逆时针规划其他顶点。<br>
![img26](../picture/charts/img_26.png) <br>

### 数据格式

数据格式和柱线、多柱线都是类似的，注意实际作为name的字段，比如时间字段，有且只能有1个，数据集选择时选择名称，其余是顶点字段，不明白可以看静态数据格式。<br>
![img27](../picture/charts/img_27.png) <br>
![img28](../picture/charts/img_28.png) <br>


## 柱线堆叠图

图表的基本操作和柱状堆叠图、折线堆叠并无大区别，需要在意的是数据格式的区别，绑定动态数据的时候，需要你数据集有4个字段，其中俩个字段作为x，y区分字段，另外俩个作为数值字段。<br>
![img29](../picture/charts/img_29.png) <br>
![img30](../picture/charts/img_30.png) <br>

### 数据格式

和另外俩个堆叠图相比，多了查询的一列数据，比如 "select a,b,sum(c),sum(d) from aa group by a,b"
![img31](../picture/charts/img_31.png) <br>
