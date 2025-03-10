package cz.siret.prank.score.metrics
/**
 * Arbitrary 2D curve
 */
class Curve {

//    List<Point> points = new ArrayList<>()
//
//    static class Point {
//        double x, y
//    }

    double[] xx
    double[] yy

    Curve(double[] xx, double[] yy) {
        this.xx = xx
        this.yy = yy
    }

    static Curve create(double[] xx, double[] yy) {
        new Curve(xx, yy)
    }

    String toCSV() {
        [xx, yy].transpose().collect { '' + it[0] + ', ' + it[1] }.join('\n')
    }
}
