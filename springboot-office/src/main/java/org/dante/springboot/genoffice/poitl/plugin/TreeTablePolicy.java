package org.dante.springboot.genoffice.poitl.plugin;

import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.dante.springboot.genoffice.vo.MergeVO;
import org.dante.springboot.genoffice.vo.NormColVO;

import com.deepoove.poi.data.Rows;
import com.deepoove.poi.policy.DynamicTableRenderPolicy;
import com.deepoove.poi.policy.TableRenderPolicy;
import com.deepoove.poi.util.TableTools;
import com.google.common.collect.Maps;

/**
 * 参考： 
 * http://deepoove.com/poi-tl/#plugin-dynamic-table
 * https://blog.csdn.net/qq_45731464/article/details/119247125
 * 
 * 不支持：http://javax.xml.XMLConstants/property/accessExternalStylesheet
 * https://blog.csdn.net/csdn_avatar_2019/article/details/123791224?spm=1001.2101.3001.6661.1&utm_medium=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123791224-blog-121074155.pc_relevant_3mothn_strategy_recovery&depth_1-utm_source=distribute.pc_relevant_t0.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-1-123791224-blog-121074155.pc_relevant_3mothn_strategy_recovery&utm_relevant_index=1
 * 
 * @author dante
 *
 */
public class TreeTablePolicy extends DynamicTableRenderPolicy {

	@Override
	@SuppressWarnings("unchecked")
	public void render(XWPFTable table, Object data) throws Exception {
		if (null == data) return;
		List<NormColVO> cols = (List<NormColVO>) data;
		// 行数
		int colCount = 5;
		
		Map<String, MergeVO> mergeMap = Maps.newLinkedHashMap();
		// 循环插入行
		for (int i = 0; i < cols.size(); i++) {
			NormColVO col = cols.get(i);
			XWPFTableRow insertNewTableRow = table.insertNewTableRow(i + 2);
			this.calMerge(col, i, mergeMap);
			for (int j = 0; j < colCount; j++)  {
				insertNewTableRow.createCell();
			}
			TableRenderPolicy.Helper.renderRow(table.getRow(i + 2), Rows.of(col.getTitle(), col.getLevel1(), col.getLevel2(), col.getLevel3(), col.getValue()).center().create());
		}
		table.removeRow(0);
		
		// 合并单元格
		mergeMap.forEach((k, v) -> {
			TableTools.mergeCellsVertically(table, v.getCol(), v.getFrowRow(), v.getToRow());
		});
		
	}
	
	/**
	 * 计算单元格合并
	 * 
	 * @param normCol
	 * @param col
	 * @param mergeMap
	 */
	private void calMerge(NormColVO normCol, int start, Map<String, MergeVO> mergeMap) {
		if(mergeMap.containsKey(normCol.getTitle())) {
			MergeVO mv = mergeMap.get(normCol.getTitle());
			if(mv != null) {
				mv.setToRow(mv.getToRow() + 1);
			}
		} else {
			MergeVO merge = new MergeVO();
			merge.setCol(0);
			merge.setFrowRow(start);
			merge.setToRow(merge.getFrowRow() + 1);
			mergeMap.put(normCol.getTitle(), merge);
		}
		
		if(mergeMap.containsKey(normCol.getLevel1())) {
			MergeVO mv = mergeMap.get(normCol.getLevel1());
			if(mv != null) {
				mv.setToRow(mv.getToRow() + 1);
			}
		} else {
			MergeVO merge = new MergeVO();
			merge.setCol(1);
			merge.setFrowRow(start + 1);
			merge.setToRow(merge.getFrowRow());
			mergeMap.put(normCol.getLevel1(), merge);
		}
		
		if(mergeMap.containsKey(normCol.getLevel2())) {
			MergeVO mv = mergeMap.get(normCol.getLevel2());
			if(mv != null) {
				mv.setToRow(mv.getToRow() + 1);
			}
		} else {
			MergeVO merge = new MergeVO();
			merge.setCol(2);
			merge.setFrowRow(start + 1);
			merge.setToRow(merge.getFrowRow());
			mergeMap.put(normCol.getLevel2(), merge);
		}
	}
	
	

}
