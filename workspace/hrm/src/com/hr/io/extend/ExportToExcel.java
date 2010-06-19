package com.hr.io.extend;

import com.hr.io.domain.OutmatchModel;
import com.hr.util.comparator.ListComparator;
import com.hr.util.excel.ExcelFormat;
import com.hr.util.excel.IExportToExcel;
import com.hr.util.output.FieldOperate;
import com.hr.util.output.StatisticBgColor;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportToExcel extends IExportToExcel {
    public static final String StatisticAllName = "全部";
    private static int groupLayerInTitle = 1;
    private String[][] outputList;
    private List<FieldOperate> fieldList;
    private List<List<List<String>>> statisticList;
    private List<List<Integer>> groupPlaceList;
    private OutmatchModel outmatchModel;
    private int nColNum = 0;

    private int nRowNum = 0;

    private int nGroupFieldNum = 0;

    private int nStatisticNum = 0;
    private WritableCellFormat[][] statisticCellFormatArr;
    private Map<Integer, WritableCellFormat> numberCellFormatMap = new HashMap();
    private WritableCellFormat[] colCellFormatArr;
    private Map<Color, Colour> colorMap = new HashMap();

    private static Colour[] jxlColours = Colour.getAllColours();

    private int colourPointer = 10;

    public ExportToExcel() throws Exception {
    }

    public ExportToExcel(String filePathName, String[][] outputList, List<FieldOperate> fieldList,
            List<List<List<String>>> statisticList, List<List<Integer>> groupPlaceList,
            OutmatchModel outmatchModel) throws Exception {
        super(filePathName);
        setOutputList(outputList);
        setFieldList(fieldList);
        setStatisticList(statisticList);
        setGroupPlaceList(groupPlaceList);
        setOutmatchModel(outmatchModel);
    }

    public void buildContent() throws Exception {
        this.nColNum = this.fieldList.size();
        this.nRowNum = this.outputList.length;
        this.nGroupFieldNum = (this.groupPlaceList.size() - 1);

        List groupFieldPlaceList = new ArrayList();

        List statisticFieldPlaceList = new ArrayList();
        for (int i = 0; i < this.nColNum; ++i) {
            if (((FieldOperate) this.fieldList.get(i)).getStatisticMethod() != null) {
                statisticFieldPlaceList.add(Integer.valueOf(i));
            }
            if (Math.abs(((FieldOperate) this.fieldList.get(i)).getGroupOrder()) == 2) {
                groupFieldPlaceList.add(Integer.valueOf(i));
            }
        }
        this.nStatisticNum = statisticFieldPlaceList.size();

        int nAddSheetNum = 0;

        setCellFormats();
        int nGroupAllNum = 0;
        for (int i = 0; i < this.nGroupFieldNum + 1; ++i) {
            nGroupAllNum += ((List) this.groupPlaceList.get(i)).size() - 1;
        }

        List statisticAllList = new ArrayList();

        if (this.nGroupFieldNum > 0) {
            int[] nStatisticPlace = new int[this.nGroupFieldNum + 1];
            for (int i = 0; i < this.nGroupFieldNum + 1; ++i) {
                nStatisticPlace[i] = 1;
            }
            for (int i = 1; i < ((List) this.groupPlaceList.get(0)).size(); ++i) {
                int placeTmp1 = ((Integer) ((List) this.groupPlaceList.get(0)).get(i)).intValue();
                for (int j = 0; j < this.nGroupFieldNum + 1; ++j) {
                    int placeTmp = ((Integer) ((List) this.groupPlaceList.get(j))
                            .get(nStatisticPlace[j])).intValue();
                    if (placeTmp != placeTmp1)
                        break;
                    List listTmp = new ArrayList();
                    int placePreTmp = ((Integer) ((List) this.groupPlaceList.get(j))
                            .get(nStatisticPlace[j] - 1)).intValue();
                    int numTmp = placeTmp - placePreTmp;
                    listTmp.add(Integer.valueOf(this.nGroupFieldNum - j));
                    listTmp.add(Integer.valueOf(placeTmp));
                    listTmp.add(Integer.valueOf(numTmp));
                    for (int k = 0; k < this.nGroupFieldNum - j; ++k) {
                        listTmp.add(this.outputList[placePreTmp][((Integer) groupFieldPlaceList
                                .get(k)).intValue()]);
                    }
                    if (j > 0) {
                        listTmp.add("全部");
                        for (int k = j - 1; k > 0; --k) {
                            listTmp.add("");
                        }
                    }
                    for (int k = 0; k < this.nColNum; ++k) {
                        listTmp.add(((List) ((List) this.statisticList.get(k)).get(j))
                                .get(nStatisticPlace[j] - 1));
                    }
                    statisticAllList.add(listTmp);
                    nStatisticPlace[j] += 1;
                }
            }

        } else {
            List listTmp = new ArrayList();
            listTmp.add(Integer.valueOf(0));
            listTmp.add(Integer.valueOf(this.nRowNum));
            listTmp.add(Integer.valueOf(this.nRowNum));
            for (int i = 0; i < this.nColNum; ++i) {
                listTmp.add(((List) ((List) this.statisticList.get(i)).get(this.nGroupFieldNum))
                        .get(0));
            }
            statisticAllList.add(listTmp);
        }

        int existSheetNum = this.writableWorkbook.getNumberOfSheets();

        if (this.outmatchModel.getOmmStatisticPlace().intValue() < 3) {
            WritableSheet ws1 = createSheet(this.sheetDefaultName + existSheetNum, existSheetNum
                    + nAddSheetNum++, this.writableWorkbook);

            writeSheet(0, this.nRowNum, ws1, this.outmatchModel.getOmmNoTitle().intValue());

            if ((this.statisticList != null)
                    && (this.outmatchModel.getOmmStatisticPlace().intValue() == 1)
                    && (this.nStatisticNum > 0)) {
                int rowsDiff = ws1.getRows() - this.nRowNum;
                for (int i = nGroupAllNum - 1; i >= 0; --i) {
                    int insertPlace = ((Integer) ((List) statisticAllList.get(i)).get(1))
                            .intValue()
                            + rowsDiff;
                    ws1.insertRow(insertPlace);

                    for (int j = 0; j < this.nColNum; ++j) {
                        writeStatisticCell(
                                           j,
                                           insertPlace,
                                           (String) ((List) statisticAllList.get(i)).get(j + 3
                                                   + this.nGroupFieldNum),
                                           ((FieldOperate) this.fieldList.get(j))
                                                   .getStatisticDataType(),
                                           ws1,
                                           this.statisticCellFormatArr[j][((Integer) ((List) statisticAllList
                                                   .get(i)).get(0)).intValue()]);
                    }

                    for (int j = 0; j < ((Integer) ((List) statisticAllList.get(i)).get(0))
                            .intValue(); ++j) {
                        ws1.addCell(new Label(((Integer) groupFieldPlaceList.get(j)).intValue(),
                                insertPlace, (String) ((List) statisticAllList.get(i)).get(3 + j),
                                this.statisticCellFormatArr[((Integer) groupFieldPlaceList.get(j))
                                        .intValue()][((Integer) ((List) statisticAllList.get(i))
                                        .get(0)).intValue()]));
                    }
                }
            }

            setRowHeight(ws1);
        }

        if (this.outmatchModel.getOmmStatisticPlace().intValue() >= 2) {
            WritableSheet ws2 = createSheet(this.statisticSheetDefaultName + existSheetNum,
                                            existSheetNum + nAddSheetNum++, this.writableWorkbook);

            if (this.outmatchModel.getOmmStatisticDisplayType().intValue() == 0) {
                int colPlaceTmp = 0;
                for (int i = 0; i < this.nGroupFieldNum; ++i) {
                    ws2.setColumnView(colPlaceTmp, ((FieldOperate) this.fieldList
                            .get(((Integer) groupFieldPlaceList.get(i)).intValue())).getColWidth());
                    ws2.addCell(new Label(colPlaceTmp, 0, ((FieldOperate) this.fieldList
                            .get(((Integer) groupFieldPlaceList.get(i)).intValue()))
                            .getNormalName(), this.format.getTitleCellFormat()));
                    for (int j = 0; j < nGroupAllNum; ++j) {
                        ws2.addCell(new Label(colPlaceTmp, j + 1, (String) ((List) statisticAllList
                                .get(j)).get(i + 3), this.format.getTitleCellFormat()));
                    }
                    ++colPlaceTmp;
                }

                boolean hasStatistic = false;
                for (int i = 0; i < this.nColNum; ++i) {
                    if (((FieldOperate) this.fieldList.get(i)).getStatisticMethod() != null) {
                        hasStatistic = true;
                        ws2.setColumnView(colPlaceTmp, ((FieldOperate) this.fieldList.get(i))
                                .getColWidth());
                        ws2.addCell(new Label(colPlaceTmp, 0,
                                ((FieldOperate) this.fieldList.get(i)).getStatisitcName(),
                                this.format.getTitleCellFormat()));
                        for (int j = 0; j < nGroupAllNum; ++j) {
                            writeStatisticCell(
                                               colPlaceTmp,
                                               j + 1,
                                               (String) ((List) statisticAllList.get(j)).get(i + 3
                                                       + this.nGroupFieldNum),
                                               ((FieldOperate) this.fieldList.get(i))
                                                       .getStatisticDataType(),
                                               ws2,
                                               this.statisticCellFormatArr[i][((Integer) ((List) statisticAllList
                                                       .get(j)).get(0)).intValue()]);
                        }

                        ++colPlaceTmp;
                    }
                }

                if (!hasStatistic) {
                    ws2.setColumnView(colPlaceTmp, 10);
                    ws2.addCell(new Label(colPlaceTmp, 0, "计数", this.format.getTitleCellFormat()));
                    for (int i = 0; i < nGroupAllNum; ++i) {
                        ws2.addCell(new Number(colPlaceTmp, i + 1,
                                ((Integer) ((List) statisticAllList.get(i)).get(2)).intValue(),
                                this.format.getBodyCellFormat()));
                    }
                    ++colPlaceTmp;
                }
            } else if (this.nGroupFieldNum > groupLayerInTitle) {
                int rowGroupNum = ((List) this.groupPlaceList.get(this.nGroupFieldNum - 1)).size();
                List sampleList = new ArrayList();
                for (int i = 0; i < ((List) this.groupPlaceList.get(0)).size() - 1; ++i) {
                    List listTmp = new ArrayList();
                    listTmp.add(Integer.valueOf(this.nGroupFieldNum));
                    for (int j = groupLayerInTitle; j < this.nGroupFieldNum; ++j) {
                        listTmp.add(this.outputList[((Integer) ((List) this.groupPlaceList.get(0))
                                .get(i)).intValue()][((Integer) groupFieldPlaceList.get(j))
                                .intValue()]);
                    }
                    sampleList.add(listTmp);
                }
                int[][] orderArr = new int[this.nGroupFieldNum - 1][2];
                for (int i = 1; i < this.nGroupFieldNum - groupLayerInTitle + 1; ++i) {
                    orderArr[(i - 1)][0] = i;
                    orderArr[(i - 1)][1] = ((FieldOperate) this.fieldList
                            .get(((Integer) groupFieldPlaceList.get(i - 1 + groupLayerInTitle))
                                    .intValue())).getGroupOrder();
                }
                ListComparator listComparator = new ListComparator(orderArr, new int[0]);
                Collections.sort(sampleList, listComparator);
                for (int i = sampleList.size() - 1; i > 0; --i) {
                    List listTmp1 = (List) sampleList.get(i - 1);
                    List listTmp2 = (List) sampleList.get(i);
                    boolean deleteSample = true;
                    for (int j = 1; j < this.nGroupFieldNum - groupLayerInTitle; ++j) {
                        if (((String) listTmp1.get(j)).compareTo((String) listTmp2.get(j)) != 0) {
                            for (int k = j; k < this.nGroupFieldNum - groupLayerInTitle; ++k) {
                                List listAddTmp = new ArrayList();
                                listAddTmp.add(Integer.valueOf(k + groupLayerInTitle));
                                for (int l = 1; l < k + 1; ++l) {
                                    listAddTmp.add(listTmp1.get(l));
                                }
                                listAddTmp.add("全部");
                                for (int l = k + 2; l < this.nGroupFieldNum - groupLayerInTitle + 1; ++l) {
                                    listAddTmp.add("");
                                }
                                sampleList.add(i, listAddTmp);
                            }
                            deleteSample = false;
                            break;
                        }
                    }
                    if ((deleteSample)
                            && (((String) listTmp1.get(this.nGroupFieldNum - groupLayerInTitle))
                                    .compareTo((String) listTmp2.get(this.nGroupFieldNum
                                            - groupLayerInTitle)) == 0)) {
                        sampleList.remove(i);
                    }
                }
                int nRowGroupNum = sampleList.size();
                for (int i = this.nGroupFieldNum - groupLayerInTitle - 1; i >= 0; --i) {
                    List listTmp = (List) sampleList.get(nRowGroupNum - 1);
                    List listAddTmp = new ArrayList();
                    listAddTmp.add(Integer.valueOf(i + groupLayerInTitle));
                    for (int j = 0; j < i; ++j) {
                        listAddTmp.add(listTmp.get(j + 1));
                    }
                    listAddTmp.add("全部");
                    for (int j = i + 2; j < this.nGroupFieldNum - groupLayerInTitle + 1; ++j) {
                        listAddTmp.add("");
                    }
                    sampleList.add(listAddTmp);
                }
                nRowGroupNum = sampleList.size();
                List statistic3dList = new ArrayList();
                List statistic3dTitleList = new ArrayList();
                int indexBgnTmp = 0;
                List statisticNullList = new ArrayList();
                for (int i = 0; i < this.nColNum; ++i) {
                    statisticNullList.add("");
                }
                for (int i = 0; i < nGroupAllNum - 1; ++i) {
                    int groupLayerTmp = ((Integer) ((List) statisticAllList.get(i)).get(0))
                            .intValue();
                    if (groupLayerTmp == groupLayerInTitle) {
                        List listTmp = new ArrayList();
                        for (int j = indexBgnTmp; j < i + 1; ++j) {
                            listTmp.add(statisticAllList.get(j));
                        }
                        List listTitleTmp = new ArrayList();
                        for (int j = 3; j < 3 + groupLayerInTitle; ++j) {
                            listTitleTmp.add((String) ((List) statisticAllList.get(i)).get(j));
                        }
                        for (int j = 0; j < nRowGroupNum; ++j) {
                            if (j > listTmp.size() - 1) {
                                for (int k = j; k < nRowGroupNum; ++k) {
                                    List listAddTmp1 = new ArrayList();
                                    listAddTmp1.add(((List) sampleList.get(k)).get(0));
                                    listAddTmp1.add(Integer.valueOf(0));
                                    listAddTmp1.add(Integer.valueOf(0));
                                    listAddTmp1.addAll(listTitleTmp);
                                    listAddTmp1
                                            .addAll(((List) sampleList.get(k))
                                                    .subList(1, this.nGroupFieldNum
                                                            - groupLayerInTitle + 1));
                                    listAddTmp1.addAll(statisticNullList);
                                    listTmp.add(listAddTmp1);
                                }
                                break;
                            }
                            boolean addNull = false;
                            if ((Integer) ((List) sampleList.get(j)).get(0) != (Integer) ((List) listTmp
                                    .get(j)).get(0)) {
                                addNull = true;
                            }
                            for (int k = 0; (k < this.nGroupFieldNum - groupLayerInTitle)
                                    && (!addNull); ++k) {
                                if (((String) ((List) sampleList.get(j)).get(k + 1))
                                        .compareTo((String) ((List) listTmp.get(j)).get(k + 3
                                                + groupLayerInTitle)) != 0) {
                                    addNull = true;
                                }
                            }
                            if (addNull) {
                                List listAddTmp1 = new ArrayList();
                                listAddTmp1.add(((List) sampleList.get(j)).get(0));
                                listAddTmp1.add(Integer.valueOf(0));
                                listAddTmp1.add(Integer.valueOf(0));
                                listAddTmp1.addAll(listTitleTmp);
                                listAddTmp1.addAll(((List) sampleList.get(j))
                                        .subList(1, this.nGroupFieldNum - groupLayerInTitle + 1));
                                listAddTmp1.addAll(statisticNullList);
                                listTmp.add(j, listAddTmp1);
                            }
                        }

                        statistic3dList.add(listTmp);
                        statistic3dTitleList.add(listTitleTmp);
                        indexBgnTmp = i + 1;
                    } else if (groupLayerTmp < groupLayerInTitle) {
                        indexBgnTmp = i + 1;
                    }
                }
                int nColGroupNum = statistic3dTitleList.size();
                for (int i = 0; i < nColGroupNum; ++i)
                    ;
                for (int i = 0; i < this.nGroupFieldNum - groupLayerInTitle; ++i) {
                    ws2.setColumnView(i, ((FieldOperate) this.fieldList
                            .get(((Integer) groupFieldPlaceList.get(i + groupLayerInTitle))
                                    .intValue())).getColWidth());
                    ws2.addCell(new Label(i, groupLayerInTitle, ((FieldOperate) this.fieldList
                            .get(((Integer) groupFieldPlaceList.get(i + groupLayerInTitle))
                                    .intValue())).getStatisitcName(), this.format
                            .getTitleCellFormat()));
                    for (int j = 0; j < nRowGroupNum; ++j) {
                        ws2.addCell(new Label(i, j + 1 + groupLayerInTitle,
                                (String) ((List) ((List) statistic3dList.get(0)).get(j)).get(i + 3
                                        + groupLayerInTitle), this.format.getTitleCellFormat()));
                    }
                }

                for (int i = 0; i < nColGroupNum; ++i) {
                    int colPlaceTmp = this.nGroupFieldNum - groupLayerInTitle + i
                            * this.nStatisticNum;
                    for (int j = 0; j < groupLayerInTitle; ++j) {
                        ws2.addCell(new Label(colPlaceTmp, j, (String) ((List) statistic3dTitleList
                                .get(i)).get(j), this.format.getTitleCellFormat()));
                        ws2.mergeCells(colPlaceTmp, j, colPlaceTmp + this.nStatisticNum - 1, j);
                    }
                    for (int j = 0; j < this.nStatisticNum; ++j) {
                        ws2.setColumnView(colPlaceTmp, ((FieldOperate) this.fieldList
                                .get(((Integer) statisticFieldPlaceList.get(j)).intValue()))
                                .getColWidth());
                        ws2
                                .addCell(new Label(colPlaceTmp, groupLayerInTitle,
                                        ((FieldOperate) this.fieldList
                                                .get(((Integer) statisticFieldPlaceList.get(j))
                                                        .intValue())).getStatisitcName(),
                                        this.format.getTitleCellFormat()));
                        for (int k = 0; k < nRowGroupNum; ++k) {
                            writeStatisticCell(
                                               colPlaceTmp,
                                               k + 1 + groupLayerInTitle,
                                               (String) ((List) ((List) statistic3dList.get(i))
                                                       .get(k))
                                                       .get(((Integer) statisticFieldPlaceList
                                                               .get(j)).intValue()
                                                               + 3 + this.nGroupFieldNum),
                                               ((FieldOperate) this.fieldList
                                                       .get(((Integer) statisticFieldPlaceList
                                                               .get(j)).intValue()))
                                                       .getStatisticDataType(),
                                               ws2,
                                               this.statisticCellFormatArr[((Integer) statisticFieldPlaceList
                                                       .get(j)).intValue()][((Integer) ((List) ((List) statistic3dList
                                                       .get(i)).get(k)).get(0)).intValue()]);
                        }

                        ++colPlaceTmp;
                    }
                }
            }
            setRowHeight(ws2);
        }

        if ((this.outmatchModel.getOmmStatisticPlace().intValue() == 3)
                && (this.nGroupFieldNum > 0)) {
            List topGroupList = (List) this.groupPlaceList.get(this.nGroupFieldNum - 1);
            for (int i = 1; i < topGroupList.size(); ++i) {
                WritableSheet wsN = createSheet(this.outputList[((Integer) topGroupList.get(i - 1))
                        .intValue()][((Integer) groupFieldPlaceList.get(0)).intValue()],
                                                existSheetNum + nAddSheetNum++,
                                                this.writableWorkbook);

                writeSheet(((Integer) topGroupList.get(i - 1)).intValue(), ((Integer) topGroupList
                        .get(i)).intValue(), wsN, this.outmatchModel.getOmmNoTitle().intValue());
                int insertRowNum = wsN.getRows();
                if (this.nStatisticNum > 0) {
                    for (int j = 0; j < this.nColNum; ++j) {
                        writeStatisticCell(j, insertRowNum,
                                           (String) ((List) ((List) this.statisticList.get(j))
                                                   .get(this.nGroupFieldNum - 1)).get(i - 1),
                                           ((FieldOperate) this.fieldList.get(j))
                                                   .getStatisticDataType(), wsN,
                                           this.statisticCellFormatArr[j][0]);
                    }
                }

                setRowHeight(wsN);
            }
        }

        this.writableWorkbook.write();
    }

    private void setRowHeight(WritableSheet ws) throws Exception {
        if ((this.outmatchModel.getOmmRowHeight() == null)
                || (this.outmatchModel.getOmmRowHeight().intValue() <= 0)) {
            return;
        }

        int rowHeight = this.outmatchModel.getOmmRowHeight().intValue() * 20;
        int rowNum = ws.getRows();
        for (int i = 0; i < rowNum; ++i)
            ws.setRowView(i, rowHeight);
    }

    private void setCellFormats() throws Exception {
        this.colCellFormatArr = new WritableCellFormat[this.nColNum];
        this.statisticCellFormatArr = new WritableCellFormat[this.nColNum][this.nGroupFieldNum + 1];
        for (int i = 0; i < this.nColNum; ++i) {
            FieldOperate foTmp = (FieldOperate) this.fieldList.get(i);
            if ((foTmp.getDataType().compareTo("decimal") == 0) && (foTmp.getFormat() != null))
                this.colCellFormatArr[i] = ExcelFormat
                        .createBodyCellFormat(new int[] { new Integer(
                                ((FieldOperate) this.fieldList.get(i)).getFormat()).intValue() });
            else {
                this.colCellFormatArr[i] = ExcelFormat.createBodyCellFormat(new int[0]);
            }
            if (((FieldOperate) this.fieldList.get(i)).getColBgColor() != null) {
                setCellBgColor(this.colCellFormatArr[i], ((FieldOperate) this.fieldList.get(i))
                        .getColBgColor());
            }
            for (int j = 0; j < this.nGroupFieldNum + 1; ++j) {
                if ((foTmp.getStatisticDataType() != null)
                        && (foTmp.getStatisticDataType().compareTo("decimal") == 0)
                        && (foTmp.getStatisticFormat() != null)) {
                    this.statisticCellFormatArr[i][j] = ExcelFormat
                            .createBodyCellFormat(new int[] { new Integer(
                                    ((FieldOperate) this.fieldList.get(i)).getStatisticFormat())
                                    .intValue() });
                } else if ((foTmp.getStatisticDataType() != null)
                        && (foTmp.getStatisticDataType().compareTo("integer") == 0))
                    this.statisticCellFormatArr[i][j] = ExcelFormat
                            .createBodyCellFormat(new int[] { 0 });
                else {
                    this.statisticCellFormatArr[i][j] = ExcelFormat
                            .createBodyCellFormat(new int[0]);
                }
                if ((j == this.nGroupFieldNum)
                        && (this.outmatchModel.getOmmStatisticPlace().intValue() > 1)) {
                    continue;
                }
                setStatisticCellBgColor(this.statisticCellFormatArr[i][j], j);
            }
        }
    }

    private void writeSheet(int indexBgn, int indexEnd, WritableSheet ws, int needTitle)
            throws Exception {
        for (int i = 0; i < this.nColNum; ++i) {
            FieldOperate foTmp = (FieldOperate) this.fieldList.get(i);
            ws.setColumnView(i, foTmp.getColWidth());
            int rowAdd = 0;
            if (needTitle == 0) {
                ws
                        .addCell(new Label(i, 0, foTmp.getNormalName(), this.format
                                .getTitleCellFormat()));
                rowAdd = 1;
            }
            if (("integer".compareTo(foTmp.getDataType()) == 0)
                    || ("decimal".compareTo(foTmp.getDataType()) == 0)) {
                for (int j = indexBgn; j < indexEnd; ++j) {
                    if (this.outputList[j][i].length() > 0)
                        ws.addCell(new Number(i, j - indexBgn + rowAdd, new Double(
                                this.outputList[j][i]).doubleValue(), this.colCellFormatArr[i]));
                    else
                        ws.addCell(new Label(i, j - indexBgn + rowAdd, this.outputList[j][i],
                                this.colCellFormatArr[i]));
                }
            } else
                for (int j = indexBgn; j < indexEnd; ++j)
                    ws.addCell(new Label(i, j - indexBgn + rowAdd, this.outputList[j][i],
                            this.colCellFormatArr[i]));
        }
    }

    private void writeStatisticCell(int col, int row, String content, String dataType,
            WritableSheet ws, WritableCellFormat wcFormat) throws Exception {
        if ((dataType != null) && (content.length() > 0)
                && ((("integer".compareTo(dataType) == 0) || ("decimal".compareTo(dataType) == 0)))) {
            ws.addCell(new Number(col, row, new Double(content).doubleValue(), wcFormat));
        } else
            ws.addCell(new Label(col, row, content, wcFormat));
    }

    private void setCellBgColor(WritableCellFormat cf, Color color) throws Exception {
        if (this.colorMap.containsKey(color)) {
            cf.setBackground((Colour) this.colorMap.get(color));
        } else {
            this.writableWorkbook.setColourRGB(jxlColours[this.colourPointer], color.getRed(),
                                               color.getGreen(), color.getBlue());
            cf.setBackground(jxlColours[this.colourPointer]);
            this.colorMap.put(color, jxlColours[this.colourPointer]);
            this.colourPointer += 1;
        }
    }

    private void setStatisticCellBgColor(WritableCellFormat cf, int layer) throws Exception {
        Color color = StatisticBgColor.getBgColor(layer);
        setCellBgColor(cf, color);
    }

    public String[][] getOutputList() {
        return this.outputList;
    }

    public List<FieldOperate> getFieldList() {
        return this.fieldList;
    }

    public List<List<List<String>>> getStatisticList() {
        return this.statisticList;
    }

    public List<List<Integer>> getGroupPlaceList() {
        return this.groupPlaceList;
    }

    public OutmatchModel getOutmatchModel() {
        return this.outmatchModel;
    }

    public void setOutputList(String[][] outputList) {
        this.outputList = outputList;
    }

    public void setFieldList(List<FieldOperate> fieldList) {
        this.fieldList = fieldList;
    }

    public void setStatisticList(List<List<List<String>>> statisticList) {
        this.statisticList = statisticList;
    }

    public void setGroupPlaceList(List<List<Integer>> groupPlaceList) {
        this.groupPlaceList = groupPlaceList;
    }

    public void setOutmatchModel(OutmatchModel outmatchModel) {
        this.outmatchModel = outmatchModel;
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.io.extend.ExportToExcel JD-Core Version: 0.5.4
 */