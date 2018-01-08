package com.jiakaiyang.library.easyform.core;

import java.util.List;
import java.util.Set;

/**
 * Created by jia on 2018/1/8.
 * 自定义的适配器，用于给表格填充子View
 */

public interface EFFormController {


    /**
     * 合并单元格，由使用者设置需要被合并的单元格，单元格
     *
     * @param cells       最小单元格的集合
     * @param columnCount 列数
     * @param rowCount    行数
     * @return 需要被合并的最小单元格，Set中的每一个List列表中的所以最小单元格会被合并，如果一个List中的
     * 最小单元格不在同一行或者同一列，这个列表将会被忽略
     */
    public abstract Set<List<EFCell>> onMergeCell(EFCell[][] cells, int columnCount, int rowCount);
}
