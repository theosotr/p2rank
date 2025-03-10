package cz.siret.prank.score

import cz.siret.prank.domain.Pocket
import cz.siret.prank.domain.Prediction
import cz.siret.prank.features.api.ProcessedItemContext

class PocketPolarityRescorer extends PocketRescorer {

    @Override
    void rescorePockets(Prediction prediction, ProcessedItemContext context) {
        prediction.pockets.each { Pocket pocket ->

            pocket.newScore = pocket.stats.polarityScore
        }
    }

}
