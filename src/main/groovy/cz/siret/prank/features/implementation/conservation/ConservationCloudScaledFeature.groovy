package cz.siret.prank.features.implementation.conservation

import cz.siret.prank.domain.Protein
import cz.siret.prank.features.api.ProcessedItemContext
import cz.siret.prank.features.api.SasFeatureCalculationContext
import cz.siret.prank.features.api.SasFeatureCalculator
import cz.siret.prank.geom.Atoms
import cz.siret.prank.program.params.Parametrized
import cz.siret.prank.utils.PerfUtils
import groovy.transform.CompileStatic
import org.biojava.nbio.structure.Atom

/**
 * Simple single value SAS feature that adds "conservation" of protein surface to SAS feature
 * vector.
 * Conservation scores from residue from protrusion radius are scaled using 1/L2 metrics
 */
@CompileStatic
class ConservationCloudScaledFeature extends SasFeatureCalculator implements Parametrized {

    static final String NAME = "conservationcloudscaled"

    @Override
    String getName() { NAME }

    @Override
    void preProcessProtein(Protein protein, ProcessedItemContext itemContext) {
        // Check if conservation is already loaded.
        if (!protein.secondaryData.getOrDefault(ConservationScore.conservationLoadedKey, false)) {
            // Load conservation score.
            protein.loadConservationScores(itemContext)
        }
    }

    @Override
    double[] calculateForSasPoint(Atom sasPoint, SasFeatureCalculationContext context) {

        ConservationScore score = (ConservationScore) context.protein.secondaryData.get(ConservationScore.conservationScoreKey)
        if (score == null) {
            return [0.0] as double[]
        }

        Atoms surroundingAtoms = context.extractor.deepLayer.cutoffAroundAtom(sasPoint, params.protrusion_radius)
        double value = 0.0;
        for (Atom atom : surroundingAtoms) {
            double scale = 1.0 / PerfUtils.sqrDist(sasPoint.coords, atom.coords);
            value += scale * score.getScoreForResidue(atom.getGroup().getResidueNumber());
        }
        return [value] as double[]
    }

}
