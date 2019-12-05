/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main.draw;

import main.math.Vector3;
import main.screen.ScreenConverter;
import main.screen.ScreenCoordinates;
import main.screen.ScreenPoint;
import main.third.PointLight;
import main.third.PolyLine3D;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;


public class SimpleEdgePolygonDrawer extends ScreenGraphicsDrawer {
    PointLight pointLight;

    public SimpleEdgePolygonDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
        pointLight = new PointLight(new Vector3(0, 0, 0), 300, 10, Color.ORANGE);
    }

    /**
     * Рисует одну полилинию на графиксе.
     *
     * @param polyline полилиния
     */
    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();
        /*переводим все точки в экранные*/
        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().r2s(v));
        getGraphics().setColor(Color.BLACK);
        /*если точек меньше двух, то рисуем отдельными алгоритмами*/
        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getI(), points.get(0).getJ(), 1, 1);
            return;
        }
        /*создаём хранилище этих точек в виде двух массивов*/
        ScreenCoordinates crds = new ScreenCoordinates(points);
        double k;
        /*если линия замкнута - рисем полиго, иначе - полилинию*/
        if (polyline.isClosed()) {
            getGraphics().drawPolygon(crds.getXx(), crds.getYy(), crds.size());
            k = (getFaceDistance(polyline) * pointLight.getdIntensity()) / pointLight.getIntensity();
            if (k > 1)
                k = 0;
            getGraphics().setColor(new Color((int) (250 * k), (int) (218 * k), (int) (94 * k)));
            //getGraphics().setColor(Color.ORANGE);
            getGraphics().fillPolygon(crds.getXx(), crds.getYy(), crds.size());
        } else
        getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());
    }

    /**
     * В данной реализации возвращаем фильтр, который одобряет все полилинии.
     *
     * @return фильтр полилиний
     */
    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return new IFilter<PolyLine3D>() {
            @Override
            public boolean permit(PolyLine3D line) {
                return true;
            }
        };
    }

    /**
     * Сравниваем полилинии по среднему Z.
     *
     * @return компаратор
     */
    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;

            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }

    @Override
    public void drawPolygons() {

    }

    @Override
    public void drawCounters() {
    }

    private double getFaceDistance(PolyLine3D line){
        double min = line.getPoints().get(0).getDistance(pointLight.getPos());
        double max = 0;
        double t;
        for (Vector3 v:line.getPoints()
             ) {
            t = v.getDistance(pointLight.getPos());
            if(t>max)
                max = t;
            if(t<min)
                min = t;
        }
        return (min+max)/2;
    }
}
