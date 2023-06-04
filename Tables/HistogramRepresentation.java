package Tables;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class HistogramRepresentation extends JPanel {

    private String[] days;
    private long[] numBusiness;
    private long[] avgPrice;

    /**
     * Constructor of representation of the Histogram option
     * @param days days of the week
     * @param numBusiness number of businesses
     * @param avgPrice average price of businesses
     */
    public HistogramRepresentation(String[] days, long[] numBusiness, long[] avgPrice) {
        this.days = days;
        this.numBusiness = numBusiness;
        this.avgPrice = avgPrice;
    }

    /**
     * Gets the max value of number of businesses
     * @param numBusiness number of businesses
     * @return max value of number of businesses
     */
    public long getMaxValue(long[] numBusiness) {
        long max = 0;
        for (long business : numBusiness) {
            if (business > max) {
                max = business;
            }
        }
        return max;
    }

    /**
     * Get max value of the avgPrice per day
     * @param avgPrice avgPrice per day
     * @param numBusiness number of businesses per day
     * @return max value of the avgPrice per day
     */
    public long getMaxValueOfDay(long[] avgPrice, long[] numBusiness) {
        long max = 0;
        for (int i = 0; i < 7; i++) {
            if (avgPrice[i] / numBusiness[i] > max) {
                max = avgPrice[i] / numBusiness[i];
            }
        }
        return max;
    }


    /**
     * Paint the Histogram
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphic = (Graphics2D) g.create();
        //get scale for first column bar (number of businesses for day)
        long maxNumber = getMaxValue(numBusiness);
        long scale = maxNumber/400;
        scale = Math.max(scale, 1);
        long[] valuesScaled = new long[7];
        for (int i = 0; i < 7; i++){
            valuesScaled[i] = numBusiness[i]/scale;
        }
        //get scale for second column bar (average price of business per day)
        long maxNumber2 = getMaxValueOfDay(avgPrice, numBusiness);
        long scale2 = maxNumber2/200;
        scale2 = Math.max(scale2, 1);
        long[] valuesScaled2 = new long[7];
        for (int i = 0; i < 7; i++){
            valuesScaled2[i] = (avgPrice[i]/numBusiness[i])/scale2;
        }
        //COLUMN BAR FOR MONDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(50,740-valuesScaled[0],55, valuesScaled[0]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[0],50,770);
        graphic.drawString(String.valueOf(numBusiness[0]),60,730-valuesScaled[0]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(115,740 - valuesScaled2[0],55, valuesScaled2[0]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",125,770);
        graphic.drawString(String.valueOf(avgPrice[0]/numBusiness[0]),125,730-valuesScaled2[0]);
        //COLUMN BAR FOR TUESDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(220,740-valuesScaled[1],55, valuesScaled[1]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[1],220,770);
        graphic.drawString(String.valueOf(numBusiness[1]),230,730-valuesScaled[1]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(285,740-valuesScaled2[1],55, valuesScaled2[1]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",295,770);
        graphic.drawString(String.valueOf(avgPrice[1]/numBusiness[1]),295,730-valuesScaled2[1]);
        //COLUMN BAR FOR WEDNESDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(390,740-valuesScaled[2],55, valuesScaled[2]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[2],390,770);
        graphic.drawString(String.valueOf(numBusiness[2]),400,730-valuesScaled[2]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(455,740-valuesScaled2[2],55, valuesScaled2[2]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",465,770);
        graphic.drawString(String.valueOf(avgPrice[2]/numBusiness[2]),465,730-valuesScaled2[2]);
        //COLUMN BAR FOR THURSDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(560,740-valuesScaled[3],55, valuesScaled[3]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[3],560,770);
        graphic.drawString(String.valueOf(numBusiness[3]),570,730-valuesScaled[3]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(625,740-valuesScaled2[3],55, valuesScaled2[3]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",635,770);
        graphic.drawString(String.valueOf(avgPrice[3]/numBusiness[3]),635,730-valuesScaled2[3]);
        //COLUMN BAR FOR FRIDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(730,740-valuesScaled[4],55, valuesScaled[4]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[4],730,770);
        graphic.drawString(String.valueOf(numBusiness[4]),740,730-valuesScaled[4]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(795,740-valuesScaled2[4],55, valuesScaled2[4]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",805,770);
        graphic.drawString(String.valueOf(avgPrice[4]/numBusiness[4]),805,730-valuesScaled2[4]);
        //COLUMN BAR FOR SATURDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(900,740-valuesScaled[5],55, valuesScaled[5]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[5],900,770);
        graphic.drawString(String.valueOf(numBusiness[5]),910,730-valuesScaled[5]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(965,740-valuesScaled2[5],55, valuesScaled2[5]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",975,770);
        graphic.drawString(String.valueOf(avgPrice[5]/numBusiness[5]),975,730-valuesScaled2[5]);
        //COLUMN BAR FOR SUNDAY
        graphic.setColor(Color.RED);
        graphic.fill(new Rectangle2D.Double(1070,740-valuesScaled[6],55, valuesScaled[6]));
        graphic.setColor(Color.BLACK);
        graphic.drawString(days[6],1070,770);
        graphic.drawString(String.valueOf(numBusiness[6]),1080,730-valuesScaled[6]);
        graphic.setColor(Color.ORANGE);
        graphic.fill(new Rectangle2D.Double(1125,740-valuesScaled2[6],55, valuesScaled2[6]));
        graphic.setColor(Color.BLACK);
        graphic.drawString("Avg Price €",1135,770);
        graphic.drawString(String.valueOf(avgPrice[6]/numBusiness[6]),1135,730-valuesScaled2[6]);
    }


}