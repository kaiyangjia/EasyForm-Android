package com.jiakaiyang.library.easyform.core;

/**
 * Created by jia on 2018/1/5.
 * EFNode represent one node for EFForm
 * <p>
 * 交点
 */

public class EFNode {
    private static final String TAG = "EFNode";

    public static final int DIRECTION_LEFT = 1;
    public static final int DIRECTION_TOP = 2;
    public static final int DIRECTION_RIGHT = 3;
    public static final int DIRECTION_DOWM = 4;

    // 四元组，表示(left, top. right, down)方法的分割线是否需要被画出来
    private boolean shouldDrawLeft = true;
    private boolean shouldDrawRight = true;
    private boolean shouldDrawTop = true;
    private boolean shouldDrawDown = true;


    // 点在表格中的坐标，该坐标只是交点在表格中的位置
    private int indexX;
    private int indexY;

    public EFNode() {
    }

    public EFNode(int indexX, int indexY) {
        this.indexX = indexX;
        this.indexY = indexY;
    }

    public EFNode(int indexX, int indexY, int columnCount, int rowCount) {
        this.indexX = indexX;
        this.indexY = indexY;

        if (indexX == 0) {
            shouldDrawLeft = false;
        }

        if (indexX == columnCount - 1) {
            shouldDrawRight = false;
        }

        if (indexY == 0) {
            shouldDrawTop = false;
        }

        if (indexY == rowCount - 1) {
            shouldDrawDown = false;
        }
    }

    /**
     * 计算一个点在某个方向上的表格线的坐标
     *
     * @param targetNode 交点
     * @param direction  方向
     * @param cellWidth  单元格宽度
     * @param cellHeight 单元格高度
     * @return 表示一条线的坐标[x, y]
     */
    public static float[] calculateDivider(EFNode targetNode
            , int direction
            , int cellWidth
            , int cellHeight) {
        EFNode nextNode = new EFNode();

        switch (direction) {
            case DIRECTION_LEFT:
                nextNode.setIndexX(targetNode.getIndexX() - 1);
                nextNode.setIndexY(targetNode.getIndexY());
                break;
            case DIRECTION_TOP:
                nextNode.setIndexX(targetNode.getIndexX());
                nextNode.setIndexY(targetNode.getIndexY() - 1);
                break;
            case DIRECTION_RIGHT:
                nextNode.setIndexX(targetNode.getIndexX() + 1);
                nextNode.setIndexY(targetNode.getIndexY());
                break;
            case DIRECTION_DOWM:
                nextNode.setIndexX(targetNode.getIndexX());
                nextNode.setIndexY(targetNode.getIndexY() + 1);
                break;
            default:
                throw new IllegalArgumentException("方向不存在");
        }

        return lineBetweenNodes(targetNode, nextNode, cellWidth, cellHeight);
    }


    public static float[] calculateDivider(EFNode targetNode
            , float[] originPoint
            , int direction
            , int cellWidth
            , int cellHeight) {
        if (originPoint == null
                || originPoint.length < 2) {
            throw new IllegalArgumentException("参数 originPoint 错误, originPoint的长度最少为2");
        }

        float[] result = calculateDivider(targetNode, direction, cellWidth, cellHeight);
        result[0] += originPoint[0];
        result[1] += originPoint[1];
        result[2] += originPoint[0];
        result[3] += originPoint[1];
        return result;
    }

    /**
     * 计算两个交点之间的线的坐标，坐标原点是相对于表格的左上角。
     *
     * @param nodeA
     * @param nodeB
     * @param cellWidth
     * @param cellHeight
     * @return
     */
    public static float[] lineBetweenNodes(EFNode nodeA
            , EFNode nodeB
            , int cellWidth
            , int cellHeight) {

        if (nodeA == null
                || nodeB == null) {
            throw new IllegalArgumentException("交点不能为空");
        }

        /*boolean sameRow = nodeA.getStartX() == nodeB.getStartY();
        boolean sameColumn = nodeA.getStartY() == nodeB.getStartY();

        if (!(sameRow || sameColumn)) {
            throw new IllegalArgumentException("交点必须在同一行或者同一列");
        }*/

        float[] nodeAValue = new float[2];
        float[] nodeBValue = new float[2];

        calculateCoordinateByIndex(nodeA.getIndexX()
                , nodeA.getIndexY()
                , cellWidth
                , cellHeight
                , nodeAValue);

        calculateCoordinateByIndex(nodeB.getIndexX()
                , nodeB.getIndexY()
                , cellWidth
                , cellHeight
                , nodeBValue);

        float[] lineValue = new float[4];
        lineValue[0] = nodeAValue[0];
        lineValue[1] = nodeAValue[1];
        lineValue[2] = nodeBValue[0];
        lineValue[3] = nodeBValue[1];
        return lineValue;
    }


    /**
     * 根据交点的位置计算它的坐标值
     *
     * @param indexX
     * @param indexY
     * @param cellWidth
     * @param cellHeight
     * @param returnValue
     */
    public static void calculateCoordinateByIndex(
            int indexX
            , int indexY
            , int cellWidth
            , int cellHeight
            , float[] returnValue) {
        if (returnValue == null
                || returnValue.length < 2) {
            throw new IllegalArgumentException("参数 returnValue 错误");
        }

        returnValue[0] = indexX * cellWidth;
        returnValue[1] = indexY * cellHeight;
    }


    public static void calculateCoordinateByIndex(
            float[] originPoint
            , int indexX
            , int indexY
            , int cellWidth
            , int cellHeight
            , float[] returnValue) {
        calculateCoordinateByIndex(indexX, indexY, cellWidth, cellHeight, returnValue);
        returnValue[0] += originPoint[0];
        returnValue[1] += originPoint[1];
    }

    @Override
    public String toString() {
        return "EFNode[shouldDrawLeft: " + shouldDrawLeft
                + "][shouldDrawTop:" + shouldDrawTop
                + "][shouldDrawRight:" + shouldDrawRight
                + "][shouldDrawDown:" + shouldDrawDown + "]";
    }

    public int getIndexX() {
        return indexX;
    }

    public void setIndexX(int indexX) {
        this.indexX = indexX;
    }

    public int getIndexY() {
        return indexY;
    }

    public void setIndexY(int indexY) {
        this.indexY = indexY;
    }

    public boolean isShouldDrawLeft() {
        return shouldDrawLeft;
    }

    public void setShouldDrawLeft(boolean shouldDrawLeft) {
        this.shouldDrawLeft = shouldDrawLeft;
    }

    public boolean isShouldDrawRight() {
        return shouldDrawRight;
    }

    public void setShouldDrawRight(boolean shouldDrawRight) {
        this.shouldDrawRight = shouldDrawRight;
    }

    public boolean isShouldDrawTop() {
        return shouldDrawTop;
    }

    public void setShouldDrawTop(boolean shouldDrawTop) {
        this.shouldDrawTop = shouldDrawTop;
    }

    public boolean isShouldDrawDown() {
        return shouldDrawDown;
    }

    public void setShouldDrawDown(boolean shouldDrawDown) {
        this.shouldDrawDown = shouldDrawDown;
    }
}
