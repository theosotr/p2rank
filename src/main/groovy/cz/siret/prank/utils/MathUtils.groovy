package cz.siret.prank.utils

import groovy.transform.CompileStatic

import static cz.siret.prank.utils.StatSample.newStatSample

@CompileStatic
class MathUtils {

    static final double SQRT2PI = Math.sqrt(2*Math.PI)

    static double gauss(double x, double sigma) {
        return gauss(x, 1/(sigma*SQRT2PI), sigma )
    }

    static double gauss(double x, double a, double c) {
        return a*Math.exp(-(x*x)/(2*c*c))
    }

    static double gaussNorm(double x, double sigma) {
        return gauss(x,sigma)/gauss(0,sigma)
    }

    static int ranndomInt() {
        new Random().nextInt()
    }

//===========================================================================================================//

    static double stddev(List<Double> sample) {
        newStatSample(sample).stddev
    }


}
