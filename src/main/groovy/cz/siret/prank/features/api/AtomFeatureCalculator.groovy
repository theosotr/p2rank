package cz.siret.prank.features.api

import groovy.transform.CompileStatic
import org.biojava.nbio.structure.Atom

/**
 * Extend this class to implement new feature that adds values to Atom feature vector.
 * Values are then projected to SAS points to SAS feature vector by P2RANK.
 *
 * Register implementation in FeatureRegistry.
 * To use feature in experiments add feature name (FeatureCalculator.getName()) to Params.extra_features.
 */
@CompileStatic
abstract class AtomFeatureCalculator extends AbstractFeatureCalculator {

    @Override
    FeatureCalculator.Type getType() {
        return FeatureCalculator.Type.ATOM
    }

    @Override
    double[] calculateForSasPoint(Atom sasPoint, SasFeatureCalculationContext context) {
        throw new UnsupportedOperationException()
    }

}
