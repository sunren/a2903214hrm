package com.hr.util.output;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class StatisticBgColor {
    private static final List<Color> colorList = new ArrayList();

    protected static void setColorList() {
        if (colorList.isEmpty()) {
            Color color0 = new Color(6526703);
            colorList.add(color0);
            Color color1 = new Color(16772872);
            colorList.add(color1);
            Color color2 = new Color(7066483);
            colorList.add(color2);
            Color color3 = new Color(16221811);
            colorList.add(color3);
            Color color4 = new Color(13408767);
            colorList.add(color4);
        }
    }

    public static Color getBgColor(int layer) {
        if (colorList.isEmpty()) {
            setColorList();
        }
        int size = colorList.size();
        if (layer < size) {
            return (Color) colorList.get(layer);
        }
        return (Color) colorList.get(size - 1);
    }
}

/*
 * Location: D:\Program Files\365HRM\web\WEB-INF\lib\hr.jar Qualified Name:
 * com.hr.util.output.StatisticBgColor JD-Core Version: 0.5.4
 */