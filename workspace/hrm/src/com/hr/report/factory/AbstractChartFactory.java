package com.hr.report.factory;

import com.hr.report.domain.ReportDef;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;
import org.eclipse.birt.chart.model.Chart;
import org.eclipse.birt.chart.model.attribute.ChartDimension;
import org.eclipse.birt.chart.model.attribute.ColorDefinition;
import org.eclipse.birt.chart.model.attribute.impl.ColorDefinitionImpl;

public abstract class AbstractChartFactory {
    public abstract Chart createChart(ReportDef paramReportDef);

    protected final ColorDefinition getColorDefinitionByGivenString(String color) {
        ColorDefinition d = ColorDefinitionImpl.WHITE();
        if (StringUtils.isEmpty(color)) {
            return d;
        }

        if (color.matches("^(\\d{1,3},){2}\\d{1,3}")) {
            String[] rgb = color.split(",");
            if (rgb.length == 3) {
                int r = Integer.parseInt(rgb[0]);
                int g = Integer.parseInt(rgb[1]);
                int b = Integer.parseInt(rgb[2]);
                return ColorDefinitionImpl.create(r, g, b);
            }

        }

        if ("RED".equalsIgnoreCase(color))
            return ColorDefinitionImpl.RED();
        if ("BLACK".equalsIgnoreCase(color))
            return ColorDefinitionImpl.BLACK();
        if ("CREAM".equalsIgnoreCase(color))
            return ColorDefinitionImpl.CREAM();
        if ("CYAN".equalsIgnoreCase(color))
            return ColorDefinitionImpl.CYAN();
        if ("GREEN".equalsIgnoreCase(color))
            return ColorDefinitionImpl.GREEN();
        if ("GREY".equalsIgnoreCase(color))
            return ColorDefinitionImpl.GREY();
        if ("ORANGE".equalsIgnoreCase(color))
            return ColorDefinitionImpl.ORANGE();
        if ("PINK".equalsIgnoreCase(color))
            return ColorDefinitionImpl.PINK();
        if ("RED".equalsIgnoreCase(color))
            return ColorDefinitionImpl.RED();
        if ("TRANSPARENT".equalsIgnoreCase(color))
            return ColorDefinitionImpl.TRANSPARENT();
        if ("WHITE".equalsIgnoreCase(color))
            return ColorDefinitionImpl.WHITE();
        if ("YELLOW".equalsIgnoreCase(color)) {
            return ColorDefinitionImpl.YELLOW();
        }

        if (color.matches("^#[a-fA-F0-9]{6}")) {
            Pattern p = Pattern.compile("[A-F0-9]{2}", 2);
            Matcher m = p.matcher(color);
            Integer[] temp = new Integer[3];
            int i = 0;
            while (m.find()) {
                temp[(i++)] = Integer.valueOf(m.group(), 16);
            }
            return ColorDefinitionImpl.create(temp[0].intValue(), temp[1].intValue(), temp[2]
                    .intValue());
        }
        return d;
    }

    protected final ChartDimension getDimension(int dimension) {
        ChartDimension result = null;

        switch (dimension) {
        case 0:
            result = ChartDimension.TWO_DIMENSIONAL_LITERAL;
            break;
        case 1:
            result = ChartDimension.TWO_DIMENSIONAL_WITH_DEPTH_LITERAL;
            break;
        case 2:
            result = ChartDimension.THREE_DIMENSIONAL_LITERAL;
            break;
        default:
            result = ChartDimension.TWO_DIMENSIONAL_LITERAL;
        }

        return result;
    }

    protected String[] getParamsFromSelectClause(String sql) {
        return sql.split(",");
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.report.factory.AbstractChartFactory JD-Core Version: 0.5.4
 */