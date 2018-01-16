package com.jiakaiyang.library.easyform.core;

import android.view.View;

/**
 * Created by jia on 2018/1/8.
 * 单元格
 */

public class EFCell {
    private static final String TAG = "EFCell";

    private View realCellView;
    private EFNode ltNode, trNode, rbNode, blNode;

    // cell在表格中的坐标，该坐标只是交点在表格中的位置
    private int indexX;
    private int indexY;


    public EFCell(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    /**
     * 设置cell的周边node
     *
     * @param formNodes 表格的节点数组
     */
    public void setNodes(EFNode[][] formNodes) {
        setLtNode(formNodes[indexX][indexY]);
        setTrNode(formNodes[indexX + 1][indexY]);
        setRbNode(formNodes[indexX + 1][indexY + 1]);
        setBlNode(formNodes[indexX][indexY + 1]);
    }


    /**
     * 获取测量的位置
     *
     * @param position
     */
    public void getLayoutPosition(int[] position, int[] originPoint, int cellWidth, int cellHeight) {
        if (position == null
                || position.length < 4) {
            throw new IllegalArgumentException("position should not be null and length should at least 4");
        }

        int originX = originPoint[0];
        int originY = originPoint[1];

        int l = indexX * cellWidth + originX;
        int t = indexY * cellHeight + originY;

        int r = l + cellWidth;
        int b = t + cellHeight;

        position[0] = l;
        position[1] = t;
        position[2] = r;
        position[3] = b;
    }

    /**
     * 对该cell进行布局，会间接调用View的layout
     *
     * @param originPoint 表格左上角的点相对于父View的坐标
     */
    public void layout(int[] originPoint, int cellWidth, int cellHeight) {
        int[] position = new int[4];
        getLayoutPosition(position, originPoint, cellWidth, cellHeight);

        realCellView.layout(position[0], position[1], position[2], position[3]);
    }


    /**
     * 对该cell进行测量，会间接调用View的measure
     */
    public void measure() {

    }

    public View getRealCellView() {
        return realCellView;
    }

    public void setRealCellView(View realCellView) {
        this.realCellView = realCellView;
    }

    public EFNode getLtNode() {
        return ltNode;
    }

    public void setLtNode(EFNode ltNode) {
        this.ltNode = ltNode;
    }

    public EFNode getTrNode() {
        return trNode;
    }

    public void setTrNode(EFNode trNode) {
        this.trNode = trNode;
    }

    public EFNode getRbNode() {
        return rbNode;
    }

    public void setRbNode(EFNode rbNode) {
        this.rbNode = rbNode;
    }

    public EFNode getBlNode() {
        return blNode;
    }

    public void setBlNode(EFNode blNode) {
        this.blNode = blNode;
    }
}
