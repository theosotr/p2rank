package cz.siret.prank.collectors

import cz.siret.prank.domain.Pocket
import cz.siret.prank.domain.PredictionPair
import cz.siret.prank.features.FeatureExtractor
import cz.siret.prank.features.FeatureVector
import cz.siret.prank.features.api.ProcessedItemContext
import cz.siret.prank.score.criteria.IdentificationCriterium
import cz.siret.prank.utils.CollectionUtils
import groovy.util.logging.Slf4j

@Slf4j
class PocketFingerprintCollector extends VectorCollector  {

    FeatureExtractor evalFactory
    IdentificationCriterium assessor

    PocketFingerprintCollector(FeatureExtractor evalFactory, IdentificationCriterium assessor) {
        this.assessor = assessor
        this.evalFactory = evalFactory
    }

    @Override
    Result collectVectors(PredictionPair pair, ProcessedItemContext context) {
        Result res = new Result()

        pair.getCorrectlyPredictedPockets(assessor).each { Pocket pocket ->
            processPocket(pair, pocket, true, res)
        }

        CollectionUtils.head(3,
        pair.getFalsePositivePockets(assessor)).each { Pocket pocket ->
            processPocket(pair, pocket, false, res)
        }

        return res
    }

    private processPocket(PredictionPair pair, Pocket pocket, boolean isPositive, Result res ) {
        try {
            FeatureExtractor eval = evalFactory.createInstanceForPocket(pair.queryProtein.structure, pocket)
            FeatureVector prop = eval.calcFingerprint(pocket.vornoiCenters)

            if (isPositive) {
                res.add( [*prop.vector, 1] )
                res.positives++
            } else {
                res.add( [*prop.vector, 0] )
                res.negatives++
            }

        } catch (Exception e) {
            log.error "skiping extraction from pocket:$pocket.name", e
        }
    }

    @Override
    List<String> getHeader() {
        return evalFactory.vectorHeader + "is_true_pocket"
    }

}